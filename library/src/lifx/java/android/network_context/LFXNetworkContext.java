package lifx.java.android.network_context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;

import lifx.java.android.client.LFXClient;
import lifx.java.android.constant.LFXSDKConstants;
import lifx.java.android.entities.internal.LFXBinaryPath;
import lifx.java.android.entities.internal.LFXBinaryTargetID;
import lifx.java.android.entities.internal.LFXDeviceMapping;
import lifx.java.android.entities.internal.LFXGatewayDescriptor;
import lifx.java.android.entities.internal.LFXMessage;
import lifx.java.android.entities.internal.LFXTagMapping;
import lifx.java.android.entities.internal.LFXTarget;
import lifx.java.android.entities.internal.LFXBinaryTargetID.TagField;
import lifx.java.android.entities.internal.LFXMessageObservationDescriptor.LFXMessageObserverCallback;
import lifx.java.android.entities.internal.structle.LxProtocol.Type;
import lifx.java.android.light.LFXLight;
import lifx.java.android.light.LFXLightCollection;
import lifx.java.android.light.LFXTaggedLightCollection;
import lifx.java.android.light.internal.LFXAllLightsCollection;
import lifx.java.android.network_context.internal.routing_table.LFXRoutingTable;
import lifx.java.android.network_context.internal.transport_manager.LFXTransportManager;
import lifx.java.android.network_context.internal.transport_manager.LFXTransportManager.LFXTransportManagerListener;
import lifx.java.android.util.LFXTimerUtils;

public class LFXNetworkContext implements LFXTransportManagerListener
{
	public static final String LFXNetworkContextConnectionStateDidChangeNotificationName = "LFXNetworkContextConnectionStateDidChangeNotificationName";

	private String name;
	private LFXClient client;

	private boolean isConnected;

	// Lights
	private LFXLightCollection allLightsCollection;
	
	private LFXTransportManager transportManager;

	// New Lights
	private LFXLightCollection newLightsCollection;

	private LFXRoutingTable routingTable;

	// Lights and Light States
	private ArrayList<LFXTaggedLightCollection> mutableTaggedLightCollections;

	private Timer siteScanTimer;

	public boolean isConnected()
	{
		return transportManager.isConnected();
	}

	public ArrayList<LFXTaggedLightCollection> getTaggedLightCollection()
	{
		return (ArrayList<LFXTaggedLightCollection>) mutableTaggedLightCollections.clone();
	}
	
	public void transportManagerDidConnect( LFXTransportManager transportManager)
	{
		scanNetworkForLightStates();
	}

	public void transportManagerDidDisconnect( LFXTransportManager transportManager)
	{
		
	}

	public void transportManagerDidConnectToGateway( LFXTransportManager transportManager, LFXGatewayDescriptor gatewayDescriptor)
	{
		scanNetworkForLightStates();
	}

	public void transportManagerDidDisconnectFromGateway( LFXTransportManager transportManager, LFXGatewayDescriptor gatewayDescriptor)
	{
		
	}

	public ArrayList<LFXTaggedLightCollection> getTaggedLightCollections()
	{
		return (ArrayList<LFXTaggedLightCollection>) mutableTaggedLightCollections.clone();
	}

	public void siteScanTimerDidFire()
	{
		if( !isConnected) 
		{
			return;
		}
		
		scanNetworkForLightStates();
	}

	public void handleMessage( LFXMessage message)
	{
		routingTable.updateMappingsFromMessage( message);
		updateTaggedCollectionsFromRoutingTable();
		updateDeviceTagMembershipsFromRoutingTable();
		
		for( String aDeviceID : routingTable.getDeviceIDsForBinaryPath( message.getPath()))
		{
			forwardMessageToDeviceWithDeviceID( message, aDeviceID);
		}
	}

	public void forwardMessageToDeviceWithDeviceID( LFXMessage message, String deviceID)
	{
//		LFXLogVerbose(@"Forwarding message to '%@': %@", deviceID, message);
		LFXLight light = allLightsCollection.getLightWithDeviceID( deviceID);
		
		if( light == null)
		{
			light = LFXLight.lightWithDeviceID( deviceID, this);
			allLightsCollection.addLight( light);
		}
		
		light.handleMessage( message);
	}

