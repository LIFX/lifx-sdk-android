//
//  LFXBinaryPath.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.entities.internal;

import java.util.StringTokenizer;

public class LFXBinaryPath
{
	public static LFXBinaryPath getPathWithString( String stringPath)
	{
		StringTokenizer tokenizer = new StringTokenizer( stringPath, "/");
		
		int tokens = tokenizer.countTokens();
		
		if( tokens != 2)
		{
			return null;
		}
		
		String siteString = tokenizer.nextToken();
		String targetString = tokenizer.nextToken();
		return getPathWithSiteIDAndTargetID( LFXSiteID.getSiteIDWithString( siteString), LFXBinaryTargetID.getTargetIDWithString( targetString));
	}
	
	public String getStringValue()
	{
		return toString();
	}

	public String toString()
	{
		return siteID.toString() + "/" + targetID.toString();
	}
	
	public static LFXBinaryPath getPathWithSiteIDAndTargetID( LFXSiteID siteID, LFXBinaryTargetID targetID)
	{
		LFXBinaryPath path = new LFXBinaryPath();
		path.siteID = siteID;
		path.targetID = targetID;
		return path;
	}

	private LFXSiteID siteID;
	private LFXBinaryTargetID targetID;
	
	public LFXSiteID getSiteID()
	{
		return siteID;
	}
	
	public LFXBinaryTargetID getBinaryTargetID()
	{
		return targetID;
	}

	public String getDebugStringValue()
	{
		return toString();
	}

	public boolean equals( LFXBinaryPath aBinaryPath)
	{
		if( aBinaryPath == null)
		{
			return false;
		}
		
		if( !this.targetID.equals( aBinaryPath.targetID))
		{
			return false;
		}
		
		if( !this.siteID.equals( aBinaryPath.siteID))
		{
			return false;
		}
		
		return true;
	}

	public static LFXBinaryPath getBroadcastBinaryPathWithSiteID( LFXSiteID siteID)
	{
		return getPathWithSiteIDAndTargetID( siteID, LFXBinaryTargetID.getBroadcastTargetID());
	}
	
	public Object clone()
	{
		LFXBinaryPath newPath = new LFXBinaryPath();
		newPath.siteID = this.siteID;
		newPath.targetID = this.targetID;
		return newPath;
	}
}
