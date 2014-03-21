package lifx.java.android.util;

public class LFXLog
{
	private static boolean error = true;
	private static boolean warning = true;
	private static boolean verbose = true;
	
	public static void Error( String input)
	{
		if( error)
		{
			System.out.println( "Error: " + input);
		}
	}
	
	public static void Warn( String input)
	{
		if( warning)
		{
			System.out.println( "Warning: " + input);
		}
	}
	
	public static void Verbose( String input)
	{
		if( verbose)
		{
			System.out.println( "Verbose: " + input);
		}
	}
	
	public static void Debug( String input)
	{
		if( verbose)
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
