package lifx.java.android.entities.internal;

import java.util.StringTokenizer;

public class LFXBinaryPath implements Cloneable
{
	public static LFXBinaryPath getPathWithString( String stringPath)
	{
		StringTokenizer tokenizer = new StringTokenizer( stringPath, "/");
		
		int tokens = tokenizer.countTokens();
		
		if( tokens != 2)
		{
			// LFXLogError( "Error: Path doesn't contain 2 elements separated by a '/': " + stringPath);
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
			System.out.println( "TargetIDS are not equal.");
			return false;
		}
		
		if( !this.siteID.equals( aBinaryPath.siteID))
		{
			System.out.println( "SiteIDs are not equal.");
			return false;
		}
		
		return true;
	}

	public static LFXBinaryPath getBroadcastBinaryPathWithSiteID( LFXSiteID siteID)
	{
		return getPathWithSiteIDAndTargetID( siteID, LFXBinaryTargetID.getBroadcastTargetID());
	}
	
	public int hash()
	{
		return this.targetID.hash() ^ this.siteID.hash();
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException
	{
		LFXBinaryPath newPath = new LFXBinaryPath();
		newPath.siteID = this.siteID;
		newPath.targetID = this.targetID;
		return newPath;
	}
}
