//
//  Lx.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

// Start File: @./Lx.java 

package lifx.java.android.entities.internal.structle;

/*
	**** GENERATED CODE ****
	Start Of File: Lx.java 
*/

import lifx.java.android.entities.internal.structle.StructleTypes.LxProtocolTypeBase;
import lifx.java.android.entities.internal.structle.StructleTypes.ProtocolField;
import lifx.java.android.entities.internal.structle.StructleTypes.RoutingField;
import lifx.java.android.entities.internal.structle.StructleTypes.UInt16;
import lifx.java.android.entities.internal.structle.StructleTypes.UInt32;
import lifx.java.android.entities.internal.structle.StructleTypes.UInt64;

public class Lx
{

	
	public static class Frame extends LxProtocolTypeBase		// Struct: Lx::Frame 
	{
  		// Fields: size, _protocol, reserved;
  		private UInt16 size;			// Field: size - Structle::Uint16 byte offset: 0
  		private ProtocolField _protocol;				// Field: _protocol - Lx::ProtocolField byte offset: 2
  		private UInt32 reserved;			// Field: reserved - Structle::Uint32 byte offset: 4
  
  private static final int PAYLOAD_SIZE = 8;
  
  		public Frame( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public Frame( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[2];
			member0Data[0] = bytes[initialOffset + 0];
			member0Data[1] = bytes[initialOffset + 1];
  			
  				
  			size = new UInt16( member0Data); 
    		
			byte[] member1Data = new byte[2];
			member1Data[0] = bytes[initialOffset + 2];
			member1Data[1] = bytes[initialOffset + 3];
  			
  				
  			_protocol = new ProtocolField( member1Data); 
    		
			byte[] member2Data = new byte[4];
			member2Data[0] = bytes[initialOffset + 4];
			member2Data[1] = bytes[initialOffset + 5];
			member2Data[2] = bytes[initialOffset + 6];
			member2Data[3] = bytes[initialOffset + 7];
  			
  				
  			reserved = new UInt32( member2Data); 
    		
    		}
    		
    		public Frame( Object padding
  		, UInt16 size
  		, ProtocolField _protocol
  		, UInt32 reserved
    			)
    		{
    			this.size = size;
    			this._protocol = _protocol;
    			this.reserved = reserved;
    		}
    		
  		public UInt16  getSize()
    		{
    			return size;
    		}			
  		public ProtocolField  get_protocol()
    		{
    			return _protocol;
    		}				
  		public UInt32  getReserved()
    		{
    			return reserved;
    		}			
    		
    		public void printMessageData()
    		{
  		size.printValue( "size");			// Field: size - Structle::Uint16 byte offset: 8
  		_protocol.printValue( "_protocol");				// Field: _protocol - Lx::ProtocolField byte offset: 8
  		reserved.printValue( "reserved");			// Field: reserved - Structle::Uint32 byte offset: 8
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, UInt16 size
  		, ProtocolField _protocol
  		, UInt32 reserved
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = size.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = _protocol.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = reserved.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, UInt16 size
  		, ProtocolField _protocol
  		, UInt32 reserved
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, size
  		, _protocol
  		, reserved
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = size.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = _protocol.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = reserved.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			
    			return bytes;
    		}
    		
    		public static int getPayloadSize()
    		{
    			return PAYLOAD_SIZE;
    		}
	}
	
	public static class FrameAddress extends LxProtocolTypeBase		// Struct: Lx::FrameAddress 
	{
  		// Fields: size, _protocol, reserved, target, site, _routing;
  		private UInt16 size;			// Field: size - Structle::Uint16 byte offset: 0
  		private ProtocolField _protocol;				// Field: _protocol - Lx::ProtocolField byte offset: 2
  		private UInt32 reserved;			// Field: reserved - Structle::Uint32 byte offset: 4
  		private byte[]  target = new byte[8];		// Field: target - Structle::Bytes byte offset: 8
  		private byte[]  site = new byte[6];		// Field: site - Structle::Bytes byte offset: 16
  		private RoutingField _routing;				// Field: _routing - Lx::RoutingField byte offset: 22
  
  private static final int PAYLOAD_SIZE = 24;
  
