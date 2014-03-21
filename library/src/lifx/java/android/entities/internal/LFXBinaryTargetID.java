package lifx.java.android.entities.internal;

import java.util.ArrayList;

import lifx.java.android.util.LFXByteUtils;

public class LFXBinaryTargetID implements Cloneable
{
	private static final int DEVICE_TARGET_ID_BYTES = 6;
	private static final int TAG_TARGET_ID_BYTES = 8;
	private static final int TAG_TARGET_ID_BITS = TAG_TARGET_ID_BYTES * 8;
	
	public enum LFXBinaryTargetType
	{
		DEVICE,
		TAG,
		BROADCAST, // Special case of a group
	};
	
	public static class TagField
	{
		public byte[] tagData;
		
		public TagField()
		{
			tagData = new byte[8];
		}
		
		public boolean equals( TagField otherTagField)
		{
			if( otherTagField == null)
			{
				return false;
			}
			
			if( LFXByteUtils.areByteArraysEqual( tagData, otherTagField.tagData))
			{
				return true;
			}
			
			return false;
		}
	}
	
	private LFXBinaryTargetType targetType;
	private TagField groupTagField = new TagField();
	byte[] deviceBytes;
	
	// LFXSiteID instances are immutable

	// Canonical representation is a string
	//
	// Device: 6 byte, 12 char hex string				(704192abcd12)
	// Group: uint64 bitfield prefixed with # in hex	(#8 - group 4)
	// Broadcast: "*"

	public LFXBinaryTargetType getTargetType()
	{
		return targetType;
	}
	
	public static LFXBinaryTargetID getTargetIDWithString( String stringValue)
	{
		if( stringValue.contains( "*"))
		{
			return getBroadcastTargetID();
		}
		
		if( stringValue.contains( "#"))
		{
			String hexString = stringValue.substring( stringValue.indexOf( '#') + 1);
			TagField tagField = new TagField();
			tagField.tagData = LFXByteUtils.hexStringToByteArray( hexString);
			return getGroupTargetIDWithTagField( tagField);
		}
		else
		{
			// Device Target (6 bytes)
			return getDeviceTargetIDWithString( stringValue);
		}
	}

	// Device Targets
	public static LFXBinaryTargetID getDeviceTargetIDWithData( byte[] data)
	{
		LFXBinaryTargetID targetID = new LFXBinaryTargetID();
		targetID.targetType = LFXBinaryTargetType.DEVICE;
		targetID.deviceBytes = new byte[DEVICE_TARGET_ID_BYTES];
		LFXByteUtils.copyBytesIntoByteArrayUpToLength( targetID.deviceBytes, data, DEVICE_TARGET_ID_BYTES);
		return targetID;
	}
	
	public static LFXBinaryTargetID getDeviceTargetIDWithString( String string)
	{
		byte[] data = LFXByteUtils.hexStringToByteArray( string);		//[NSData lfx_dataWithHexString:string];
		return getDeviceTargetIDWithData( data);
	}
	
	public byte[] getDeviceDataValue()
	{
		return deviceBytes;
	}

	// Group Targets
	public static LFXBinaryTargetID getGroupTargetIDWithTagField( TagField tagField)
	{
		LFXBinaryTargetID targetID = new LFXBinaryTargetID();
		targetID.targetType = LFXByteUtils.isByteArrayEmpty( tagField.tagData) ? LFXBinaryTargetType.BROADCAST : LFXBinaryTargetType.TAG;
		targetID.groupTagField = tagField;
		return targetID;
	}

	// Broadcast Targets
	public static LFXBinaryTargetID getBroadcastTargetID()
	{
		LFXBinaryTargetID targetID = new LFXBinaryTargetID();
		targetID.targetType = LFXBinaryTargetType.BROADCAST;
		return targetID;
	}

	// Tag Field Enumeration
	//+ (void)enumerateTagField:(tagField_t)tagField block:(void (^)(tagField_t singularTagField))block;
	
//	public static void enumerateTagField( TagField tagField, Runnable r)				// TODO: ?/??????
//	{
//		
//	}

//	+ (LFXBinaryTargetID *)targetIDWithString:(NSString *)stringValue
//	{
//		
//	}

