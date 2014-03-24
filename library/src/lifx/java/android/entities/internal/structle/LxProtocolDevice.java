//
//  LxProtocolDevice.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

// Start File: @./LxProtocolDevice.java 

package lifx.java.android.entities.internal.structle;

/*
	**** GENERATED CODE ****
	Start Of File: LxProtocolDevice.java 
*/

import android.annotation.SuppressLint;
import java.util.HashMap;

import lifx.java.android.entities.internal.structle.StructleTypes.Bool8;
import lifx.java.android.entities.internal.structle.StructleTypes.Float32;
import lifx.java.android.entities.internal.structle.StructleTypes.Int16;
import lifx.java.android.entities.internal.structle.StructleTypes.Int32;
import lifx.java.android.entities.internal.structle.StructleTypes.Int64;
import lifx.java.android.entities.internal.structle.StructleTypes.LxProtocolTypeBase;
import lifx.java.android.entities.internal.structle.StructleTypes.ProtocolField;
import lifx.java.android.entities.internal.structle.StructleTypes.RoutingField;
import lifx.java.android.entities.internal.structle.StructleTypes.UInt16;
import lifx.java.android.entities.internal.structle.StructleTypes.UInt32;
import lifx.java.android.entities.internal.structle.StructleTypes.UInt64;
import lifx.java.android.entities.internal.structle.StructleTypes.UInt8;

@SuppressWarnings( "unused")
public class LxProtocolDevice
{
	public enum Service									// Enum Lx::Protocol::Device::Service
	{
  		LX_PROTOCOL_DEVICE_SERVICE_UDP,				// LX_PROTOCOL_DEVICE_SERVICE_UDP = 1
  		LX_PROTOCOL_DEVICE_SERVICE_TCP,				// LX_PROTOCOL_DEVICE_SERVICE_TCP = 2
	};
	
	public static HashMap<Service,Integer> serviceValueMap;
	public static HashMap<Integer,Service> serviceMap;
	

	static
  	{
  		serviceValueMap = new HashMap<Service,Integer>();
  		serviceMap = new HashMap<Integer,Service>();
		serviceValueMap.put( Service.LX_PROTOCOL_DEVICE_SERVICE_UDP, 1);
		serviceMap.put( 1, Service.LX_PROTOCOL_DEVICE_SERVICE_UDP);
		serviceValueMap.put( Service.LX_PROTOCOL_DEVICE_SERVICE_TCP, 2);
		serviceMap.put( 2, Service.LX_PROTOCOL_DEVICE_SERVICE_TCP);
  
  	};
	
	public static class SetSite extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::SetSite 
	{
  		// Fields: site;
  		private byte[]  site = new byte[6];		// Field: site - Structle::Bytes byte offset: 0
  
  private static final int PAYLOAD_SIZE = 6;
  
