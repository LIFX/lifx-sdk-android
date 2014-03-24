//
//  LFXNetworkContext.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.network_context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import lifx.java.android.client.LFXClient;
import lifx.java.android.constant.LFXSDKConstants;
import lifx.java.android.entities.internal.LFXBinaryPath;
import lifx.java.android.entities.internal.LFXBinaryTargetID;
import lifx.java.android.entities.internal.LFXDeviceMapping;
import lifx.java.android.entities.internal.LFXGatewayDescriptor;
import lifx.java.android.entities.internal.LFXMessage;
import lifx.java.android.entities.internal.LFXSiteID;
import lifx.java.android.entities.internal.LFXTagMapping;
import lifx.java.android.entities.internal.LFXTarget;
import lifx.java.android.entities.internal.LFXBinaryTargetID.TagField;
import lifx.java.android.entities.internal.LFXMessageObservationDescriptor.LFXMessageObserverCallback;
import lifx.java.android.entities.internal.structle.LxProtocol.Type;
import lifx.java.android.entities.internal.structle.LxProtocolDevice.SetTagLabels;
import lifx.java.android.entities.internal.structle.LxProtocolDevice.SetTags;
import lifx.java.android.entities.internal.structle.LxProtocolDevice;
import lifx.java.android.entities.internal.structle.StructleTypes.UInt64;
import lifx.java.android.light.LFXLight;
import lifx.java.android.light.LFXLightCollection;
import lifx.java.android.light.LFXTaggedLightCollection;
import lifx.java.android.light.internal.LFXAllLightsCollection;
import lifx.java.android.network_context.internal.routing_table.LFXRoutingTable;
import lifx.java.android.network_context.internal.transport_manager.LFXTransportManager;
import lifx.java.android.network_context.internal.transport_manager.LFXTransportManager.LFXTransportManagerListener;
import lifx.java.android.util.LFXByteUtils;
import lifx.java.android.util.LFXLog;
import lifx.java.android.util.LFXTimerUtils;

public class LFXNetworkContext implements LFXTransportManagerListener
{
	public interface LFXNetworkContextListener
	{
		public void networkContextDidConnect( LFXNetworkContext networkContext);
		public void networkContextDidDisconnect( LFXNetworkContext networkContext);

		public void networkContextDidAddTaggedLightCollection( LFXNetworkContext networkContext, LFXTaggedLightCollection collection);
		public void networkContextDidRemoveTaggedLightCollection( LFXNetworkContext networkContext, LFXTaggedLightCollection collection);
	}
	
	private String name;
	private LFXClient client;

	private ArrayList<LFXNetworkContextListener> listeners = new ArrayList<LFXNetworkContextListener>();
	
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
	
	public String getName()
	{
		return name;
	}
	
	public LFXClient getClient()
	{
		return client;
	}
	
	public LFXLightCollection getNewLightCollection()
	{
		return newLightsCollection;
	}
	
	public void connect()
	{
		transportManager.connect();
	}
	
	public void addNetworkContextListener( LFXNetworkContextListener listener)
	{
		if( !listeners.contains( listener))
		{
			listeners.add( listener);
		}
	} 
	
	public void removeAllNetworkContextListeners()
	{
		listeners.clear();
	}
	
	public void removeNetworkContextListener( LFXNetworkContextListener listener)
	{
		listeners.remove( listener);
	}
	
	public void transportManagerDidConnect( LFXTransportManager transportManager)
	{
		for( LFXNetworkContextListener aListener : listeners)
		{
			aListener.networkContextDidConnect( this);
		}
		
		scanNetworkForLightStates();
		
		if( siteScanTimer != null)
		{
			siteScanTimer.cancel();
			siteScanTimer.purge(); 
		}
		
		siteScanTimer = LFXTimerUtils.getTimerTaskWithPeriod( getSiteScanTimerTask() , LFXSDKConstants.LFX_SITE_SCAN_TIMER_INTERVAL, false); 
	}

