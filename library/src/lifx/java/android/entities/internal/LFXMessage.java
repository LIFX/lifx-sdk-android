//
//  LFXMessage.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.entities.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lifx.java.android.entities.internal.LFXBinaryTargetID.LFXBinaryTargetType;
import lifx.java.android.entities.internal.LFXBinaryTargetID.TagField;
import lifx.java.android.entities.internal.structle.LxProtocol;
import lifx.java.android.entities.internal.structle.LxProtocol.Type;
import lifx.java.android.entities.internal.structle.StructleTypes;
import lifx.java.android.entities.internal.structle.StructleTypes.LxProtocolTypeBase;
import lifx.java.android.entities.internal.structle.StructleTypes.UInt64;
import lifx.java.android.util.LFXByteUtils;
import lifx.java.android.util.LFXLog;

public class LFXMessage
{
	private static final String PAYLOAD_SIZE_METHOD_NAME = "getPayloadSize";
	
	private static final short ADDRESSABLE_BIT = 0x1000;
	private static final short TAGGED_BIT = 0x2000;
	private static final int PROTOCOL_VERSION_BITS = 0x0FFF;
	
	//private static final short ACKNOWLEDGEMENT_BIT = 0x0001;
	
	private static final int LX_PROTOCOL_V1 = 1024;
	private static final int CURRENT_PROTOCOL = LX_PROTOCOL_V1;
	private static final int BASE_MESSAGE_SIZE = 36;
	private static final int PAYLOAD_START_INDEX = 36;
	
	enum LFXMessageDirection
	{
		INCOMING,
		OUTGOING,
	};

	private long timestamp;								// When the message was received (incoming) or created (outgoing)

	private LFXMessageDirection messageDirection;		// incoming/outgoing
	private LFXGatewayDescriptor gatewayDescriptor;		// will be set by the message router when sent/received

	// Network host (this will be set by the Message Router to be the host of the receiving
	// network connection). For outgoing messages, this will be nil.
	private String sourceNetworkHost;

	private Type messageType;

	// For outgoing messages
	private LFXTarget target;

	// LIFX Protocol Header Properties
	private int size;
	private int protocol;
	private LFXBinaryPath path;
	private long atTime;
	private LxProtocolTypeBase payload;

	// Non-protocol messages
	@SuppressWarnings( "unused")
	private boolean isNonProtocolMessage = false;
	@SuppressWarnings( "unused")
	private byte[] rawData;

	// Routing Preferences
	private boolean prefersUDPOverTCP = false;
	
	private static boolean messageIsAddressable( byte[] data)
	{
		boolean addressable = false;
		short protocol = StructleTypes.getShortValue( data[2], data[3]);
		
		if( (protocol & ADDRESSABLE_BIT) != 0)
		{
			addressable = true;
		}
		else
		{
			addressable = false;
		}
		
		return addressable;
	}
	
	private static int getProtocolFromMessageData( byte[] data)
	{
		short protocol = StructleTypes.getShortValue( data[2], data[3]);
		int protocolVersion = (protocol & PROTOCOL_VERSION_BITS);
		return protocolVersion;
	}
	
	public static int getSizeFromMessageData( byte[] data)
	{
		int size = StructleTypes.getShortValue( data[0] , data[1]);
		return size;
	}
	
	private static long getAtTimeFromMessageData( byte[] data)
	{
		byte[] atTimeArray = new byte[8];
		
		atTimeArray[0] = data[24];
		atTimeArray[1] = data[25];
		atTimeArray[2] = data[26];
		atTimeArray[3] = data[27];
		atTimeArray[4] = data[28];
		atTimeArray[5] = data[29];
		atTimeArray[6] = data[30];
		atTimeArray[7] = data[31];
		
		return StructleTypes.getLongValue( atTimeArray[0], atTimeArray[1], atTimeArray[2], atTimeArray[3], atTimeArray[4], atTimeArray[5], atTimeArray[6], atTimeArray[7]);
	}
	
