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

public class LFXLANTransportManager extends LFXTransportManager implements LFXGatewayConnectionListener, LFXGatewayDiscoveryControllerListener
{
	private LFXGatewayConnection broadcastUDPConnection;
	private LFXGatewayConnection peerToPeerUDPConnection;
	
	private LFXGatewayDiscoveryController gatewayDiscoveryController;

	private HashMap<LFXGatewayDescriptor,LFXGatewayConnection> gatewayConnections;	// Keyed by .gatewayDescriptor

	public void disconnect()
	{
		broadcastUDPConnection.disconnect();
		peerToPeerUDPConnection.disconnect();
		gatewayDiscoveryController.shutDown();
		
		for( LFXGatewayConnection aGatewayConnection : gatewayConnections.values())
		{
			aGatewayConnection.disconnect();
		}
		
		gatewayConnections.clear();
	}
	
	public LFXLANTransportManager()
	{
		super();
		LFXLog.Debug( "LFXLAN Initializer");
		
		gatewayDiscoveryController = LFXGatewayDiscoveryController.getGatewayDiscoveryControllerWithLANTransportManager( this, this);
		gatewayConnections = new HashMap<LFXGatewayDescriptor,LFXGatewayConnection>();
		
		LFXLog.Debug( "Setup UDP Connections");
		setupBroadcastUDPConnection();
		setupPeerToPeerUDPConnection();
			
		LFXLog.Debug( "Start Discovery");
		gatewayDiscoveryController.setDiscoveryMode( LFXGatewayDiscoveryMode.ACTIVELY_SEARCHING);
		gatewayDiscoveryController.sendGatewayDiscoveryMessage();
	}

	public void setupBroadcastUDPConnection()
	{
		broadcastUDPConnection = LFXGatewayConnection.getGatewayConnectionWithGatewayDescriptor( LFXGatewayDescriptor.getBroadcastGatewayDescriptor(), this);
		broadcastUDPConnection.connect();
	}

	public void setupPeerToPeerUDPConnection()
	{
		peerToPeerUDPConnection = LFXGatewayConnection.getGatewayConnectionWithGatewayDescriptor( LFXGatewayDescriptor.getClientPeerToPeerGatewayDescriptor(), this);
		peerToPeerUDPConnection.connect();
	}

	public void connectionStatesDidChange()
	{
		System.out.println( "Connection State did change called.");
		
		boolean newIsConnected = false;
		for( LFXGatewayConnection aGatewayConnection : gatewayConnections.values())
		{
			System.out.println( aGatewayConnection.getGatewayDescriptor().toString() + " : " +  aGatewayConnection.getConnectionState().toString());
			
			if( aGatewayConnection.getConnectionState() == LFXGatewayConnectionState.CONNECTED)
			{
				newIsConnected = true;
			}
		}
		
		setIsConnected( newIsConnected);
		
		gatewayDiscoveryController.setDiscoveryMode( isConnected() ? LFXGatewayDiscoveryMode.NORMAL : LFXGatewayDiscoveryMode.ACTIVELY_SEARCHING);
		
		logConnectionStates();
	}

	private void logConnectionStates()
	{
//		LFXLogError(@"Current Connections:");
//		LFXLogError(@"... Broadcast UDP: %@", self.broadcastUDPConnection);
//		LFXLogError(@"... Peer to Peer UDP: %@", self.peerToPeerUDPConnection);
//		LFXLogError(@"... Gateway Connections:");
//		for (LFXGatewayConnection *aConnection in self.gatewayConnections.allValues)
//		{
//			LFXLogError(@"... ... %@", aConnection);
//		}
	}

