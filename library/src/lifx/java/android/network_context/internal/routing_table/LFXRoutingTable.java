//
//  LFXRoutingTable.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.network_context.internal.routing_table;

import java.util.ArrayList;
import java.util.HashMap;

import lifx.java.android.entities.internal.LFXBinaryPath;
import lifx.java.android.entities.internal.LFXBinaryTargetID;
import lifx.java.android.entities.internal.LFXDeviceMapping;
import lifx.java.android.entities.internal.LFXMessage;
import lifx.java.android.entities.internal.LFXSiteID;
import lifx.java.android.entities.internal.LFXTagMapping;
import lifx.java.android.entities.internal.LFXTarget;
import lifx.java.android.entities.internal.LFXBinaryTargetID.TagField;
import lifx.java.android.entities.internal.structle.LxProtocolDevice;
import lifx.java.android.entities.internal.structle.LxProtocolLight;
import lifx.java.android.entities.internal.structle.LxProtocol.Type;

public class LFXRoutingTable
{
	private HashMap<String,LFXDeviceMapping> mutableDeviceMappingsByDeviceID;
	private ArrayList<LFXTagMapping> mutableTagMappings;
	private ArrayList<LFXSiteID> mutableSiteIDs;	// Could be derived from DeviceMappings, but is cached separately for performance reasons

	public LFXRoutingTable()
	{
		super();
		mutableDeviceMappingsByDeviceID = new HashMap<String,LFXDeviceMapping>();
		mutableTagMappings = new ArrayList<LFXTagMapping>();
		mutableSiteIDs = new ArrayList<LFXSiteID>();
	}

	public ArrayList<LFXDeviceMapping> getDeviceMappings()
	{
		ArrayList<LFXDeviceMapping> mappings = new ArrayList<LFXDeviceMapping>();
		mappings.addAll( mutableDeviceMappingsByDeviceID.values());
		return mappings;
	}

	public ArrayList<LFXTagMapping> getTagMappings()
	{
		ArrayList<LFXTagMapping> mappings = new ArrayList<LFXTagMapping>();
		mappings.addAll( this.mutableTagMappings);
		return mappings;
	}

	public ArrayList<LFXSiteID> getSiteIDs()
	{
		ArrayList<LFXSiteID> siteIDs = new ArrayList<LFXSiteID>();
		siteIDs.addAll( this.mutableSiteIDs);
		return siteIDs;
	}

	public void updateMappingsFromMessage( LFXMessage message)
	{
		if( message.isAResponseMessage() == false) 
		{
			return;
		}
		
		updateSiteID( message.getPath().getSiteID());
		updateDeviceMappingWithDeviceIDSiteID( message.getPath().getBinaryTargetID().getStringValue(), message.getPath().getSiteID());
		
		if( message.getType() == Type.LX_PROTOCOL_LIGHT_STATE)
		{
			String path = message.getPath().getBinaryTargetID().getStringValue();
			LFXSiteID siteID = message.getPath().getSiteID();
			
			LxProtocolLight.State payload = (LxProtocolLight.State) message.getPayload();
			TagField tagField = new TagField();
			tagField.tagData = payload.getTags().getBytes();
			
			updateDeviceMappingWithDeviceID( path, siteID, tagField);
		}
		
		if( message.getType() == Type.LX_PROTOCOL_DEVICE_STATE_TAGS)
		{
			String path = message.getPath().getBinaryTargetID().getStringValue();
			LFXSiteID siteID = message.getPath().getSiteID();
			
			LxProtocolDevice.StateTags payload = (LxProtocolDevice.StateTags) message.getPayload();
			TagField tagField = new TagField();
			tagField.tagData = payload.getTags().getBytes();
			
			updateDeviceMappingWithDeviceID( path, siteID, tagField);
		}
		
		if( message.getType() == Type.LX_PROTOCOL_DEVICE_STATE_TAG_LABELS)
		{
			LFXSiteID siteID = message.getPath().getSiteID();
			
			LxProtocolDevice.StateTagLabels payload = (LxProtocolDevice.StateTagLabels) message.getPayload();
			TagField tagField = new TagField();
			tagField.tagData = payload.getTags().getBytes();
			
			ArrayList<TagField> singularTagFields = LFXBinaryTargetID.enumerateTagField( tagField);
			
			for( TagField aTagField : singularTagFields)
			{
				updateTagMappingWithTag( payload.getLabel(), siteID, aTagField);
			}
		}
	}