	private static byte[] getSiteIDFromMessageData( byte[] data)
	{
		byte[] siteIdArray = new byte[6];
		siteIdArray[0] = data[16];
		siteIdArray[1] = data[17];
		siteIdArray[2] = data[18];
		siteIdArray[3] = data[19];
		siteIdArray[4] = data[20];
		siteIdArray[5] = data[21];
		
		return siteIdArray;
	}
	
	private static byte[] getTargetFromMessageData( byte[] data)
	{
		boolean tagged = getIsTaggedFromMessageData( data);
		byte[] targetByteArray = null;
	    
	    if( tagged)
	    {
	    	targetByteArray = new byte[8];
	    	targetByteArray[0] = data[8];
			targetByteArray[1] = data[9];
			targetByteArray[2] = data[10];
			targetByteArray[3] = data[11];
			targetByteArray[4] = data[12];
			targetByteArray[5] = data[13];
			targetByteArray[6] = data[14];
			targetByteArray[7] = data[15];
	    }
	    else
	    {
	    	targetByteArray = new byte[6];
	    	targetByteArray[0] = data[8];
			targetByteArray[1] = data[9];
			targetByteArray[2] = data[10];
			targetByteArray[3] = data[11];
			targetByteArray[4] = data[12];
			targetByteArray[5] = data[13];
	    }
	    
	    return targetByteArray;
	}
	
	private static boolean getIsTaggedFromMessageData( byte[] data)
	{
		short protocol = StructleTypes.getShortValue( data[2], data[3]);
		boolean tagged = false;
		
		if( (protocol & TAGGED_BIT) != 0)
		{
			tagged = true;
		}
		else
		{
			tagged = false;
		}
		
		return tagged;
	}
	
	private static Type getTypeFromMessageData( byte[] data)
	{
		short type = StructleTypes.getShortValue( data[32], data[33]);
		
		return LxProtocol.typeMap.get( (int)type);
	}
	
	private static LxProtocolTypeBase getPayloadFromMessageData( byte[] data)
	{
		LxProtocol.Type messageType = getTypeFromMessageData( data);
		Class<? extends LxProtocolTypeBase> messagePayloadClass = LxProtocol.typeClassMap.get( messageType);
		
		LxProtocolTypeBase payload = null;
		
		try 
		{
			payload = messagePayloadClass.getDeclaredConstructor( new Class[]{ byte[].class, int.class}).newInstance( data, PAYLOAD_START_INDEX);
		} 
		catch (InstantiationException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalArgumentException e) 
		{
			e.printStackTrace();
		} 
		catch (InvocationTargetException e) 
		{
			e.printStackTrace();
		} 
		catch (NoSuchMethodException e) 
		{
			e.printStackTrace();
		}
		
		return payload;
	}
	
	public static LFXMessage messageWithMessageData( byte [] data)
	{
		if( data == null || data.length == 0)
		{
			return null;
		}
		
		byte[] bytes = new byte[data.length];
		LFXByteUtils.copyBytesIntoByteArray( bytes, data);
		
		if( !messageIsAddressable( bytes))
		{
			// We don't know how to deal with non-addressable messages, but the bulbs are sometimes not setting this flag correctly
			LFXLog.warn( "Warning: Message claims to be non-addressable: " + data);
			return null;
		}
		
		int protocol = getProtocolFromMessageData( bytes);
		if( protocol != CURRENT_PROTOCOL)
		{
			LFXLog.warn( "Handling non-protocol message of protocol " + protocol);
			return LFXMessage.initWithNonProtocolMessageData( data);
		}
		
		return LFXMessage.initWithMessageData( data);	//[[class alloc] initWithMessageData:data];
	}