	public void updateTaggedCollectionsFromRoutingTable()
	{
		// Remove any existing tags that don't have a mapping
		//NSSet *tagsThatHaveMappings = [NSSet setWithArray:self.routingTable.allTags];
		Set<String> tagsThatHaveMappings = new HashSet<String>( routingTable.getAllTags());
		
		for( LFXTaggedLightCollection anExistingTaggedCollection : getTaggedLightCollection())//.clone())
		{
			if( tagsThatHaveMappings.contains( anExistingTaggedCollection.getTag()) == false)
			{
				mutableTaggedLightCollections.remove( anExistingTaggedCollection);
			}
		}
		
		// Create any non-existant tags that do have a mapping
		ArrayList<String> tagsThatHaveACollection = new ArrayList<String>();
		
		for( LFXTaggedLightCollection aTaggedLightCollection : getTaggedLightCollections())
		{
			tagsThatHaveACollection.add( aTaggedLightCollection.getTag());
		}
		
		//NSMutableSet *tagsThatHaveACollection = [NSMutableSet setWithArray:[self.taggedLightCollections lfx_arrayByMapping:^id(LFXTaggedLightCollection *collection) { return collection.tag; }]];
		for( LFXTagMapping aTagMapping : routingTable.getTagMappings())
		{
			if( tagsThatHaveACollection.contains( aTagMapping.getTag()) == false)
			{
				LFXTaggedLightCollection newCollection = LFXTaggedLightCollection.lightCollectionWithNetworkContext( this);
				newCollection.setTag( aTagMapping.getTag());
				mutableTaggedLightCollections.add( newCollection);
				tagsThatHaveACollection.add( aTagMapping.getTag());
			}
		}
	}

	public void updateDeviceTagMembershipsFromRoutingTable()
	{
		// For each device, find out what tags it should be in
		for( LFXLight aLight : allLightsCollection.getLights())
		{
			// DeviceMapping tells us the SiteID and TagField of this device
			LFXDeviceMapping deviceMapping = routingTable.getDeviceMappingForDeviceID( aLight.getDeviceID());

			// Now we need to find the tags corresponding to the SiteID and Tagfield
			ArrayList<LFXTaggedLightCollection> oldTaggedCollections = aLight.getTaggedCollections();
			
			ArrayList<String> tags = new ArrayList<String>();
			ArrayList<LFXTaggedLightCollection> taggedCollections = new ArrayList<LFXTaggedLightCollection>();
			
			ArrayList<TagField> tagFields = LFXBinaryTargetID.enumerateTagField( deviceMapping.getTagField());
			
			for( TagField aTagField : tagFields)
			{
				LFXTagMapping tagMapping = routingTable.getTagMappingForSiteIDAndTagField( deviceMapping.getSiteID(), aTagField);
				
				if( tagMapping == null) 
				{
					return;
				}
				
				String tag = tagMapping.getTag();
				
				LFXTaggedLightCollection collection = getTaggedLightCollectionForTag( tag);
				
				tags.add( tag);
				taggedCollections.add( collection);
				
				if( collection.getLights().contains( aLight) == false)
				{
					collection.addLight( aLight);
				}
			}
			
			aLight.setTags( tags);
			aLight.setTaggedCollections( taggedCollections);
			
			Set<LFXTaggedLightCollection> collectionsThatDeviceNoLongerBelongsTo = new HashSet<LFXTaggedLightCollection>( oldTaggedCollections);
			collectionsThatDeviceNoLongerBelongsTo.removeAll( taggedCollections);
			
			for( LFXTaggedLightCollection aCollection : collectionsThatDeviceNoLongerBelongsTo)
			{
				aCollection.removeLight( aLight);
			}
		}
	}

	public LFXTaggedLightCollection getTaggedLightCollectionForTag( String tag)
	{
		for( LFXTaggedLightCollection aCollection : mutableTaggedLightCollections)
		{
			if( aCollection.getTag().equals( tag))
			{
				return aCollection;
			}
		}
		
		return null;
		//return [self.taggedLightCollections lfx_firstObjectWhere:^BOOL(LFXTaggedLightCollection *collection) { return [collection.tag isEqualToString:tag]; }];
	}

	private Runnable siteScanTimerTask = new Runnable() 
	{
	    public void run() 
	    {
	    	siteScanTimerDidFire();
	    }
	};
	
	public static LFXNetworkContext initWithClientTransportManagerAndName( LFXClient client, LFXTransportManager transportManager, String name)
	{
		LFXNetworkContext networkContext = new LFXNetworkContext();
		
		networkContext.client = client;
		networkContext.name = name;
		networkContext.transportManager = transportManager;
		networkContext.transportManager.setNetworkContext( networkContext);
		networkContext.transportManager.setListener( networkContext);
		networkContext.routingTable = new LFXRoutingTable();
		networkContext.allLightsCollection = LFXAllLightsCollection.getLightCollectionWithNetworkContext( networkContext);
		networkContext.mutableTaggedLightCollections = new ArrayList<LFXTaggedLightCollection>();
			
		networkContext.transportManager.addMessageObserverObjectWithCallback( networkContext, new LFXMessageObserverCallback()
		{
			@Override
			public void run( Object context, LFXMessage message)
			{
				LFXNetworkContext networkContext = (LFXNetworkContext) context;
				networkContext.handleMessage( message);
			}
		});
		
//		MAKE_WEAK_REF( self, weakSelf);
//		[_transportManager addMessageObserverObject:self withCallback:^(LFXMessage *message) 
//		{
//			[weakSelf handleMessage:message];
//		}];
			
		networkContext.siteScanTimer = LFXTimerUtils.getTimerTaskWithPeriod( networkContext.siteScanTimerTask , LFXSDKConstants.LFX_SITE_SCAN_TIMER_INTERVAL); 
		//[NSTimer scheduledTimerWithTimeInterval:LFXSiteScanTimerInterval target:self selector:@selector(siteScanTimerDidFire) userInfo:nil repeats:YES];
		return networkContext;
	}

