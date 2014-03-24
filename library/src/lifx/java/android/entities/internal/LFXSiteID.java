//
//  LFXSiteID.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.entities.internal;

import lifx.java.android.util.LFXByteUtils;

public class LFXSiteID
{
	private static final int LFX_SITE_ID_NUMBER_OF_BYTES = 6;
	
	private byte[] data;
	
	private LFXSiteID()
	{
		data = new byte[LFX_SITE_ID_NUMBER_OF_BYTES];
	}
	
	// The canonical representation of a Site ID is a hex string. This is the form
	// used in the LIFX Cloud API.
	public static LFXSiteID getSiteIDWithString( String siteIDString)
	{
		byte[] data = LFXByteUtils.hexStringToByteArray( siteIDString);
		return getSiteIDWithData( data);
	}
	
	public String getStringValue()
	{
		if( this.isZeroSite()) 
		{
			return "*";
		}
		
		return LFXByteUtils.byteArrayToHexString( data);
	}

	// The LIFX Protocol uses 6-bytes to represent a Site ID
	public static LFXSiteID getSiteIDWithData( byte[] data)
	{
		LFXSiteID siteID = new LFXSiteID();
		LFXByteUtils.copyBytesIntoByteArray( siteID.data, data);
		return siteID;
	}
	
	public byte[] getDataValue()
	{
		return data;
	}

	// When a device hasn't been added to a site yet, it will have a 'zero' Site ID.
	public static LFXSiteID getZeroSiteID()
	{
		byte[] bytes = new byte[]{0,0,0,0,0,0};
		return getSiteIDWithData( bytes);
	}
	
	public boolean isZeroSite()
	{
		return LFXByteUtils.isByteArrayEmpty( data);
	}

	// A shorter string representation - useful for logging and diagnostics
	public String getDebugStringValue()
	{
		return "LFXSiteID: " + LFXByteUtils.byteArrayToHexString( data);
	}

	public String toString()
	{
		return getDebugStringValue();
	}

	public boolean equals( LFXSiteID aSiteID)
	{
		if( aSiteID == null)
		{
			return false;
		}
		
		if( !LFXByteUtils.areByteArraysEqual( data, aSiteID.data) )
		{
			return false;
		}
		
		return true;
	}

	@Override
	public Object clone()
	{	
		return getSiteIDWithData( data);
	}
}
