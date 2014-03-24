//
//  LFXLog.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.util;

public class LFXLog
{
	private static boolean info = true;
	private static boolean error = true;
	private static boolean warning = true;
	private static boolean verbose = false;
	private static boolean debug = false;
	
	public static void info( String input)
	{
		if( info)
		{
			System.out.println( "Error: " + input);
		}
	}
	
	public static void error( String input)
	{
		if( error)
		{
			System.out.println( "Error: " + input);
		}
	}
	
	public static void warn( String input)
	{
		if( warning)
		{
			System.out.println( "Warning: " + input);
		}
	}
	
	public static void verbose( String input)
	{
		if( verbose)
		{
			System.out.println( "Verbose: " + input);
		}
	}
	
	public static void debug( String input)
	{
		if( debug)
		{
			System.out.println( "Debug: " + input);
		}
	}
	
	public static void LFXMessage( byte[] data)
	{
		System.out.println( "Size: " + data.length);
		
		for( int i = 0; i < data.length; i++)
		{
			System.out.printf( "0x%02X ", data[i]);
		}
	}
}
