package lifx.java.android.entities.internal;

import java.lang.reflect.InvocationTargetException;

import lifx.java.android.entities.internal.LFXBinaryTargetID.LFXBinaryTargetType;
import lifx.java.android.entities.internal.LFXBinaryTargetID.TagField;
import lifx.java.android.entities.internal.structle.LxProtocol;
import lifx.java.android.entities.internal.structle.LxProtocolDevice;
import lifx.java.android.entities.internal.structle.LxProtocol.Type;
import lifx.java.android.entities.internal.structle.StructleTypes;
import lifx.java.android.entities.internal.structle.StructleTypes.LxProtocolTypeBase;
import lifx.java.android.entities.internal.structle.StructleTypes.UInt64;
import lifx.java.android.util.LFXByteUtils;
import lifx.java.android.util.LFXLog;

public class LFXMessageNew implements Cloneable
{
	private static final String PAYLOAD_SIZE_FIELD_NAME = "PAYLOAD_SIZE";
	
	private static final short ADDRESSABLE_BIT = 0x1000;
	private static final short TAGGED_BIT = 0x2000;
	private static final int PROTOCOL_VERSION_BITS = 0x0FFF;
	
	private static final short ACKNOWLEDGEMENT_BIT = 0x0001;
	
	private static final int LX_PROTOCOL_V1 = 1024;
	private static final int CURRENT_PROTOCOL = LX_PROTOCOL_V1;
	private static final int BASE_MESSAGE_SIZE = 36;
	private static final int PAYLOAD_START_INDEX = 36;
	
	enum LFXMessageDirection
	{
		INCOMING,
		OUTGOING,
	};

	// This is the factory method which should be used for data off the network.
	// It will automatically select the correct class and handle parsing for you.

	private long timestamp;		// When the message was received (incoming) or created (outgoing)

	private LFXMessageDirection messageDirection;		// incoming/outgoing
	private LFXGatewayDescriptor gatewayDescriptor;	// will be set by the message router when sent/received

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
	private boolean isNonProtocolMessage;
	private byte[] rawData;

	// Routing Preferences
	private boolean prefersUDPOverTCP;
	
	// ***************************************************

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
	
	private static int getSizeFromMessageData( byte[] data)
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
	
	public static LFXMessageNew messageWithMessageData( byte [] data)
	{
		byte[] bytes = new byte[data.length];
		LFXByteUtils.copyBytesIntoByteArray( bytes, data);
		
		if( !messageIsAddressable( bytes))
		{
			// We don't know how to deal with non-addressable messages, but the bulbs are sometimes not setting this flag correctly
			LFXLog.Warn( "Warning: Message claims to be non-addressable: " + data);
			return null;
		}
		
		int protocol = getProtocolFromMessageData( bytes);
		if( protocol != LX_PROTOCOL_V1)
		{
			LFXLog.Warn( "Handling non-protocol message of protocol " + protocol);
			return LFXMessageNew.initWithNonProtocolMessageData( data);
		}
		
		return LFXMessageNew.initWithMessageData( data);	//[[class alloc] initWithMessageData:data];
	}