	public void transportManagerDidDisconnect( LFXTransportManager transportManager)
	{
		for( LFXNetworkContextListener aListener : listeners)
		{
			aListener.networkContextDidDisconnect( this);
		}
	}

	public void transportManagerDidConnectToGateway( LFXTransportManager transportManager, LFXGatewayDescriptor gatewayDescriptor)
	{
		scanNetworkForLightStates();
	}

	public void transportManagerDidDisconnectFromGateway( LFXTransportManager transportManager, LFXGatewayDescriptor gatewayDescriptor)
	{
		
	}

	@SuppressWarnings( "unchecked")
	public ArrayList<LFXTaggedLightCollection> getTaggedLightCollections()
	{
		return (ArrayList<LFXTaggedLightCollection>) mutableTaggedLightCollections.clone();
	}

	public void siteScanTimerDidFire()
	{
		if( !isConnected()) 
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
		LFXLight light = allLightsCollection.getLightWithDeviceID( deviceID);
		
		if( light == null)
		{
			light = LFXLight.lightWithDeviceID( deviceID, this);
			allLightsCollection.addLight( light);
		}
		
		light.handleMessage( message);
	}
	
	@SuppressWarnings( "unchecked")
	private void updateTaggedCollectionsFromRoutingTable()
	{
		// Remove any existing tags that don't have a mapping
		Set<String> tagsThatHaveMappings = new HashSet<String>( routingTable.getAllTags());
		
		for( LFXTaggedLightCollection anExistingTaggedCollection : ((ArrayList<LFXTaggedLightCollection>) getTaggedLightCollections().clone()))
		{
			if( tagsThatHaveMappings.contains( anExistingTaggedCollection.getTag()) == false)
			{
				mutableTaggedLightCollections.remove( anExistingTaggedCollection);
				
				for( LFXNetworkContextListener aListener : listeners)
				{
					aListener.networkContextDidRemoveTaggedLightCollection( this, anExistingTaggedCollection);
				}
			}
		}
		
		// Create any non-existant tags that do have a mapping
		ArrayList<String> tagsTemp = new ArrayList<String>();
		for( LFXTaggedLightCollection collection : getTaggedLightCollections())
		{
			tagsTemp.add( collection.getTag());
		}
		
		Set<String> tagsThatHaveACollection = new HashSet<String>( tagsTemp);
		for( LFXTagMapping aTagMapping : routingTable.getTagMappings())
		{
			if( tagsThatHaveACollection.contains( aTagMapping.getTag()) == false)
			{
				LFXTaggedLightCollection newCollection = LFXTaggedLightCollection.getLightCollectionWithNetworkContext( this);
				newCollection.setTag( aTagMapping.getTag());
				mutableTaggedLightCollections.add( newCollection);
				tagsThatHaveACollection.add( aTagMapping.getTag());
				
				for( LFXNetworkContextListener aListener : listeners)
				{
					aListener.networkContextDidAddTaggedLightCollection( this, newCollection);
				}
			}
		}
	}
	
