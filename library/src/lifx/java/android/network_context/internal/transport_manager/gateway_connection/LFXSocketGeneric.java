//
//  LFXSocketGeneric.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.network_context.internal.transport_manager.gateway_connection;

import java.util.ArrayList;

import lifx.java.android.entities.internal.LFXMessage;
import lifx.java.android.util.LFXByteUtils;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public abstract class LFXSocketGeneric 
{
	protected static final int SOCKET_RECEIVED_MESSAGE = 4;
	protected static final int SOCKET_IS_CONNECTING = 5;
	protected static final int SOCKET_IS_CONNECTED = 6;
	protected static final int SOCKET_IS_DISCONNECTED = 7;
	
	public enum SocketState
	{
		CONNECTING,
		CONNECTED,
		DISCONNECTED
	}
	
	public enum ConnectionType
	{
		UDP,
		TCP
	}
	
	public abstract void connect( byte[] ipAddress, int port);
	public abstract void sendMessages( SocketMessage[] messages);
	public abstract ConnectionType getConnectionType();
	
	public static class SocketMessage
	{
		private byte[] message;
		private byte[] ipAddress;
		private int port;
		
		public SocketMessage( byte[] message)
		{
			this( message, null, 0);
		}
		
		public SocketMessage( byte[] message, byte[] ipAddress, int port)
		{
			int size = LFXMessage.getSizeFromMessageData( message);
			byte[] bytes = new byte[size];
			LFXByteUtils.copyBytesIntoByteArrayUpToLength( bytes, message, size);
			this.message = bytes;
			this.ipAddress = ipAddress;
			this.port = port;
		}

		public byte[] getMessageData() 
		{
			return message;
		}

		public byte[] getIpAddress() 
		{
			return ipAddress;
		}

		public int getPort() 
		{
			return port;
		}
		
		public SocketMessage cloneWithIpAddressAndPort( byte[] ipAddress, int port)
		{
			byte[] cloneMessage = new byte[message.length];
			for( int i = 0; i <  message.length; i++)
			{
				cloneMessage[i] = message[i];
			}
			
			byte[] cloneIpAddress = new byte[ipAddress.length];
			for( int i = 0; i <  ipAddress.length; i++)
			{
				cloneIpAddress[i] = ipAddress[i];
			}
			
			SocketMessage clone = new SocketMessage( cloneMessage, cloneIpAddress, port);
			
			return clone;
		}
	}
	
	public interface SocketStateListener
	{
		public void notifySocketStateChanged( LFXSocketGeneric socket, SocketState state);
	}
	
	public interface SocketMessageListener
	{
		public void notifyMessageReceived( SocketMessage message);
	}	
	
	private ArrayList<SocketStateListener> stateListeners;
	private ArrayList<SocketMessageListener> listeners;
    private boolean serverRunning = true;
	private byte[] ipAddress;
	private int port;
	protected Handler handler;
	protected SocketState state = SocketState.DISCONNECTED;
	protected Object closeLock = new Object();
	
	public LFXSocketGeneric() 
	{
		stateListeners = new ArrayList<SocketStateListener>();
		listeners = new ArrayList<LFXSocketGeneric.SocketMessageListener>();
		setHandler();
	}
	
//	public LFXSocketGeneric( byte[] ipAddress, int port) 
//	{
//		this();
//		this.ipAddress = ipAddress;
//		this.port = port;
//	}
	
	protected void bind( byte[] ipAddress, int port) 
	{
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	public Handler getMessageHandler()
	{
		return handler;
	}
	
	public SocketState getSocketState()
	{
		return state;
	}
	
	protected byte[] getIpAddress()
	{
		return ipAddress;
	}
	
	private void setHandler()
    {
    	handler = new Handler( Looper.getMainLooper())
    	{
    		@Override
    		public void handleMessage( Message message)
    		{
    			int type = message.what;
    			
    			if(	type == SOCKET_RECEIVED_MESSAGE)
        		{
            		notifyAllListenersMessageReceived( (SocketMessage) message.obj);
        		}
        		else if( type == SOCKET_IS_CONNECTING)
        		{
        			state = SocketState.CONNECTING;
        			notifyAllListenersSocketStateChanged( SocketState.CONNECTING);
        		}
        		else if( type == SOCKET_IS_CONNECTED)
        		{
        			state = SocketState.CONNECTED;
        			notifyAllListenersSocketStateChanged( SocketState.CONNECTED);
        		}
        		else if( type == SOCKET_IS_DISCONNECTED)
        		{
        			state = SocketState.DISCONNECTED;
        			notifyAllListenersSocketStateChanged( SocketState.DISCONNECTED);
        		}
    		}
    	};
    }
	
	protected int getPort()
	{
		return port;
	}
	
	public final void addStateListener( SocketStateListener listener)
	{
		if( !stateListeners.contains( listener))
		{
			stateListeners.add( listener);
		}
	}
	
	public final void removeConnectListener( SocketStateListener listener)
	{
		if( stateListeners.contains( listener))
		{
			stateListeners.remove( listener);
		}
	}
	
	protected void notifyAllListenersSocketStateChanged( SocketState state)
	{
		for( SocketStateListener listener : stateListeners)
		{
			listener.notifySocketStateChanged( this, state);
		}
	}
	
	public final void addMessageListener( SocketMessageListener listener)
	{
		if( !listeners.contains( listener))
		{
			listeners.add( listener);
		}
	}
	
	public final void removeMessageListener( SocketMessageListener listener)
	{
		if( listeners.contains( listener))
		{
			listeners.remove( listener);
		}
	}
	
	protected void notifyAllListenersMessageReceived( SocketMessage message)
	{
		for( SocketMessageListener listener : listeners)
		{
			listener.notifyMessageReceived( message);
		}
	}
	
	public final void sendMessage( SocketMessage message)
    {
    	SocketMessage[] messages = new SocketMessage[]{ message};
    	sendMessages( messages);
    }
	
	public void close() 
	{
		setServerRunning( false);
	}

	protected void setServerRunning( boolean serverRunning)
	{
		synchronized( closeLock)
		{
			this.serverRunning = serverRunning;
		}
	}
	
	protected boolean getServerRunning()
	{
		boolean value = false;
		
		synchronized( closeLock)
		{
			value = serverRunning;
		}
		
		return value;
	}

	public final static byte[] getBroadcastAddress( Context context)
	{
	    WifiManager wifi = (WifiManager) context.getSystemService( Context.WIFI_SERVICE);
	    DhcpInfo dhcp = wifi.getDhcpInfo();
	    // handle null somehow

	    int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
	    byte[] quads = new byte[4]; 
	    
	    for( int k = 0; k < 4; k++)
	    {
	    	quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
	    }
	    
	    return quads;
	}
}