	private static LFXMessageNew initWithMessageData( byte[] data)
	{
		LFXMessageNew message = new LFXMessageNew();
		message.timestamp = System.currentTimeMillis();
		message.messageDirection = LFXMessageDirection.INCOMING;
		
		byte[] bytes = new byte[data.length];
		LFXByteUtils.copyBytesIntoByteArray( bytes, data);
		
		message.size = getSizeFromMessageData( bytes);
		message.protocol = getProtocolFromMessageData( bytes);
		message.atTime = getAtTimeFromMessageData( data);
		
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

	private static LFXMessageNew initWithNonProtocolMessageData( byte[] data)
	{
		LFXMessageNew message = new LFXMessageNew();
		message.timestamp = System.currentTimeMillis();
		message.messageDirection = LFXMessageDirection.INCOMING;
		
		byte[] bytes = new byte[data.length];
		LFXByteUtils.copyBytesIntoByteArray( bytes, data);
		
		message.size = getSizeFromMessageData( data);
		message.protocol = getProtocolFromMessageData( data);
		
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
		//LFXBinaryTargetID *target = frame.tagged ? [LFXBinaryTargetID groupTargetIDWithTagField:*((tagField_t *)&frame.target)] : [LFXBinaryTargetID deviceTargetIDWithData:[NSData dataWithBytes:frame.target length:6]];
		message.path = LFXBinaryPath.getPathWithSiteIDAndTargetID( site, target);
		
		message.rawData = data;
		message.isNonProtocolMessage = true;
		
		return message;
	}

	private LFXMessageNew()
	{
		
	}
	
	private static LFXMessageNew init( Type messageType)	// for outgoing messages
	{
		LFXMessageNew message = new LFXMessageNew();
		
		message.timestamp = System.currentTimeMillis();
		message.messageDirection = LFXMessageDirection.OUTGOING;
		message.protocol = LX_PROTOCOL_V1;
			
		message.path = LFXBinaryPath.getPathWithSiteIDAndTargetID( LFXSiteID.getZeroSiteID(), LFXBinaryTargetID.getBroadcastTargetID());
		message.payload = null;
		
		return message;
	}
	
	private int getMessageDataRepresentationLength()
	{
		int prePayloadLength = BASE_MESSAGE_SIZE;
		
		Class<? extends LxProtocolTypeBase> payloadClass = LxProtocol.typeClassMap.get( messageType);
		int payloadLength = 0;
		
		try
		{
			payloadLength = payloadClass.getField( PAYLOAD_SIZE_FIELD_NAME).getInt( null);
		} 
		catch( IllegalAccessException e)
		{
			e.printStackTrace();
		} 
		catch( IllegalArgumentException e)
		{
			e.printStackTrace();
		} 
		catch( NoSuchFieldException e)
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
	
	private byte[] getMessageDataRepresentation()
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
		
		writeSizeToMessage( (short)size, data);
		writeProtocolToMessage( (short)protocol, data);
		writeIsAddressableToMessage( true, data);
		writeAtTimeToMessage( atTime, data);
		writeTypeToMessage( messageType, data);
		writeSiteIDToMessage( path.getSiteID().getDataValue(), data);
		
		if( path.getBinaryTargetID().geTargetType() != LFXBinaryTargetType.DEVICE)
		{
			writeTargetIDtoMessage( path.getBinaryTargetID().getGroupTagField().tagData, data);
			writeIsTaggedToMessage( true, data);
		}
		else
		{
			writeTargetIDtoMessage( path.getBinaryTargetID().getDeviceDataValue(), data);
			writeIsTaggedToMessage( false, data);
		}
		
//		protocol.size = _size;
//		protocol.protocol = _protocol;
//		protocol.addressable = 1;
//		protocol.at_time = _atTime;
//		protocol.type = [self messageType];
		
//		[_path.siteID.dataValue getBytes:protocol.site];
//		
//		if (_path.targetID.targetType == LFXBinaryTargetTypeDevice)
//		{
//			protocol.tagged = 0;
//			[_path.targetID.deviceDataValue getBytes:protocol.target];
//		}
//		else
//		{
//			protocol.tagged = 1;
//			tagField_t tagField = _path.targetID.groupTagField;
//			memcpy(protocol.target, &tagField, sizeof(tagField));
//		}
		
		if( payload != null)
		{
			byte[] payloadData = payload.getBytes();
			
			LFXByteUtils.copyBytesIntoByteArrayAtOffset( data, payloadData, PAYLOAD_START_INDEX);
		}
		
//		NSData *payloadData = self.payload.dataValue;
//		NSUInteger prePayloadLength = __offsetof(lx_protocol_t, payload);
//		protocol.size = prePayloadLength + payloadData.length;
//		[data appendBytes:&protocol length:prePayloadLength];
//		[data appendData:payloadData];
//		
		return data;
	}

	//+ (instancetype)messageWithPath:(LFXBinaryPath *)path
	//{
//		LFXMessage *message = [[self alloc] init];
//		message.path = path;
//		return message;
	//}
	//
	//+ (instancetype)messageWithSite:(LFXSiteID *)site
	//{
//		return [self messageWithPath:[LFXBinaryPath pathWithSiteID:site targetID:[LFXBinaryTargetID broadcastTargetID]]];
	//}

	public static LFXMessageNew messageWithTypeAndTarget( Type type, LFXTarget target)
	{
		LFXMessageNew message = LFXMessageNew.init( type);
		message.target = target;
		return message;
	}

	public static LFXMessageNew messageWithTypeAndTarget( Type type, LFXBinaryPath path)
	{
		LFXMessageNew message = LFXMessageNew.init( type);
		message.path = path;
		return message;
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

//	- (NSString *)description
//	{
//		NSMutableString *str = [NSMutableString new];
//		[str appendFormat:@"<%@ %p>", self.class, self];
//		[str appendString:self.messageDirection == LFXMessageDirectionOutgoing ? @" out" : @" in"];
//		[str appendFormat:@" path = %@, protocol = %i, type = %i, timestamp = %@", _path.debugStringValue, _protocol, self.messageType, _timestamp];
//		for (NSString *aPropertyKey in self.payload.propertyKeysToBeAddedToDescription)
//		{
//			[str appendFormat:@" %@ = %@", aPropertyKey, [self.payload valueForKey:aPropertyKey]];
//		}
//		return str;
//	}

//	- (NSString *)niceLoggingDescription
//	{
//		NSMutableString *str = [NSMutableString new];
//		
//		NSString *messageName = NSStringFromClass(self.class);
//		if ([messageName hasPrefix:@"LFXMessage"]) messageName = [messageName substringFromIndex:@"LFXMessage".length];
//		[str appendString:messageName];
//		[str appendFormat:@" path = %@,", _path.debugStringValue];
//		for (NSString *aPropertyKey in self.payload.propertyKeysToBeAddedToDescription)
//		{
//			[str appendFormat:@" %@ = %@", aPropertyKey, [self.payload valueForKey:aPropertyKey]];
//		}
//		return str;
//	}


	@Override
	protected Object clone() throws CloneNotSupportedException
	{
		LFXMessageNew message = new LFXMessageNew();
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
		return message;
	}
	
//	- (id)copyWithZone:(NSZone *)zone
//	{
//		LFXMessage *message = [[self class] new];
//		message->_timestamp = self->_timestamp;
//		message->_messageDirection = self->_messageDirection;
//		message->_gatewayDescriptor = self->_gatewayDescriptor;
//		message->_sourceNetworkHost = self->_sourceNetworkHost;
//		message->_size = self->_size;
//		message->_protocol = self->_protocol;
//		message->_path = self->_path;
//		message->_target = self->_target;
//		message->_atTime = self->_atTime;
//		message->_payload = self->_payload;
//		return message;
//	}
}
