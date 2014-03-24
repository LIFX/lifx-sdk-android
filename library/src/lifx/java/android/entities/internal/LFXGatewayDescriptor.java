//
//  LFXGatewayDescriptor.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.entities.internal;

import lifx.java.android.entities.internal.structle.LxProtocolDevice;
import lifx.java.android.entities.internal.structle.LxProtocolDevice.Service;
import lifx.java.android.internal.LFXWiFiObserver;

public class LFXGatewayDescriptor
{
	public static LFXGatewayDescriptor getGatewayDescriptorWithHostPortPathService( String host, int port, LFXBinaryPath path, LxProtocolDevice.Service service)
	{
		LFXGatewayDescriptor gatewayDescriptor = new LFXGatewayDescriptor();
		gatewayDescriptor.host = host;
		gatewayDescriptor.port = port;
		gatewayDescriptor.path = path;
		gatewayDescriptor.service = service;
		return gatewayDescriptor;
	}

	public static LFXGatewayDescriptor getBroadcastGatewayDescriptor()
	{
		return getGatewayDescriptorWithHostPortPathService( LFXWiFiObserver.getWiFiBroadcastAddress(), 56700, null, Service.LX_PROTOCOL_DEVICE_SERVICE_UDP);
	}

	public static LFXGatewayDescriptor getClientPeerToPeerGatewayDescriptor()
	{
		return getGatewayDescriptorWithHostPortPathService( LFXWiFiObserver.getWiFiBroadcastAddress(), 56750, null, Service.LX_PROTOCOL_DEVICE_SERVICE_UDP);
	}
	
	public static LFXGatewayDescriptor getSoftAPGatewayDescriptor()
	{
		return getGatewayDescriptorWithHostPortPathService( "172.16.0.1", 56700, null, Service.LX_PROTOCOL_DEVICE_SERVICE_TCP);
	}

	public boolean isBroadcastGateway()
	{
		return this.equals( getBroadcastGatewayDescriptor());
	}
	
	public boolean isClientPeerToPeerGateway()
	{
		return this.equals( getClientPeerToPeerGatewayDescriptor());
	}
	
	public boolean isSoftAPGateway()
	{
		return this.equals( getSoftAPGatewayDescriptor());
	}

	private String host;
	private int port;
	private LFXBinaryPath path;
	private LxProtocolDevice.Service service;

	public String getHost()
	{
		return host;
	}
	
	public int getPort()
	{
		return port;
	}
	
	public LFXBinaryPath getPath()
	{
		return path;
	}
	
	public LxProtocolDevice.Service getService()
	{
		return service;
	}

	public String getProtocolString()
	{
		return service.toString();
	}

	public String toString()
	{
		if( path != null)
		{
			return "LFXGatewayDescriptor - Host: " + host + ", Port: " + port + ", Path: " + path.toString() + ", Protocol: " + getProtocolString();
		}
		else
		{
			return "LFXGatewayDescriptor - Host: " + host + ", Port: " + port + ", Path: " + path + ", Protocol: " + getProtocolString();
		}
	}

	public boolean equals( LFXGatewayDescriptor aGatewayDescriptor)
	{
		if( aGatewayDescriptor == null)
		{
			return false;
		}
		
		if( !aGatewayDescriptor.getHost().equals( this.host)) 
		{
			return false;
		}
		
		if( aGatewayDescriptor.getPort() != this.port) 
		{
			return false;
		}
		
		if( aGatewayDescriptor.getService() != this.service)
		{
			return false;
		}
		
		return true;
	}

	public Object clone()
	{
		LFXGatewayDescriptor newGatewayDescriptor = new LFXGatewayDescriptor();
		newGatewayDescriptor.host = this.host;
		newGatewayDescriptor.port = this.port;
		newGatewayDescriptor.path = this.path;
		newGatewayDescriptor.service = this.service;
		return newGatewayDescriptor;
	}
}
