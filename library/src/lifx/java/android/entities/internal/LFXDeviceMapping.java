package lifx.java.android.entities.internal;

import lifx.java.android.entities.internal.LFXBinaryTargetID.TagField;

public class LFXDeviceMapping
{
	private String deviceID;

	private LFXSiteID siteID;
	private TagField tagField;	// The tagField indicating what tags the device belongs to in the context of .siteID

	// This is used for uniqueing in the routing table within a Network Context
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
