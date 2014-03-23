package lifx.java.android.network_context.internal.transport_manager.gateway_connection;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import lifx.java.android.constant.LFXSDKConstants;
import lifx.java.android.entities.internal.LFXGatewayDescriptor;
import lifx.java.android.entities.internal.LFXMessage;
import lifx.java.android.entities.internal.structle.LxProtocol.Type;
import lifx.java.android.network_context.internal.transport_manager.gateway_connection.LFXSocketGeneric.SocketMessage;
import lifx.java.android.network_context.internal.transport_manager.gateway_connection.LFXSocketGeneric.SocketMessageListener;
import lifx.java.android.network_context.internal.transport_manager.gateway_connection.LFXSocketGeneric.SocketState;
import lifx.java.android.network_context.internal.transport_manager.gateway_connection.LFXSocketGeneric.SocketStateListener;
import lifx.java.android.util.LFXLog;
import lifx.java.android.util.LFXNetworkUtils;
import lifx.java.android.util.LFXTimerUtils;
import lifx.java.internal.LFXWiFiObserver;

public class LFXUDPGatewayConnection extends LFXGatewayConnection implements SocketMessageListener, SocketStateListener
{
	private final static boolean LOG_FILE_ENABLED = false;

	private Queue<LFXMessage> messageOutbox;
	private LFXSocketGeneric socket;
	private Timer outboxTimer;
	private Timer heartbeatTimer;
	private Timer idleTimeoutTimer;
	
	private Runnable getHeartbeatTimerTask()
	{
		Runnable heartbeatTimerTask = new TimerTask()
		{
			public void run()
			{
				//System.out.println( "Heartbeat fired: " + getGatewayDescriptor().toString() + ", " + this);
				heartbeatTimerDidFire();
			}
		};
		
		return heartbeatTimerTask;
	}
	
	private Runnable getIdleTimerTask()
	{
		Runnable idleTimerTask = new TimerTask()
		{
			public void run()
			{
				idleTimeoutTimerDidFire();
			}
		};
		
		return idleTimerTask;
	}
	
	private Runnable getOutBoxTimerTask()
	{
		Runnable outBoxTimerTask = new TimerTask() 
		{
			public void run() 
			{
				sendNextMessageFromOutbox();
			}	
		};
		
		return outBoxTimerTask;
	}
	
	public LFXUDPGatewayConnection( LFXGatewayDescriptor gatewayDescriptor, LFXGatewayConnectionListener listener)
	{
		super( gatewayDescriptor, listener);
		//LFXLog.Debug( "UDP Gateway Initializer");
		setConnectionState(LFXGatewayConnectionState.NOT_CONNECTED);
		messageOutbox = new LinkedList<LFXMessage>();
		//System.out.println( "Making Outbox Timer task.");
		outboxTimer = LFXTimerUtils.getTimerTaskWithPeriod( getOutBoxTimerTask(), LFXSDKConstants.LFX_UDP_MESSAGE_SEND_RATE_LIMIT_INTERVAL, false);
		socket = new LFXSocketUDP();
		//System.out.println( "Making Heartbeat Timer task.");
		heartbeatTimer = LFXTimerUtils.getTimerTaskWithPeriod( getHeartbeatTimerTask(), LFXSDKConstants.LFX_UDP_HEARTBEAT_INTERVAL, false);
		resetIdleTimeoutTimer();
	}

	public boolean isBroadcastConnection()
	{
		return getGatewayDescriptor().getHost().equals( LFXWiFiObserver.getWiFiBroadcastAddress());
	}

	public void heartbeatTimerDidFire()
	{
		if( isBroadcastConnection()) 
		{
			return;
		}
		
		if( getConnectionState() == LFXGatewayConnectionState.CONNECTED)
		{
			LFXMessage message = LFXMessage.messageWithTypeAndPath( Type.LX_PROTOCOL_DEVICE_GET_PAN_GATEWAY, getGatewayDescriptor().getPath());
			sendMessage( message);
		}
	}

