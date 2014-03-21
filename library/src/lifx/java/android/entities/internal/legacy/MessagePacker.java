package lifx.java.android.entities.internal.legacy;

import lifx.java.android.entities.internal.structle.LxProtocol;
import lifx.java.android.entities.internal.structle.StructleTypes.UInt64;

public class MessagePacker 
{
	private static final int BASE_MESSAGE_SIZE = 36;			// without a payload, the message size is 36 bytes
	private static final short ADDRESSABLE_BIT = 0x1000;
	private static final short TAGGED_BIT = 0x2000;
	private static final short ACKNOWLEDGEMENT_BIT = 0x0001;
	private static final int PROTOCOL_VERSION_BITS = 0x0FFF;
	
	private static final int CURRENT_PROTOCOL = 1024;
	
	public static void turnOffAckFlag( byte[] messageData)
	{
		short reserved = 0x0000;
		
//		if( ack)
//		{
//			reserved = (short) (reserved | ACKNOWLEDGEMENT_BIT);
//		}
		
		messageData[22] = (byte)(reserved & 0xff);
		messageData[23] = (byte)((reserved >> 8) & 0xff);
	}
	
	public static byte[] copyMessage( byte[] messageData)
	{
		byte[] newMessageData = new byte[messageData.length];
		
		for( int i = 0; i < messageData.length; i++)
		{
			newMessageData[i] = messageData[i];
		}
		
		return newMessageData;
	}
	
	public static byte[] copyMessageWithNewTarget( byte[] messageData, byte[] target, byte[] siteId)
	{
		byte[] newMessageData = new byte[messageData.length];
		
		for( int i = 0; i < messageData.length; i++)
		{
			newMessageData[i] = messageData[i];
		}
		
		short protocol = 0x0000;
		boolean tagged = false;
		if( target.length == 8)
		{
			tagged = true;
		}
		
		// ********** PACK THE PROTOCOL DATA
		if( true)
		{
			protocol = (short) (protocol | ADDRESSABLE_BIT);
		}
		
		if( tagged)
		{
			protocol = (short) (protocol | TAGGED_BIT);
		}
		
		short version = (CURRENT_PROTOCOL & PROTOCOL_VERSION_BITS);
		
		protocol = (short) (protocol | version);
		
		newMessageData[2] = (byte)(protocol & 0xff);
		newMessageData[3] = (byte)((protocol >> 8) & 0xff);
		
		// ********** PACK THE ADDRESS DATA
		int targetOffsetIndex = 8;
		for( int i = 0; i < target.length; i++)
		{
			newMessageData[targetOffsetIndex + i] = target[i];
		}
		
		int siteOffsetIndex = 16;
		for( int i = 0; i < siteId.length; i++)
		{
			newMessageData[siteOffsetIndex + i] = siteId[i];
		}
		
		return newMessageData;
	}
	
	public static byte[] packMessage( LxProtocol.Type type, byte[] target, byte[] siteId, boolean addressable, long at_time, int payloadSize, boolean ack)
	{
		int typeValue = LxProtocol.typeValueMap.get( type); 
		short messageSize = (short) (BASE_MESSAGE_SIZE + payloadSize);
		
		byte[] messageData = new byte[messageSize];
		
		// ********** PACK THE SIZE DATA
		messageData[0] = (byte)(messageSize & 0xff);
		messageData[1] = (byte)((messageSize >> 8) & 0xff);
		
		short protocol = 0x0000;
		boolean tagged = false;
		if( target.length == 8)
		{
			tagged = true;
		}
		
		// ********** PACK THE PROTOCOL DATA
		if( addressable)
		{
			protocol = (short) (protocol | ADDRESSABLE_BIT);
		}
		
		if( tagged)
		{
			protocol = (short) (protocol | TAGGED_BIT);
		}
		
		short version = (CURRENT_PROTOCOL & PROTOCOL_VERSION_BITS);
		
		protocol = (short) (protocol | version);
		
		messageData[2] = (byte)(protocol & 0xff);
		messageData[3] = (byte)((protocol >> 8) & 0xff);
		
		// ********** PACK THE ADDRESS DATA
		int targetOffsetIndex = 8;
		for( int i = 0; i < target.length; i++)
		{
			messageData[targetOffsetIndex + i] = target[i];
		}
		
		int siteOffsetIndex = 16;
		for( int i = 0; i < siteId.length; i++)
		{
			messageData[siteOffsetIndex + i] = siteId[i];
		}
		
		// ************* PACK THE ACKNOWLEDGEMENT FLAG
		// TODO:
		short reserved = 0x0000;
		
		if( ack)
		{
			reserved = (short) (reserved | ACKNOWLEDGEMENT_BIT);
		}
		
		messageData[22] = (byte)(reserved & 0xff);
		messageData[23] = (byte)((reserved >> 8) & 0xff);
		
		// ************* PACK THE TYPE DATA
		short typeValueShort = (short)typeValue;
		messageData[32] = (byte)(typeValueShort & 0xff);
		messageData[33] = (byte)((typeValueShort >> 8) & 0xff);
	
		// ************* PACK AT_TIME
		UInt64 atTime = new UInt64( at_time);
		byte[] atTimeBytes = atTime.getBytes();
		
		messageData[24] = atTimeBytes[0];
		messageData[25] = atTimeBytes[1];
		messageData[26] = atTimeBytes[2];
		messageData[27] = atTimeBytes[3];
		messageData[28] = atTimeBytes[4];
		messageData[29] = atTimeBytes[5];
		messageData[30] = atTimeBytes[6];
		messageData[31] = atTimeBytes[7];
		
		return messageData;
	}
	
	public static byte[] packMessage( LxProtocol.Type type, byte[] target, byte[] siteId, boolean addressable, int payloadSize)
	{
		return packMessage( type, target, siteId, addressable, 0, payloadSize, false);
	}
}
