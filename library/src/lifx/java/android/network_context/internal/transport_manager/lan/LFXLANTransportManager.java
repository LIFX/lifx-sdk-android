//
//  LFXLANTransportManager.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.network_context.internal.transport_manager.lan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import lifx.java.android.entities.internal.LFXGatewayDescriptor;
import lifx.java.android.entities.internal.LFXMessage;
import lifx.java.android.entities.internal.LFXSiteID;
import lifx.java.android.entities.internal.structle.LxProtocolDevice.Service;
import lifx.java.android.network_context.internal.transport_manager.LFXTransportManager;
import lifx.java.android.network_context.internal.transport_manager.gateway_connection.LFXGatewayConnection;
import lifx.java.android.network_context.internal.transport_manager.gateway_connection.LFXGatewayConnection.LFXGatewayConnectionListener;
import lifx.java.android.network_context.internal.transport_manager.gateway_connection.LFXGatewayConnection.LFXGatewayConnectionState;
import lifx.java.android.network_context.internal.transport_manager.lan.gateway_discovery.LFXGatewayDiscoveryController;
import lifx.java.android.network_context.internal.transport_manager.lan.gateway_discovery.LFXGatewayDiscoveryTableEntry;
import lifx.java.android.network_context.internal.transport_manager.lan.gateway_discovery.LFXGatewayDiscoveryController.LFXGatewayDiscoveryControllerListener;
import lifx.java.android.network_context.internal.transport_manager.lan.gateway_discovery.LFXGatewayDiscoveryController.LFXGatewayDiscoveryMode;
import lifx.java.android.util.LFXLog;
import lifx.java.android.util.LFXNetworkUtils;
import lifx.java.android.util.LFXTimerUtils;

public class LFXLANTransportManager extends LFXTransportManager implements LFXGatewayConnectionListener, LFXGatewayDiscoveryControllerListener {
    private final static String TAG = LFXLANTransportManager.class.getSimpleName();

    private LFXGatewayConnection broadcastUDPConnection;
    private LFXGatewayConnection peerToPeerUDPConnection;

    private LFXGatewayDiscoveryController gatewayDiscoveryController;

    private HashMap<LFXGatewayDescriptor, LFXGatewayConnection> gatewayConnections;

    private boolean enabled = true;

    public void disconnect() {
        enabled = false;

        broadcastUDPConnection.disconnect();
        broadcastUDPConnection.setListener(null);
        peerToPeerUDPConnection.disconnect();
        peerToPeerUDPConnection.setListener(null);
        gatewayDiscoveryController.shutDown();

        for (LFXGatewayConnection aGatewayConnection : gatewayConnections.values()) {
            aGatewayConnection.disconnect();
            aGatewayConnection.setListener(null);
        }

        gatewayConnections.clear();
    }

    public LFXLANTransportManager() {
        super();
        LFXLog.d(TAG, "LFXLANTransportManager() - Constructor");
    }

    public void connect() {
        enabled = true;

        gatewayDiscoveryController = LFXGatewayDiscoveryController.getGatewayDiscoveryControllerWithLANTransportManager(this, this);
        gatewayConnections = new HashMap<LFXGatewayDescriptor, LFXGatewayConnection>();

        LFXLog.d(TAG, "connect() - Setup UDP Connections");
        setupBroadcastUDPConnection();
        setupPeerToPeerUDPConnection();

        LFXLog.d(TAG, "connect() - Start Discovery");
        gatewayDiscoveryController.setDiscoveryMode(LFXGatewayDiscoveryMode.ACTIVELY_SEARCHING);
        gatewayDiscoveryController.sendGatewayDiscoveryMessage();
    }

    public void setupBroadcastUDPConnection() {
        LFXLog.d(TAG, "setupBroadcastUDPConnection()");
        if(broadcastUDPConnection!=null) {
            broadcastUDPConnection.disconnect();
            broadcastUDPConnection.setListener(null);
        }
        broadcastUDPConnection = LFXGatewayConnection.getGatewayConnectionWithGatewayDescriptor(LFXGatewayDescriptor.getBroadcastGatewayDescriptor(), this);
        broadcastUDPConnection.connect();
    }

    public void setupPeerToPeerUDPConnection() {
        LFXLog.d(TAG, "setupPeerToPeerUDPConnection()");
        if(peerToPeerUDPConnection!=null) {
            peerToPeerUDPConnection.disconnect();
            peerToPeerUDPConnection.setListener(null);
        }
        peerToPeerUDPConnection = LFXGatewayConnection.getGatewayConnectionWithGatewayDescriptor(LFXGatewayDescriptor.getClientPeerToPeerGatewayDescriptor(), this);
        peerToPeerUDPConnection.connect();
    }

