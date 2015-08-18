//
//  LFXUDPGatewayConnection.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.network_context.internal.transport_manager.gateway_connection;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import lifx.java.android.constant.LFXSDKConstants;
import lifx.java.android.entities.internal.LFXGatewayDescriptor;
import lifx.java.android.entities.internal.LFXMessage;
import lifx.java.android.entities.internal.structle.LxProtocol.Type;
import lifx.java.android.internal.LFXWiFiObserver;
import lifx.java.android.network_context.internal.transport_manager.gateway_connection.LFXSocketGeneric.SocketMessage;
import lifx.java.android.network_context.internal.transport_manager.gateway_connection.LFXSocketGeneric.SocketMessageListener;
import lifx.java.android.network_context.internal.transport_manager.gateway_connection.LFXSocketGeneric.SocketState;
import lifx.java.android.network_context.internal.transport_manager.gateway_connection.LFXSocketGeneric.SocketStateListener;
import lifx.java.android.util.LFXLog;
import lifx.java.android.util.LFXNetworkUtils;
import lifx.java.android.util.LFXTimerUtils;

public class LFXUDPGatewayConnection extends LFXGatewayConnection implements SocketMessageListener, SocketStateListener {
    private final static String TAG = LFXUDPGatewayConnection.class.getSimpleName();

    private Queue<LFXMessage> messageOutbox;
    private LFXSocketGeneric socket;
    private Timer outboxTimer;
    private Timer heartbeatTimer;
    private Timer idleTimeoutTimer;

    private Runnable getHeartbeatTimerTask() {
        Runnable heartbeatTimerTask = new TimerTask() {
            public void run() {
                heartbeatTimerDidFire();
            }
        };

        return heartbeatTimerTask;
    }

    private Runnable getIdleTimerTask() {
        Runnable idleTimerTask = new TimerTask() {
            public void run() {
                idleTimeoutTimerDidFire();
            }
        };

        return idleTimerTask;
    }

    private Runnable getOutBoxTimerTask() {
        Runnable outBoxTimerTask = new TimerTask() {
            public void run() {
                sendNextMessageFromOutbox();
            }
        };

        return outBoxTimerTask;
    }

    public LFXUDPGatewayConnection(LFXGatewayDescriptor gatewayDescriptor, LFXGatewayConnectionListener listener) {
        super(gatewayDescriptor, listener);
        setConnectionState(LFXGatewayConnectionState.NOT_CONNECTED);
        messageOutbox = new LinkedList<LFXMessage>();
        outboxTimer = LFXTimerUtils.getTimerTaskWithPeriod(getOutBoxTimerTask(), LFXSDKConstants.LFX_UDP_MESSAGE_SEND_RATE_LIMIT_INTERVAL, false, "SendRateLimitTimer");
        socket = new LFXSocketUDP();
        heartbeatTimer = LFXTimerUtils.getTimerTaskWithPeriod(getHeartbeatTimerTask(), LFXSDKConstants.LFX_UDP_HEARTBEAT_INTERVAL, false, "UDPHeartbeatTimer");
        resetIdleTimeoutTimer();
        LFXLog.d(TAG, "LFXUDPGatewayConnection() - Constructor, HeartBeat, SendRate & Idle Timer Tasks");
    }

    public boolean isBroadcastConnection() {
        return getGatewayDescriptor().getHost().equals(LFXWiFiObserver.getWiFiBroadcastAddress());
    }

    public void heartbeatTimerDidFire() {
        if (isBroadcastConnection()) {
            return;
        }

        if (getConnectionState() == LFXGatewayConnectionState.CONNECTED) {
            if(getGatewayDescriptor().getPath()!=null) {
                LFXMessage message = LFXMessage.messageWithTypeAndPath(Type.LX_PROTOCOL_DEVICE_GET_PAN_GATEWAY, getGatewayDescriptor().getPath());
                sendMessage(message);
            }
            else {
                LFXLog.e(TAG,"heartbeatTimerDidFire() - getGatewayDescriptor().getPath()==null");
            }
        }
    }