	public void resetAllCaches()
	{
		routingTable.resetRoutingTable();
		allLightsCollection.removeAllLights();
		mutableTaggedLightCollections.clear();
	}

	public void logEverything()
	{
		System.out.println( "Log Everything Called.");
//		LFXLogError(@"Network Context: %@", self.name);
//		LFXLogError(@"... Visible Sites:");
//		for (LFXSiteID *aSiteID in self.routingTable.siteIDs)
//		{
//			LFXLogError(@"... ... %@", aSiteID.stringValue);
//		}
//		LFXLogError(@"... Tag Mappings:");
//		for (LFXTagMapping *aTagMapping in self.routingTable.tagMappings)
//		{
//			LFXLogError(@"... ... %@\\%@    tag = '%@'", aTagMapping.siteID.stringValue, [NSString lfx_hexStringWithUInt64:aTagMapping.tagField], aTagMapping.tag);
//		}
//		LFXLogError(@"... Device Mappings:");
//		for (LFXDeviceMapping *aDeviceMapping in self.routingTable.deviceMappings)
//		{
//			LFXLogError(@"... ... %@    site = %@ tagField = %@", aDeviceMapping.deviceID, aDeviceMapping.siteID.stringValue, [NSString lfx_hexStringWithUInt64:aDeviceMapping.tagField]);
//		}
//		LFXLogError(@"... Lights:");
//		for (LFXLight *aLight in self.allLightsCollection.lights)
//		{
//			LFXLogError(@"... ... %@    color = %@  powerState = %@  label = '%@'  tags = %@", aLight.deviceID, aLight.color.stringValue, NSStringFromLFXPowerState(aLight.powerState), aLight.label, aLight.tags.lfx_singleLineDescription);
//		}
//		LFXLogError(@"... Groups:");
//		for (LFXTaggedLightCollection *aGroup in self.taggedLightCollections)
//		{
//			LFXLogError(@"... ... '%@'    fuzzyPowerState = %@  lights = %@", aGroup.tag, NSStringFromLFXFuzzyPowerState(aGroup.fuzzyPowerState), [aGroup.lights lfx_arrayByMapping:^id(LFXLight *light) { return light.deviceID; }].lfx_singleLineDescription);
//		}
	}

	public void sendMessage( LFXMessage message)
	{
		// For messages that have their Target set
		if( message.getTarget() != null)
		{
			for( LFXBinaryPath aBinaryPath : routingTable.getBinaryPathsForTarget( message.getTarget()))
			{
				LFXMessage newMessage = (LFXMessage) message.clone();// copy;
				newMessage.setPath( aBinaryPath);
				transportManager.sendMessage( newMessage);
			}
		}
		else // For message that have their Binary Path set explicitily (for internal use only)
		{
			transportManager.sendMessage( message);
		}
	}

	public void scanNetworkForLightStates()
	{
		LFXMessage lightGet = LFXMessage.messageWithTypeAndTarget( Type.LX_PROTOCOL_LIGHT_GET, LFXTarget.getBroadcastTarget());
		sendMessage( lightGet);
		
		LFXMessage getTagLabels = LFXMessage.messageWithTypeAndTarget( Type.LX_PROTOCOL_DEVICE_GET_TAG_LABELS, LFXTarget.getBroadcastTarget());
		//getTagLabels.payload.tags = ~0;
		sendMessage( getTagLabels);
		
		Runnable task = new Runnable()
		{
			@Override
			public void run()
			{
				LFXMessage lightGet = LFXMessage.messageWithTypeAndTarget( Type.LX_PROTOCOL_LIGHT_GET, LFXTarget.getBroadcastTarget());
				sendMessage( lightGet);
			}
		};
		
		// TODO: delayed LIGHT_GET
//		LFXRunBlockWithDelay(3.0, ^{
//			[self sendMessage:[LFXMessageLightGet messageWithTarget:[LFXTarget broadcastTarget]]];
//		});
		
		LFXTimerUtils.scheduleDelayedTask( task, 3000);
	}

	public LFXTaggedLightCollection createTaggedLightCollectionWithTag( String tag)
	{
		return null;
	}

	public LFXLightCollection getNewLightsCollection()
	{
		return null;
	}

	public void claimDevice( LFXLight light)
	{
		
	}
}