	public static ArrayList<TagField> enumerateTagField( TagField tagField)
	{
		ArrayList<TagField> singularTagFields = new ArrayList<TagField>();
		
		for( int tagIndex = 0; tagIndex < TAG_TARGET_ID_BITS; tagIndex++)
		{
			if( LFXByteUtils.isBitSet( tagField.tagData, tagIndex))
			{
				TagField tempTagField = new TagField();
				byte[] tempTagBytes = new byte[TAG_TARGET_ID_BYTES];
				LFXByteUtils.setBit( tempTagBytes, tagIndex);
				tempTagField.tagData = tempTagBytes;
				
				singularTagFields.add( tempTagField);
			}
		}
		
		return singularTagFields;
	}
	
	public String getStringValue()
	{
		switch( targetType)
		{
			case BROADCAST:
			{
				return "*";
			}
			case TAG:
			{
				return "#" + LFXByteUtils.byteArrayToHexString( groupTagField.tagData);
			}
			case DEVICE:
			{
				return LFXByteUtils.byteArrayToHexString( deviceBytes);	//[_deviceBytes lfx_hexStringValue];
			}
		}
		
		return "LFXBinaryTarget: Unknown Type";
	}

	public String toString()
	{
		return "<LFXBinaryTargetID " + getDebugStringValue() + ">";
	}

	public LFXBinaryTargetType geTargetType()
	{
		return targetType;
	}

//	public static LFXBinaryTargetID deviceTargetIDWithData:(NSData *)data
//	{
//		
//	}

//	+ (LFXBinaryTargetID *)deviceTargetIDWithString:(NSString *)string
//	{
//		
//	}

//	+ (LFXBinaryTargetID *)groupTargetIDWithTagField:(tagField_t)tagField
//	{
//		
//	}

	public TagField getGroupTagField()
	{
		return groupTagField;
	}


//	+ (LFXBinaryTargetID *)broadcastTargetID
//	{
//		
//	}

	public ArrayList<LFXBinaryTargetID> getIndividualGroupTargetIDs()
	{
		// For future optimisation, this could get generated once, when a groupTargetID is created
		ArrayList<LFXBinaryTargetID> targetIDs = new ArrayList<LFXBinaryTargetID>();
		
		for( int bit = 0; bit < TAG_TARGET_ID_BITS; bit++)
		{
			if( LFXByteUtils.isBitSet( groupTagField.tagData, bit))
			{
				byte[] newTag = new byte[TAG_TARGET_ID_BYTES];
				LFXByteUtils.setBit( newTag, bit);
				TagField tagField = new TagField();
				tagField.tagData = newTag;
				targetIDs.add( getGroupTargetIDWithTagField( tagField));
			}
		}
		
		return targetIDs;
	}

	public String getDebugStringValue()
	{
		if( getTargetType() == LFXBinaryTargetType.DEVICE)
		{
			return LFXByteUtils.byteArrayToHexString( this.deviceBytes);
		}
		else if( getTargetType() == LFXBinaryTargetType.TAG)
		{
			return LFXByteUtils.byteArrayToHexString( this.groupTagField.tagData);
		}
		else
		{
			return LFXByteUtils.byteArrayToHexString( this.groupTagField.tagData);
		}
	}

	public int hash()
	{
		// TODO: 
//		NSUInteger hash = 0;
//		hash ^= _groupTagField;	// This may be crappy in a 32-bit runtime - the top 32 bits will be lost
//		hash ^= _deviceBytes.hash;
		int i = 0;
		return i;
	}

	public boolean equal( LFXBinaryTargetID aTargetID)
	{
		if( aTargetID == null)
		{
			return false;
		}
		
		if( targetType != aTargetID.targetType) 
		{
			return false;
		}
		
		switch( targetType)
		{
			case BROADCAST:
				return true;
			case DEVICE:
				return LFXByteUtils.areByteArraysEqual( deviceBytes, aTargetID.deviceBytes);//[_deviceBytes isEqualToData:aTargetID->_deviceBytes];
			case TAG:
				return LFXByteUtils.areByteArraysEqual( groupTagField.tagData, aTargetID.groupTagField.tagData);
		}
		
		return false;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException
	{
		// TODO Auto-generated method stub
		return super.clone();
	}
}