  		public SetSite( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public SetSite( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[6];
			member0Data[0] = bytes[initialOffset + 0];
			member0Data[1] = bytes[initialOffset + 1];
			member0Data[2] = bytes[initialOffset + 2];
			member0Data[3] = bytes[initialOffset + 3];
			member0Data[4] = bytes[initialOffset + 4];
			member0Data[5] = bytes[initialOffset + 5];
  			
  				
  			site = member0Data; 
    		
    		}
    		
    		public SetSite( Object padding
  		, byte[]  site	
    			)
    		{
    			this.site = site;
    		}
    		
  		public byte[]  getSite()
    		{
    			return site;
    		}		
    		
    		public void printMessageData()
    		{
  		System.out.println( "Byte Array Print not currently supported");
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, byte[]  site	
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = site;	
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, byte[]  site	
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, site	
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = site;	
    		
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
	
	public static class GetPanGateway extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::GetPanGateway 
	{
  
  private static final int PAYLOAD_SIZE = 0;
  
  		public GetPanGateway( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public GetPanGateway( byte[] bytes, int initialOffset)
  		{
    		}
    		
    		public GetPanGateway( Object padding
    			)
    		{
    		}
    		
    		
    		public void printMessageData()
    		{
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			
    			return bytes;
    		}
    		
    		public static int getPayloadSize()
    		{
    			return PAYLOAD_SIZE;
    		}
	}
	
	public static class StatePanGateway extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::StatePanGateway 
	{
  		// Fields: service, port;
  		private UInt8  service;			// Field: service - Structle::Uint8 byte offset: 0
  		private UInt32 port;			// Field: port - Structle::Uint32 byte offset: 1
  
  private static final int PAYLOAD_SIZE = 5;
  
  		public StatePanGateway( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public StatePanGateway( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[1];
			member0Data[0] = bytes[initialOffset + 0];
  			
  				
  			service = new UInt8( member0Data); 
    		
			byte[] member1Data = new byte[4];
			member1Data[0] = bytes[initialOffset + 1];
			member1Data[1] = bytes[initialOffset + 2];
			member1Data[2] = bytes[initialOffset + 3];
			member1Data[3] = bytes[initialOffset + 4];
  			
  				
  			port = new UInt32( member1Data); 
    		
    		}
    		
    		public StatePanGateway( Object padding
  		, UInt8  service
  		, UInt32 port
    			)
    		{
    			this.service = service;
    			this.port = port;
    		}
    		
  		public UInt8   getService()
    		{
    			return service;
    		}			
  		public UInt32  getPort()
    		{
    			return port;
    		}			
    		
    		public void printMessageData()
    		{
  		service.printValue( "service");			// Field: service - Structle::Uint8 byte offset: 5
  		port.printValue( "port");			// Field: port - Structle::Uint32 byte offset: 5
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, UInt8  service
  		, UInt32 port
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = service.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = port.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, UInt8  service
  		, UInt32 port
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, service
  		, port
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = service.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = port.getBytes();
    		
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
	
	public static class GetTime extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::GetTime 
	{
  
  private static final int PAYLOAD_SIZE = 0;
  
  		public GetTime( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public GetTime( byte[] bytes, int initialOffset)
  		{
    		}
    		
    		public GetTime( Object padding
    			)
    		{
    		}
    		
    		
    		public void printMessageData()
    		{
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			
    			return bytes;
    		}
    		
    		public static int getPayloadSize()
    		{
    			return PAYLOAD_SIZE;
    		}
	}
	
	public static class SetTime extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::SetTime 
	{
  		// Fields: time;
  		private UInt64 time;			// Field: time - Structle::Uint64 byte offset: 0
  
  private static final int PAYLOAD_SIZE = 8;
  
  		public SetTime( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public SetTime( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[8];
			member0Data[0] = bytes[initialOffset + 0];
			member0Data[1] = bytes[initialOffset + 1];
			member0Data[2] = bytes[initialOffset + 2];
			member0Data[3] = bytes[initialOffset + 3];
			member0Data[4] = bytes[initialOffset + 4];
			member0Data[5] = bytes[initialOffset + 5];
			member0Data[6] = bytes[initialOffset + 6];
			member0Data[7] = bytes[initialOffset + 7];
  			
  				
  			time = new UInt64( member0Data); 
    		
    		}
    		
    		public SetTime( Object padding
  		, UInt64 time
    			)
    		{
    			this.time = time;
    		}
    		
  		public UInt64  getTime()
    		{
    			return time;
    		}			
    		
    		public void printMessageData()
    		{
  		time.printValue( "time");			// Field: time - Structle::Uint64 byte offset: 8
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, UInt64 time
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = time.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, UInt64 time
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, time
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = time.getBytes();
    		
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
	
	public static class StateTime extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::StateTime 
	{
  		// Fields: time;
  		private UInt64 time;			// Field: time - Structle::Uint64 byte offset: 0
  
  private static final int PAYLOAD_SIZE = 8;
  
  		public StateTime( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public StateTime( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[8];
			member0Data[0] = bytes[initialOffset + 0];
			member0Data[1] = bytes[initialOffset + 1];
			member0Data[2] = bytes[initialOffset + 2];
			member0Data[3] = bytes[initialOffset + 3];
			member0Data[4] = bytes[initialOffset + 4];
			member0Data[5] = bytes[initialOffset + 5];
			member0Data[6] = bytes[initialOffset + 6];
			member0Data[7] = bytes[initialOffset + 7];
  			
  				
  			time = new UInt64( member0Data); 
    		
    		}
    		
    		public StateTime( Object padding
  		, UInt64 time
    			)
    		{
    			this.time = time;
    		}
    		
  		public UInt64  getTime()
    		{
    			return time;
    		}			
    		
    		public void printMessageData()
    		{
  		time.printValue( "time");			// Field: time - Structle::Uint64 byte offset: 8
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, UInt64 time
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = time.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, UInt64 time
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, time
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = time.getBytes();
    		
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
	
	public static class GetResetSwitch extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::GetResetSwitch 
	{
  
  private static final int PAYLOAD_SIZE = 0;
  
  		public GetResetSwitch( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public GetResetSwitch( byte[] bytes, int initialOffset)
  		{
    		}
    		
    		public GetResetSwitch( Object padding
    			)
    		{
    		}
    		
    		
    		public void printMessageData()
    		{
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			
    			return bytes;
    		}
    		
    		public static int getPayloadSize()
    		{
    			return PAYLOAD_SIZE;
    		}
	}
	
	public static class StateResetSwitch extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::StateResetSwitch 
	{
  		// Fields: position;
  		private UInt8  position;			// Field: position - Structle::Uint8 byte offset: 0
  
  private static final int PAYLOAD_SIZE = 1;
  
  		public StateResetSwitch( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public StateResetSwitch( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[1];
			member0Data[0] = bytes[initialOffset + 0];
  			
  				
  			position = new UInt8( member0Data); 
    		
    		}
    		
    		public StateResetSwitch( Object padding
  		, UInt8  position
    			)
    		{
    			this.position = position;
    		}
    		
  		public UInt8   getPosition()
    		{
    			return position;
    		}			
    		
    		public void printMessageData()
    		{
  		position.printValue( "position");			// Field: position - Structle::Uint8 byte offset: 1
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, UInt8  position
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = position.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, UInt8  position
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, position
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = position.getBytes();
    		
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
	
	public static class GetDummyLoad extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::GetDummyLoad 
	{
  
  private static final int PAYLOAD_SIZE = 0;
  
  		public GetDummyLoad( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public GetDummyLoad( byte[] bytes, int initialOffset)
  		{
    		}
    		
    		public GetDummyLoad( Object padding
    			)
    		{
    		}
    		
    		
    		public void printMessageData()
    		{
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			
    			return bytes;
    		}
    		
    		public static int getPayloadSize()
    		{
    			return PAYLOAD_SIZE;
    		}
	}
	
	public static class SetDummyLoad extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::SetDummyLoad 
	{
  		// Fields: on;
  		private Bool8 on;			// Field: on - Structle::Bool byte offset: 0
  
  private static final int PAYLOAD_SIZE = 1;
  
  		public SetDummyLoad( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public SetDummyLoad( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[1];
			member0Data[0] = bytes[initialOffset + 0];
  			
  				
  			on = new Bool8( member0Data);
    		
    		}
    		
    		public SetDummyLoad( Object padding
  		, Bool8 on
    			)
    		{
    			this.on = on;
    		}
    		
  		public Bool8  getOn()
    		{
    			return on;
    		}		
    		
    		public void printMessageData()
    		{
  		on.printValue( "on");			// Field: on - Structle::Bool byte offset: 1
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, Bool8 on
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = on.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, Bool8 on
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, on
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = on.getBytes();
    		
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
	
	public static class StateDummyLoad extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::StateDummyLoad 
	{
  		// Fields: on;
  		private Bool8 on;			// Field: on - Structle::Bool byte offset: 0
  
  private static final int PAYLOAD_SIZE = 1;
  
  		public StateDummyLoad( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public StateDummyLoad( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[1];
			member0Data[0] = bytes[initialOffset + 0];
  			
  				
  			on = new Bool8( member0Data);
    		
    		}
    		
    		public StateDummyLoad( Object padding
  		, Bool8 on
    			)
    		{
    			this.on = on;
    		}
    		
  		public Bool8  getOn()
    		{
    			return on;
    		}		
    		
    		public void printMessageData()
    		{
  		on.printValue( "on");			// Field: on - Structle::Bool byte offset: 1
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, Bool8 on
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = on.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, Bool8 on
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, on
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = on.getBytes();
    		
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
	
	public static class GetMeshInfo extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::GetMeshInfo 
	{
  
  private static final int PAYLOAD_SIZE = 0;
  
  		public GetMeshInfo( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public GetMeshInfo( byte[] bytes, int initialOffset)
  		{
    		}
    		
    		public GetMeshInfo( Object padding
    			)
    		{
    		}
    		
    		
    		public void printMessageData()
    		{
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			
    			return bytes;
    		}
    		
    		public static int getPayloadSize()
    		{
    			return PAYLOAD_SIZE;
    		}
	}
	
	public static class StateMeshInfo extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::StateMeshInfo 
	{
  		// Fields: signal, tx, rx, mcu_temperature;
  		private Float32 signal;				// Field: signal - Structle::Float byte offset: 0
  		private UInt32 tx;			// Field: tx - Structle::Uint32 byte offset: 4
  		private UInt32 rx;			// Field: rx - Structle::Uint32 byte offset: 8
  		private Int16 mcu_temperature;				// Field: mcu_temperature - Structle::Int16 byte offset: 12
  
  private static final int PAYLOAD_SIZE = 14;
  
  		public StateMeshInfo( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public StateMeshInfo( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[4];
			member0Data[0] = bytes[initialOffset + 0];
			member0Data[1] = bytes[initialOffset + 1];
			member0Data[2] = bytes[initialOffset + 2];
			member0Data[3] = bytes[initialOffset + 3];
  			
  				
  			signal = new Float32( member0Data);
    		
			byte[] member1Data = new byte[4];
			member1Data[0] = bytes[initialOffset + 4];
			member1Data[1] = bytes[initialOffset + 5];
			member1Data[2] = bytes[initialOffset + 6];
			member1Data[3] = bytes[initialOffset + 7];
  			
  				
  			tx = new UInt32( member1Data); 
    		
			byte[] member2Data = new byte[4];
			member2Data[0] = bytes[initialOffset + 8];
			member2Data[1] = bytes[initialOffset + 9];
			member2Data[2] = bytes[initialOffset + 10];
			member2Data[3] = bytes[initialOffset + 11];
  			
  				
  			rx = new UInt32( member2Data); 
    		
			byte[] member3Data = new byte[2];
			member3Data[0] = bytes[initialOffset + 12];
			member3Data[1] = bytes[initialOffset + 13];
  			
  				
  			mcu_temperature = new Int16( member3Data); 
    		
    		}
    		
    		public StateMeshInfo( Object padding
  		, Float32 signal
  		, UInt32 tx
  		, UInt32 rx
  		, Int16 mcu_temperature
    			)
    		{
    			this.signal = signal;
    			this.tx = tx;
    			this.rx = rx;
    			this.mcu_temperature = mcu_temperature;
    		}
    		
  		public Float32  getSignal()
    		{
    			return signal;
    		}				
  		public UInt32  getTx()
    		{
    			return tx;
    		}			
  		public UInt32  getRx()
    		{
    			return rx;
    		}			
  		public Int16  getMcu_temperature()
    		{
    			return mcu_temperature;
    		}				
    		
    		public void printMessageData()
    		{
  		signal.printValue( "signal");				// Field: signal - Structle::Float byte offset: 14
  		tx.printValue( "tx");			// Field: tx - Structle::Uint32 byte offset: 14
  		rx.printValue( "rx");			// Field: rx - Structle::Uint32 byte offset: 14
  		mcu_temperature.printValue( "mcu_temperature");				// Field: mcu_temperature - Structle::Int16 byte offset: 14
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, Float32 signal
  		, UInt32 tx
  		, UInt32 rx
  		, Int16 mcu_temperature
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = signal.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = tx.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = rx.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = mcu_temperature.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, Float32 signal
  		, UInt32 tx
  		, UInt32 rx
  		, Int16 mcu_temperature
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, signal
  		, tx
  		, rx
  		, mcu_temperature
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = signal.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = tx.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = rx.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = mcu_temperature.getBytes();
    		
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
	
	public static class GetMeshFirmware extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::GetMeshFirmware 
	{
  
  private static final int PAYLOAD_SIZE = 0;
  
  		public GetMeshFirmware( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public GetMeshFirmware( byte[] bytes, int initialOffset)
  		{
    		}
    		
    		public GetMeshFirmware( Object padding
    			)
    		{
    		}
    		
    		
    		public void printMessageData()
    		{
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			
    			return bytes;
    		}
    		
    		public static int getPayloadSize()
    		{
    			return PAYLOAD_SIZE;
    		}
	}
	
	public static class StateMeshFirmware extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::StateMeshFirmware 
	{
  		// Fields: build, install, version;
  		private UInt64 build;			// Field: build - Structle::Uint64 byte offset: 0
  		private UInt64 install;			// Field: install - Structle::Uint64 byte offset: 8
  		private UInt32 version;			// Field: version - Structle::Uint32 byte offset: 16
  
  private static final int PAYLOAD_SIZE = 20;
  
  		public StateMeshFirmware( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public StateMeshFirmware( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[8];
			member0Data[0] = bytes[initialOffset + 0];
			member0Data[1] = bytes[initialOffset + 1];
			member0Data[2] = bytes[initialOffset + 2];
			member0Data[3] = bytes[initialOffset + 3];
			member0Data[4] = bytes[initialOffset + 4];
			member0Data[5] = bytes[initialOffset + 5];
			member0Data[6] = bytes[initialOffset + 6];
			member0Data[7] = bytes[initialOffset + 7];
  			
  				
  			build = new UInt64( member0Data); 
    		
			byte[] member1Data = new byte[8];
			member1Data[0] = bytes[initialOffset + 8];
			member1Data[1] = bytes[initialOffset + 9];
			member1Data[2] = bytes[initialOffset + 10];
			member1Data[3] = bytes[initialOffset + 11];
			member1Data[4] = bytes[initialOffset + 12];
			member1Data[5] = bytes[initialOffset + 13];
			member1Data[6] = bytes[initialOffset + 14];
			member1Data[7] = bytes[initialOffset + 15];
  			
  				
  			install = new UInt64( member1Data); 
    		
			byte[] member2Data = new byte[4];
			member2Data[0] = bytes[initialOffset + 16];
			member2Data[1] = bytes[initialOffset + 17];
			member2Data[2] = bytes[initialOffset + 18];
			member2Data[3] = bytes[initialOffset + 19];
  			
  				
  			version = new UInt32( member2Data); 
    		
    		}
    		
    		public StateMeshFirmware( Object padding
  		, UInt64 build
  		, UInt64 install
  		, UInt32 version
    			)
    		{
    			this.build = build;
    			this.install = install;
    			this.version = version;
    		}
    		
  		public UInt64  getBuild()
    		{
    			return build;
    		}			
  		public UInt64  getInstall()
    		{
    			return install;
    		}			
  		public UInt32  getVersion()
    		{
    			return version;
    		}			
    		
    		public void printMessageData()
    		{
  		build.printValue( "build");			// Field: build - Structle::Uint64 byte offset: 20
  		install.printValue( "install");			// Field: install - Structle::Uint64 byte offset: 20
  		version.printValue( "version");			// Field: version - Structle::Uint32 byte offset: 20
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, UInt64 build
  		, UInt64 install
  		, UInt32 version
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = build.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = install.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = version.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, UInt64 build
  		, UInt64 install
  		, UInt32 version
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, build
  		, install
  		, version
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = build.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = install.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = version.getBytes();
    		
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
	
	public static class GetWifiInfo extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::GetWifiInfo 
	{
  
  private static final int PAYLOAD_SIZE = 0;
  
  		public GetWifiInfo( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public GetWifiInfo( byte[] bytes, int initialOffset)
  		{
    		}
    		
    		public GetWifiInfo( Object padding
    			)
    		{
    		}
    		
    		
    		public void printMessageData()
    		{
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			
    			return bytes;
    		}
    		
    		public static int getPayloadSize()
    		{
    			return PAYLOAD_SIZE;
    		}
	}
	
	public static class StateWifiInfo extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::StateWifiInfo 
	{
  		// Fields: signal, tx, rx, mcu_temperature;
  		private Float32 signal;				// Field: signal - Structle::Float byte offset: 0
  		private UInt32 tx;			// Field: tx - Structle::Uint32 byte offset: 4
  		private UInt32 rx;			// Field: rx - Structle::Uint32 byte offset: 8
  		private Int16 mcu_temperature;				// Field: mcu_temperature - Structle::Int16 byte offset: 12
  
  private static final int PAYLOAD_SIZE = 14;
  
  		public StateWifiInfo( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public StateWifiInfo( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[4];
			member0Data[0] = bytes[initialOffset + 0];
			member0Data[1] = bytes[initialOffset + 1];
			member0Data[2] = bytes[initialOffset + 2];
			member0Data[3] = bytes[initialOffset + 3];
  			
  				
  			signal = new Float32( member0Data);
    		
			byte[] member1Data = new byte[4];
			member1Data[0] = bytes[initialOffset + 4];
			member1Data[1] = bytes[initialOffset + 5];
			member1Data[2] = bytes[initialOffset + 6];
			member1Data[3] = bytes[initialOffset + 7];
  			
  				
  			tx = new UInt32( member1Data); 
    		
			byte[] member2Data = new byte[4];
			member2Data[0] = bytes[initialOffset + 8];
			member2Data[1] = bytes[initialOffset + 9];
			member2Data[2] = bytes[initialOffset + 10];
			member2Data[3] = bytes[initialOffset + 11];
  			
  				
  			rx = new UInt32( member2Data); 
    		
			byte[] member3Data = new byte[2];
			member3Data[0] = bytes[initialOffset + 12];
			member3Data[1] = bytes[initialOffset + 13];
  			
  				
  			mcu_temperature = new Int16( member3Data); 
    		
    		}
    		
    		public StateWifiInfo( Object padding
  		, Float32 signal
  		, UInt32 tx
  		, UInt32 rx
  		, Int16 mcu_temperature
    			)
    		{
    			this.signal = signal;
    			this.tx = tx;
    			this.rx = rx;
    			this.mcu_temperature = mcu_temperature;
    		}
    		
  		public Float32  getSignal()
    		{
    			return signal;
    		}				
  		public UInt32  getTx()
    		{
    			return tx;
    		}			
  		public UInt32  getRx()
    		{
    			return rx;
    		}			
  		public Int16  getMcu_temperature()
    		{
    			return mcu_temperature;
    		}				
    		
    		public void printMessageData()
    		{
  		signal.printValue( "signal");				// Field: signal - Structle::Float byte offset: 14
  		tx.printValue( "tx");			// Field: tx - Structle::Uint32 byte offset: 14
  		rx.printValue( "rx");			// Field: rx - Structle::Uint32 byte offset: 14
  		mcu_temperature.printValue( "mcu_temperature");				// Field: mcu_temperature - Structle::Int16 byte offset: 14
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, Float32 signal
  		, UInt32 tx
  		, UInt32 rx
  		, Int16 mcu_temperature
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = signal.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = tx.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = rx.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = mcu_temperature.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, Float32 signal
  		, UInt32 tx
  		, UInt32 rx
  		, Int16 mcu_temperature
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, signal
  		, tx
  		, rx
  		, mcu_temperature
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = signal.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = tx.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = rx.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = mcu_temperature.getBytes();
    		
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
	
	public static class GetWifiFirmware extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::GetWifiFirmware 
	{
  
  private static final int PAYLOAD_SIZE = 0;
  
  		public GetWifiFirmware( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public GetWifiFirmware( byte[] bytes, int initialOffset)
  		{
    		}
    		
    		public GetWifiFirmware( Object padding
    			)
    		{
    		}
    		
    		
    		public void printMessageData()
    		{
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			
    			return bytes;
    		}
    		
    		public static int getPayloadSize()
    		{
    			return PAYLOAD_SIZE;
    		}
	}
	
	public static class StateWifiFirmware extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::StateWifiFirmware 
	{
  		// Fields: build, install, version;
  		private UInt64 build;			// Field: build - Structle::Uint64 byte offset: 0
  		private UInt64 install;			// Field: install - Structle::Uint64 byte offset: 8
  		private UInt32 version;			// Field: version - Structle::Uint32 byte offset: 16
  
  private static final int PAYLOAD_SIZE = 20;
  
  		public StateWifiFirmware( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public StateWifiFirmware( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[8];
			member0Data[0] = bytes[initialOffset + 0];
			member0Data[1] = bytes[initialOffset + 1];
			member0Data[2] = bytes[initialOffset + 2];
			member0Data[3] = bytes[initialOffset + 3];
			member0Data[4] = bytes[initialOffset + 4];
			member0Data[5] = bytes[initialOffset + 5];
			member0Data[6] = bytes[initialOffset + 6];
			member0Data[7] = bytes[initialOffset + 7];
  			
  				
  			build = new UInt64( member0Data); 
    		
			byte[] member1Data = new byte[8];
			member1Data[0] = bytes[initialOffset + 8];
			member1Data[1] = bytes[initialOffset + 9];
			member1Data[2] = bytes[initialOffset + 10];
			member1Data[3] = bytes[initialOffset + 11];
			member1Data[4] = bytes[initialOffset + 12];
			member1Data[5] = bytes[initialOffset + 13];
			member1Data[6] = bytes[initialOffset + 14];
			member1Data[7] = bytes[initialOffset + 15];
  			
  				
  			install = new UInt64( member1Data); 
    		
			byte[] member2Data = new byte[4];
			member2Data[0] = bytes[initialOffset + 16];
			member2Data[1] = bytes[initialOffset + 17];
			member2Data[2] = bytes[initialOffset + 18];
			member2Data[3] = bytes[initialOffset + 19];
  			
  				
  			version = new UInt32( member2Data); 
    		
    		}
    		
    		public StateWifiFirmware( Object padding
  		, UInt64 build
  		, UInt64 install
  		, UInt32 version
    			)
    		{
    			this.build = build;
    			this.install = install;
    			this.version = version;
    		}
    		
  		public UInt64  getBuild()
    		{
    			return build;
    		}			
  		public UInt64  getInstall()
    		{
    			return install;
    		}			
  		public UInt32  getVersion()
    		{
    			return version;
    		}			
    		
    		public void printMessageData()
    		{
  		build.printValue( "build");			// Field: build - Structle::Uint64 byte offset: 20
  		install.printValue( "install");			// Field: install - Structle::Uint64 byte offset: 20
  		version.printValue( "version");			// Field: version - Structle::Uint32 byte offset: 20
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, UInt64 build
  		, UInt64 install
  		, UInt32 version
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = build.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = install.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = version.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, UInt64 build
  		, UInt64 install
  		, UInt32 version
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, build
  		, install
  		, version
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = build.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = install.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = version.getBytes();
    		
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
	
	public static class GetPower extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::GetPower 
	{
  
  private static final int PAYLOAD_SIZE = 0;
  
  		public GetPower( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public GetPower( byte[] bytes, int initialOffset)
  		{
    		}
    		
    		public GetPower( Object padding
    			)
    		{
    		}
    		
    		
    		public void printMessageData()
    		{
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			
    			return bytes;
    		}
    		
    		public static int getPayloadSize()
    		{
    			return PAYLOAD_SIZE;
    		}
	}
	
	public static class SetPower extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::SetPower 
	{
  		// Fields: level;
  		private UInt16 level;			// Field: level - Structle::Uint16 byte offset: 0
  
  private static final int PAYLOAD_SIZE = 2;
  
  		public SetPower( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public SetPower( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[2];
			member0Data[0] = bytes[initialOffset + 0];
			member0Data[1] = bytes[initialOffset + 1];
  			
  				
  			level = new UInt16( member0Data); 
    		
    		}
    		
    		public SetPower( Object padding
  		, UInt16 level
    			)
    		{
    			this.level = level;
    		}
    		
  		public UInt16  getLevel()
    		{
    			return level;
    		}			
    		
    		public void printMessageData()
    		{
  		level.printValue( "level");			// Field: level - Structle::Uint16 byte offset: 2
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, UInt16 level
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = level.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, UInt16 level
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, level
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = level.getBytes();
    		
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
	
	public static class StatePower extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::StatePower 
	{
  		// Fields: level;
  		private UInt16 level;			// Field: level - Structle::Uint16 byte offset: 0
  
  private static final int PAYLOAD_SIZE = 2;
  
  		public StatePower( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public StatePower( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[2];
			member0Data[0] = bytes[initialOffset + 0];
			member0Data[1] = bytes[initialOffset + 1];
  			
  				
  			level = new UInt16( member0Data); 
    		
    		}
    		
    		public StatePower( Object padding
  		, UInt16 level
    			)
    		{
    			this.level = level;
    		}
    		
  		public UInt16  getLevel()
    		{
    			return level;
    		}			
    		
    		public void printMessageData()
    		{
  		level.printValue( "level");			// Field: level - Structle::Uint16 byte offset: 2
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, UInt16 level
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = level.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, UInt16 level
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, level
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = level.getBytes();
    		
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
	
	public static class GetLabel extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::GetLabel 
	{
  
  private static final int PAYLOAD_SIZE = 0;
  
  		public GetLabel( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public GetLabel( byte[] bytes, int initialOffset)
  		{
    		}
    		
    		public GetLabel( Object padding
    			)
    		{
    		}
    		
    		
    		public void printMessageData()
    		{
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			
    			return bytes;
    		}
    		
    		public static int getPayloadSize()
    		{
    			return PAYLOAD_SIZE;
    		}
	}
	
	public static class SetLabel extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::SetLabel 
	{
  		// Fields: label;
  		private String label;			// Field: label - Structle::String byte offset: 0
  
  private static final int PAYLOAD_SIZE = 32;
  
  		public SetLabel( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public SetLabel( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[32];
			member0Data[0] = bytes[initialOffset + 0];
			member0Data[1] = bytes[initialOffset + 1];
			member0Data[2] = bytes[initialOffset + 2];
			member0Data[3] = bytes[initialOffset + 3];
			member0Data[4] = bytes[initialOffset + 4];
			member0Data[5] = bytes[initialOffset + 5];
			member0Data[6] = bytes[initialOffset + 6];
			member0Data[7] = bytes[initialOffset + 7];
			member0Data[8] = bytes[initialOffset + 8];
			member0Data[9] = bytes[initialOffset + 9];
			member0Data[10] = bytes[initialOffset + 10];
			member0Data[11] = bytes[initialOffset + 11];
			member0Data[12] = bytes[initialOffset + 12];
			member0Data[13] = bytes[initialOffset + 13];
			member0Data[14] = bytes[initialOffset + 14];
			member0Data[15] = bytes[initialOffset + 15];
			member0Data[16] = bytes[initialOffset + 16];
			member0Data[17] = bytes[initialOffset + 17];
			member0Data[18] = bytes[initialOffset + 18];
			member0Data[19] = bytes[initialOffset + 19];
			member0Data[20] = bytes[initialOffset + 20];
			member0Data[21] = bytes[initialOffset + 21];
			member0Data[22] = bytes[initialOffset + 22];
			member0Data[23] = bytes[initialOffset + 23];
			member0Data[24] = bytes[initialOffset + 24];
			member0Data[25] = bytes[initialOffset + 25];
			member0Data[26] = bytes[initialOffset + 26];
			member0Data[27] = bytes[initialOffset + 27];
			member0Data[28] = bytes[initialOffset + 28];
			member0Data[29] = bytes[initialOffset + 29];
			member0Data[30] = bytes[initialOffset + 30];
			member0Data[31] = bytes[initialOffset + 31];
  			
				int endOfStringIndex;
				byte[] subString;
  				
  				endOfStringIndex = member0Data.length;
  				
  				for( int i = 0; i < member0Data.length; i ++)
  				{
  					if( member0Data[i] == 0x00)
  					{
  						endOfStringIndex = i;
  						break;
  					}
  				}
  				
  				subString = new byte[endOfStringIndex];
  				for( int i = 0; i < endOfStringIndex; i++)
  				{
  					subString[i] = member0Data[i];
  				}
  				
  			label = new String( subString);
    		
    		}
    		
    		public SetLabel( Object padding
  		, String label
    			)
    		{
    			this.label = label;
    		}
    		
  		public String  getLabel()
    		{
    			return label;
    		}		
    		
    		public void printMessageData()
    		{
  		System.out.println( label);			// Field: label - Structle::String byte offset: 32
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, String label
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		char[] labelchars = label.toCharArray();
  		//byte[] labelBytes = new byte[labelchars.length];
  		byte[] labelBytes = new byte[32];
  		
  		for( int i = 0; i < 32; i++)
  		{
  			labelBytes[i] = 0x00;
  		}
  		
  		for( int i = 0; i < labelchars.length; i++)
  		{
  			labelBytes[i] = (byte)labelchars[i];
  		}
  		
  		memberData = labelBytes;
  		
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, String label
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, label
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		char[] labelchars = label.toCharArray();
  		//byte[] labelBytes = new byte[labelchars.length];
  		byte[] labelBytes = new byte[32];
  		
  		for( int i = 0; i < 32; i++)
  		{
  			labelBytes[i] = 0x00;
  		}
  		
  		for( int i = 0; i < labelchars.length; i++)
  		{
  			labelBytes[i] = (byte)labelchars[i];
  		}
  		
  		memberData = labelBytes;
  		
    		
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
	
	public static class StateLabel extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::StateLabel 
	{
  		// Fields: label;
  		private String label;			// Field: label - Structle::String byte offset: 0
  
  private static final int PAYLOAD_SIZE = 32;
  
  		public StateLabel( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public StateLabel( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[32];
			member0Data[0] = bytes[initialOffset + 0];
			member0Data[1] = bytes[initialOffset + 1];
			member0Data[2] = bytes[initialOffset + 2];
			member0Data[3] = bytes[initialOffset + 3];
			member0Data[4] = bytes[initialOffset + 4];
			member0Data[5] = bytes[initialOffset + 5];
			member0Data[6] = bytes[initialOffset + 6];
			member0Data[7] = bytes[initialOffset + 7];
			member0Data[8] = bytes[initialOffset + 8];
			member0Data[9] = bytes[initialOffset + 9];
			member0Data[10] = bytes[initialOffset + 10];
			member0Data[11] = bytes[initialOffset + 11];
			member0Data[12] = bytes[initialOffset + 12];
			member0Data[13] = bytes[initialOffset + 13];
			member0Data[14] = bytes[initialOffset + 14];
			member0Data[15] = bytes[initialOffset + 15];
			member0Data[16] = bytes[initialOffset + 16];
			member0Data[17] = bytes[initialOffset + 17];
			member0Data[18] = bytes[initialOffset + 18];
			member0Data[19] = bytes[initialOffset + 19];
			member0Data[20] = bytes[initialOffset + 20];
			member0Data[21] = bytes[initialOffset + 21];
			member0Data[22] = bytes[initialOffset + 22];
			member0Data[23] = bytes[initialOffset + 23];
			member0Data[24] = bytes[initialOffset + 24];
			member0Data[25] = bytes[initialOffset + 25];
			member0Data[26] = bytes[initialOffset + 26];
			member0Data[27] = bytes[initialOffset + 27];
			member0Data[28] = bytes[initialOffset + 28];
			member0Data[29] = bytes[initialOffset + 29];
			member0Data[30] = bytes[initialOffset + 30];
			member0Data[31] = bytes[initialOffset + 31];
  			
				int endOfStringIndex;
				byte[] subString;
  				
  				endOfStringIndex = member0Data.length;
  				
  				for( int i = 0; i < member0Data.length; i ++)
  				{
  					if( member0Data[i] == 0x00)
  					{
  						endOfStringIndex = i;
  						break;
  					}
  				}
  				
  				subString = new byte[endOfStringIndex];
  				for( int i = 0; i < endOfStringIndex; i++)
  				{
  					subString[i] = member0Data[i];
  				}
  				
  			label = new String( subString);
    		
    		}
    		
    		public StateLabel( Object padding
  		, String label
    			)
    		{
    			this.label = label;
    		}
    		
  		public String  getLabel()
    		{
    			return label;
    		}		
    		
    		public void printMessageData()
    		{
  		System.out.println( label);			// Field: label - Structle::String byte offset: 32
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, String label
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		char[] labelchars = label.toCharArray();
  		//byte[] labelBytes = new byte[labelchars.length];
  		byte[] labelBytes = new byte[32];
  		
  		for( int i = 0; i < 32; i++)
  		{
  			labelBytes[i] = 0x00;
  		}
  		
  		for( int i = 0; i < labelchars.length; i++)
  		{
  			labelBytes[i] = (byte)labelchars[i];
  		}
  		
  		memberData = labelBytes;
  		
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, String label
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, label
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		char[] labelchars = label.toCharArray();
  		//byte[] labelBytes = new byte[labelchars.length];
  		byte[] labelBytes = new byte[32];
  		
  		for( int i = 0; i < 32; i++)
  		{
  			labelBytes[i] = 0x00;
  		}
  		
  		for( int i = 0; i < labelchars.length; i++)
  		{
  			labelBytes[i] = (byte)labelchars[i];
  		}
  		
  		memberData = labelBytes;
  		
    		
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
	
	public static class GetTags extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::GetTags 
	{
  
  private static final int PAYLOAD_SIZE = 0;
  
  		public GetTags( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public GetTags( byte[] bytes, int initialOffset)
  		{
    		}
    		
    		public GetTags( Object padding
    			)
    		{
    		}
    		
    		
    		public void printMessageData()
    		{
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			
    			return bytes;
    		}
    		
    		public static int getPayloadSize()
    		{
    			return PAYLOAD_SIZE;
    		}
	}
	
	public static class SetTags extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::SetTags 
	{
  		// Fields: tags;
  		private UInt64 tags;			// Field: tags - Structle::Uint64 byte offset: 0
  
  private static final int PAYLOAD_SIZE = 8;
  
  		public SetTags( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public SetTags( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[8];
			member0Data[0] = bytes[initialOffset + 0];
			member0Data[1] = bytes[initialOffset + 1];
			member0Data[2] = bytes[initialOffset + 2];
			member0Data[3] = bytes[initialOffset + 3];
			member0Data[4] = bytes[initialOffset + 4];
			member0Data[5] = bytes[initialOffset + 5];
			member0Data[6] = bytes[initialOffset + 6];
			member0Data[7] = bytes[initialOffset + 7];
  			
  				
  			tags = new UInt64( member0Data); 
    		
    		}
    		
    		public SetTags( Object padding
  		, UInt64 tags
    			)
    		{
    			this.tags = tags;
    		}
    		
  		public UInt64  getTags()
    		{
    			return tags;
    		}			
    		
    		public void printMessageData()
    		{
  		tags.printValue( "tags");			// Field: tags - Structle::Uint64 byte offset: 8
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, UInt64 tags
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = tags.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, UInt64 tags
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, tags
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = tags.getBytes();
    		
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
	
	public static class StateTags extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::StateTags 
	{
  		// Fields: tags;
  		private UInt64 tags;			// Field: tags - Structle::Uint64 byte offset: 0
  
  private static final int PAYLOAD_SIZE = 8;
  
  		public StateTags( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public StateTags( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[8];
			member0Data[0] = bytes[initialOffset + 0];
			member0Data[1] = bytes[initialOffset + 1];
			member0Data[2] = bytes[initialOffset + 2];
			member0Data[3] = bytes[initialOffset + 3];
			member0Data[4] = bytes[initialOffset + 4];
			member0Data[5] = bytes[initialOffset + 5];
			member0Data[6] = bytes[initialOffset + 6];
			member0Data[7] = bytes[initialOffset + 7];
  			
  				
  			tags = new UInt64( member0Data); 
    		
    		}
    		
    		public StateTags( Object padding
  		, UInt64 tags
    			)
    		{
    			this.tags = tags;
    		}
    		
  		public UInt64  getTags()
    		{
    			return tags;
    		}			
    		
    		public void printMessageData()
    		{
  		tags.printValue( "tags");			// Field: tags - Structle::Uint64 byte offset: 8
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, UInt64 tags
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = tags.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, UInt64 tags
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, tags
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = tags.getBytes();
    		
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
	
	public static class GetTagLabels extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::GetTagLabels 
	{
  		// Fields: tags;
  		private UInt64 tags;			// Field: tags - Structle::Uint64 byte offset: 0
  
  private static final int PAYLOAD_SIZE = 8;
  
  		public GetTagLabels( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public GetTagLabels( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[8];
			member0Data[0] = bytes[initialOffset + 0];
			member0Data[1] = bytes[initialOffset + 1];
			member0Data[2] = bytes[initialOffset + 2];
			member0Data[3] = bytes[initialOffset + 3];
			member0Data[4] = bytes[initialOffset + 4];
			member0Data[5] = bytes[initialOffset + 5];
			member0Data[6] = bytes[initialOffset + 6];
			member0Data[7] = bytes[initialOffset + 7];
  			
  				
  			tags = new UInt64( member0Data); 
    		
    		}
    		
    		public GetTagLabels( Object padding
  		, UInt64 tags
    			)
    		{
    			this.tags = tags;
    		}
    		
  		public UInt64  getTags()
    		{
    			return tags;
    		}			
    		
    		public void printMessageData()
    		{
  		tags.printValue( "tags");			// Field: tags - Structle::Uint64 byte offset: 8
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, UInt64 tags
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = tags.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, UInt64 tags
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, tags
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = tags.getBytes();
    		
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
	
	public static class SetTagLabels extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::SetTagLabels 
	{
  		// Fields: tags, label;
  		private UInt64 tags;			// Field: tags - Structle::Uint64 byte offset: 0
  		private String label;			// Field: label - Structle::String byte offset: 8
  
  private static final int PAYLOAD_SIZE = 40;
  
  		public SetTagLabels( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public SetTagLabels( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[8];
			member0Data[0] = bytes[initialOffset + 0];
			member0Data[1] = bytes[initialOffset + 1];
			member0Data[2] = bytes[initialOffset + 2];
			member0Data[3] = bytes[initialOffset + 3];
			member0Data[4] = bytes[initialOffset + 4];
			member0Data[5] = bytes[initialOffset + 5];
			member0Data[6] = bytes[initialOffset + 6];
			member0Data[7] = bytes[initialOffset + 7];
  			
  				
  			tags = new UInt64( member0Data); 
    		
			byte[] member1Data = new byte[32];
			member1Data[0] = bytes[initialOffset + 8];
			member1Data[1] = bytes[initialOffset + 9];
			member1Data[2] = bytes[initialOffset + 10];
			member1Data[3] = bytes[initialOffset + 11];
			member1Data[4] = bytes[initialOffset + 12];
			member1Data[5] = bytes[initialOffset + 13];
			member1Data[6] = bytes[initialOffset + 14];
			member1Data[7] = bytes[initialOffset + 15];
			member1Data[8] = bytes[initialOffset + 16];
			member1Data[9] = bytes[initialOffset + 17];
			member1Data[10] = bytes[initialOffset + 18];
			member1Data[11] = bytes[initialOffset + 19];
			member1Data[12] = bytes[initialOffset + 20];
			member1Data[13] = bytes[initialOffset + 21];
			member1Data[14] = bytes[initialOffset + 22];
			member1Data[15] = bytes[initialOffset + 23];
			member1Data[16] = bytes[initialOffset + 24];
			member1Data[17] = bytes[initialOffset + 25];
			member1Data[18] = bytes[initialOffset + 26];
			member1Data[19] = bytes[initialOffset + 27];
			member1Data[20] = bytes[initialOffset + 28];
			member1Data[21] = bytes[initialOffset + 29];
			member1Data[22] = bytes[initialOffset + 30];
			member1Data[23] = bytes[initialOffset + 31];
			member1Data[24] = bytes[initialOffset + 32];
			member1Data[25] = bytes[initialOffset + 33];
			member1Data[26] = bytes[initialOffset + 34];
			member1Data[27] = bytes[initialOffset + 35];
			member1Data[28] = bytes[initialOffset + 36];
			member1Data[29] = bytes[initialOffset + 37];
			member1Data[30] = bytes[initialOffset + 38];
			member1Data[31] = bytes[initialOffset + 39];
  			
				int endOfStringIndex;
				byte[] subString;
  				
  				endOfStringIndex = member1Data.length;
  				
  				for( int i = 0; i < member1Data.length; i ++)
  				{
  					if( member1Data[i] == 0x00)
  					{
  						endOfStringIndex = i;
  						break;
  					}
  				}
  				
  				subString = new byte[endOfStringIndex];
  				for( int i = 0; i < endOfStringIndex; i++)
  				{
  					subString[i] = member1Data[i];
  				}
  				
  			label = new String( subString);
    		
    		}
    		
    		public SetTagLabels( Object padding
  		, UInt64 tags
  		, String label
    			)
    		{
    			this.tags = tags;
    			this.label = label;
    		}
    		
  		public UInt64  getTags()
    		{
    			return tags;
    		}			
  		public String  getLabel()
    		{
    			return label;
    		}		
    		
    		public void printMessageData()
    		{
  		tags.printValue( "tags");			// Field: tags - Structle::Uint64 byte offset: 40
  		System.out.println( label);			// Field: label - Structle::String byte offset: 40
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, UInt64 tags
  		, String label
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = tags.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		char[] labelchars = label.toCharArray();
  		//byte[] labelBytes = new byte[labelchars.length];
  		byte[] labelBytes = new byte[32];
  		
  		for( int i = 0; i < 32; i++)
  		{
  			labelBytes[i] = 0x00;
  		}
  		
  		for( int i = 0; i < labelchars.length; i++)
  		{
  			labelBytes[i] = (byte)labelchars[i];
  		}
  		
  		memberData = labelBytes;
  		
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, UInt64 tags
  		, String label
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, tags
  		, label
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = tags.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		char[] labelchars = label.toCharArray();
  		//byte[] labelBytes = new byte[labelchars.length];
  		byte[] labelBytes = new byte[32];
  		
  		for( int i = 0; i < 32; i++)
  		{
  			labelBytes[i] = 0x00;
  		}
  		
  		for( int i = 0; i < labelchars.length; i++)
  		{
  			labelBytes[i] = (byte)labelchars[i];
  		}
  		
  		memberData = labelBytes;
  		
    		
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
	
	public static class StateTagLabels extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::StateTagLabels 
	{
  		// Fields: tags, label;
  		private UInt64 tags;			// Field: tags - Structle::Uint64 byte offset: 0
  		private String label;			// Field: label - Structle::String byte offset: 8
  
  private static final int PAYLOAD_SIZE = 40;
  
  		public StateTagLabels( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public StateTagLabels( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[8];
			member0Data[0] = bytes[initialOffset + 0];
			member0Data[1] = bytes[initialOffset + 1];
			member0Data[2] = bytes[initialOffset + 2];
			member0Data[3] = bytes[initialOffset + 3];
			member0Data[4] = bytes[initialOffset + 4];
			member0Data[5] = bytes[initialOffset + 5];
			member0Data[6] = bytes[initialOffset + 6];
			member0Data[7] = bytes[initialOffset + 7];
  			
  				
  			tags = new UInt64( member0Data); 
    		
			byte[] member1Data = new byte[32];
			member1Data[0] = bytes[initialOffset + 8];
			member1Data[1] = bytes[initialOffset + 9];
			member1Data[2] = bytes[initialOffset + 10];
			member1Data[3] = bytes[initialOffset + 11];
			member1Data[4] = bytes[initialOffset + 12];
			member1Data[5] = bytes[initialOffset + 13];
			member1Data[6] = bytes[initialOffset + 14];
			member1Data[7] = bytes[initialOffset + 15];
			member1Data[8] = bytes[initialOffset + 16];
			member1Data[9] = bytes[initialOffset + 17];
			member1Data[10] = bytes[initialOffset + 18];
			member1Data[11] = bytes[initialOffset + 19];
			member1Data[12] = bytes[initialOffset + 20];
			member1Data[13] = bytes[initialOffset + 21];
			member1Data[14] = bytes[initialOffset + 22];
			member1Data[15] = bytes[initialOffset + 23];
			member1Data[16] = bytes[initialOffset + 24];
			member1Data[17] = bytes[initialOffset + 25];
			member1Data[18] = bytes[initialOffset + 26];
			member1Data[19] = bytes[initialOffset + 27];
			member1Data[20] = bytes[initialOffset + 28];
			member1Data[21] = bytes[initialOffset + 29];
			member1Data[22] = bytes[initialOffset + 30];
			member1Data[23] = bytes[initialOffset + 31];
			member1Data[24] = bytes[initialOffset + 32];
			member1Data[25] = bytes[initialOffset + 33];
			member1Data[26] = bytes[initialOffset + 34];
			member1Data[27] = bytes[initialOffset + 35];
			member1Data[28] = bytes[initialOffset + 36];
			member1Data[29] = bytes[initialOffset + 37];
			member1Data[30] = bytes[initialOffset + 38];
			member1Data[31] = bytes[initialOffset + 39];
  			
				int endOfStringIndex;
				byte[] subString;
  				
  				endOfStringIndex = member1Data.length;
  				
  				for( int i = 0; i < member1Data.length; i ++)
  				{
  					if( member1Data[i] == 0x00)
  					{
  						endOfStringIndex = i;
  						break;
  					}
  				}
  				
  				subString = new byte[endOfStringIndex];
  				for( int i = 0; i < endOfStringIndex; i++)
  				{
  					subString[i] = member1Data[i];
  				}
  				
  			label = new String( subString);
    		
    		}
    		
    		public StateTagLabels( Object padding
  		, UInt64 tags
  		, String label
    			)
    		{
    			this.tags = tags;
    			this.label = label;
    		}
    		
  		public UInt64  getTags()
    		{
    			return tags;
    		}			
  		public String  getLabel()
    		{
    			return label;
    		}		
    		
    		public void printMessageData()
    		{
  		tags.printValue( "tags");			// Field: tags - Structle::Uint64 byte offset: 40
  		System.out.println( label);			// Field: label - Structle::String byte offset: 40
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, UInt64 tags
  		, String label
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = tags.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		char[] labelchars = label.toCharArray();
  		//byte[] labelBytes = new byte[labelchars.length];
  		byte[] labelBytes = new byte[32];
  		
  		for( int i = 0; i < 32; i++)
  		{
  			labelBytes[i] = 0x00;
  		}
  		
  		for( int i = 0; i < labelchars.length; i++)
  		{
  			labelBytes[i] = (byte)labelchars[i];
  		}
  		
  		memberData = labelBytes;
  		
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, UInt64 tags
  		, String label
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, tags
  		, label
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = tags.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		char[] labelchars = label.toCharArray();
  		//byte[] labelBytes = new byte[labelchars.length];
  		byte[] labelBytes = new byte[32];
  		
  		for( int i = 0; i < 32; i++)
  		{
  			labelBytes[i] = 0x00;
  		}
  		
  		for( int i = 0; i < labelchars.length; i++)
  		{
  			labelBytes[i] = (byte)labelchars[i];
  		}
  		
  		memberData = labelBytes;
  		
    		
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
	
	public static class GetVersion extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::GetVersion 
	{
  
  private static final int PAYLOAD_SIZE = 0;
  
  		public GetVersion( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public GetVersion( byte[] bytes, int initialOffset)
  		{
    		}
    		
    		public GetVersion( Object padding
    			)
    		{
    		}
    		
    		
    		public void printMessageData()
    		{
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			
    			return bytes;
    		}
    		
    		public static int getPayloadSize()
    		{
    			return PAYLOAD_SIZE;
    		}
	}
	
	public static class StateVersion extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::StateVersion 
	{
  		// Fields: vendor, product, version;
  		private UInt32 vendor;			// Field: vendor - Structle::Uint32 byte offset: 0
  		private UInt32 product;			// Field: product - Structle::Uint32 byte offset: 4
  		private UInt32 version;			// Field: version - Structle::Uint32 byte offset: 8
  
  private static final int PAYLOAD_SIZE = 12;
  
  		public StateVersion( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public StateVersion( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[4];
			member0Data[0] = bytes[initialOffset + 0];
			member0Data[1] = bytes[initialOffset + 1];
			member0Data[2] = bytes[initialOffset + 2];
			member0Data[3] = bytes[initialOffset + 3];
  			
  				
  			vendor = new UInt32( member0Data); 
    		
			byte[] member1Data = new byte[4];
			member1Data[0] = bytes[initialOffset + 4];
			member1Data[1] = bytes[initialOffset + 5];
			member1Data[2] = bytes[initialOffset + 6];
			member1Data[3] = bytes[initialOffset + 7];
  			
  				
  			product = new UInt32( member1Data); 
    		
			byte[] member2Data = new byte[4];
			member2Data[0] = bytes[initialOffset + 8];
			member2Data[1] = bytes[initialOffset + 9];
			member2Data[2] = bytes[initialOffset + 10];
			member2Data[3] = bytes[initialOffset + 11];
  			
  				
  			version = new UInt32( member2Data); 
    		
    		}
    		
    		public StateVersion( Object padding
  		, UInt32 vendor
  		, UInt32 product
  		, UInt32 version
    			)
    		{
    			this.vendor = vendor;
    			this.product = product;
    			this.version = version;
    		}
    		
  		public UInt32  getVendor()
    		{
    			return vendor;
    		}			
  		public UInt32  getProduct()
    		{
    			return product;
    		}			
  		public UInt32  getVersion()
    		{
    			return version;
    		}			
    		
    		public void printMessageData()
    		{
  		vendor.printValue( "vendor");			// Field: vendor - Structle::Uint32 byte offset: 12
  		product.printValue( "product");			// Field: product - Structle::Uint32 byte offset: 12
  		version.printValue( "version");			// Field: version - Structle::Uint32 byte offset: 12
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, UInt32 vendor
  		, UInt32 product
  		, UInt32 version
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = vendor.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = product.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = version.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, UInt32 vendor
  		, UInt32 product
  		, UInt32 version
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, vendor
  		, product
  		, version
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = vendor.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = product.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = version.getBytes();
    		
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
	
	public static class GetInfo extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::GetInfo 
	{
  
  private static final int PAYLOAD_SIZE = 0;
  
  		public GetInfo( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public GetInfo( byte[] bytes, int initialOffset)
  		{
    		}
    		
    		public GetInfo( Object padding
    			)
    		{
    		}
    		
    		
    		public void printMessageData()
    		{
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			
    			return bytes;
    		}
    		
    		public static int getPayloadSize()
    		{
    			return PAYLOAD_SIZE;
    		}
	}
	
	public static class StateInfo extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::StateInfo 
	{
  		// Fields: time, uptime, downtime;
  		private UInt64 time;			// Field: time - Structle::Uint64 byte offset: 0
  		private UInt64 uptime;			// Field: uptime - Structle::Uint64 byte offset: 8
  		private UInt64 downtime;			// Field: downtime - Structle::Uint64 byte offset: 16
  
  private static final int PAYLOAD_SIZE = 24;
  
  		public StateInfo( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public StateInfo( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[8];
			member0Data[0] = bytes[initialOffset + 0];
			member0Data[1] = bytes[initialOffset + 1];
			member0Data[2] = bytes[initialOffset + 2];
			member0Data[3] = bytes[initialOffset + 3];
			member0Data[4] = bytes[initialOffset + 4];
			member0Data[5] = bytes[initialOffset + 5];
			member0Data[6] = bytes[initialOffset + 6];
			member0Data[7] = bytes[initialOffset + 7];
  			
  				
  			time = new UInt64( member0Data); 
    		
			byte[] member1Data = new byte[8];
			member1Data[0] = bytes[initialOffset + 8];
			member1Data[1] = bytes[initialOffset + 9];
			member1Data[2] = bytes[initialOffset + 10];
			member1Data[3] = bytes[initialOffset + 11];
			member1Data[4] = bytes[initialOffset + 12];
			member1Data[5] = bytes[initialOffset + 13];
			member1Data[6] = bytes[initialOffset + 14];
			member1Data[7] = bytes[initialOffset + 15];
  			
  				
  			uptime = new UInt64( member1Data); 
    		
			byte[] member2Data = new byte[8];
			member2Data[0] = bytes[initialOffset + 16];
			member2Data[1] = bytes[initialOffset + 17];
			member2Data[2] = bytes[initialOffset + 18];
			member2Data[3] = bytes[initialOffset + 19];
			member2Data[4] = bytes[initialOffset + 20];
			member2Data[5] = bytes[initialOffset + 21];
			member2Data[6] = bytes[initialOffset + 22];
			member2Data[7] = bytes[initialOffset + 23];
  			
  				
  			downtime = new UInt64( member2Data); 
    		
    		}
    		
    		public StateInfo( Object padding
  		, UInt64 time
  		, UInt64 uptime
  		, UInt64 downtime
    			)
    		{
    			this.time = time;
    			this.uptime = uptime;
    			this.downtime = downtime;
    		}
    		
  		public UInt64  getTime()
    		{
    			return time;
    		}			
  		public UInt64  getUptime()
    		{
    			return uptime;
    		}			
  		public UInt64  getDowntime()
    		{
    			return downtime;
    		}			
    		
    		public void printMessageData()
    		{
  		time.printValue( "time");			// Field: time - Structle::Uint64 byte offset: 24
  		uptime.printValue( "uptime");			// Field: uptime - Structle::Uint64 byte offset: 24
  		downtime.printValue( "downtime");			// Field: downtime - Structle::Uint64 byte offset: 24
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, UInt64 time
  		, UInt64 uptime
  		, UInt64 downtime
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = time.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = uptime.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = downtime.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, UInt64 time
  		, UInt64 uptime
  		, UInt64 downtime
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, time
  		, uptime
  		, downtime
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = time.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = uptime.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = downtime.getBytes();
    		
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
	
	public static class GetMcuRailVoltage extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::GetMcuRailVoltage 
	{
  
  private static final int PAYLOAD_SIZE = 0;
  
  		public GetMcuRailVoltage( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public GetMcuRailVoltage( byte[] bytes, int initialOffset)
  		{
    		}
    		
    		public GetMcuRailVoltage( Object padding
    			)
    		{
    		}
    		
    		
    		public void printMessageData()
    		{
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			
    			return bytes;
    		}
    		
    		public static int getPayloadSize()
    		{
    			return PAYLOAD_SIZE;
    		}
	}
	
	public static class StateMcuRailVoltage extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::StateMcuRailVoltage 
	{
  		// Fields: voltage;
  		private UInt32 voltage;			// Field: voltage - Structle::Uint32 byte offset: 0
  
  private static final int PAYLOAD_SIZE = 4;
  
  		public StateMcuRailVoltage( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public StateMcuRailVoltage( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[4];
			member0Data[0] = bytes[initialOffset + 0];
			member0Data[1] = bytes[initialOffset + 1];
			member0Data[2] = bytes[initialOffset + 2];
			member0Data[3] = bytes[initialOffset + 3];
  			
  				
  			voltage = new UInt32( member0Data); 
    		
    		}
    		
    		public StateMcuRailVoltage( Object padding
  		, UInt32 voltage
    			)
    		{
    			this.voltage = voltage;
    		}
    		
  		public UInt32  getVoltage()
    		{
    			return voltage;
    		}			
    		
    		public void printMessageData()
    		{
  		voltage.printValue( "voltage");			// Field: voltage - Structle::Uint32 byte offset: 4
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, UInt32 voltage
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = voltage.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, UInt32 voltage
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, voltage
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = voltage.getBytes();
    		
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
	
	public static class Reboot extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::Reboot 
	{
  
  private static final int PAYLOAD_SIZE = 0;
  
  		public Reboot( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public Reboot( byte[] bytes, int initialOffset)
  		{
    		}
    		
    		public Reboot( Object padding
    			)
    		{
    		}
    		
    		
    		public void printMessageData()
    		{
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			
    			return bytes;
    		}
    		
    		public static int getPayloadSize()
    		{
    			return PAYLOAD_SIZE;
    		}
	}
	
	public static class SetFactoryTestMode extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::SetFactoryTestMode 
	{
  		// Fields: on;
  		private Bool8 on;			// Field: on - Structle::Bool byte offset: 0
  
  private static final int PAYLOAD_SIZE = 1;
  
  		public SetFactoryTestMode( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public SetFactoryTestMode( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[1];
			member0Data[0] = bytes[initialOffset + 0];
  			
  				
  			on = new Bool8( member0Data);
    		
    		}
    		
    		public SetFactoryTestMode( Object padding
  		, Bool8 on
    			)
    		{
    			this.on = on;
    		}
    		
  		public Bool8  getOn()
    		{
    			return on;
    		}		
    		
    		public void printMessageData()
    		{
  		on.printValue( "on");			// Field: on - Structle::Bool byte offset: 1
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, Bool8 on
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = on.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, Bool8 on
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, on
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = on.getBytes();
    		
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
	
	public static class DisableFactoryTestMode extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::DisableFactoryTestMode 
	{
  
  private static final int PAYLOAD_SIZE = 0;
  
  		public DisableFactoryTestMode( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public DisableFactoryTestMode( byte[] bytes, int initialOffset)
  		{
    		}
    		
    		public DisableFactoryTestMode( Object padding
    			)
    		{
    		}
    		
    		
    		public void printMessageData()
    		{
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			
    			return bytes;
    		}
    		
    		public static int getPayloadSize()
    		{
    			return PAYLOAD_SIZE;
    		}
	}
	
	public static class StateFactoryTestMode extends LxProtocolTypeBase		// Struct: Lx::Protocol::Device::StateFactoryTestMode 
	{
  		// Fields: on, disabled;
  		private Bool8 on;			// Field: on - Structle::Bool byte offset: 0
  		private Bool8 disabled;			// Field: disabled - Structle::Bool byte offset: 1
  
  private static final int PAYLOAD_SIZE = 2;
  
  		public StateFactoryTestMode( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public StateFactoryTestMode( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[1];
			member0Data[0] = bytes[initialOffset + 0];
  			
  				
  			on = new Bool8( member0Data);
    		
			byte[] member1Data = new byte[1];
			member1Data[0] = bytes[initialOffset + 1];
  			
  				
  			disabled = new Bool8( member1Data);
    		
    		}
    		
    		public StateFactoryTestMode( Object padding
  		, Bool8 on
  		, Bool8 disabled
    			)
    		{
    			this.on = on;
    			this.disabled = disabled;
    		}
    		
  		public Bool8  getOn()
    		{
    			return on;
    		}		
  		public Bool8  getDisabled()
    		{
    			return disabled;
    		}		
    		
    		public void printMessageData()
    		{
  		on.printValue( "on");			// Field: on - Structle::Bool byte offset: 2
  		disabled.printValue( "disabled");			// Field: disabled - Structle::Bool byte offset: 2
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, Bool8 on
  		, Bool8 disabled
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = on.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		memberData = disabled.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, Bool8 on
  		, Bool8 disabled
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, on
  		, disabled
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = on.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		memberData = disabled.getBytes();
    		
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
	End Of File: LxProtocolDevice.java
*/

