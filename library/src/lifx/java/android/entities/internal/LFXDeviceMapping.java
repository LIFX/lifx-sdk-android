//
//  LFXDeviceMapping.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.entities.internal;

import lifx.java.android.entities.internal.LFXBinaryTargetID.TagField;

public class LFXDeviceMapping
{
	private String deviceID;

	private LFXSiteID siteID;
	private TagField tagField = new TagField();	

	public boolean matchesDeviceID( String deviceID)
	{
		return this.deviceID.equals( deviceID);
	}
	
	public void setDeviceID( String deviceID)
	{
		this.deviceID = deviceID;
	}
	
	public void setSiteID( LFXSiteID siteID)
	{
		this.siteID = siteID;
	}
	
	public void setTagField( TagField tagField)
	{
		this.tagField = tagField;
	}
	
	public String getDeviceId()
	{
		return deviceID;
	}
	
	public LFXSiteID getSiteID()
	{
		return siteID;
	}
	
	public TagField getTagField()
	{
		return tagField;
	}
}