  		public FrameAddress( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public FrameAddress( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[2];
			member0Data[0] = bytes[initialOffset + 0];
			member0Data[1] = bytes[initialOffset + 1];
  			
  				
  			size = new UInt16( member0Data); 
    		
			byte[] member1Data = new byte[2];
			member1Data[0] = bytes[initialOffset + 2];
			member1Data[1] = bytes[initialOffset + 3];
  			
  				
  			_protocol = new ProtocolField( member1Data); 
    		
			byte[] member2Data = new byte[4];
			member2Data[0] = bytes[initialOffset + 4];
			member2Data[1] = bytes[initialOffset + 5];
			member2Data[2] = bytes[initialOffset + 6];
			member2Data[3] = bytes[initialOffset + 7];
  			
  				
  			reserved = new UInt32( member2Data); 
    		
			byte[] member3Data = new byte[8];
			member3Data[0] = bytes[initialOffset + 8];
			member3Data[1] = bytes[initialOffset + 9];
			member3Data[2] = bytes[initialOffset + 10];
			member3Data[3] = bytes[initialOffset + 11];
			member3Data[4] = bytes[initialOffset + 12];
			member3Data[5] = bytes[initialOffset + 13];
			member3Data[6] = bytes[initialOffset + 14];
			member3Data[7] = bytes[initialOffset + 15];
  			
  				
  			target = member3Data; 
    		
			byte[] member4Data = new byte[6];
			member4Data[0] = bytes[initialOffset + 16];
			member4Data[1] = bytes[initialOffset + 17];
			member4Data[2] = bytes[initialOffset + 18];
			member4Data[3] = bytes[initialOffset + 19];
			member4Data[4] = bytes[initialOffset + 20];
			member4Data[5] = bytes[initialOffset + 21];
  			
  				
  			site = member4Data; 
    		
			byte[] member5Data = new byte[2];
			member5Data[0] = bytes[initialOffset + 22];
			member5Data[1] = bytes[initialOffset + 23];
  			
  				
  			_routing = new RoutingField( member5Data);
    		
    		}
    		
    		public FrameAddress( Object padding
  		, UInt16 size
  		, ProtocolField _protocol
  		, UInt32 reserved
  		, byte[]  target	
  		, byte[]  site	
  		, RoutingField _routing
    			)
    		{
    			this.size = size;
    			this._protocol = _protocol;
    			this.reserved = reserved;
    			this.target = target;
    			this.site = site;
    			this._routing = _routing;
    		}
    		
  		public UInt16  getSize()
    		{
    			return size;
    		}			
  		public ProtocolField  get_protocol()
    		{
    			return _protocol;
    		}				
  		public UInt32  getReserved()
    		{
    			return reserved;
    		}			
  		public byte[]  getTarget()
    		{
    			return target;
    		}		
  		public byte[]  getSite()
    		{
    			return site;
    		}		
  		public RoutingField  get_routing()
    		{
    			return _routing;
    		}				
    		
    		public void printMessageData()
    		{
  		size.printValue( "size");			// Field: size - Structle::Uint16 byte offset: 24
  		_protocol.printValue( "_protocol");				// Field: _protocol - Lx::ProtocolField byte offset: 24
  		reserved.printValue( "reserved");			// Field: reserved - Structle::Uint32 byte offset: 24
  		System.out.println( "Byte Array Print not currently supported");
  		System.out.println( "Byte Array Print not currently supported");
  		_routing.printValue( "_routing");				// Field: _routing - Lx::RoutingField byte offset: 24
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, UInt16 size
  		, ProtocolField _protocol
  		, UInt32 reserved
  		, byte[]  target	
  		, byte[]  site	
  		, RoutingField _routing
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = size.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = _protocol.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = reserved.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = target;	
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = site;	
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = _routing.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, UInt16 size
  		, ProtocolField _protocol
  		, UInt32 reserved
  		, byte[]  target	
  		, byte[]  site	
  		, RoutingField _routing
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, size
  		, _protocol
  		, reserved
  		, target	
  		, site	
  		, _routing
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = size.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = _protocol.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = reserved.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = target;	
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = site;	
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = _routing.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			
    			return bytes;
    		}
    		
    		public static int getPayloadSize()
    		{
    			return PAYLOAD_SIZE;
    		}
	}
	