	private static LFXMessage initWithMessageData( byte[] data)
	{
		Type messageType = getTypeFromMessageData( data);
		
		LFXMessage message = new LFXMessage( messageType);
		message.timestamp = System.currentTimeMillis();
		message.messageDirection = LFXMessageDirection.INCOMING;
		
		byte[] bytes = new byte[data.length];
		LFXByteUtils.copyBytesIntoByteArray( bytes, data);
		
		message.size = getSizeFromMessageData( bytes);
		message.protocol = getProtocolFromMessageData( bytes);
		message.atTime = getAtTimeFromMessageData( bytes);
		message.messageType = getTypeFromMessageData( bytes);
		
		LFXSiteID site = LFXSiteID.getSiteIDWithData( getSiteIDFromMessageData( bytes));
		
		LFXBinaryTargetID target = null;
		if( getIsTaggedFromMessageData( bytes))
		{
			TagField tagField = new TagField();
			tagField.tagData = getTargetFromMessageData( bytes);
			target = LFXBinaryTargetID.getGroupTargetIDWithTagField( tagField);
		}
		else
		{
			target = LFXBinaryTargetID.getDeviceTargetIDWithData( getTargetFromMessageData( bytes));
		}
		
		message.path = LFXBinaryPath.getPathWithSiteIDAndTargetID( site, target);
		
		message.payload = getPayloadFromMessageData( bytes);
		
		message.rawData = data;
			
		return message;
	}

	private static LFXMessage initWithNonProtocolMessageData( byte[] data)
	{
		Type type = getTypeFromMessageData( data);
		
		LFXMessage message = new LFXMessage( type);
		message.timestamp = System.currentTimeMillis();
		message.messageDirection = LFXMessageDirection.INCOMING;
		
		byte[] bytes = new byte[data.length];
		LFXByteUtils.copyBytesIntoByteArray( bytes, data);
		
		message.size = getSizeFromMessageData( data);
		message.protocol = getProtocolFromMessageData( data);
		message.messageType = getTypeFromMessageData( data);
		
		LFXSiteID site = LFXSiteID.getSiteIDWithData( getSiteIDFromMessageData( data));
		
		LFXBinaryTargetID target = null;
		if( getIsTaggedFromMessageData( bytes))
		{
			TagField tagField = new TagField();
			tagField.tagData = getTargetFromMessageData( bytes);
			target = LFXBinaryTargetID.getGroupTargetIDWithTagField( tagField);
		}
		else
		{
			target = LFXBinaryTargetID.getDeviceTargetIDWithData( getTargetFromMessageData( bytes));
		}
		
		message.path = LFXBinaryPath.getPathWithSiteIDAndTargetID( site, target);
		
		message.rawData = data;
		message.isNonProtocolMessage = true;
		
		return message;
	}

	private LFXMessage( Type messageType)
	{
		this.messageType = messageType;
	}
	
	private static LFXMessage init( Type messageType)	// for outgoing messages
	{
		LFXMessage message = new LFXMessage( messageType);
		
		message.timestamp = System.currentTimeMillis();
		message.messageDirection = LFXMessageDirection.OUTGOING;
		message.protocol = CURRENT_PROTOCOL;
			
		message.path = LFXBinaryPath.getPathWithSiteIDAndTargetID( LFXSiteID.getZeroSiteID(), LFXBinaryTargetID.getBroadcastTargetID());
		message.payload = null;
		message.messageType = messageType;
		
		return message;
	}
	
	private int getMessageDataRepresentationLength()
	{
		int prePayloadLength = BASE_MESSAGE_SIZE;
		
		Class<? extends LxProtocolTypeBase> payloadClass = LxProtocol.typeClassMap.get( messageType);
		int payloadLength = 0;
		
		try
		{
			Method method = payloadClass.getMethod( PAYLOAD_SIZE_METHOD_NAME);
			Object o = method.invoke( null);
			
			if( o != null)
			{
				payloadLength = (Integer) o;
			}
		} 
		catch( IllegalAccessException e)
		{
			e.printStackTrace();
		} 
		catch( IllegalArgumentException e)
		{
			e.printStackTrace();
		} 
		catch( NoSuchMethodException e)
		{
			e.printStackTrace();
		} 
		catch( InvocationTargetException e)
		{
			e.printStackTrace();
		}
		
		return (payloadLength + prePayloadLength);
	}

	private static void writeSizeToMessage( short size, byte[] data)
	{
		data[0] = (byte)(size & 0xff);
		data[1] = (byte)((size >> 8) & 0xff);
	}
	
