//
//  LFXBinaryTypes.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.entities.internal;

import lifx.java.android.entities.LFXHSBKColor;
import lifx.java.android.entities.LFXTypes.LFXPowerState;
import lifx.java.android.entities.internal.structle.LxProtocolLight;
import lifx.java.android.entities.internal.structle.StructleTypes.UInt16;

public class LFXBinaryTypes
{
	public static UInt16 getLFXProtocolPowerLevelFromLFXPowerState( LFXPowerState powerState)
	{
		switch( powerState)
		{
			case OFF:	
				return new UInt16( 0);
			case ON:	
				return new UInt16( 1);
		}
		
		return new UInt16( 1);
	}

	public static LFXPowerState getLFXPowerStateFromLFXProtocolPowerLevel( UInt16 powerLevel)
	{
		if( powerLevel.getValue() == 0)
		{
			return LFXPowerState.OFF;
		}
		else
		{
			return LFXPowerState.ON;
		}
	}

	public static LFXHSBKColor getLFXHSBKColorFromLXProtocolLightHsbk( LxProtocolLight.Hsbk protocolHsbk)
	{
		float hue = (float) protocolHsbk.getHue().getValue() * 360.0f / (float) UInt16.MAX_U16_VALUE;
		float saturation = (float) protocolHsbk.getSaturation().getValue() / (float) UInt16.MAX_U16_VALUE;
		float brightness = (float) protocolHsbk.getBrightness().getValue() / (float) UInt16.MAX_U16_VALUE;
		int kelvin = protocolHsbk.getKelvin().getValue();
		
		LFXHSBKColor color = LFXHSBKColor.getColor( hue, saturation, brightness, kelvin);
		
		return color;
	}

	public static LxProtocolLight.Hsbk getLXProtocolLightHsbkFromLFXHSBKColor( LFXHSBKColor color)
	{
		int hue = (int)( color.getHue() / 360.0 * (float) UInt16.MAX_U16_VALUE);
		int saturation = (int)( color.getSaturation() * (float) UInt16.MAX_U16_VALUE);
		int brightness = (int)( color.getBrightness() * (float) UInt16.MAX_U16_VALUE);
		int kelvin = color.getKelvin();
		
		UInt16 wrappedHue = new UInt16( hue);
		UInt16 wrappedSaturation = new UInt16( saturation);
		UInt16 wrappedBrightness = new UInt16( brightness);
		UInt16 wrappedKelvin = new UInt16( kelvin);
		
		Object padding = new Object();
		
		LxProtocolLight.Hsbk lightHSBK = new LxProtocolLight.Hsbk( padding, wrappedHue, wrappedSaturation, wrappedBrightness, wrappedKelvin);
		
		return lightHSBK;
	}
}