	public static class Protocol extends LxProtocolTypeBase		// Struct: Lx::Protocol 
	{
  		// Fields: size, _protocol, reserved, target, site, _routing, at_time, type, reserved2, payload;
  		private UInt16 size;			// Field: size - Structle::Uint16 byte offset: 0
  		private ProtocolField _protocol;				// Field: _protocol - Lx::ProtocolField byte offset: 2
  		private UInt32 reserved;			// Field: reserved - Structle::Uint32 byte offset: 4
  		byte[] target = new byte[8];			// Field: target - Lx::Target byte offset: 8
  		private byte[]  site = new byte[6];		// Field: site - Structle::Bytes byte offset: 16
  		private RoutingField _routing;				// Field: _routing - Lx::RoutingField byte offset: 22
  		private UInt64 at_time;			// Field: at_time - Structle::Uint64 byte offset: 24
  		private UInt16 type;			// Field: type - Structle::Uint16 byte offset: 32
  		private byte[]  reserved2 = new byte[2];		// Field: reserved2 - Structle::Bytes byte offset: 34
  		LxProtocolTypeBase payload;				// Field: payload - Lx::Protocol::Payload byte offset: 36
  
  private static final int PAYLOAD_SIZE = 134;
  
  		public Protocol( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public Protocol( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[2];
			member0Data[0] = bytes[initialOffset + 0];
			member0Data[1] = bytes[initialOffset + 1];
  			
  				
  			size = new UInt16( member0Data); 
    		
			byte[] member1Data = new byte[2];
			member1Data[0] = bytes[initialOffset + 2];
			member1Data[1] = bytes[initialOffset + 3];
  			
  				
  			_protocol = new ProtocolField( member1Data); 
    		
			byte[] member2Data = new byte[4];
			member2Data[0] = bytes[initialOffset + 4];
			member2Data[1] = bytes[initialOffset + 5];
			member2Data[2] = bytes[initialOffset + 6];
			member2Data[3] = bytes[initialOffset + 7];
  			
  				
  			reserved = new UInt32( member2Data); 
    		
			byte[] member3Data = new byte[8];
			member3Data[0] = bytes[initialOffset + 8];
			member3Data[1] = bytes[initialOffset + 9];
			member3Data[2] = bytes[initialOffset + 10];
			member3Data[3] = bytes[initialOffset + 11];
			member3Data[4] = bytes[initialOffset + 12];
			member3Data[5] = bytes[initialOffset + 13];
			member3Data[6] = bytes[initialOffset + 14];
			member3Data[7] = bytes[initialOffset + 15];
  			
  				
  			@SuppressWarnings( "unused")
			byte[] target = new byte[8];			// Field: target - Lx::Target byte offset: 8
    		
			byte[] member4Data = new byte[6];
			member4Data[0] = bytes[initialOffset + 16];
			member4Data[1] = bytes[initialOffset + 17];
			member4Data[2] = bytes[initialOffset + 18];
			member4Data[3] = bytes[initialOffset + 19];
			member4Data[4] = bytes[initialOffset + 20];
			member4Data[5] = bytes[initialOffset + 21];
  			
  				
  			site = member4Data; 
    		
			byte[] member5Data = new byte[2];
			member5Data[0] = bytes[initialOffset + 22];
			member5Data[1] = bytes[initialOffset + 23];
  			
  				
  			_routing = new RoutingField( member5Data);
    		
			byte[] member6Data = new byte[8];
			member6Data[0] = bytes[initialOffset + 24];
			member6Data[1] = bytes[initialOffset + 25];
			member6Data[2] = bytes[initialOffset + 26];
			member6Data[3] = bytes[initialOffset + 27];
			member6Data[4] = bytes[initialOffset + 28];
			member6Data[5] = bytes[initialOffset + 29];
			member6Data[6] = bytes[initialOffset + 30];
			member6Data[7] = bytes[initialOffset + 31];
  			
  				
  			at_time = new UInt64( member6Data); 
    		
			byte[] member7Data = new byte[2];
			member7Data[0] = bytes[initialOffset + 32];
			member7Data[1] = bytes[initialOffset + 33];
  			
  				
  			type = new UInt16( member7Data); 
    		
			byte[] member8Data = new byte[2];
			member8Data[0] = bytes[initialOffset + 34];
			member8Data[1] = bytes[initialOffset + 35];
  			
  				
  			reserved2 = member8Data; 
    		
			byte[] member9Data = new byte[98];
			member9Data[0] = bytes[initialOffset + 36];
			member9Data[1] = bytes[initialOffset + 37];
			member9Data[2] = bytes[initialOffset + 38];
			member9Data[3] = bytes[initialOffset + 39];
			member9Data[4] = bytes[initialOffset + 40];
			member9Data[5] = bytes[initialOffset + 41];
			member9Data[6] = bytes[initialOffset + 42];
			member9Data[7] = bytes[initialOffset + 43];
			member9Data[8] = bytes[initialOffset + 44];
			member9Data[9] = bytes[initialOffset + 45];
			member9Data[10] = bytes[initialOffset + 46];
			member9Data[11] = bytes[initialOffset + 47];
			member9Data[12] = bytes[initialOffset + 48];
			member9Data[13] = bytes[initialOffset + 49];
			member9Data[14] = bytes[initialOffset + 50];
			member9Data[15] = bytes[initialOffset + 51];
			member9Data[16] = bytes[initialOffset + 52];
			member9Data[17] = bytes[initialOffset + 53];
			member9Data[18] = bytes[initialOffset + 54];
			member9Data[19] = bytes[initialOffset + 55];
			member9Data[20] = bytes[initialOffset + 56];
			member9Data[21] = bytes[initialOffset + 57];
			member9Data[22] = bytes[initialOffset + 58];
			member9Data[23] = bytes[initialOffset + 59];
			member9Data[24] = bytes[initialOffset + 60];
			member9Data[25] = bytes[initialOffset + 61];
			member9Data[26] = bytes[initialOffset + 62];
			member9Data[27] = bytes[initialOffset + 63];
			member9Data[28] = bytes[initialOffset + 64];
			member9Data[29] = bytes[initialOffset + 65];
			member9Data[30] = bytes[initialOffset + 66];
			member9Data[31] = bytes[initialOffset + 67];
			member9Data[32] = bytes[initialOffset + 68];
			member9Data[33] = bytes[initialOffset + 69];
			member9Data[34] = bytes[initialOffset + 70];
			member9Data[35] = bytes[initialOffset + 71];
			member9Data[36] = bytes[initialOffset + 72];
			member9Data[37] = bytes[initialOffset + 73];
			member9Data[38] = bytes[initialOffset + 74];
			member9Data[39] = bytes[initialOffset + 75];
			member9Data[40] = bytes[initialOffset + 76];
			member9Data[41] = bytes[initialOffset + 77];
			member9Data[42] = bytes[initialOffset + 78];
			member9Data[43] = bytes[initialOffset + 79];
			member9Data[44] = bytes[initialOffset + 80];
			member9Data[45] = bytes[initialOffset + 81];
			member9Data[46] = bytes[initialOffset + 82];
			member9Data[47] = bytes[initialOffset + 83];
			member9Data[48] = bytes[initialOffset + 84];
			member9Data[49] = bytes[initialOffset + 85];
			member9Data[50] = bytes[initialOffset + 86];
			member9Data[51] = bytes[initialOffset + 87];
			member9Data[52] = bytes[initialOffset + 88];
			member9Data[53] = bytes[initialOffset + 89];
			member9Data[54] = bytes[initialOffset + 90];
			member9Data[55] = bytes[initialOffset + 91];
			member9Data[56] = bytes[initialOffset + 92];
			member9Data[57] = bytes[initialOffset + 93];
			member9Data[58] = bytes[initialOffset + 94];
			member9Data[59] = bytes[initialOffset + 95];
			member9Data[60] = bytes[initialOffset + 96];
			member9Data[61] = bytes[initialOffset + 97];
			member9Data[62] = bytes[initialOffset + 98];
			member9Data[63] = bytes[initialOffset + 99];
			member9Data[64] = bytes[initialOffset + 100];
			member9Data[65] = bytes[initialOffset + 101];
			member9Data[66] = bytes[initialOffset + 102];
			member9Data[67] = bytes[initialOffset + 103];
			member9Data[68] = bytes[initialOffset + 104];
			member9Data[69] = bytes[initialOffset + 105];
			member9Data[70] = bytes[initialOffset + 106];
			member9Data[71] = bytes[initialOffset + 107];
			member9Data[72] = bytes[initialOffset + 108];
			member9Data[73] = bytes[initialOffset + 109];
			member9Data[74] = bytes[initialOffset + 110];
			member9Data[75] = bytes[initialOffset + 111];
			member9Data[76] = bytes[initialOffset + 112];
			member9Data[77] = bytes[initialOffset + 113];
			member9Data[78] = bytes[initialOffset + 114];
			member9Data[79] = bytes[initialOffset + 115];
			member9Data[80] = bytes[initialOffset + 116];
			member9Data[81] = bytes[initialOffset + 117];
			member9Data[82] = bytes[initialOffset + 118];
			member9Data[83] = bytes[initialOffset + 119];
			member9Data[84] = bytes[initialOffset + 120];
			member9Data[85] = bytes[initialOffset + 121];
			member9Data[86] = bytes[initialOffset + 122];
			member9Data[87] = bytes[initialOffset + 123];
			member9Data[88] = bytes[initialOffset + 124];
			member9Data[89] = bytes[initialOffset + 125];
			member9Data[90] = bytes[initialOffset + 126];
			member9Data[91] = bytes[initialOffset + 127];
			member9Data[92] = bytes[initialOffset + 128];
			member9Data[93] = bytes[initialOffset + 129];
			member9Data[94] = bytes[initialOffset + 130];
			member9Data[95] = bytes[initialOffset + 131];
			member9Data[96] = bytes[initialOffset + 132];
			member9Data[97] = bytes[initialOffset + 133];
  			
  				
  		@SuppressWarnings( "unused")
		LxProtocolTypeBase payload;				// Field: payload - Lx::Protocol::Payload byte offset: 36
    		
    		}
    		
    		public Protocol( Object padding
  		, UInt16 size
  		, ProtocolField _protocol
  		, UInt32 reserved
  		, byte[] target
  		, byte[]  site	
  		, RoutingField _routing
  		, UInt64 at_time
  		, UInt16 type
  		, byte[]  reserved2	
  		, LxProtocolTypeBase payload
    			)
    		{
    			this.size = size;
    			this._protocol = _protocol;
    			this.reserved = reserved;
    			this.target = target;
    			this.site = site;
    			this._routing = _routing;
    			this.at_time = at_time;
    			this.type = type;
    			this.reserved2 = reserved2;
    			this.payload = payload;
    		}
    		
  		public UInt16  getSize()
    		{
    			return size;
    		}			
  		public ProtocolField  get_protocol()
    		{
    			return _protocol;
    		}				
  		public UInt32  getReserved()
    		{
    			return reserved;
    		}			
  		public byte[] getTarget()
  		{
  			return target;
  		}
  		public byte[]  getSite()
    		{
    			return site;
    		}		
  		public RoutingField  get_routing()
    		{
    			return _routing;
    		}				
  		public UInt64  getAt_time()
    		{
    			return at_time;
    		}			
  		public UInt16  getType()
    		{
    			return type;
    		}			
  		public byte[]  getReserved2()
    		{
    			return reserved2;
    		}		
  		public LxProtocolTypeBase  getPayload()
  		{
  			return payload;
  		}	
    		
    		public void printMessageData()
    		{
  		size.printValue( "size");			// Field: size - Structle::Uint16 byte offset: 134
  		_protocol.printValue( "_protocol");				// Field: _protocol - Lx::ProtocolField byte offset: 134
  		reserved.printValue( "reserved");			// Field: reserved - Structle::Uint32 byte offset: 134
  		System.out.println( "Byte Array Print not currently supported");
  		System.out.println( "Byte Array Print not currently supported");
  		_routing.printValue( "_routing");				// Field: _routing - Lx::RoutingField byte offset: 134
  		at_time.printValue( "at_time");			// Field: at_time - Structle::Uint64 byte offset: 134
  		type.printValue( "type");			// Field: type - Structle::Uint16 byte offset: 134
  		System.out.println( "Byte Array Print not currently supported");
  		payload.printMessageData();
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, UInt16 size
  		, ProtocolField _protocol
  		, UInt32 reserved
  		, byte[] target
  		, byte[]  site	
  		, RoutingField _routing
  		, UInt64 at_time
  		, UInt16 type
  		, byte[]  reserved2	
  		, LxProtocolTypeBase payload
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = size.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = _protocol.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = reserved.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = target;
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = site;	
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = _routing.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = at_time.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = type.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = reserved2;	
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = payload.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, UInt16 size
  		, ProtocolField _protocol
  		, UInt32 reserved
  		, byte[] target
  		, byte[]  site	
  		, RoutingField _routing
  		, UInt64 at_time
  		, UInt16 type
  		, byte[]  reserved2	
  		, LxProtocolTypeBase payload
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, size
  		, _protocol
  		, reserved
  		, target
  		, site	
  		, _routing
  		, at_time
  		, type
  		, reserved2	
  		, payload
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = size.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = _protocol.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = reserved.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = target;
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = site;	
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = _routing.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = at_time.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = type.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = reserved2;	
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = payload.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			
    			return bytes;
    		}
    		
    		public static int getPayloadSize()
    		{
    			return PAYLOAD_SIZE;
    		}
	}
	
}

/*
	End Of File: Lx.java
*/