	public void updateSiteID( LFXSiteID siteID)
	{
		if( mutableSiteIDs.contains( siteID))
		{
			return;
		}
		
		mutableSiteIDs.add( siteID);
	}

	public void updateDeviceMappingWithDeviceIDSiteID( String deviceID, LFXSiteID siteID)
	{
		LFXDeviceMapping deviceMapping = getDeviceMappingForDeviceID( deviceID);
		
		if( deviceMapping == null)
		{
			deviceMapping = new LFXDeviceMapping();
			mutableDeviceMappingsByDeviceID.put( deviceID, deviceMapping);
		}
		
		deviceMapping.setDeviceID( deviceID);
		deviceMapping.setSiteID( siteID);
	}

	public void updateDeviceMappingWithDeviceID( String deviceID, LFXSiteID siteID, TagField tagField)
	{
		LFXDeviceMapping deviceMapping = getDeviceMappingForDeviceID( deviceID);
		
		if( deviceMapping == null)
		{
			deviceMapping = new LFXDeviceMapping();
			mutableDeviceMappingsByDeviceID.put( deviceID, deviceMapping);
		}
		
		deviceMapping.setDeviceID( deviceID);
		deviceMapping.setSiteID( siteID);
		deviceMapping.setTagField( tagField);
	}

	public void updateTagMappingWithTag( String tag, LFXSiteID siteID, TagField tagField)
	{
		if( siteID.isZeroSite()) 
		{
			return;
		}

		LFXTagMapping tagMapping = getTagMappingForSiteIDAndTagField( siteID, tagField);
		
		if( tag.length() > 0)
		{
			if( tagMapping == null)
			{
				tagMapping = new LFXTagMapping();
				mutableTagMappings.add( tagMapping);
			}
			
			tagMapping.setTag( tag);
			tagMapping.setSiteID( siteID);
			tagMapping.setTagField( tagField);
		}
		else
		{
			if( tagMapping != null)
			{
				mutableTagMappings.remove( tagMapping);
			}
		}
	}

	public void resetRoutingTable()
	{
		mutableDeviceMappingsByDeviceID.clear();
		mutableTagMappings.clear();
		mutableSiteIDs.clear();
	}

	public ArrayList<String> getAllTags()
	{
		ArrayList<String> allTags = new ArrayList<String>();
		
		for( LFXTagMapping aTagMapping : mutableTagMappings)
		{
			allTags.add( aTagMapping.getTag());
		}
		
		return allTags;
	}

	@SuppressWarnings( "unchecked")
	public ArrayList<LFXSiteID> getAllSiteIDs()
	{
		return (ArrayList<LFXSiteID>) mutableSiteIDs.clone();
	}

	public LFXDeviceMapping getDeviceMappingForDeviceID( String deviceID)
	{
		return mutableDeviceMappingsByDeviceID.get( deviceID);
	}

	public ArrayList<LFXDeviceMapping> getDeviceMappingsForSiteID( LFXSiteID siteID)
	{
		ArrayList<LFXDeviceMapping> deviceMappings = new ArrayList<LFXDeviceMapping>();
		
		for( LFXDeviceMapping aDeviceMapping : mutableDeviceMappingsByDeviceID.values())
		{
			if( aDeviceMapping.getSiteID().equals( siteID))
			{
				deviceMappings.add( aDeviceMapping);
			}
		}
		
		return deviceMappings;
	}

	public ArrayList<LFXDeviceMapping> getDeviceMappingsForSiteIDTagField( LFXSiteID siteID, TagField tagField)
	{
		ArrayList<LFXDeviceMapping> deviceMappings = new ArrayList<LFXDeviceMapping>();
		
		for( LFXDeviceMapping aDeviceMapping : mutableDeviceMappingsByDeviceID.values())
		{
			if( aDeviceMapping.getSiteID().equals( siteID) &&
				aDeviceMapping.getTagField().equals( tagField))
			{
				deviceMappings.add( aDeviceMapping);
			}
		}
		
		return deviceMappings;
	}

	public LFXTagMapping getTagMappingForSiteIDAndTagField( LFXSiteID siteID, TagField tagField)
	{
		for( LFXTagMapping aTagMapping : mutableTagMappings)
		{
			if( aTagMapping.getSiteID().equals( siteID) &&
				aTagMapping.getTagField().equals( tagField))
			{
				return aTagMapping;
			}
		}
		
		return null;
	}