	public void sendMessage( LFXMessage message)
	{
		if( message.getPath().getSiteID().isZeroSite())
		{
			for( String aGatewayHost : getGatewayHosts())
			{
				LFXGatewayConnection tcpConnection = getGatewayConnectionForHost( aGatewayHost, Service.LX_PROTOCOL_DEVICE_SERVICE_TCP);
				LFXGatewayConnection udpConnection = getGatewayConnectionForHost( aGatewayHost, Service.LX_PROTOCOL_DEVICE_SERVICE_UDP);
				
				ArrayList<LFXGatewayConnection> connections = new ArrayList<LFXGatewayConnection>();
				
				if( tcpConnection != null)
				{
					connections.add( tcpConnection); 
				}
				
				if( udpConnection != null)
				{
					connections.add( udpConnection);
				}
				
				LFXGatewayConnection connectionToUse = null;
				
				if( connections.size() > 0)
				{
					connectionToUse = connections.get( 0);
				}
				
				if( connectionToUse != null)
				{
					sendMessageOnConnection( message, connectionToUse);
				}
			}
		}
		else
		{
			boolean messageWasSent = false;
			for( String aGatewayHost : getGatewayHostsForSiteID( message.getPath().getSiteID()))
			{
				LFXGatewayConnection tcpConnection = getGatewayConnectionForHost( aGatewayHost, Service.LX_PROTOCOL_DEVICE_SERVICE_TCP);
				LFXGatewayConnection udpConnection = getGatewayConnectionForHost( aGatewayHost, Service.LX_PROTOCOL_DEVICE_SERVICE_UDP);
				
				ArrayList<LFXGatewayConnection> connections = new ArrayList<LFXGatewayConnection>();
				if( message.prefersUDPOverTCP())
				{
					if( udpConnection != null)
					{
						connections.add( udpConnection);
					}
					
					if( tcpConnection != null)
					{
						connections.add( tcpConnection); 
					}
				}
				else
				{
					if( tcpConnection != null)
					{
						connections.add( tcpConnection); 
					}
					
					if( udpConnection != null)
					{
						connections.add( udpConnection);
					}
				}
				
				LFXGatewayConnection connectionToUse = null;
				
				if( connections.size() > 0)
				{
					connectionToUse = connections.get( 0);
				}
				
				if( connectionToUse != null)
				{
					sendMessageOnConnection( message, connectionToUse);
					messageWasSent = true;
				}
			}
			
			if( messageWasSent == false)
			{
				sendMessageOnConnection( message, broadcastUDPConnection);
			}
		}
		
		sendObserverCallbacksForMessage( message);
	}

	public void sendBroadcastUDPMessage( LFXMessage message)
	{
//		if( message == null)
//		{
//			System.out.println( "Message == null");
//		}
//		
//		if( broadcastUDPConnection == null)
//		{
//			System.out.println( "broadcastUDPConnection == null");
//		}
		
		sendMessageOnConnection( message, broadcastUDPConnection);
		sendObserverCallbacksForMessage( message);
	}

	public void sendMessageOnConnection( LFXMessage message, LFXGatewayConnection connection)
	{
		if( connection != null)
		{
			connection.sendMessage( (LFXMessage) message.clone());
		}
	}

	public ArrayList<LFXGatewayDescriptor> getGatewayDescriptorsForSiteID( LFXSiteID siteID)
	{
		ArrayList<LFXGatewayDescriptor> descriptors = new ArrayList<LFXGatewayDescriptor>();
		
		for( LFXGatewayDescriptor key : gatewayConnections.keySet())
		{
			if( key.getPath().getSiteID().equals( siteID))
			{
				descriptors.add( key);
			}
		}
		
		return descriptors;
		
		//return [self.gatewayConnections.allKeys lfx_allObjectsWhere:^BOOL(LFXGatewayDescriptor *gateway) { return [gateway.path.siteID isEqual:siteID]; }];
	}

	public Set<String> getGatewayHostsForSiteID( LFXSiteID siteID)
	{
		ArrayList<LFXGatewayDescriptor> descriptors = getGatewayDescriptorsForSiteID( siteID);
		ArrayList<String> hosts = new ArrayList<String>();
		for( LFXGatewayDescriptor aDescriptor : descriptors)
		{
			hosts.add( aDescriptor.getHost());
		}
		
		return new HashSet<String>( hosts);
		
		//return [[[self gatewayDescriptorsForSiteID:siteID] lfx_arrayByMapping:^id(LFXGatewayDescriptor *gateway) { return gateway.host; }] lfx_set];
	}

	public Set<String> getGatewayHosts()
	{
		ArrayList<String> hosts = new ArrayList<String>();
		
		for( LFXGatewayDescriptor key : gatewayConnections.keySet())
		{
			hosts.add( key.getHost());
		}
		
		return new HashSet<String>( hosts);
		//return [[self.gatewayConnections.allKeys lfx_arrayByMapping:^id(LFXGatewayDescriptor *gateway) { return gateway.host; }] lfx_set];
	}