	public void updateDeviceTagMembershipsFromRoutingTable()
	{
		// For each device, find out what tags it should be in
		//for( LFXLight aLight : allLightsCollection.getLights())
		for( int i = 0; i < allLightsCollection.getLights().size(); i++)
		{
			LFXLight aLight = allLightsCollection.getLights().get( i);
			// DeviceMapping tells us the SiteID and TagField of this device
			LFXDeviceMapping deviceMapping = routingTable.getDeviceMappingForDeviceID( aLight.getDeviceID());

			// Now we need to find the tags corresponding to the SiteID and Tagfield
			ArrayList<LFXTaggedLightCollection> oldTaggedCollections = aLight.getTaggedCollections();
			
			ArrayList<String> tagsForThisLight = new ArrayList<String>();
			ArrayList<LFXTaggedLightCollection> taggedCollectionsThisLightShouldBeIn = new ArrayList<LFXTaggedLightCollection>();
			
			ArrayList<LFXTaggedLightCollection> collectionsToAddThisLightTo = new ArrayList<LFXTaggedLightCollection>();
			
			ArrayList<TagField> tagFields = LFXBinaryTargetID.enumerateTagField( deviceMapping.getTagField());
			
			for( TagField singularTagField : tagFields)
			{
				LFXTagMapping tagMapping = routingTable.getTagMappingForSiteIDAndTagField( deviceMapping.getSiteID(), singularTagField);
				if( tagMapping == null) 
				{
					return;
				}
				
				String tag = tagMapping.getTag();
				
				LFXTaggedLightCollection collection = getTaggedLightCollectionForTag( tag);
				tagsForThisLight.add( tag);
				taggedCollectionsThisLightShouldBeIn.add( collection);
				
				if( collection.getLights().contains( aLight) == false)
				{
					collectionsToAddThisLightTo.add( collection);
				}
			}
			
			Set<LFXTaggedLightCollection> collectionsToRemoveThisLightFrom = new HashSet<LFXTaggedLightCollection>( oldTaggedCollections);
			collectionsToRemoveThisLightFrom.removeAll( new HashSet<LFXTaggedLightCollection>( taggedCollectionsThisLightShouldBeIn));
			
			for( LFXTaggedLightCollection aCollection : collectionsToRemoveThisLightFrom)
			{
				ArrayList<String> tempTags = aLight.getTags();
				tempTags.remove( aCollection.getTag());
				aLight.setTags( tempTags);
				
				ArrayList<LFXTaggedLightCollection> tempCols = aLight.getTaggedCollections();
				tempCols.remove( aCollection);
				aLight.setTaggedCollections( tempCols);
				
				aCollection.removeLight( aLight);
			}
			
			for( LFXTaggedLightCollection aCollection : collectionsToAddThisLightTo)
			{
				ArrayList<String> tempTags = aLight.getTags();
				tempTags.add( aCollection.getTag());
				aLight.setTags( tempTags);
				
				ArrayList<LFXTaggedLightCollection> tempCols = aLight.getTaggedCollections();
				tempCols.add( aCollection);
				aLight.setTaggedCollections( tempCols);
				
				aCollection.addLight( aLight);
			}
		}
	}

