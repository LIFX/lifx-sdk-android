//
//  LxProtocolWan.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

// Start File: @./LxProtocolWan.java 

package lifx.java.android.entities.internal.structle;

/*
	**** GENERATED CODE ****
	Start Of File: LxProtocolWan.java 
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
public class LxProtocolWan
{

	
	public static class ConnectPlain extends LxProtocolTypeBase		// Struct: Lx::Protocol::Wan::ConnectPlain 
	{
  		// Fields: user, pass;
  		private String user;			// Field: user - Structle::String byte offset: 0
  		private String pass;			// Field: pass - Structle::String byte offset: 32
  
  private static final int PAYLOAD_SIZE = 64;
  
  		public ConnectPlain( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public ConnectPlain( byte[] bytes, int initialOffset)
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
  				
  			user = new String( subString);
    		
			byte[] member1Data = new byte[32];
			member1Data[0] = bytes[initialOffset + 32];
			member1Data[1] = bytes[initialOffset + 33];
			member1Data[2] = bytes[initialOffset + 34];
			member1Data[3] = bytes[initialOffset + 35];
			member1Data[4] = bytes[initialOffset + 36];
			member1Data[5] = bytes[initialOffset + 37];
			member1Data[6] = bytes[initialOffset + 38];
			member1Data[7] = bytes[initialOffset + 39];
			member1Data[8] = bytes[initialOffset + 40];
			member1Data[9] = bytes[initialOffset + 41];
			member1Data[10] = bytes[initialOffset + 42];
			member1Data[11] = bytes[initialOffset + 43];
			member1Data[12] = bytes[initialOffset + 44];
			member1Data[13] = bytes[initialOffset + 45];
			member1Data[14] = bytes[initialOffset + 46];
			member1Data[15] = bytes[initialOffset + 47];
			member1Data[16] = bytes[initialOffset + 48];
			member1Data[17] = bytes[initialOffset + 49];
			member1Data[18] = bytes[initialOffset + 50];
			member1Data[19] = bytes[initialOffset + 51];
			member1Data[20] = bytes[initialOffset + 52];
			member1Data[21] = bytes[initialOffset + 53];
			member1Data[22] = bytes[initialOffset + 54];
			member1Data[23] = bytes[initialOffset + 55];
			member1Data[24] = bytes[initialOffset + 56];
			member1Data[25] = bytes[initialOffset + 57];
			member1Data[26] = bytes[initialOffset + 58];
			member1Data[27] = bytes[initialOffset + 59];
			member1Data[28] = bytes[initialOffset + 60];
			member1Data[29] = bytes[initialOffset + 61];
			member1Data[30] = bytes[initialOffset + 62];
			member1Data[31] = bytes[initialOffset + 63];
  			
  				
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
  				
  			pass = new String( subString);
    		
    		}
    		
    		public ConnectPlain( Object padding
  		, String user
  		, String pass
    			)
    		{
    			this.user = user;
    			this.pass = pass;
    		}
    		
  		public String  getUser()
    		{
    			return user;
    		}		
  		public String  getPass()
    		{
    			return pass;
    		}		
    		
    		public void printMessageData()
    		{
  		System.out.println( user);			// Field: user - Structle::String byte offset: 64
  		System.out.println( pass);			// Field: pass - Structle::String byte offset: 64
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, String user
  		, String pass
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		char[] userchars = user.toCharArray();
  		//byte[] userBytes = new byte[userchars.length];
  		byte[] userBytes = new byte[32];
  		
  		for( int i = 0; i < 32; i++)
  		{
  			userBytes[i] = 0x00;
  		}
  		
  		for( int i = 0; i < userchars.length; i++)
  		{
  			userBytes[i] = (byte)userchars[i];
  		}
  		
  		memberData = userBytes;
  		
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    			
    		
  		char[] passchars = pass.toCharArray();
  		//byte[] passBytes = new byte[passchars.length];
  		byte[] passBytes = new byte[32];
  		
  		for( int i = 0; i < 32; i++)
  		{
  			passBytes[i] = 0x00;
  		}
  		
  		for( int i = 0; i < passchars.length; i++)
  		{
  			passBytes[i] = (byte)passchars[i];
  		}
  		
  		memberData = passBytes;
  		
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, String user
  		, String pass
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, user
  		, pass
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		char[] userchars = user.toCharArray();
  		//byte[] userBytes = new byte[userchars.length];
  		byte[] userBytes = new byte[32];
  		
  		for( int i = 0; i < 32; i++)
  		{
  			userBytes[i] = 0x00;
  		}
  		
  		for( int i = 0; i < userchars.length; i++)
  		{
  			userBytes[i] = (byte)userchars[i];
  		}
  		
  		memberData = userBytes;
  		
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				bytes[(offset + i)] = memberData[i];
    			}
    			
    			offset += memberData.length;
    			// = name.getBytes();        		
  		char[] passchars = pass.toCharArray();
  		//byte[] passBytes = new byte[passchars.length];
  		byte[] passBytes = new byte[32];
  		
  		for( int i = 0; i < 32; i++)
  		{
  			passBytes[i] = 0x00;
  		}
  		
  		for( int i = 0; i < passchars.length; i++)
  		{
  			passBytes[i] = (byte)passchars[i];
  		}
  		
  		memberData = passBytes;
  		
    		
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
	
	public static class ConnectKey extends LxProtocolTypeBase		// Struct: Lx::Protocol::Wan::ConnectKey 
	{
  		// Fields: key;
  		private byte[]  key = new byte[32];		// Field: key - Structle::Bytes byte offset: 0
  
  private static final int PAYLOAD_SIZE = 32;
  
  		public ConnectKey( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public ConnectKey( byte[] bytes, int initialOffset)
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
  			
  				
  			key = member0Data; 
    		
    		}
    		
    		public ConnectKey( Object padding
  		, byte[]  key	
    			)
    		{
    			this.key = key;
    		}
    		
  		public byte[]  getKey()
    		{
    			return key;
    		}		
    		
    		public void printMessageData()
    		{
  		System.out.println( "Byte Array Print not currently supported");
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, byte[]  key	
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = key;	
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, byte[]  key	
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, key	
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = key;	
    		
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
	
	public static class StateConnect extends LxProtocolTypeBase		// Struct: Lx::Protocol::Wan::StateConnect 
	{
  		// Fields: key;
  		private byte[]  key = new byte[32];		// Field: key - Structle::Bytes byte offset: 0
  
  private static final int PAYLOAD_SIZE = 32;
  
  		public StateConnect( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public StateConnect( byte[] bytes, int initialOffset)
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
  			
  				
  			key = member0Data; 
    		
    		}
    		
    		public StateConnect( Object padding
  		, byte[]  key	
    			)
    		{
    			this.key = key;
    		}
    		
  		public byte[]  getKey()
    		{
    			return key;
    		}		
    		
    		public void printMessageData()
    		{
  		System.out.println( "Byte Array Print not currently supported");
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, byte[]  key	
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
  		memberData = key;	
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, byte[]  key	
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, key	
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
    			// = name.getBytes();        		
  		memberData = key;	
    		
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
	
	public static class Sub extends LxProtocolTypeBase		// Struct: Lx::Protocol::Wan::Sub 
	{
  		// Fields: target, site, device;
  		private byte[]  target = new byte[8];		// Field: target - Structle::Bytes byte offset: 0
  		private byte[]  site = new byte[6];		// Field: site - Structle::Bytes byte offset: 8
  		private Bool8 device;			// Field: device - Structle::Bool byte offset: 14
  
  private static final int PAYLOAD_SIZE = 15;
  
  		public Sub( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public Sub( byte[] bytes, int initialOffset)
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
  			
  				
  			target = member0Data; 
    		
			byte[] member1Data = new byte[6];
			member1Data[0] = bytes[initialOffset + 8];
			member1Data[1] = bytes[initialOffset + 9];
			member1Data[2] = bytes[initialOffset + 10];
			member1Data[3] = bytes[initialOffset + 11];
			member1Data[4] = bytes[initialOffset + 12];
			member1Data[5] = bytes[initialOffset + 13];
  			
  				
  			site = member1Data; 
    		
			byte[] member2Data = new byte[1];
			member2Data[0] = bytes[initialOffset + 14];
  			
  				
  			device = new Bool8( member2Data);
    		
    		}
    		
    		public Sub( Object padding
  		, byte[]  target	
  		, byte[]  site	
  		, Bool8 device
    			)
    		{
    			this.target = target;
    			this.site = site;
    			this.device = device;
    		}
    		
  		public byte[]  getTarget()
    		{
    			return target;
    		}		
  		public byte[]  getSite()
    		{
    			return site;
    		}		
  		public Bool8  getDevice()
    		{
    			return device;
    		}		
    		
    		public void printMessageData()
    		{
  		System.out.println( "Byte Array Print not currently supported");
  		System.out.println( "Byte Array Print not currently supported");
  		device.printValue( "device");			// Field: device - Structle::Bool byte offset: 15
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, byte[]  target	
  		, byte[]  site	
  		, Bool8 device
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
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
    			
    		
  		memberData = device.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, byte[]  target	
  		, byte[]  site	
  		, Bool8 device
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, target	
  		, site	
  		, device
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
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
  		memberData = device.getBytes();
    		
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
	
	public static class Unsub extends LxProtocolTypeBase		// Struct: Lx::Protocol::Wan::Unsub 
	{
  		// Fields: target, site, device;
  		private byte[]  target = new byte[8];		// Field: target - Structle::Bytes byte offset: 0
  		private byte[]  site = new byte[6];		// Field: site - Structle::Bytes byte offset: 8
  		private Bool8 device;			// Field: device - Structle::Bool byte offset: 14
  
  private static final int PAYLOAD_SIZE = 15;
  
  		public Unsub( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public Unsub( byte[] bytes, int initialOffset)
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
  			
  				
  			target = member0Data; 
    		
			byte[] member1Data = new byte[6];
			member1Data[0] = bytes[initialOffset + 8];
			member1Data[1] = bytes[initialOffset + 9];
			member1Data[2] = bytes[initialOffset + 10];
			member1Data[3] = bytes[initialOffset + 11];
			member1Data[4] = bytes[initialOffset + 12];
			member1Data[5] = bytes[initialOffset + 13];
  			
  				
  			site = member1Data; 
    		
			byte[] member2Data = new byte[1];
			member2Data[0] = bytes[initialOffset + 14];
  			
  				
  			device = new Bool8( member2Data);
    		
    		}
    		
    		public Unsub( Object padding
  		, byte[]  target	
  		, byte[]  site	
  		, Bool8 device
    			)
    		{
    			this.target = target;
    			this.site = site;
    			this.device = device;
    		}
    		
  		public byte[]  getTarget()
    		{
    			return target;
    		}		
  		public byte[]  getSite()
    		{
    			return site;
    		}		
  		public Bool8  getDevice()
    		{
    			return device;
    		}		
    		
    		public void printMessageData()
    		{
  		System.out.println( "Byte Array Print not currently supported");
  		System.out.println( "Byte Array Print not currently supported");
  		device.printValue( "device");			// Field: device - Structle::Bool byte offset: 15
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, byte[]  target	
  		, byte[]  site	
  		, Bool8 device
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
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
    			
    		
  		memberData = device.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, byte[]  target	
  		, byte[]  site	
  		, Bool8 device
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, target	
  		, site	
  		, device
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
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
  		memberData = device.getBytes();
    		
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
	
	public static class StateSub extends LxProtocolTypeBase		// Struct: Lx::Protocol::Wan::StateSub 
	{
  		// Fields: target, site, device;
  		private byte[]  target = new byte[8];		// Field: target - Structle::Bytes byte offset: 0
  		private byte[]  site = new byte[6];		// Field: site - Structle::Bytes byte offset: 8
  		private Bool8 device;			// Field: device - Structle::Bool byte offset: 14
  
  private static final int PAYLOAD_SIZE = 15;
  
  		public StateSub( byte[] bytes)
  		{
  			this( bytes, 0);
  		}
  
  		public StateSub( byte[] bytes, int initialOffset)
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
  			
  				
  			target = member0Data; 
    		
			byte[] member1Data = new byte[6];
			member1Data[0] = bytes[initialOffset + 8];
			member1Data[1] = bytes[initialOffset + 9];
			member1Data[2] = bytes[initialOffset + 10];
			member1Data[3] = bytes[initialOffset + 11];
			member1Data[4] = bytes[initialOffset + 12];
			member1Data[5] = bytes[initialOffset + 13];
  			
  				
  			site = member1Data; 
    		
			byte[] member2Data = new byte[1];
			member2Data[0] = bytes[initialOffset + 14];
  			
  				
  			device = new Bool8( member2Data);
    		
    		}
    		
    		public StateSub( Object padding
  		, byte[]  target	
  		, byte[]  site	
  		, Bool8 device
    			)
    		{
    			this.target = target;
    			this.site = site;
    			this.device = device;
    		}
    		
  		public byte[]  getTarget()
    		{
    			return target;
    		}		
  		public byte[]  getSite()
    		{
    			return site;
    		}		
  		public Bool8  getDevice()
    		{
    			return device;
    		}		
    		
    		public void printMessageData()
    		{
  		System.out.println( "Byte Array Print not currently supported");
  		System.out.println( "Byte Array Print not currently supported");
  		device.printValue( "device");			// Field: device - Structle::Bool byte offset: 15
    		}
    		
    		public static void loadMessageDataWithPayloadAtOffset( byte[] messageData, int offset
  		, byte[]  target	
  		, byte[]  site	
  		, Bool8 device
    			)
    		{
    			byte[] memberData;		// = name.getBytes();
    		
    			
    		
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
    			
    		
  		memberData = device.getBytes();
    		
    			for( int i = 0; i < (memberData.length); i++)
    			{
    				messageData[(offset + i)] = memberData[i];
    			}
    		
    			offset += memberData.length;
    		}
    		
    		public static void loadMessageDataWithPayloadAtDefaultOffset( byte[] messageData
  		, byte[]  target	
  		, byte[]  site	
  		, Bool8 device
    			)
    		{
    			int offset = PAYLOAD_OFFSET;
    			
    			loadMessageDataWithPayloadAtOffset( messageData, offset
  		, target	
  		, site	
  		, device
    			);
    		}
    		
    		public byte[] getBytes()
    		{
    			int offset = 0;
    		
    			byte[] bytes = new byte[getPayloadSize()];
    			
    			byte[] memberData;
    			
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
  		memberData = device.getBytes();
    		
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
	End Of File: LxProtocolWan.java
*/