	private static void writeProtocolToMessage( short protocol, byte[] data)
	{
		short protocolField = StructleTypes.getShortValue( data[2], data[3]);
		
		short version = (short) (protocol & PROTOCOL_VERSION_BITS);
		
		protocolField = (short) (protocolField | version);
		
		data[2] = (byte)(protocolField & 0xff);
		data[3] = (byte)((protocolField >> 8) & 0xff);
	}
	
	private static void writeIsAddressableToMessage( boolean isAddressable, byte[] data)
	{
		short protocolField = StructleTypes.getShortValue( data[2], data[3]);
		
		if( isAddressable)
		{
			protocolField = (short) (protocolField | ADDRESSABLE_BIT);
		}
		
		data[2] = (byte)(protocolField & 0xff);
		data[3] = (byte)((protocolField >> 8) & 0xff);
	}
	
	private static void writeAtTimeToMessage( long atTime, byte[] data)
	{
		UInt64 wrappedAtTime = new UInt64( atTime);
		byte[] atTimeBytes = wrappedAtTime.getBytes();
		
		data[24] = atTimeBytes[0];
		data[25] = atTimeBytes[1];
		data[26] = atTimeBytes[2];
		data[27] = atTimeBytes[3];
		data[28] = atTimeBytes[4];
		data[29] = atTimeBytes[5];
		data[30] = atTimeBytes[6];
		data[31] = atTimeBytes[7];
	}
	
	private static void writeTypeToMessage( Type type, byte[] data)
	{
		int typeValue = LxProtocol.typeValueMap.get( type); 
		short typeValueShort = (short) typeValue;
		data[32] = (byte) (typeValueShort & 0xff);
		data[33] = (byte) ((typeValueShort >> 8) & 0xff);
	}
	
	private static void writeSiteIDToMessage( byte[] siteID, byte[] data)
	{
		int siteOffsetIndex = 16;
		for( int i = 0; i < siteID.length; i++)
		{
			data[siteOffsetIndex + i] = siteID[i];
		}
	}
	
	private static void writeTargetIDtoMessage( byte[] targetID, byte[] data)
	{
		int targetOffsetIndex = 8;
		for( int i = 0; i < targetID.length; i++)
		{
			data[targetOffsetIndex + i] = targetID[i];
		}
	}
	
	private static void writeIsTaggedToMessage( boolean tagged, byte[] data)
	{
		short protocolField = StructleTypes.getShortValue( data[2], data[3]);
		
		if( tagged)
		{
			protocolField = (short) (protocolField | TAGGED_BIT);
		}
		
		data[2] = (byte)(protocolField & 0xff);
		data[3] = (byte)((protocolField >> 8) & 0xff);
	}
	
	public byte[] getMessageDataRepresentation()
	{
		byte[] data = null;
		
		if( payload != null)
		{
			data = new byte[getMessageDataRepresentationLength()];
		}
		else
		{
			data = new byte[BASE_MESSAGE_SIZE];
		}
		
		writeSizeToMessage( (short) data.length, data);
		writeProtocolToMessage( (short) protocol, data);
		writeIsAddressableToMessage( true, data);
		writeAtTimeToMessage( atTime, data);
		writeTypeToMessage( messageType, data);
		writeSiteIDToMessage( path.getSiteID().getDataValue(), data);
		
		if( path.getBinaryTargetID().geTargetType() == LFXBinaryTargetType.DEVICE)
		{
			writeTargetIDtoMessage( path.getBinaryTargetID().getDeviceDataValue(), data);
			writeIsTaggedToMessage( false, data);
		}
		else
		{
			writeTargetIDtoMessage( path.getBinaryTargetID().getGroupTagField().tagData, data);
			writeIsTaggedToMessage( true, data);
		}
		
		if( payload != null)
		{
			byte[] payloadData = payload.getBytes();
			
			LFXByteUtils.copyBytesIntoByteArrayAtOffset( data, payloadData, PAYLOAD_START_INDEX);
		}
		
		return data;
	}

	public static LFXMessage messageWithType( Type type)
	{
		LFXMessage message = LFXMessage.init( type);
		return message;
	}
	
