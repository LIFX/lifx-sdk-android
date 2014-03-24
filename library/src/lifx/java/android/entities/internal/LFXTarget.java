//
//  LFXTarget.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.entities.internal;

public class LFXTarget
{
	public enum LFXTargetType
	{
		BROADCAST,
		DEVICE,
		TAG,
	};
	
	private LFXTargetType targetType;
	private String deviceID;
	private String tag;

	public LFXTargetType getTargetType()
	{
		return targetType;
	}
	
	public String getTag()
	{
		return tag;
	}
	
	public String getDeviceID()
	{
		return deviceID;
	}
	
	public static LFXTarget getBroadcastTarget()
	{
		LFXTarget target = new LFXTarget();
		target.targetType = LFXTargetType.BROADCAST;
		return target;
	}

	public static LFXTarget getDeviceTargetWithDeviceID( String deviceID)
	{
		LFXTarget target = new LFXTarget();
		target.targetType = LFXTargetType.DEVICE;
		target.deviceID = deviceID;
		return target;
	}

	public static LFXTarget getTagTargetWithTag( String tag)
	{
		LFXTarget target = new LFXTarget();
		target.targetType = LFXTargetType.TAG;
		target.tag = tag;
		return target;
	}

	public String toString()
	{
		return "" + getStringValue();
	}

	public String getStringValue()
	{
		switch( targetType)
		{
			case BROADCAST:
				return "*";
			case TAG:
				return "#" + tag;
			case DEVICE:
				return deviceID;
		}
		
		return "Unknown Target Type";
	}

	public static LFXTarget getTargetWithString( String stringValue)
	{	
		if( stringValue.contains( "*"))
		{
			return getBroadcastTarget();
		}
		
		if( stringValue.contains( "#"))
		{
			String tagString = stringValue.substring( stringValue.indexOf( '#') + 1);
			return getTagTargetWithTag( tagString);
		}
		else
		{
			return getDeviceTargetWithDeviceID( stringValue);
		}
	}

	public boolean equals( LFXTarget aTarget)
	{
		if( aTarget == null)
		{
			return false;
		}
		
		if( this.targetType != aTarget.targetType)
		{
			return false;
		}
		
		switch( targetType)
		{
			case BROADCAST:
				return true;
			case DEVICE:
				return deviceID.equals( aTarget.deviceID);
			case TAG:
				return tag.equals( aTarget.tag);
		}
		
		return false;
	}
}