	public LFXGatewayConnection getGatewayConnectionForHost( String host, Service service)
	{	
		for( LFXGatewayConnection aConnection : gatewayConnections.values())
		{
			if( aConnection.getGatewayDescriptor().getHost().equals( host) &&
				aConnection.getGatewayDescriptor().getService() == service)
			{
				return aConnection;
			}
		}
		
		return null;
		
		//return [self.gatewayConnections.allValues lfx_firstObjectWhere:^BOOL(LFXGatewayConnection *connection) { return connection.gatewayDescriptor.service == service && [connection.gatewayDescriptor.host isEqualToString:host]; }];
	}

	//#pragma mark - LFXGatewayConnectionDelegate

	public void gatewayConnectionDidConnect( LFXGatewayConnection connection)
	{
		//LFXLogInfo(@"Connection %@ did connect", connection);
		connectionStatesDidChange();
		listener.transportManagerDidConnectToGateway( this, connection.getGatewayDescriptor());
	}

	public void gatewayConnectionDidDisconnectWithError( LFXGatewayConnection connection, String error)
	{
		//LFXLogInfo(@"Connection %@ did disconnect: %@", connection, error.localizedDescription);
		if( connection == broadcastUDPConnection)
		{
			broadcastUDPConnection.disconnect();
			broadcastUDPConnection.setListener( null);
			// [self performSelector:@selector(setupBroadcastUDPConnection) withObject:nil afterDelay:1.0];
			
			Runnable task = new Runnable()
			{
				@Override
				public void run()
				{
					setupBroadcastUDPConnection();
				}
			};
			
			LFXTimerUtils.scheduleDelayedTask( task, 1000);
		}
		else if( connection == peerToPeerUDPConnection)
		{
			peerToPeerUDPConnection.disconnect();
			peerToPeerUDPConnection.setListener( null);
			// [self performSelector:@selector(setupPeerToPeerUDPConnection) withObject:nil afterDelay:3.0];
			
			Runnable task = new Runnable()
			{
				@Override
				public void run()
				{
					setupPeerToPeerUDPConnection();
				}
			};
			
			LFXTimerUtils.scheduleDelayedTask( task, 1000);
		}
		else
		{
			gatewayConnections.remove( connection.getGatewayDescriptor());
		}
		
		connectionStatesDidChange();
		listener.transportManagerDidDisconnectFromGateway( this, connection.getGatewayDescriptor());
	}

	public void gatewayConnectionDidReceiveMessageFromHost( LFXGatewayConnection connection, LFXMessage message, String host)
	{
		host = LFXNetworkUtils.getIPv4StringByStrippingIPv6Prefix( host);
		
		message.setGatewayDescriptor( connection.getGatewayDescriptor());
		message.setSourceNetworkHost( host);
		sendObserverCallbacksForMessage( message);
	}
	
	public void gatewayDiscoveryControllerDidUpdateEntry( LFXGatewayDiscoveryController table, LFXGatewayDiscoveryTableEntry tableEntry, boolean entryIsNew)
	{		
		//LFXLog.Verbose( "Received %@ gateway discovery entry: %@", entryIsNew ? @"a new" : @"an updated", tableEntry);
		
		LFXGatewayDescriptor gateway = tableEntry.getGatewayDescriptor();
		
		if( gateway.getPort() == 0)
		{
			//LFXLogInfo(@"Service %@/%@:%u unavailable (port == 0), ignoring", gateway.protocolString, gateway.host, gateway.port);
			return;
		}
		
		LFXGatewayConnection existingConnection = null;
		
		for( LFXGatewayDescriptor aDescriptor : gatewayConnections.keySet())
		{
			if( aDescriptor.toString().equals( gateway.toString()))
			{
				existingConnection = gatewayConnections.get( aDescriptor);
			}
		}
		
		//System.out.println( "Gateway Already Exists: " + tableEntry.getGatewayDescriptor().toString());
		
		if( existingConnection == null)
		{
			//LFXLogInfo(@"Service %@/%@:%u has no existing connection, connecting", gateway.protocolString, gateway.host, gateway.port);
			//System.out.println( "Connection DId not exist");
			//System.out.println( "Starting Gateway: " + tableEntry.getGatewayDescriptor().toString());
			
//			if( gateway == null)
//			{
//				System.out.println( "Gateway == null");
//			}
			
			LFXGatewayConnection newConnection = LFXGatewayConnection.getGatewayConnectionWithGatewayDescriptor( gateway, this);
			newConnection.connect();
			gatewayConnections.put( gateway, newConnection);
			connectionStatesDidChange();
			
			//System.out.println( newConnection.getConnectionStateString());
		}
	}
}