	public static LFXMessage messageWithTypeAndTarget( Type type, LFXTarget target)
	{
		LFXMessage message = LFXMessage.init( type);
		message.target = target;
		return message;
	}

	public static LFXMessage messageWithTypeAndPath( Type type, LFXBinaryPath path)
	{
		LFXMessage message = LFXMessage.init( type);
		message.path = path;
		return message;
	}

	public long getTimestamp()
	{
		return timestamp;
	}
	
	public Type getType()
	{
		return messageType;
	}

	public boolean isAResponseMessage()
	{
		if( getType() == Type.LX_PROTOCOL_DEVICE_STATE_DUMMY_LOAD ||
			getType() == Type.LX_PROTOCOL_DEVICE_STATE_INFO ||
			getType() == Type.LX_PROTOCOL_DEVICE_STATE_LABEL ||
			getType() == Type.LX_PROTOCOL_DEVICE_STATE_MCU_RAIL_VOLTAGE ||
			getType() == Type.LX_PROTOCOL_DEVICE_STATE_MESH_FIRMWARE ||
			getType() == Type.LX_PROTOCOL_DEVICE_STATE_MESH_INFO ||
			getType() == Type.LX_PROTOCOL_DEVICE_STATE_PAN_GATEWAY ||
			getType() == Type.LX_PROTOCOL_DEVICE_STATE_POWER ||
			getType() == Type.LX_PROTOCOL_DEVICE_STATE_RESET_SWITCH ||
			getType() == Type.LX_PROTOCOL_DEVICE_STATE_TAG_LABELS ||
			getType() == Type.LX_PROTOCOL_DEVICE_STATE_TAGS ||
			getType() == Type.LX_PROTOCOL_DEVICE_STATE_TIME ||
			getType() == Type.LX_PROTOCOL_DEVICE_STATE_VERSION ||
			getType() == Type.LX_PROTOCOL_DEVICE_STATE_WIFI_FIRMWARE ||
			getType() == Type.LX_PROTOCOL_DEVICE_STATE_WIFI_INFO ||
			getType() == Type.LX_PROTOCOL_LIGHT_STATE ||
			getType() == Type.LX_PROTOCOL_LIGHT_STATE_RAIL_VOLTAGE ||
			getType() == Type.LX_PROTOCOL_LIGHT_STATE_TEMPERATURE)
		{
			return true;
		}
		
		return false;
	}

	public Object clone()
	{
		LFXMessage message = new LFXMessage( this.messageType);
		message.timestamp = this.timestamp;
		message.messageDirection = this.messageDirection;
		message.gatewayDescriptor = this.gatewayDescriptor;
		message.sourceNetworkHost = this.sourceNetworkHost;
		message.size = this.size;
		message.protocol = this.protocol;
		message.path = this.path;
		message.target = this.target;
		message.atTime = this.atTime;
		message.payload = this.payload;
		message.messageType = this.messageType;
		return message;
	}
	
	public void setPayload( LxProtocolTypeBase payload)
	{
		this.payload = payload;
	}
	
	public LxProtocolTypeBase getPayload()
	{
		return payload;
	}
	
	public LFXBinaryPath getPath()
	{
		return path;
	}
	
	public void setPath( LFXBinaryPath path)
	{
		this.path = path;
	}
	
	public LFXTarget getTarget()
	{
		return target;
	}
	
	public LFXGatewayDescriptor getGatewayDescriptor()
	{
		return gatewayDescriptor;
	}
	
	public void setGatewayDescriptor( LFXGatewayDescriptor gatewayDescriptor)
	{
		this.gatewayDescriptor = gatewayDescriptor;
	}
	
	public String getSourceNetworkHost()
	{
		return sourceNetworkHost;
	}
	
	public void setSourceNetworkHost( String sourceNetworkHost)
	{
		this.sourceNetworkHost = sourceNetworkHost;
	}
	
	public boolean prefersUDPOverTCP()
	{
		return prefersUDPOverTCP;
	}
	
	public void setPrefersUDPOverTCP( boolean prefersUDPOverTCP)
	{
		this.prefersUDPOverTCP = prefersUDPOverTCP;
	}
}