    public void connect() {
        LFXLog.d(TAG, "connect() - ConnectionState: " + getConnectionState());
        if (getConnectionState() != LFXGatewayConnectionState.NOT_CONNECTED) {
            return;
        }

        LFXLog.d(TAG, "connect() - Connecting UDP Socket " + getGatewayDescriptor().getHost() + ":" + getGatewayDescriptor().getPort());

        try {
            socket.addMessageListener(this);
            socket.addStateListener(this);
            socket.connect(InetAddress.getByName(getGatewayDescriptor().getHost()).getAddress(), getGatewayDescriptor().getPort());
            setConnectionState(LFXGatewayConnectionState.CONNECTED);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private boolean shouldIgnoreDataFromHost(String host) {
        String localHost = LFXNetworkUtils.getLocalHostAddress();
        return localHost.equals(host);
    }

    public void sendData(byte[] data) {
        socket.sendMessage(new SocketMessage(data));
    }

    public void sendMessage(LFXMessage message) {
        LinkedList<LFXMessage> outboxAsLinkedList = (LinkedList<LFXMessage>) messageOutbox;

        for (int outboxIndex = 0; outboxIndex < outboxAsLinkedList.size(); outboxIndex++) {
            if (newMessageMakesQueuedMessageRedundant(message, outboxAsLinkedList.get(outboxIndex))) {
                outboxAsLinkedList.remove(outboxIndex);
                outboxAsLinkedList.add(outboxIndex, message);
                logMessageOutboxSize();
                return;
            }
        }

        messageOutbox.add(message);
        logMessageOutboxSize();
    }

    private void sendNextMessageFromOutbox() {
        LFXMessage nextMessage = messageOutbox.poll();

        if (nextMessage == null) {
            return;
        }

        byte[] messageData = nextMessage.getMessageDataRepresentation();

        try {
            socket.sendMessage(new SocketMessage(messageData, InetAddress.getByName(getGatewayDescriptor().getHost()).getAddress(), getGatewayDescriptor().getPort()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, Integer> types = new HashMap<String, Integer>();

    public void logMessageOutboxSize() {
        if (messageOutbox.size() > 10) {
            types.clear();

            LinkedList<LFXMessage> outboxAsLinkedList = (LinkedList<LFXMessage>) messageOutbox;

            for (int i = 0; i < messageOutbox.size(); i++) {
                String key = outboxAsLinkedList.get(i).getType().toString();
                Integer bla = types.get(key);

                if (bla == null) {
                    bla = 0;
                }

                bla = bla + 1;

                types.put(key, bla);
            }

            LFXLog.w(TAG, "logMessageOutboxSize() - UDP " + getGatewayDescriptor().getHost() + " Message Outbox backlog is " + messageOutbox.size());
            for (String aKey : types.keySet()) {
                LFXLog.d(TAG,"logMessageOutboxSize()"+aKey + ": " + types.get(aKey) + ", ");
            }

        }
    }

    public void resetIdleTimeoutTimer() {
        if (isBroadcastConnection()) {
            return;
        }

        if (idleTimeoutTimer != null) {
            idleTimeoutTimer.cancel();
            idleTimeoutTimer.purge();
        }

        idleTimeoutTimer = LFXTimerUtils.getTimerTaskWithPeriod(getIdleTimerTask(), LFXSDKConstants.LFX_UDP_IDLE_TIMEOUT_INTERVAL, false, "IdleTimeoutTimer");
    }

    public void idleTimeoutTimerDidFire() {
        LFXLog.w(TAG, "idleTimeoutTimerDidFire() - Occurred on UDP Connection " + toString() + ", disconnecting");
        setConnectionState(LFXGatewayConnectionState.NOT_CONNECTED);
        if(getListener()!=null) getListener().gatewayConnectionDidDisconnectWithError(this, null);
    }

    private void udpSocketDidReceiveDataFromAddressWithFilterContext(LFXSocketGeneric socket, byte[] data, byte[] address, Object filterContext) {
        InetAddress hostAddress = null;
        try {
            hostAddress = InetAddress.getByAddress(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (hostAddress == null) {
            return;
        }

        String host = hostAddress.getHostAddress();

        host = LFXNetworkUtils.getIPv4StringByStrippingIPv6Prefix(host);

        if (!host.equals(getGatewayDescriptor().getHost()) && !isBroadcastConnection()) {
            return;
        }

        if (shouldIgnoreDataFromHost(host)) {
            return;
        }

        LFXMessage message = LFXMessage.messageWithMessageData(data);

        if (message == null) {
            LFXLog.e(TAG, "udpSocketRx() - Couldn't create message from data: " + Arrays.toString(data));
            return;
        }
        else {
            LFXLog.i(TAG, "udpSocketRx() - Got: " + message.getType().toString());
        }

        if (getListener() != null) {
            // NOTE: this is here to get rid of the duplicates that occur due to both the broadcast
            // UDP listener and gateway specific UDP listeners "receiving" the message.
            if (getGatewayDescriptor().getHost().equals(LFXWiFiObserver.getWiFiBroadcastAddress())) {
                getListener().gatewayConnectionDidReceiveMessageFromHost(this, message, host);
            }
        }

        resetIdleTimeoutTimer();
    }

    public void udpSocketDidCloseWithError(LFXSocketGeneric socket, String error) {
        setConnectionState(LFXGatewayConnectionState.NOT_CONNECTED);
        if(getListener()!=null) getListener().gatewayConnectionDidDisconnectWithError(this, error);
    }

    public void udpSocketDidNotConnect(LFXSocketGeneric socket, String error) {
        setConnectionState(LFXGatewayConnectionState.NOT_CONNECTED);
        if(getListener()!=null) getListener().gatewayConnectionDidDisconnectWithError(this, error);
    }

    @Override
    public void disconnect() {
        socket.close();
        if(heartbeatTimer!=null) {
            heartbeatTimer.cancel();
            heartbeatTimer.purge();
            heartbeatTimer = null;
        }
        if(outboxTimer!=null) {
            outboxTimer.cancel();
            outboxTimer.purge();
            outboxTimer = null;
        }
        if (idleTimeoutTimer != null) {
            idleTimeoutTimer.cancel();
            idleTimeoutTimer.purge();
            idleTimeoutTimer=null;
        }
    }

    @Override
    public void notifyMessageReceived(SocketMessage message) {
        udpSocketDidReceiveDataFromAddressWithFilterContext(null, message.getMessageData(), message.getIpAddress(), null);
    }

    @Override
    public void notifySocketStateChanged(LFXSocketGeneric socket, SocketState state) {
        switch (state) {
            case CONNECTED:
                if(getListener()!=null) getListener().gatewayConnectionDidConnect(this);
                break;

            case CONNECTING:
                break;

            case DISCONNECTED:
                if(getListener()!=null) getListener().gatewayConnectionDidDisconnectWithError(this, "");
                break;
        }
    }
}
