package lifx.java.android.network_context.internal.transport_manager.lan.gateway_discovery;

import lifx.java.android.entities.internal.LFXGatewayDescriptor;

public class LFXGatewayDiscoveryTableEntry
{
	private LFXGatewayDescriptor gatewayDescriptor;
	private long lastDiscoveryResponseDate;	// this is the date of the last DeviceStatePanGateway message corresponding to this gateway
	
	public LFXGatewayDescriptor getGatewayDescriptor()
	{
		return gatewayDescriptor;
	}
	
	public void setGatewayDescriptor( LFXGatewayDescriptor gatewayDescriptor)
	{
		this.gatewayDescriptor = gatewayDescriptor;
	}
	
	public long getLastDiscoveryResponseDate()
	{
		return lastDiscoveryResponseDate;
	}
	
	public void setLastDiscoveryResponseDate( long lastDiscoveryResponseDate)
	{
		this.lastDiscoveryResponseDate = lastDiscoveryResponseDate;
	}
}
