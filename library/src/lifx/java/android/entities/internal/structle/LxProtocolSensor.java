//
//  LxProtocolSensor.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

// Start File: @./LxProtocolSensor.java 

package lifx.java.android.entities.internal.structle;

/*
	**** GENERATED CODE ****
	Start Of File: LxProtocolSensor.java 
*/

import java.util.HashMap;

import android.annotation.SuppressLint;

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

@SuppressLint( "UseSparseArrays")
@SuppressWarnings( "unused")
public class LxProtocolSensor
{

	
	public static class GetAmbientLight extends LxProtocolTypeBase		// Struct: Lx::Protocol::Sensor::GetAmbientLight 
	{
  
  private static final int PAYLOAD_SIZE = 0;
  
  		public GetAmbientLight( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public GetAmbientLight( byte[] bytes, int initialOffset)
  		{
    		}
    		
    		public GetAmbientLight( Object padding
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
	
	public static class StateAmbientLight extends LxProtocolTypeBase		// Struct: Lx::Protocol::Sensor::StateAmbientLight 
	{
  		// Fields: lux;
  		private Float32 lux;				// Field: lux - Structle::Float byte offset: 0
  
  private static final int PAYLOAD_SIZE = 4;
  
  		public StateAmbientLight( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public StateAmbientLight( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[4];
			member0Data[0] = bytes[initialOffset + 0];
			member0Data[1] = bytes[initialOffset + 1];
			member0Data[2] = bytes[initialOffset + 2];
			member0Data[3] = bytes[initialOffset + 3];
  			
  				
  			lux = new Float32( member0Data);
    		
    		}
    		
    		public StateAmbientLight( Object padding
  		, Float32 lux
    			)
    		{
    			this.lux = lux;
    		}
    		
  		public Float32  getLux()
    		{
    			return lux;
    		}				
    		
    		public void printMessageData()
    		{
  		lux.printValue( "lux");				// Field: lux - Structle::Float byte offset: 4
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, Float32 lux
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = lux.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, Float32 lux
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, lux
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = lux.getBytes();
    		
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
	
	public static class GetDimmerVoltage extends LxProtocolTypeBase		// Struct: Lx::Protocol::Sensor::GetDimmerVoltage 
	{
  
  private static final int PAYLOAD_SIZE = 0;
  
  		public GetDimmerVoltage( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public GetDimmerVoltage( byte[] bytes, int initialOffset)
  		{
    		}
    		
    		public GetDimmerVoltage( Object padding
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
	
	public static class StateDimmerVoltage extends LxProtocolTypeBase		// Struct: Lx::Protocol::Sensor::StateDimmerVoltage 
	{
  		// Fields: voltage;
  		private UInt32 voltage;			// Field: voltage - Structle::Uint32 byte offset: 0
  
  private static final int PAYLOAD_SIZE = 4;
  
  		public StateDimmerVoltage( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public StateDimmerVoltage( byte[] bytes, int initialOffset)
  		{
			byte[] member0Data = new byte[4];
			member0Data[0] = bytes[initialOffset + 0];
			member0Data[1] = bytes[initialOffset + 1];
			member0Data[2] = bytes[initialOffset + 2];
			member0Data[3] = bytes[initialOffset + 3];
  			
  				
  			voltage = new UInt32( member0Data); 
    		
    		}
    		
    		public StateDimmerVoltage( Object padding
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
	
}

/*
	End Of File: LxProtocolSensor.java
*/