    public void connectionStatesDidChange() {
        if (enabled) {
            LFXLog.d(TAG, "connectionStatesDidChange()");

            boolean newIsConnected = false;
            for (LFXGatewayConnection aGatewayConnection : gatewayConnections.values()) {
                LFXLog.d(TAG,"connectionStatesDidChange() - "+aGatewayConnection.getGatewayDescriptor().toString() + " : " + aGatewayConnection.getConnectionState().toString());

                if (aGatewayConnection.getConnectionState() == LFXGatewayConnectionState.CONNECTED) {
                    newIsConnected = true;
                }
            }

            setIsConnected(newIsConnected);

            gatewayDiscoveryController.setDiscoveryMode(isConnected() ? LFXGatewayDiscoveryMode.NORMAL : LFXGatewayDiscoveryMode.ACTIVELY_SEARCHING);
        }
    }

    public void sendMessage(LFXMessage message) {
        LFXLog.i(TAG, "sendMessage() - " + message.getType().toString());
        if (message.getPath().getSiteID().isZeroSite()) {
            for (String aGatewayHost : getGatewayHosts()) {
                //LFXGatewayConnection tcpConnection = getGatewayConnectionForHost(aGatewayHost, Service.LX_PROTOCOL_DEVICE_SERVICE_TCP);
                LFXGatewayConnection udpConnection = getGatewayConnectionForHost(aGatewayHost, Service.LX_PROTOCOL_DEVICE_SERVICE_UDP);

                if(udpConnection!=null) {
                    sendMessageOnConnection(message, udpConnection);
                }
                else {
                    LFXLog.e(TAG,"sendMessage() - No connection?");
                }


// CAKEY123445 - No TCP Support so removed transport

//                ArrayList<LFXGatewayConnection> connections = new ArrayList<LFXGatewayConnection>();
//
//                if (tcpConnection != null) {
//                    connections.add(tcpConnection);
//                }
//
//                if (udpConnection != null) {
//                    connections.add(udpConnection);
//                }
//
//                LFXGatewayConnection connectionToUse = null;
//
//                if (connections.size() > 0) {
//                    connectionToUse = connections.get(0);
//                }
//
//                if (connectionToUse != null) {
//                    sendMessageOnConnection(message, connectionToUse);
//                }
            }
        }
        else {
            boolean messageWasSent = false;
            for (String aGatewayHost : getGatewayHostsForSiteID(message.getPath().getSiteID())) {
                //LFXGatewayConnection tcpConnection = getGatewayConnectionForHost(aGatewayHost, Service.LX_PROTOCOL_DEVICE_SERVICE_TCP);
                LFXGatewayConnection udpConnection = getGatewayConnectionForHost(aGatewayHost, Service.LX_PROTOCOL_DEVICE_SERVICE_UDP);

                if(udpConnection!=null) {
                    sendMessageOnConnection(message, udpConnection);
                    messageWasSent = true;
                }

// CAKEY123445 - No TCP Support so removed transport

//                ArrayList<LFXGatewayConnection> connections = new ArrayList<LFXGatewayConnection>();
//                if (message.prefersUDPOverTCP()) {
//                    if (udpConnection != null) {
//                        connections.add(udpConnection);
//                    }
//
//                    if (tcpConnection != null) {
//                        connections.add(tcpConnection);
//                    }
//                } else {
//                    if (tcpConnection != null) {
//                        connections.add(tcpConnection);
//                    }
//
//                    if (udpConnection != null) {
//                        connections.add(udpConnection);
//                    }
//                }
//
//                LFXGatewayConnection connectionToUse = null;
//
//                if (connections.size() > 0) {
//                    connectionToUse = connections.get(0);
//                }
//
//                if (connectionToUse != null) {
//                    sendMessageOnConnection(message, connectionToUse);
//                    messageWasSent = true;
//                }
            }

            if (!messageWasSent) {
                sendMessageOnConnection(message, broadcastUDPConnection);
            }
        }

        sendObserverCallbacksForMessage(message);
    }

    public void sendBroadcastUDPMessage(LFXMessage message) {
        sendMessageOnConnection(message, broadcastUDPConnection);
        sendObserverCallbacksForMessage(message);
    }

    public void sendMessageOnConnection(LFXMessage message, LFXGatewayConnection connection) {
        if (connection != null) {
            connection.sendMessage((LFXMessage) message.clone());
        }
    }

