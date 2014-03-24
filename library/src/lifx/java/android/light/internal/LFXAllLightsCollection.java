//
//  LFXAllLightsCollection.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.light.internal;

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
import lifx.java.android.light.LFXLight;
import lifx.java.android.light.LFXLightCollection;
import lifx.java.android.network_context.LFXNetworkContext;

public class LFXAllLightsCollection extends LFXLightCollection
{
	public static LFXAllLightsCollection getLightCollectionWithNetworkContext( LFXNetworkContext networkContext)
	{
		LFXAllLightsCollection collection = new LFXAllLightsCollection();
		collection.networkContext = networkContext;
		collection.lights = new ArrayList<LFXLight>();
		return collection;
	}
	
	//private LFXTarget target;

	public LFXTargetType getTargetType()
	{
		return LFXTargetType.BROADCAST;
	}

	public String getLabel()
	{
		return "All Lights";
	}

	public LFXTarget getTarget()
	{
		return LFXTarget.getBroadcastTarget();
	}
	
	// Setters
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
	}

	public void setPowerState( LFXPowerState powerState)
	{
		LFXMessage setPower = LFXMessage.messageWithTypeAndTarget( Type.LX_PROTOCOL_DEVICE_SET_POWER, getTarget());
		Object padding = new Object();
		UInt16 protocolPowerLevel = LFXBinaryTypes.getLFXProtocolPowerLevelFromLFXPowerState( powerState);
		LxProtocolDevice.SetPower payload = new LxProtocolDevice.SetPower( padding, protocolPowerLevel);
		setPower.setPayload( payload);
		networkContext.sendMessage( setPower);
	}

	@Override
	public void setLabel( String label)
	{
		// DO NOTHING
	}

	@Override
	public void handleMessage( LFXMessage message)
	{
	}
}
