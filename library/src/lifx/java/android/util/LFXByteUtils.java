//
//  LFXByteUtils.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.util;

public class LFXByteUtils 
{	
	public static boolean areByteArraysEqual( byte[] byteArray0, byte[] byteArray1)
	{
		if( byteArray0 == null && byteArray1 != null)
		{
			return false;
		}
		else if( byteArray0 != null && byteArray1 == null)
		{
			return false;
		}
		else if( byteArray0 == null && byteArray1 == null)
		{
			return true;
		}
		
		if( byteArray0.length != byteArray1.length)
		{
			return false;
		}
		
		for( int i = 0; i < byteArray0.length; i++)
		{
			if( byteArray0[i] != byteArray1[i])
			{
				return false;
			}
		}
		
		return true;
	}
	
	public static byte[] hexStringToByteArray(String s) 
	{
        int len = s.length();
        
        byte[] data = new byte[len/2];

        for( int i = 0; i < len; i+=2)
        {
            data[i/2] = (byte) (( Character.digit( s.charAt(i), 16) << 4) + Character.digit( s.charAt(i+1), 16));
        }

        return data;
    }

	final protected static char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	
	public static String byteArrayToHexString( byte[] bytes) 
	{
	    char[] hexChars = new char[bytes.length*2];
	    int v;
	
	    for( int j = 0; j < bytes.length; j++) 
	    {
	        v = bytes[j] & 0xFF;
	        hexChars[ j * 2 ] = hexArray[ v>>>4 ];
	        hexChars[ j * 2 + 1 ] = hexArray[ v & 0x0F ];
	    }
	
	    return new String(hexChars);
	}
	
	public static void copyBytesIntoByteArrayAtOffset( byte[] dest, byte[] src, int offset)
	{
		for( int i = 0; i < src.length; i++)
		{
			dest[offset + i] = src[i];
		}
	}
	
	public static void copyBytesIntoByteArray( byte[] dest, byte[] src)
	{
		copyBytesIntoByteArrayAtOffset( dest, src, 0);
	}
	
	public static void copyBytesIntoByteArrayUpToLength( byte[] dest, byte[] src, int offset)
	{
		for( int i = 0; i < offset; i++)
		{
			dest[i] = src[i];
		}
	}
	
	public static void clearByteArray( byte[] bytes)
	{
		for( int i = 0; i < bytes.length; i++)
		{
			bytes[i] = 0;
		}
	}
	
	public static byte[] bitwiseOrByteArrays( byte[] arr0, byte[] arr1)
	{
		byte[] returnArray = new byte[arr0.length];
		
		for( int i = 0; i < arr0.length; i++)
		{
			returnArray[i] = (byte)( arr0[i] | arr1[i]);
		}
		
		return returnArray;
	}
	
	public static byte[] bitwiseAndByteArrays( byte[] arr0, byte[] arr1)
	{
		byte[] returnArray = new byte[arr0.length];
		
		for( int i = 0; i < arr0.length; i++)
		{
			returnArray[i] = (byte)( arr0[i] & arr1[i]);
		}
		
		return returnArray;
	}
	
	public static byte[] inverseByteArrayBits( byte[] arr0)
	{
		byte[] returnArray = new byte[arr0.length];
		
		for( int i = 0; i < arr0.length; i++)
		{
			returnArray[i] = (byte) ~arr0[i];
		}
		
		return returnArray;
	}
	
	public static boolean isByteArrayEmpty( byte[] bytes)
	{
		for( int i = 0; i < bytes.length; i++)
		{
			if( bytes[i] != 0x00)
			{
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean isBitSet( byte[] arr, int bit) 
	{
	    int index = bit / 8;  			// Get the index of the array for the byte with this bit
	    int bitPosition = bit % 8;  	// Position of this bit in a byte

	    return (arr[index] >> bitPosition & 1) == 1;
	}
	
	public static void setBit( byte[] arr, int bit) 
	{
	    int index = bit / 8;  			// Get the index of the array for the byte with this bit
	    int bitPosition = bit % 8;  	// Position of this bit in a byte

	    arr[index] = (byte) (arr[index] | (1 << bitPosition));
	}
}
