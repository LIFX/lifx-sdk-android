package lifx.java.android.network_context.internal.transport_manager.lan.gateway_discovery;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import lifx.java.android.constant.LFXSDKConstants;
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

	private LFXGatewayDiscoveryControllerListener listener;

	// This property will change how often DeviceGetPanGateway messages are broadcast
	private LFXGatewayDiscoveryMode discoveryMode;

	public interface LFXGatewayDiscoveryControllerListener
	{
		public void gatewayDiscoveryControllerDidUpdateEntry( LFXGatewayDiscoveryController table, LFXGatewayDiscoveryTableEntry tableEntry, boolean entryIsNew);
	}
	
	private Timer discoveryTimer;

	private LFXLANTransportManager transportManager;
	private ArrayList<LFXGatewayDiscoveryTableEntry> table;

//	@implementation LFXGatewayDiscoveryController
//
//	+ (LFXGatewayDiscoveryController *)gatewayDiscoveryControllerWithLANTransportManager:(LFXLANTransportManager *)transportManager delegate:(id <LFXGatewayDiscoveryControllerDelegate>)delegate
//	{
//		LFXGatewayDiscoveryController *discoveryTable = [LFXGatewayDiscoveryController new];
//		discoveryTable->_table = [NSMutableArray new];
//		discoveryTable->_transportManager = transportManager;
//		discoveryTable->_delegate = delegate;
//		MAKE_WEAK_REF(discoveryTable, weakDiscoveryTable);
//		[discoveryTable.transportManager addMessageObserverWithCallback:^(LFXMessage *message) {
//			if (message.messageType != LX_PROTOCOL_DEVICE_STATE_PAN_GATEWAY) return;
//			[weakDiscoveryTable handleStatePANGatewayMessage:(LFXMessageDeviceStatePanGateway *)message];
//		}];
//		discoveryTable->_discoveryMode = LFXGatewayDiscoveryModeNormal;
//		[discoveryTable configureTimerForDiscoveryMode:discoveryTable.discoveryMode];
//		return discoveryTable;
//	}

	private ArrayList<LFXGatewayDiscoveryTableEntry> getAllGatewayDiscoveryTableEntries()
	{
		return (ArrayList<LFXGatewayDiscoveryTableEntry>) table.clone();
	}

	public void removeAllGatewayDiscoveryTableEntries()
	{
		table.clear();
	}

	public String diagnosticTextDump()
	{
//		NSMutableString *string = [NSMutableString new];
//		
//		for (LFXGatewayDiscoveryTableEntry *anEntry in self.table)
//		{
//			NSTimeInterval secondsAgo = [anEntry.lastDiscoveryResponseDate lfx_timeIntervalUpToNow];
//			NSString *timeAgo;
//			if (secondsAgo < 60) {
//				timeAgo = [NSString stringWithFormat:@"%0.0fs", secondsAgo];
//			}
//			else if (secondsAgo < 60 * 60) {
//				timeAgo = [NSString stringWithFormat:@"%0.0fm", secondsAgo/60.0];
//			}
//			else {
//				timeAgo = @">1h";
//			}
//			[string appendFormat:@"%@:%hu %@ %@ (%@)\n", anEntry.gatewayDescriptor.host, anEntry.gatewayDescriptor.port, anEntry.gatewayDescriptor.protocolString, anEntry.gatewayDescriptor.path.debugStringValue, timeAgo];
//		}
		
		String value = "";
		
		return value;
	}

	public void handleStatePANGatewayMessage( LFXMessage statePanGateway)
	{
		//LFXGatewayDescriptor *gatewayDescriptor = [LFXGatewayDescriptor gatewayDescriptorWithHost:statePanGateway.sourceNetworkHost port:statePanGateway.payload.port path:statePanGateway.path service:statePanGateway.payload.service];
		
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
		
		LFXGatewayDescriptor gatewayDescriptor = LFXGatewayDescriptor.getGatewayDescriptorWithHostPortPathService( host, port, path, service);// port:statePanGateway.payload.port path:statePanGateway.path service:statePanGateway.payload.service];
		
		LFXGatewayDiscoveryTableEntry tableEntry = null;
		
		//table.lfx_firstObjectWhere:^BOOL(LFXGatewayDiscoveryTableEntry *entry) { return [entry.gatewayDescriptor isEqual:gatewayDescriptor]; }];
		
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
		//discoveryTimer = [NSTimer scheduledTimerWithTimeInterval:duration target:self selector:@selector(discoveryTimerDidFire:) userInfo:nil repeats:YES];
	}

	public void discoveryTimerDidFire()
	{
		System.out.println( "Discover Timer FIred");
		sendGatewayDiscoveryMessage();
	}
	
	public void shutDown()
	{
		if( discoveryTimer != null)
		{
			discoveryTimer.cancel();
		}
	}
}