	public ArrayList<LFXTagMapping> getTagMappingsForTag( String tag)
	{
		ArrayList<LFXTagMapping> tagMappings = new ArrayList<LFXTagMapping>();
		
		for( LFXTagMapping aTagMapping : mutableTagMappings)
		{
			if( aTagMapping.getTag().equals( tag))
			{
				tagMappings.add( aTagMapping);
			}
		}
		
		return tagMappings;
	}

	public ArrayList<LFXBinaryPath> getBinaryPathsForTarget( LFXTarget target)
	{
		ArrayList<LFXBinaryPath> returnPaths = new ArrayList<LFXBinaryPath>();
		
		switch( target.getTargetType())
		{
			case BROADCAST:
			{
				for( LFXSiteID aSiteID : mutableSiteIDs)
				{
					LFXBinaryPath path = LFXBinaryPath.getPathWithSiteIDAndTargetID( aSiteID, LFXBinaryTargetID.getBroadcastTargetID());
					returnPaths.add( path);
				}
				
				break;
			}
			case DEVICE:
			{
				// If we know what site the device is in, send it there, otherwise
				// send to each
				LFXDeviceMapping deviceMapping = getDeviceMappingForDeviceID( target.getDeviceID());
				
				if( deviceMapping != null)
				{
					LFXBinaryPath binaryPath = LFXBinaryPath.getPathWithSiteIDAndTargetID( deviceMapping.getSiteID(), LFXBinaryTargetID.getDeviceTargetIDWithString( target.getDeviceID()));// targetID:[LFXBinaryTargetID deviceTargetIDWithString:target.deviceID]];
					returnPaths.add( binaryPath);
					break;
				}
				
				for( LFXSiteID aSiteID : mutableSiteIDs)
				{
					LFXBinaryPath binaryPath = LFXBinaryPath.getPathWithSiteIDAndTargetID( aSiteID, LFXBinaryTargetID.getDeviceTargetIDWithString( target.getDeviceID()));
					returnPaths.add( binaryPath);
					// TODO: decide if to // break; here
				}
			}
			case TAG:
			{
				// Look up the Tag Mappings
				for( LFXTagMapping aTagMapping : getTagMappingsForTag( target.getTag()))
				{
					LFXBinaryTargetID targetID = LFXBinaryTargetID.getGroupTargetIDWithTagField( aTagMapping.getTagField());
					LFXBinaryPath binaryPath = LFXBinaryPath.getPathWithSiteIDAndTargetID( aTagMapping.getSiteID(), targetID);
					returnPaths.add( binaryPath);
				}
			}
		}
		
		return returnPaths;
	}

	public ArrayList<String> getDeviceIDsForBinaryPath( LFXBinaryPath binaryPath)
	{
		switch( binaryPath.getBinaryTargetID().getTargetType())
		{
			case BROADCAST:
			{
				ArrayList<String> deviceIds = new ArrayList<String>();
				
				for( LFXDeviceMapping aDeviceMapping : getDeviceMappingsForSiteID( binaryPath.getSiteID()))
				{
					deviceIds.add( aDeviceMapping.getDeviceId());
				}
				
				return deviceIds;
			}
			case DEVICE:
			{
				ArrayList<String> deviceIds = new ArrayList<String>();
				deviceIds.add( binaryPath.getBinaryTargetID().getStringValue());
				return deviceIds;
			}
			case TAG:
			{
				ArrayList<String> deviceIds = new ArrayList<String>();
				
				for( LFXDeviceMapping aDeviceMapping : getDeviceMappingsForSiteIDTagField( binaryPath.getSiteID(), binaryPath.getBinaryTargetID().getGroupTagField()))
				{
					deviceIds.add( aDeviceMapping.getDeviceId());
				}
				
				return deviceIds;				
			}
		}
		
		return new ArrayList<String>();
	}
	
	public ArrayList<LFXTagMapping> getTagMappingsForSiteIDAndTag( LFXSiteID siteID, String tag)
	{
		ArrayList<LFXTagMapping> mappings = new ArrayList<LFXTagMapping>();
		
		for( LFXTagMapping aMapping : mutableTagMappings)
		{
			if( aMapping.getSiteID().equals( siteID) && aMapping.getTag().equals( tag))	
			{
				mappings.add( aMapping);
			}
		}
		
		return mappings;
	}
}