	private Runnable getSiteScanTimerTask()
	{
		Runnable siteScanTimerTask = new TimerTask() 
		{
		    public void run() 
		    {
		    	siteScanTimerDidFire();
		    }
		};
		
		return siteScanTimerTask;
	}
	
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
		else 			// For message that have their Binary Path set explicitly (for internal use only)
		{
			transportManager.sendMessage( message);
		}
	}

	public void scanNetworkForLightStates()
	{
		LFXMessage lightGet = LFXMessage.messageWithTypeAndTarget( Type.LX_PROTOCOL_LIGHT_GET, LFXTarget.getBroadcastTarget());
		sendMessage( lightGet);
		
		Runnable task0 = new Runnable()
		{
			@Override
			public void run()
			{
				LFXMessage getTagLabels = LFXMessage.messageWithTypeAndTarget( Type.LX_PROTOCOL_DEVICE_GET_TAG_LABELS, LFXTarget.getBroadcastTarget());
				byte[] tags = new byte[8];
				tags = LFXByteUtils.inverseByteArrayBits( tags);
				LxProtocolDevice.GetTagLabels payload = new LxProtocolDevice.GetTagLabels( new Object(), new UInt64( tags));
				getTagLabels.setPayload( payload);
				sendMessage( getTagLabels);
			}
		};
		
		LFXTimerUtils.scheduleDelayedTask( task0, 1500);
		
		Runnable task1 = new Runnable()
		{
			@Override
			public void run()
			{
				LFXMessage lightGet = LFXMessage.messageWithTypeAndTarget( Type.LX_PROTOCOL_LIGHT_GET, LFXTarget.getBroadcastTarget());
				sendMessage( lightGet);
			}
		};
		
		LFXTimerUtils.scheduleDelayedTask( task1, 3000);
	}

	public void claimDevice( LFXLight light)
	{
		
	}
	
	public LFXLightCollection getAllLightsCollection()
	{
		return allLightsCollection;
	}
	
	public void disconnect()
	{
		if( siteScanTimer != null)
		{
			siteScanTimer.cancel();
			siteScanTimer.purge();
		}
		
		transportManager.disconnect();
	}
	
	public void addLightToTaggedLightCollection( LFXLight light, LFXTaggedLightCollection taggedLightCollection)
	{
		String tag = taggedLightCollection.getTag();
		LFXDeviceMapping deviceMapping = routingTable.getDeviceMappingForDeviceID( light.getDeviceID());
		LFXSiteID siteID = deviceMapping.getSiteID();
	
		ArrayList<LFXTagMapping> mappings = routingTable.getTagMappingsForSiteIDAndTag( siteID, tag);
		if( mappings.size() == 0)
		{
			addTagToSiteWithSiteID( tag, siteID);
			
			mappings = routingTable.getTagMappingsForSiteIDAndTag( siteID, tag);
			
			if( mappings.size() == 0)
			{
				return;
			}
		}
		
		LFXTagMapping tagMapping = mappings.get( 0);
		
		if( tagMapping == null)
		{
			return;
		}

		LFXMessage setTags = LFXMessage.messageWithTypeAndTarget( Type.LX_PROTOCOL_DEVICE_SET_TAGS, light.getTarget());
		Object padding = new Object();
		UInt64 tags = new UInt64( LFXByteUtils.bitwiseOrByteArrays( deviceMapping.getTagField().tagData, tagMapping.getTagField().tagData));
		LxProtocolDevice.SetTags payload = new SetTags( padding, tags);
		setTags.setPayload( payload);
		sendMessage( setTags);
	}

	public void removeLightFromTaggedLightCollection( LFXLight light, LFXTaggedLightCollection taggedLightCollection)
	{
		LFXDeviceMapping deviceMapping = routingTable.getDeviceMappingForDeviceID( light.getDeviceID());
		LFXSiteID siteID = deviceMapping.getSiteID();
		
		//LFXTagMapping tagMapping = routingTable.getTagMappingsForSiteIDAndTag( siteID, taggedLightCollection.getTag()).get( 0);
		ArrayList<LFXTagMapping> mappings = routingTable.getTagMappingsForSiteIDAndTag( siteID, taggedLightCollection.getTag());
		
		if( mappings.size() == 0)
		{
			return;
		}
		
		LFXTagMapping tagMapping = mappings.get( 0);
		
		if( tagMapping == null)
		{
			return;
		}
		
		LFXMessage setTags = LFXMessage.messageWithTypeAndPath( Type.LX_PROTOCOL_DEVICE_SET_TAGS, LFXBinaryPath.getBroadcastBinaryPathWithSiteID( tagMapping.getSiteID()));
		Object padding = new Object();
		UInt64 tags = new UInt64( LFXByteUtils.bitwiseAndByteArrays( deviceMapping.getTagField().tagData, LFXByteUtils.inverseByteArrayBits( tagMapping.getTagField().tagData)));
		LxProtocolDevice.SetTags payload = new SetTags( padding, tags);
		setTags.setPayload( payload);
		sendMessage( setTags);
	}

	public boolean renameTaggedLightCollectionWithNewTag( LFXTaggedLightCollection collection, String newTag)
	{
		LFXTaggedLightCollection existingTaggedCollectionWithNewTag = getTaggedLightCollectionForTag( newTag);
		if( existingTaggedCollectionWithNewTag != null)
		{
			LFXLog.info( "Tag " + newTag + " already exists, aborting rename of " + collection.getTag());
			return false;
		}
		
		LFXLog.info( "Renaming tag " + collection.getTag() + " to " + newTag);
		for( LFXTagMapping aTagMapping : routingTable.getTagMappingsForTag( collection.getTag()))
		{
			LFXMessage setTagLabels = LFXMessage.messageWithTypeAndPath( Type.LX_PROTOCOL_DEVICE_SET_TAG_LABELS, LFXBinaryPath.getBroadcastBinaryPathWithSiteID( aTagMapping.getSiteID()));
			Object padding = new Object();
			UInt64 tags = new UInt64( aTagMapping.getTagField().tagData);
			String label = newTag;
			LxProtocolDevice.SetTagLabels payload = new SetTagLabels( padding, tags, label);
			setTagLabels.setPayload( payload);
			sendMessage( setTagLabels);
		}
		return true;
	}
	
	public LFXTaggedLightCollection getTaggedLightCollectionForTag( String tag)
	{
		for( LFXTaggedLightCollection aCollection : getTaggedLightCollections())
		{
			if( aCollection.getTag().equals( tag))
			{
				return aCollection;
			}
		}
		
		return null;
	}

	// Creates a new Tagged Light Collection
	public LFXTaggedLightCollection addTaggedLightCollectionWithTag( String tag)
	{
		LFXTaggedLightCollection existingCollection = getTaggedLightCollectionForTag( tag);
		if( existingCollection != null) 
		{
			return existingCollection;
		}
		
		// Add the tag to each site
		for( LFXSiteID aSiteID : routingTable.getSiteIDs())
		{
			addTagToSiteWithSiteID( tag, aSiteID);
		}
		
		return getTaggedLightCollectionForTag( tag);
	}

	public void addTagToSiteWithSiteID( String tag, LFXSiteID siteID)
	{
		TagField nextAvailableTagField = new TagField();
		nextAvailableTagField.tagData = new byte[8];
		for( int tagIndex = 0; tagIndex < 64; tagIndex ++)
		{
			LFXByteUtils.clearByteArray( nextAvailableTagField.tagData);
			LFXByteUtils.setBit( nextAvailableTagField.tagData, tagIndex);
			if( routingTable.getTagMappingForSiteIDAndTagField( siteID, nextAvailableTagField) == null)
			{
				//nextAvailableTagField = tagField;
				break;
			}
		}
		
		if( LFXByteUtils.isByteArrayEmpty( nextAvailableTagField.tagData))
		{
			LFXLog.error( "Unable to create tag " + tag + " in site " + siteID.getStringValue() + ", no available tag slots");
		}
		else
		{
			LFXLog.error( "Creating tag " + tag + " in site " + siteID.getStringValue() + " with tagField " + LFXByteUtils.byteArrayToHexString( nextAvailableTagField.tagData));			
			LFXMessage setTagLabels = LFXMessage.messageWithTypeAndPath( Type.LX_PROTOCOL_DEVICE_SET_TAG_LABELS, LFXBinaryPath.getBroadcastBinaryPathWithSiteID( siteID));
			Object padding = new Object();
			UInt64 tags = new UInt64( nextAvailableTagField.tagData);
			String label = tag;
			LxProtocolDevice.SetTagLabels payload = new SetTagLabels( padding, tags, label);
			setTagLabels.setPayload( payload);
			sendMessage( setTagLabels);
		}
	}

	public void deleteTaggedLightCollection( LFXTaggedLightCollection taggedLightCollection)
	{
		for( LFXLight aLight : taggedLightCollection.getLights())
		{
			removeLightFromTaggedLightCollection( aLight, taggedLightCollection);
		}
		
		for( LFXTagMapping aTagMapping : routingTable.getTagMappingsForTag( taggedLightCollection.getTag()))
		{
			LFXMessage setTagLabels = LFXMessage.messageWithTypeAndPath( Type.LX_PROTOCOL_DEVICE_SET_TAG_LABELS, LFXBinaryPath.getBroadcastBinaryPathWithSiteID( aTagMapping.getSiteID()));
			Object padding = new Object();
			UInt64 tags = new UInt64( aTagMapping.getTagField().tagData);
			String label = "";
			LxProtocolDevice.SetTagLabels payload = new SetTagLabels( padding, tags, label);
			setTagLabels.setPayload( payload);
			sendMessage( setTagLabels);
		}
	}

}
