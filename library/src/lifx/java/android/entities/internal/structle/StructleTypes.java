//
//  StructleTypes.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.entities.internal.structle;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class StructleTypes
{
	public abstract static class LxProtocolTypeBase
	{
		protected static final int PAYLOAD_OFFSET = 36;
		public abstract void printMessageData();
		public abstract byte[] getBytes();
	}

	public static class ProtocolField
	{
		public ProtocolField( byte[] data)
		{
		}
		
		public byte[] getBytes()
		{
			return new byte[]{};
		}
		
		public void printValue( String varName)
		{
			System.out.print( varName + ": " + getValue());
		}
		
		public int getValue()
		{
			return 0;
		}
	}

	public static class RoutingField
	{
		public RoutingField( byte[] data)
		{
		}
		
		public byte[] getBytes()
		{
			return new byte[]{};
		}
		
		public void printValue( String varName)
		{
			System.out.println( varName + ": " + getValue());
		}
		
		public int getValue()
		{
			return 0;
		}
	}

	public static class Int8
	{
		private byte[] data;
		
		public Int8( byte[] value)
		{
			set( value);
		}
		
		public Int8( byte value)
		{
			data = new byte[1];
			data[0] = value;
		}
		
		public void set( byte[] value)
		{
			this.data = value;
		}
		
		public byte[] getBytes()
		{
			return data;
		}
		
		public int getValue()
		{
			return (int) data[0];
		}
		
		public void printValue( String varName)
		{
			System.out.print( varName + ": " + getValue());
			
			System.out.print( ", Hex: ( ");
			for( int i = 0; i < data.length; i++)
			{
				System.out.printf( "%02X ", data[i]);
			}
			System.out.printf( ")");
			System.out.println( "");
		}
	}
	
	public static class UInt8
	{
		private byte[] data;
		
		public UInt8( byte[] value)
		{
			set( value);
		}
		
		public UInt8( int value)
		{
			data = new byte[1];
			
			byte[] buffer = ByteBuffer.allocate( 4).order( ByteOrder.LITTLE_ENDIAN).putInt( value).array();
			data[0] = buffer[0];
		}
		
		public void set( byte[] value)
		{
			this.data = value;
		}
		
		public byte[] getBytes()
		{
			return data;
		}
		
		public int getValue()
		{
			int value = bytesToInt( new byte[]{ data[0], 0x00, 0x00, 0x00});
			
			return value;
		}
		
		public void printValue( String varName)
		{
			System.out.print( varName + ": " + getValue());
			
			System.out.print( ", Hex: ( ");
			for( int i = 0; i < data.length; i++)
			{
				System.out.printf( "%02X ", data[i]);
			}
			System.out.printf( ")");
			System.out.println( "");
		}
	}
	
	public static class Int16
	{
		private byte[] data;
		
		public Int16( byte[] value)
		{
			set( value);
		}
		
		public Int16( short value)
		{
			data = new byte[2];
			
			data[0] = (byte)(value & 0xff);
			data[1] = (byte)((value >> 8) & 0xff);
		}
		
		public void set( byte[] value)
		{
			this.data = value;
		}
		
		public byte[] getBytes()
		{
			return data;
		}
		
		public int getValue()
		{
			return (int) getShortValue(data[0], data[1]);
		}
		
		public void printValue( String varName)
		{
			System.out.print( varName + ": " + getValue());
			
			System.out.print( ", Hex: ( ");
			for( int i = 0; i < data.length; i++)
			{
				System.out.printf( "%02X ", data[i]);
			}
			System.out.printf( ")");
			System.out.println( "");
		}
	}
	
	public static class UInt16
	{
		public static final float MAX_U16_VALUE = 65535.0f;
		
		private byte[] data;
		
		public UInt16( byte[] value)
		{
			set( value);
		}
		
		public UInt16( int value)
		{
			data = new byte[2];
			
			short valueShort = (short) value;
			data[0] = (byte)(valueShort & 0xff);
			data[1] = (byte)((valueShort >> 8) & 0xff);
		}
		
		public void set( byte[] value)
		{
			this.data = value;
		}
		
		public byte[] getBytes()
		{
			return data;
		}
		
		public int getValue()
		{
//			int value = (int) getShortValue( data[0], data[1]);
//			
//			if( value < 0)
//			{
//				value += 65536;
//			}
			
			int value = bytesToInt( new byte[]{ data[0], data[1], 0x00, 0x00});
			
			return value;
		}
		
		public void printValue( String varName)
		{
			System.out.print( varName + ": " + getValue());
			
			System.out.print( ", Hex: ( ");
			for( int i = 0; i < data.length; i++)
			{
				System.out.printf( "%02X ", data[i]);
			}
			System.out.printf( ")");
			System.out.println( "");
		}
	}
	
	public static class Int32
	{
		private byte[] value;
		
		public Int32( byte[] value)
		{
			set( value);
		}
		
		public Int32( int value)
		{
			this.value = new byte[4];
			
			byte[] buffer = ByteBuffer.allocate( 4).order( ByteOrder.LITTLE_ENDIAN).putInt( value).array();
	    	
			this.value[0] = buffer[0];
			this.value[1] = buffer[1];
			this.value[2] = buffer[2];
			this.value[3] = buffer[3];
		}
		
		public void set( byte[] value)
		{
			this.value = value;
		}
		
		public byte[] getBytes()
		{
			return value;
		}
		
		public int getValue()
		{
			return (int) getIntValue( value[0], value[1], value[2], value[3]);
		}
		
		public void printValue( String varName)
		{
			System.out.print( varName + ": " + getValue());
			
			System.out.print( ", Hex: ( ");
			for( int i = 0; i < value.length; i++)
			{
				System.out.printf( "%02X ", value[i]);
			}
			System.out.printf( ")");
			System.out.println( "");
		}
	}
	
	public static class UInt32
	{
		private byte[] value;
		
		public UInt32( byte[] value)
		{
			set( value);
		}
		
		public UInt32( long value)
		{
			this.value = new byte[4];
			
			int valueInt = (int)value;
			
			byte[] buffer = ByteBuffer.allocate( 4).order( ByteOrder.LITTLE_ENDIAN).putInt( valueInt).array();
	    	
			this.value[0] = buffer[0];
			this.value[1] = buffer[1];
			this.value[2] = buffer[2];
			this.value[3] = buffer[3];
		}
		
		public void set( byte[] value)
		{
			this.value = value;
		}
		
		public byte[] getBytes()
		{
			return value;
		}
		
		public long getValue()
		{
//			long value = getIntValue( this.value[0], this.value[1], this.value[2], this.value[3]);
//			
//			if( value < 0)
//			{
//				value += 4294967296L;//2147483648L;
//			}
			long value = getLongValue(this.value[0], this.value[1], this.value[2], this.value[3], (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00);
			
			return value;
		}
		
		public void printValue( String varName)
		{
			System.out.print( varName + ": " + getValue());
			
			System.out.print( ", Hex: ( ");
			for( int i = 0; i < value.length; i++)
			{
				System.out.printf( "%02X ", value[i]);
			}
			System.out.printf( ")");
			System.out.println( "");
		}
	}
	
	public static class Int64
	{
		private byte[] value;
		
		public Int64( byte[] value)
		{
			set( value);
		}
		
		public Int64( long value)
		{
			this.value = new byte[8];
			
			byte[] buffer = ByteBuffer.allocate( 8).order( ByteOrder.LITTLE_ENDIAN).putLong( value).array();
	    	
			this.value[0] = buffer[0];
			this.value[1] = buffer[1];
			this.value[2] = buffer[2];
			this.value[3] = buffer[3];
			this.value[4] = buffer[4];
			this.value[5] = buffer[5];
			this.value[6] = buffer[6];
			this.value[7] = buffer[7];
		}
		
		public void set( byte[] value)
		{
			this.value = value;
		}
		
		public byte[] getBytes()
		{
			return value;
		}
		
		public long getValue()
		{
			return (long) getLongValue( value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7]);
		}
		
		public void printValue( String varName)
		{
			System.out.print( varName + ": " + getValue());
			
			System.out.print( ", Hex: ( ");
			for( int i = 0; i < value.length; i++)
			{
				System.out.printf( "%02X ", value[i]);
			}
			System.out.printf( ")");
			System.out.println( "");
		}
	}
	
	public static class UInt64
	{
		private byte[] value;
		
		public UInt64( byte[] value)
		{
			set( value);
		}
		
		public UInt64( long value)
		{
			this.value = new byte[8];
			
			byte[] buffer = ByteBuffer.allocate( 8).order( ByteOrder.LITTLE_ENDIAN).putLong( value).array();
	    	
			this.value[0] = buffer[0];
			this.value[1] = buffer[1];
			this.value[2] = buffer[2];
			this.value[3] = buffer[3];
			this.value[4] = buffer[4];
			this.value[5] = buffer[5];
			this.value[6] = buffer[6];
			this.value[7] = buffer[7];
		}
		
		public void set( byte[] value)
		{
			this.value = value;
		}
		
		public byte[] getBytes()
		{
			return value;
		}
		
		public long getSignedValue()
		{
			return (long) getLongValue( value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7]);
		}
		
		public boolean equals( UInt64 other)
		{
			for( int i = 0; i < this.value.length; i++)
			{
				if( other.value[i] != this.value[i])
				{
					return false;
				}
			}
			
			return true;
		}
		
		public void printValue( String varName)
		{
			System.out.print( varName + ": " + getSignedValue());
			
			System.out.print( ", Hex: ( ");
			for( int i = 0; i < value.length; i++)
			{
				System.out.printf( "%02X ", value[i]);
			}
			System.out.printf( ")");
			System.out.println( "");
		}
	}
	
	public static class Bool8
	{
		private byte[] data;
		
		public Bool8( byte[] data)
		{
			set( data);
		}
		
		public Bool8( boolean value)
		{
			data = new byte[1];
		 	
			if( value)
			{
				data[0] = (byte) 0xff;
			}
			else
			{
				data[1] = (byte) 0x00;
			}
		}
		
		public void set( byte[] data)
		{
			this.data = data;
		}
		
		public byte[] getBytes()
		{
			return data;
		}
		
		public boolean getValue()
		{
			if( data[0] != 0)
			{
				return true;
			}
			
			return false;
		}
		
		public void printValue( String varName)
		{
			System.out.print( varName + ": " + getValue());
			
			System.out.print( ", Hex: ( ");
			for( int i = 0; i < data.length; i++)
			{
				System.out.printf( "%02X ", data[i]);
			}
			System.out.printf( ")");
			System.out.println( "");
		}
	}
	
	public static class Float32
	{
		private byte[] value;
		
		public Float32( byte[] value)
		{
			set( value);
		}
		
		public Float32( float value)
		{
			this.value = new byte[4];
			
			byte[] buffer = ByteBuffer.allocate( 4).order( ByteOrder.LITTLE_ENDIAN).putFloat( value).array();
	    	
			this.value[0] = buffer[0];
			this.value[1] = buffer[1];
			this.value[2] = buffer[2];
			this.value[3] = buffer[3];
		}
		
		public void set( byte[] value)
		{
			this.value = value;
		}
		
		public byte[] getBytes()
		{
			return value;
		}
		
		public float getValue()
		{
			return getFloatValue( value[0], value[1], value[2], value[3]);
		}
		
		public void printValue( String varName)
		{
			System.out.print( varName + ": " + getValue());
			
			System.out.print( ", Hex: ( ");
			for( int i = 0; i < value.length; i++)
			{
				System.out.printf( "%02X ", value[i]);
			}
			System.out.printf( ")");
			System.out.println( "");
		}
	}
	
	public static class Double64
	{
		private byte[] value;
		
		public Double64( byte[] value)
		{
			set( value);
		}
		
		public Double64( long value)
		{
			this.value = new byte[8];
			
			byte[] buffer = ByteBuffer.allocate( 8).order( ByteOrder.LITTLE_ENDIAN).putDouble( value).array();
	    	
			this.value[0] = buffer[0];
			this.value[1] = buffer[1];
			this.value[2] = buffer[2];
			this.value[3] = buffer[3];
			this.value[4] = buffer[4];
			this.value[5] = buffer[5];
			this.value[6] = buffer[6];
			this.value[7] = buffer[7];
		}
		
		public void set( byte[] value)
		{
			this.value = value;
		}
		
		public byte[] getBytes()
		{
			return value;
		}
		
		public double getValue()
		{
			return getDoubleValue( value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7]);
		}
		
		public void printValue( String varName)
		{
			System.out.print( varName + ": " + getValue());
			
			System.out.print( ", Hex: ( ");
			for( int i = 0; i < value.length; i++)
			{
				System.out.printf( "%02X ", value[i]);
			}
			System.out.printf( ")");
			System.out.println( "");
		}
	}
	