	public void connect()
	{
		LFXLog.Debug( "ConnectionState: " + getConnectionState());
		if( getConnectionState() != LFXGatewayConnectionState.NOT_CONNECTED)
		{
			return;
		}
		
		LFXLog.Verbose( "Connecting UDP Socket " + getGatewayDescriptor().getHost() + ":" + getGatewayDescriptor().getPort());
		String error = null;
		
//		InetAddress.getByName( getGatewayDescriptor().getHost());
		
		try
		{
			socket.addMessageListener( this);
			socket.addStateListener( this);
			socket.connect( InetAddress.getByName( getGatewayDescriptor().getHost()).getAddress(), getGatewayDescriptor().getPort());
			setConnectionState( LFXGatewayConnectionState.CONNECTED);
		} 
		catch( UnknownHostException e)
		{
			e.printStackTrace();
		}
		
//	    [self.socket bindToPort:self.gatewayDescriptor.port error:&error];
//		if( error != null)
//		{
//			[self.delegate gatewayConnection:self didDisconnectWithError:error];
//			return;
//		}
//		
//		error = null;
//	    [self.socket beginReceiving:&error];
//		if( error != null)
//		{
//			[self.delegate gatewayConnection:self didDisconnectWithError:error];
//			return;
//		}
//		
//		if ([[self.gatewayDescriptor.host componentsSeparatedByString:@"."].lastObject isEqualToString:@"255"])
//		{
//			error = nil;
//			[self.socket enableBroadcast:YES error:nil];
//			if (error)
//			{
//				[self.delegate gatewayConnection:self didDisconnectWithError:error];
//				return;
//			}
//		}
//
	}

	private boolean shouldIgnoreDataFromHost( String host)
	{
	    String localHost = LFXNetworkUtils.getLocalHostAddress();//[[UIDevice currentDevice] lfx_bestIPAddress];
		return localHost.equals( host);
	}

	public void sendData( byte[] data)
	{
		socket.sendMessage( new SocketMessage( data));
	}

	public void sendMessage( LFXMessage message)
	{
		LinkedList<LFXMessage> outboxAsLinkedList = (LinkedList<LFXMessage>) messageOutbox;
		
		for( int outboxIndex = 0; outboxIndex < outboxAsLinkedList.size(); outboxIndex++)
		{
			if( newMessageMakesQueuedMessageRedundant( message, outboxAsLinkedList.get( outboxIndex)))
			{
				outboxAsLinkedList.remove( outboxIndex);
				outboxAsLinkedList.add( outboxIndex, message);
				//System.out.println( message.getPath().toString());
				logMessageOutboxSize();
				return;
			}
		}
		
		messageOutbox.add( message);
		//System.out.println( message.getPath().toString());
		logMessageOutboxSize();
	}

	private void sendNextMessageFromOutbox()
	{
		LFXMessage nextMessage = messageOutbox.poll();
		
		if( nextMessage == null) 
		{
			return;
		}
		
		//System.out.println( "SEND MESSAGE: " + nextMessage.getType());
		
		byte[] messageData = nextMessage.getMessageDataRepresentation();
		//LFXLogVerbose(@"Sending message on %@ on connection %@:%hu %@", self.gatewayDescriptor.protocolString, self.gatewayDescriptor.host, self.gatewayDescriptor.port, nextMessage.niceLoggingDescription);
		//socket.sendData( messageData, getGatewayDescriptor().getHost(), getGatewayDescriptor().getPort());
		try
		{
			//rintln( getGatewayDescriptor().getHost());
			socket.sendMessage( new SocketMessage( messageData, InetAddress.getByName( getGatewayDescriptor().getHost()).getAddress(), getGatewayDescriptor().getPort()));
		} 
		catch( UnknownHostException e)
		{
			e.printStackTrace();
		}
	}

	private HashMap<String,Integer> types = new HashMap<String,Integer>();
	public void logMessageOutboxSize()
	{
		if( messageOutbox.size() > 10)
		{
			types.clear();

			LinkedList<LFXMessage> outboxAsLinkedList = (LinkedList<LFXMessage>) messageOutbox;
			
			for( int i = 0; i < messageOutbox.size(); i++)
			{
				String key = outboxAsLinkedList.get( i).getType().toString();
				Integer bla = types.get( key);
				
				if( bla == null)
				{
					bla = 0;
				}
				
				bla = bla + 1;
				
				types.put( key, bla);
			}
			
			LFXLog.Warn( "UDP " + getGatewayDescriptor().getHost() + " Message Outbox backlog is " + messageOutbox.size());
			for( String aKey : types.keySet())
			{
				System.out.print( aKey + ": " + types.get( aKey) + ", ");
			}
			System.out.println();
			
		}
	}

