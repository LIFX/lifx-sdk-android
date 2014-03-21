package lifx.java.android.entities.internal.legacy;

import java.lang.reflect.InvocationTargetException;

import lifx.java.android.entities.internal.LFXMessage;
import lifx.java.android.entities.internal.structle.LxProtocol;
import lifx.java.android.entities.internal.structle.StructleTypes;
import lifx.java.android.entities.internal.structle.StructleTypes.LxProtocolTypeBase;

public class MessageUnPacker 
{
	private static final short ADDRESSABLE_BIT = 0x1000;
	private static final short TAGGED_BIT = 0x2000;
	private static final int PROTOCOL_VERSION_BITS = 0x0FFF;
	
	private static final int CURRENT_PROTOCOL = 1024;
	private static final int TYPE_INDEX = 32;
	private static final int TYPE_SIZE = 2;
    
	public static LxProtocol.Type getMessageType( byte[] data)
	{
		if( data != null && data.length >= (TYPE_INDEX + TYPE_SIZE))
		{
			short type = StructleTypes.getShortValue( data[32], data[33]);
			return LxProtocol.typeMap.get( (int)type);
		}
		
		return null;
	}
	
	public static boolean getProtocolVersion( byte[] data)
	{
		// TODO: jdhfjsdhfkjsdkjfksdjfkxs
		
		short protocol = StructleTypes.getShortValue( data[2], data[3]);
		
		if( (protocol & TAGGED_BIT) != 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean isTagged( byte[] data)
	{
		short protocol = StructleTypes.getShortValue( data[2], data[3]);
		
		if( (protocol & TAGGED_BIT) != 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static byte[] getTarget( byte[] data)
	{
		short protocol = StructleTypes.getShortValue( data[2], data[3]);
		
		if( (protocol & TAGGED_BIT) != 0)
		{
			byte[] target = new byte[]{ data[8], data[9], data[10], data[11], data[12], data[13], data[14], data[15]};
			return target;
		}
		else
		{
			byte[] target = new byte[]{ data[8], data[9], data[10], data[11], data[12], data[13]};
			return target;
		}
	}
	
	public static byte[] getSite( byte[] data)
	{
		byte[] site = new byte[]{ data[16], data[17], data[18], data[19], data[20], data[21]};
		return site;
	}
	
//	public static boolean dataContainsValidLFXMessage( byte[] message)
//	{
//		LFXMessage unPacked = unPackMessage( message, true, null);
//		
//		if( unPacked == null)
//		{
//			return false;
//		}
//		else
//		{
//			return true;
//		}
//	}
	
	
}
