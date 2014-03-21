package lifx.java.android.light;

import java.util.ArrayList;

import lifx.java.android.entities.LFXHSBKColor;
import lifx.java.android.entities.LFXTypes.LFXPowerState;
import lifx.java.android.entities.internal.LFXBinaryTypes;
import lifx.java.android.entities.internal.LFXMessage;
import lifx.java.android.entities.internal.LFXTarget;
import lifx.java.android.entities.internal.LFXTarget.LFXTargetType;
import lifx.java.android.entities.internal.structle.LxProtocolDevice;
import lifx.java.android.entities.internal.structle.LxProtocolLight;
import lifx.java.android.entities.internal.structle.LxProtocol.Type;
import lifx.java.android.entities.internal.structle.LxProtocolLight.Hsbk;
import lifx.java.android.entities.internal.structle.StructleTypes.UInt16;
import lifx.java.android.entities.internal.structle.StructleTypes.UInt32;
import lifx.java.android.entities.internal.structle.StructleTypes.UInt8;
import lifx.java.android.network_context.LFXNetworkContext;

public class LFXTaggedLightCollection extends LFXLightCollection
{
	public static LFXTaggedLightCollection lightCollectionWithNetworkContext( LFXNetworkContext networkContext)
	{
		LFXTaggedLightCollection collection = new LFXTaggedLightCollection();
		collection.networkContext = networkContext;
		collection.lights = new ArrayList<LFXLight>();
		return collection;
	}
	
	private String tag;

	// TODO, these don't currently work
//	- (void)addLight:(LFXLight *)light;
//	- (void)removeLight:(LFXLight *)light;
//	- (void)removeAllLights;
	
	private LFXTarget target;

	public String getTag()
	{
		return tag;
	}
	
	public LFXTargetType getTargetType()
	{
		return LFXTargetType.TAG;
	}

	public LFXTarget getTarget()
	{
		return LFXTarget.getTagTargetWithTag( tag);
	}

	public void addLight( LFXLight light)
	{
		//LFXLogError(@"Implement me");
	}

	public void removeLight( LFXLight light)
	{
		//LFXLogError(@"Implement me");
	}

	public void removeAllLights()
	{
		for( LFXLight aLight : getLights())
		{
			removeLight( aLight);
		}
	}

	// Getters

	public String getLabel()
	{
		return tag;
	}

	// Setters

	@Override
	public void setColor( LFXHSBKColor color)
	{
		setColorOverDuration( color, 250);
	}

	public void setColorOverDuration( LFXHSBKColor color, long duration)
	{
		LFXMessage lightSet = LFXMessage.messageWithTypeAndTarget( Type.LX_PROTOCOL_LIGHT_SET, getTarget());
		Object padding = new Object();
		UInt8 stream = new UInt8( 0);
		Hsbk protocolColor = LFXBinaryTypes.getLXProtocolLightHsbkFromLFXHSBKColor( color);
		UInt32 protocolDuration = new UInt32( duration);
		LxProtocolLight.Set payload = new LxProtocolLight.Set( padding, stream, protocolColor, protocolDuration);
		lightSet.setPayload( payload);
		networkContext.sendMessage( lightSet);
		
//		LFXMessageLightSet *lightSet = [LFXMessageLightSet messageWithTarget:self.target];
//		lightSet.payload.color = LXProtocolLightHsbkFromLFXHSBKColor(color);
//		lightSet.payload.duration = LFXProtocolDurationFromNSTimeInterval(duration);
//		[self.networkContext sendMessage:lightSet];
	}

	@Override
	public void setPowerState( LFXPowerState powerState)
	{
		LFXMessage setPower = LFXMessage.messageWithTypeAndTarget( Type.LX_PROTOCOL_DEVICE_SET_POWER, getTarget());
		Object padding = new Object();
		UInt16 protocolPowerLevel = LFXBinaryTypes.getLFXProtocolPowerLevelFromLFXPowerState( powerState);
		LxProtocolDevice.SetPower payload = new LxProtocolDevice.SetPower( padding, protocolPowerLevel);
		setPower.setPayload( payload);
		networkContext.sendMessage( setPower);
		
//		LFXMessageDeviceSetPower *setPower = [LFXMessageDeviceSetPower messageWithTarget:self.target];
//		setPower.payload.level = LFXProtocolPowerLevelFromLFXPowerState(powerState);
//		[self.networkContext sendMessage:setPower];
	}

	@Override
	public void setLabel( String label)
	{
		// TODO Auto-generated method stub
		
	}
	
	public void setTag( String tag)
	{
		this.tag = tag;
	}
}