public static long getLongValue( byte b0, byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7)
{
	byte[] bytes = new byte[]{ b0, b1, b2, b3, b4, b5, b6, b7};
	
	return bytesToLong( bytes);
}

public static float getFloatValue( byte b0, byte b1, byte b2, byte b3)
{
	byte[] bytes = { b0, b1, b2, b3};
	float f = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();
	return f;
}

public static double getDoubleValue( byte b0, byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7)
{
	byte[] bytes = { b0, b1, b2, b3, b4, b5, b6, b7};
	double f = ByteBuffer.wrap( bytes).order(ByteOrder.LITTLE_ENDIAN).getDouble();
	return f;
}

public static int getIntValue( byte b0, byte b1, byte b2, byte b3)
{
	byte[] bytes = { b0, b1, b2, b3};
	
	return bytesToInt( bytes);
}

public static int bytesToInt( byte[] bytes)
{
	int i = ByteBuffer.wrap( bytes).order( ByteOrder.LITTLE_ENDIAN).getInt();
	return i;
}

public static long bytesToLong( byte[] bytes)
{
	long value = 0;
	for (int i = 0; i < bytes.length; i++)
	{
	   value += ((long) bytes[i] & 0xffL) << (8 * i);
	}
	
	return value;
}

public static short getShortValue( byte b0, byte b1) 
	{	
		short value = b1;
		value = (short) ((value << 8) | b0);
		
		return value;
	}
}
