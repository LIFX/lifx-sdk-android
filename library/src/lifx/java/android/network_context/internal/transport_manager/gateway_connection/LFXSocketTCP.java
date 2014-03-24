//
//  LFXSocketTCP.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.network_context.internal.transport_manager.gateway_connection;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import android.os.Handler;
import android.os.Message;

public class LFXSocketTCP extends LFXSocketGeneric
{	
	private Thread asyncSocket;
	private Thread asyncSocketOutTask;
    
	private InputStream in;
	private OutputStream out;
	
	private Socket socket = null;
	
	public static final int RECEIVED_MESSAGE = 4;
	public static final int SOCKET_CONNECTION_FAILED = 5;
	
//	public LFXSocketTCP( byte[] ipAddress, int port)
//	{
//		super( ipAddress, port);
//	}
	
	public LFXSocketTCP()
	{
		super();
	}
	
	private class TCPReceiveTask implements Runnable
	{
		private Handler handler;
		
		public TCPReceiveTask( Handler handler)
    	{
    		super();
    		this.handler = handler;
    	}
		
		public void publishProgress( SocketMessage[] messages)
    	{
    		Message msg = handler.obtainMessage();
    	    msg.what = SOCKET_RECEIVED_MESSAGE;
    	    msg.obj = messages[0];
    	    handler.sendMessage( msg);
    	}
    	
    	public void publishConnected()
    	{
    		Message msg = handler.obtainMessage();
    		msg.obj = SOCKET_IS_CONNECTED;
    	    handler.sendMessage( msg);
    	}
    	
    	public void publishConnecting()
    	{
    		Message msg = handler.obtainMessage();
    		msg.obj = SOCKET_IS_CONNECTING;
    	    handler.sendMessage( msg);
    	}
    	
    	public void publishDisconnected()
    	{
    		Message msg = handler.obtainMessage();
    		msg.obj = SOCKET_IS_DISCONNECTED;
    	    handler.sendMessage( msg);
    	}
		
		@Override
		public void run() 
		{
			socket = null;
	            
            try 
            {
            	boolean serverStarted = false;
            	
            	publishConnecting();
            	
        		try
        		{
            		InetAddress serverAddr = InetAddress.getByAddress( getIpAddress());
    	            socket = new Socket( serverAddr, getPort());
    	            socket.setReuseAddress( true);
    	            
                	out = socket.getOutputStream();
                    in = socket.getInputStream();
                    
                    if( out == null || in == null)
                    {
                    	socket.close();
                    }
                    else
                    {
                    	serverStarted = true;
                    }
        		}
        		catch( Exception e)
        		{
        			System.out.println( "TCP Connection Failed.");
        		}
 
            	if( serverStarted)
            	{
            		setServerRunning( true);
            		publishConnected();
            	}
            	else
            	{
            		setServerRunning( false);
            	}
            	
                byte[] buffer = new byte[200];
                
                while( getServerRunning()) 
                {
                    int length = in.read( buffer);
 
                    if( length > 0) 
                    {
                    	byte[] messageData = new byte[length];
                    	for( int i = 0; i < length; i++)
                    	{
                    		messageData[i] = buffer[i];
                    	}
                    	
                    	byte[] ipAddress = socket.getInetAddress().getAddress();
                        SocketMessage message = new SocketMessage( messageData, ipAddress, length);
                        
                        SocketMessage[] messages = new SocketMessage[]{ message};
                        publishProgress( (SocketMessage[]) messages);
                    }
                    else if( length < 0)
                    {
                    	System.out.println( "TCP Socket has been closed.");
                    	close();
                    }
                }
            } 
            catch( Exception e) 
            {
            	e.printStackTrace();
            } 
            
            close();
            
            publishDisconnected();
            
            System.out.println( "TCP Socket Monitor Ended Execution.");
		}
	}
	
	@Override
	public void connect( byte[] ipAddress, int port) 
	{
		bind( ipAddress, port);
		
		state = SocketState.CONNECTING;
		
		asyncSocket = new Thread( new TCPReceiveTask( handler));
		asyncSocket.start();
	}

	private void closeInputStream()
	{
		if( in != null)
		{
			try
			{
				in.close();
			}
			catch( Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	private void closeOutputStream()
	{
		if( out != null)
		{
			try
			{
				out.close();
			}
			catch( Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	private void closeSocket()
	{
		try
		{
			if( socket != null)
			{
				socket.close();
			}
		} 
		catch( IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void close() 
	{
		super.close();
		closeInputStream();
		closeOutputStream();
		closeSocket();
	}
	
	public class SendMessageTask implements Runnable
	{
		private SocketMessage[] messages;
    	
    	public SendMessageTask( SocketMessage... messages)
    	{
    		this.messages = messages;
    	}
		
		@Override
		public void run() 
		{
			for( SocketMessage message : messages)
        	{
                try 
                {
                	byte[] messageData = message.getMessageData();
                	
            		if( out != null) 
            		{
            			DataOutputStream dos = new DataOutputStream( out);

            		    if( messageData.length > 0) 
            		    {
            		        dos.write( messageData, 0, messageData.length);
            		    }
          
                        dos.flush();   
                    }
            		
            		//System.out.println( "Message Sent Via TCP: " + MessageUnPacker.getMessageType( messageData).toString());
                } 
                catch( Exception e) 
                {
                    e.printStackTrace();
                    close();
                }
        	}
		}
	}
	
	public void sendMessages( SocketMessage[] messages) 
	{
		asyncSocketOutTask = new Thread( new SendMessageTask( messages));
		asyncSocketOutTask.start();
    }

	@Override
	public ConnectionType getConnectionType() 
	{
		return ConnectionType.TCP;
	}
}
