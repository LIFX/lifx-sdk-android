//
//  LFXTagMapping.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.entities.internal;

import lifx.java.android.entities.internal.LFXBinaryTargetID.TagField;

public class LFXTagMapping
{
	private String tag;

	private LFXSiteID siteID;
	private TagField tagField;

	// Use this for uniqueing a TagMapping
	public boolean matchesSiteIDAndTagField( LFXSiteID siteID, TagField tagField)
	{
		return tagField.equals( this.tagField) && siteID.equals( this.siteID);
	}

	// Use this for routing outgoing messages
	public boolean matchesTag( String tag)
	{
		return tag.equals( this.tag);
	}
	
	public void setTagField( TagField tagField)
	{
		this.tagField = tagField;
	}
	
	public void setTag( String tag)
	{
		this.tag = tag;
	}
	
	public void setSiteID( LFXSiteID siteID)
	{
		this.siteID = siteID;
	}
	
	public TagField getTagField()
	{
		return tagField;
	}
	
	public String getTag()
	{
		return tag;
	}
	
	public LFXSiteID getSiteID()
	{
		return siteID;
	}
}