    public ArrayList<LFXGatewayDescriptor> getGatewayDescriptorsForSiteID(LFXSiteID siteID) {
        ArrayList<LFXGatewayDescriptor> descriptors = new ArrayList<LFXGatewayDescriptor>();

        for (LFXGatewayDescriptor key : gatewayConnections.keySet()) {
            if (key.getPath().getSiteID().equals(siteID)) {
                descriptors.add(key);
            }
        }

        return descriptors;
    }

    public Set<String> getGatewayHostsForSiteID(LFXSiteID siteID) {
        ArrayList<LFXGatewayDescriptor> descriptors = getGatewayDescriptorsForSiteID(siteID);
        ArrayList<String> hosts = new ArrayList<String>();
        for (LFXGatewayDescriptor aDescriptor : descriptors) {
            hosts.add(aDescriptor.getHost());
        }

        return new HashSet<String>(hosts);
    }

    public Set<String> getGatewayHosts() {
        ArrayList<String> hosts = new ArrayList<String>();

        for (LFXGatewayDescriptor key : gatewayConnections.keySet()) {
            hosts.add(key.getHost());
        }

        return new HashSet<String>(hosts);
    }

    public LFXGatewayConnection getGatewayConnectionForHost(String host, Service service) {
        for (LFXGatewayConnection aConnection : gatewayConnections.values()) {
            if (aConnection.getGatewayDescriptor().getHost().equals(host) &&
                    aConnection.getGatewayDescriptor().getService() == service) {
                return aConnection;
            }
        }

        return null;
    }

    public void gatewayConnectionDidConnect(LFXGatewayConnection connection) {
        connectionStatesDidChange();
        listener.transportManagerDidConnectToGateway(this, connection.getGatewayDescriptor());
    }

    public void gatewayConnectionDidDisconnectWithError(LFXGatewayConnection connection, String error) {
        if (connection == broadcastUDPConnection) {
            broadcastUDPConnection.disconnect();
            broadcastUDPConnection.setListener(null);
            broadcastUDPConnection=null;

            if (enabled) {
                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        setupBroadcastUDPConnection();
                    }
                };

                LFXTimerUtils.scheduleDelayedTask(task, 1000);
                LFXLog.i(TAG, "gatewayConnectionDidDisconnectWithError() - Removed broadcastUDPConnection & deferred restart");
            }
            else {
                LFXLog.i(TAG,"gatewayConnectionDidDisconnectWithError() - Removed broadcastUDPConnection");
            }
        } else if (connection == peerToPeerUDPConnection) {
            peerToPeerUDPConnection.disconnect();
            peerToPeerUDPConnection.setListener(null);
            peerToPeerUDPConnection=null;

            if (enabled) {
                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        setupPeerToPeerUDPConnection();
                    }
                };

                LFXTimerUtils.scheduleDelayedTask(task, 3000);
                LFXLog.i(TAG, "gatewayConnectionDidDisconnectWithError() - Removed peerToPeerUDPConnection & deferred restart");
            }
            else {
                LFXLog.i(TAG,"gatewayConnectionDidDisconnectWithError() - Removed peerToPeerUDPConnection");
            }
        } else {
            gatewayConnections.remove(connection.getGatewayDescriptor());
            connection.disconnect();
            connection.setListener(null);
            LFXLog.i(TAG, "gatewayConnectionDidDisconnectWithError() - Removed other???");
        }

        connectionStatesDidChange();
        listener.transportManagerDidDisconnectFromGateway(this, connection.getGatewayDescriptor());
    }

    public void gatewayConnectionDidReceiveMessageFromHost(LFXGatewayConnection connection, LFXMessage message, String host) {
        host = LFXNetworkUtils.getIPv4StringByStrippingIPv6Prefix(host);

        message.setGatewayDescriptor(connection.getGatewayDescriptor());
        message.setSourceNetworkHost(host);
        sendObserverCallbacksForMessage(message);
    }

    public void gatewayDiscoveryControllerDidUpdateEntry(LFXGatewayDiscoveryController table, LFXGatewayDiscoveryTableEntry tableEntry, boolean entryIsNew) {
        LFXGatewayDescriptor gateway = tableEntry.getGatewayDescriptor();

        if (gateway.getPort() == 0) {
            return;
        }

        LFXGatewayConnection existingConnection = null;

        for (LFXGatewayDescriptor aDescriptor : gatewayConnections.keySet()) {
            if (aDescriptor.toString().equals(gateway.toString())) {
                existingConnection = gatewayConnections.get(aDescriptor);
            }
        }

        if (existingConnection == null) {
            LFXGatewayConnection newConnection = LFXGatewayConnection.getGatewayConnectionWithGatewayDescriptor(gateway, this);
            newConnection.connect();
            gatewayConnections.put(gateway, newConnection);
            connectionStatesDidChange();
        }
    }
}