	public void resetIdleTimeoutTimer()
	{
		if( isBroadcastConnection()) 
		{
			return;
		}
		
		if( idleTimeoutTimer != null)
		{
			idleTimeoutTimer.cancel();
			idleTimeoutTimer.purge();
		}
		
		idleTimeoutTimer = LFXTimerUtils.getTimerTaskWithPeriod( getIdleTimerTask(), LFXSDKConstants.LFX_UDP_IDLE_TIMEOUT_INTERVAL, false);
	}

	public void idleTimeoutTimerDidFire()
	{
		// TODO:
		LFXLog.Warn( "Idle timeout occured on UDP Connection " + toString() + ", disconnecting");
		setConnectionState( LFXGatewayConnectionState.NOT_CONNECTED);
		getListener().gatewayConnectionDidDisconnectWithError( this, null);
	}

	private void udpSocketDidReceiveDataFromAddressWithFilterContext( LFXSocketGeneric socket, byte[] data, byte[] address, Object filterContext)
	{
		InetAddress hostAddress = null;
		try
		{
			hostAddress = InetAddress.getByAddress( address);
		} 
		catch( UnknownHostException e)
		{
			e.printStackTrace();
		}
		
		if( hostAddress == null)
		{
			return;
		}
		
		String host = hostAddress.getHostAddress();
		
		host = LFXNetworkUtils.getIPv4StringByStrippingIPv6Prefix( host);

		if( !host.equals( getGatewayDescriptor().getHost()) && !isBroadcastConnection()) 
		{
			return;
		}
		
	    if( shouldIgnoreDataFromHost( host))
		{
	        return;
	    }
	    
		LFXMessage message = LFXMessage.messageWithMessageData( data);
		
		if( message == null)
		{
			LFXLog.Error( "Couldn't create message from data: " + data);
			LFXLog.LFXMessage( data);
			return;
		}
		else
		{
			//System.out.println( "Message Received: " + message.getType().toString());
		}
		
		//System.out.println( "Listener is: " + getListener());
		
	    if( getListener() != null)
		{
			// NOTE: this is here to get rid of the duplicates that occur due to both the broadcast
			// UDP listener and gateway specific UDP listeners "receiving" the message.
			if( getGatewayDescriptor().getHost().equals( LFXWiFiObserver.getWiFiBroadcastAddress()))
			{
				//[self.delegate gatewayConnection:self didReceiveMessage:message fromHost:[GCDAsyncUdpSocket hostFromAddress:address]];
				getListener().gatewayConnectionDidReceiveMessageFromHost( this, message, host);
			}
	    }
		
		resetIdleTimeoutTimer();
	}

	public void udpSocketDidCloseWithError( LFXSocketGeneric socket, String error)
	{
		setConnectionState( LFXGatewayConnectionState.NOT_CONNECTED);
		getListener().gatewayConnectionDidDisconnectWithError( this, error);
	}

	public void udpSocketDidNotConnect( LFXSocketGeneric socket, String error)
	{
		setConnectionState( LFXGatewayConnectionState.NOT_CONNECTED);
		getListener().gatewayConnectionDidDisconnectWithError( this, error);
	}

	@Override
	public void disconnect()
	{
		socket.close();
		heartbeatTimer.cancel();
		outboxTimer.cancel();
		// TODO: idleTimeoutTimer.cancel();
	}

	@Override
	public void notifyMessageReceived( SocketMessage message)
	{	
		udpSocketDidReceiveDataFromAddressWithFilterContext( null, message.getMessageData(), message.getIpAddress(), null);
	}

	@Override
	public void notifySocketStateChanged( LFXSocketGeneric socket, SocketState state)
	{
		switch( state)
		{
			case CONNECTED:
				getListener().gatewayConnectionDidConnect( this);
				break;
				
			case CONNECTING:
				break;
				
			case DISCONNECTED:
				getListener().gatewayConnectionDidDisconnectWithError( this, "");
				break;
		}
	}
}
