//
//  LFXGatewayDiscoveryController.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.network_context.internal.transport_manager.lan.gateway_discovery;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import lifx.java.android.entities.internal.LFXBinaryPath;
import lifx.java.android.entities.internal.LFXGatewayDescriptor;
import lifx.java.android.entities.internal.LFXMessage;
import lifx.java.android.entities.internal.LFXMessageObservationDescriptor.LFXMessageObserverCallback;
import lifx.java.android.entities.internal.structle.LxProtocolDevice;
import lifx.java.android.entities.internal.structle.LxProtocol.Type;
import lifx.java.android.entities.internal.structle.LxProtocolDevice.Service;
import lifx.java.android.network_context.internal.transport_manager.lan.LFXLANTransportManager;
import lifx.java.android.util.LFXTimerUtils;

public class LFXGatewayDiscoveryController
{
	public enum LFXGatewayDiscoveryMode
	{
		NORMAL,				// 30 seconds
		ACTIVELY_SEARCHING,	// 1 second
	};

	private LFXGatewayDiscoveryControllerListener listener;
	private boolean ended = false;

	// This property will change how often DeviceGetPanGateway messages are broadcast
	private LFXGatewayDiscoveryMode discoveryMode;
	
	public static LFXGatewayDiscoveryController getGatewayDiscoveryControllerWithLANTransportManager( LFXLANTransportManager transportManager, LFXGatewayDiscoveryControllerListener listener)
	{
		LFXGatewayDiscoveryController discoveryTable = new LFXGatewayDiscoveryController();
		discoveryTable.table = new ArrayList<LFXGatewayDiscoveryTableEntry>();
		discoveryTable.transportManager = transportManager;
		discoveryTable.listener = listener;
		
		discoveryTable.transportManager.addMessageObserverObjectWithCallback( discoveryTable, new LFXMessageObserverCallback()
		{
			@Override
			public void run( Object context, LFXMessage message)
			{
				LFXGatewayDiscoveryController discoveryTable = (LFXGatewayDiscoveryController) context;
				
				if( message.getType() != Type.LX_PROTOCOL_DEVICE_STATE_PAN_GATEWAY) 
				{
					return;
				}
				
				discoveryTable.handleStatePANGatewayMessage( message);
			}
		});
		
		discoveryTable.discoveryMode = LFXGatewayDiscoveryMode.NORMAL;
		discoveryTable.configureTimerForDiscoveryMode( discoveryTable.discoveryMode);
		return discoveryTable;
	}

	public interface LFXGatewayDiscoveryControllerListener
	{
		public void gatewayDiscoveryControllerDidUpdateEntry( LFXGatewayDiscoveryController table, LFXGatewayDiscoveryTableEntry tableEntry, boolean entryIsNew);
	}
	
	private Timer discoveryTimer;

	private LFXLANTransportManager transportManager;
	private ArrayList<LFXGatewayDiscoveryTableEntry> table;

	@SuppressWarnings( { "unchecked", "unused" })
	private ArrayList<LFXGatewayDiscoveryTableEntry> getAllGatewayDiscoveryTableEntries()
	{
		return (ArrayList<LFXGatewayDiscoveryTableEntry>) table.clone();
	}

	public void removeAllGatewayDiscoveryTableEntries()
	{
		table.clear();
	}

	public void handleStatePANGatewayMessage( LFXMessage statePanGateway)
	{
		LxProtocolDevice.StatePanGateway statePanGatewayPayload = (LxProtocolDevice.StatePanGateway) statePanGateway.getPayload();
		
		String host = statePanGateway.getSourceNetworkHost();
		int port = (int) statePanGatewayPayload.getPort().getValue();
		LFXBinaryPath path = statePanGateway.getPath();
		Service service = LxProtocolDevice.serviceMap.get( statePanGatewayPayload.getService().getValue());
		
		// TODO: remove to enable TCP
		if( service == Service.LX_PROTOCOL_DEVICE_SERVICE_TCP)
		{
			return;
		}
		
		LFXGatewayDescriptor gatewayDescriptor = LFXGatewayDescriptor.getGatewayDescriptorWithHostPortPathService( host, port, path, service);
		
		LFXGatewayDiscoveryTableEntry tableEntry = null;
		
		for( LFXGatewayDiscoveryTableEntry anEntry : table)
		{
			if( anEntry.getGatewayDescriptor().equals( gatewayDescriptor))
			{
				tableEntry = anEntry;
				break;
			}
		}
		
		if( tableEntry != null)
		{
			tableEntry.setLastDiscoveryResponseDate( statePanGateway.getTimestamp());
			listener.gatewayDiscoveryControllerDidUpdateEntry( this, tableEntry, false);
		}
		else
		{
			tableEntry = new LFXGatewayDiscoveryTableEntry();
			tableEntry.setGatewayDescriptor( gatewayDescriptor);
			tableEntry.setLastDiscoveryResponseDate( statePanGateway.getTimestamp());
			table.add( tableEntry);
			listener.gatewayDiscoveryControllerDidUpdateEntry( this, tableEntry, true);
		}
	}

	public void sendGatewayDiscoveryMessage()
	{
		LFXMessage getPANGateway = LFXMessage.messageWithType( Type.LX_PROTOCOL_DEVICE_GET_PAN_GATEWAY);
		transportManager.sendBroadcastUDPMessage( getPANGateway);
	}

	public void setDiscoveryMode( LFXGatewayDiscoveryMode discoveryMode)
	{
		if( this.discoveryMode == discoveryMode) 
		{
			return;
		}
		this.discoveryMode = discoveryMode;
		configureTimerForDiscoveryMode( discoveryMode);
	}

	private Runnable getDiscoverTimerTask() 
	{
		Runnable discoverTimerTask = new TimerTask() 
		{
		    public void run() 
		    {
		    	discoveryTimerDidFire();
		    }
		};
		
		return discoverTimerTask;
	}
	
	public void configureTimerForDiscoveryMode( LFXGatewayDiscoveryMode discoveryMode)
	{
		System.out.println( "DISCOVERYMODE: " + discoveryMode);
		
		long duration = 1000;
		switch( discoveryMode)
		{
			case NORMAL:
				duration = 30000;
				break;
			case ACTIVELY_SEARCHING:
				duration = 1000;
				break;
		}
		
		if( discoveryTimer != null)
		{
			discoveryTimer.cancel();
			discoveryTimer.purge();
		}

		System.out.println( "Making Discovery Timer task. Period: " + duration);
		discoveryTimer = LFXTimerUtils.getTimerTaskWithPeriod( getDiscoverTimerTask(), duration, false);
	}

	public void discoveryTimerDidFire()
	{
		if( !ended)
		{
			sendGatewayDiscoveryMessage();
		}
	}
	
	public void shutDown()
	{
		if( discoveryTimer != null)
		{
			discoveryTimer.cancel();
			discoveryTimer.purge();
		}
		
		ended = true;
	}
}

