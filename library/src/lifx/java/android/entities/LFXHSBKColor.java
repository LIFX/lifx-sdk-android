//
//  LFXHSBKColor.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.entities;

public class LFXHSBKColor implements Cloneable
{
	private static final float MINIMUM_SATURATION = 0.0001f;
	
	private float hue;					// 0.0 - 360.0
	private float saturation;			// 0.0 - 1.0
	private float brightness;			// 0.0 - 1.0
	private int kelvin;					// 0 - 10,000
	
	private LFXHSBKColor()
	{
		this.hue = 0.0f;
		this.saturation = 0.0f;
		this.brightness = 1.0f;
		this.kelvin = 3500;
	}
	
	public float getHue()
	{
		return hue;
	}
	
	public float getSaturation()
	{
		return saturation;
	}
	
	public float getBrightness()
	{
		return brightness;
	}
	
	public int getKelvin()
	{
		return kelvin;
	}
	
	public static LFXHSBKColor getColor( float hue, float saturation, float brightness, int kelvin)
	{
		LFXHSBKColor color = new LFXHSBKColor();
		color.hue = hue;
		color.saturation = saturation;
		color.brightness = brightness;
		color.kelvin = kelvin;
		return color;
	}
	
	public boolean isWhite()		// Returns YES if saturation = 0.0;
	{
		if( saturation <= MINIMUM_SATURATION)
		{
			return true;
		}
		
		return false;
	}
	
	public String toString()		// "HSBK: (0.1, 0.4, 0.2, 5000)"
	{
		return "HSBK: (" + hue + ", " + saturation + ", " + brightness + ", " + kelvin + ")";
	}
	
	public static LFXHSBKColor averageOfColors( LFXHSBKColor[] colors)
	{
		if( colors.length == 0)
		{
			return null;
		}
		
		float hueXTotal = 0;
		float hueYTotal = 0;
		float saturationTotal = 0;
		float brightnessTotal = 0;
		long kelvinTotal = 0;
		
		for( LFXHSBKColor aColor : colors)
		{
			hueXTotal += Math.sin( aColor.hue * Math.PI / 180.0);
			hueYTotal += Math.cos( aColor.hue * Math.PI / 180.0);
			saturationTotal += aColor.saturation;
			brightnessTotal += aColor.brightness;
			
			if( aColor.kelvin == 0)
			{
				kelvinTotal += 3500;
			}
			else
			{
				kelvinTotal += aColor.kelvin;
			}
		}
		
		float M_1_PI = (float) (1.0f / Math.PI);
		
		float hue = (float) (Math.atan2( hueXTotal, hueYTotal) * 0.5 * M_1_PI);
		if (hue < 0.0) hue += 1.0;
		float saturation = saturationTotal / (float) colors.length;
		float brightness = brightnessTotal / (float) colors.length;
		int kelvin = (int) (kelvinTotal / colors.length);
		
		return LFXHSBKColor.getColor( hue, saturation, brightness, kelvin);
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException
	{
		LFXHSBKColor newColor = new LFXHSBKColor();
		newColor.hue = this.hue;
		newColor.saturation = this.saturation;
		newColor.brightness = this.brightness;
		newColor.kelvin = this.kelvin;
		return newColor;
	}

	public boolean equals( LFXHSBKColor aColor)
	{
		if( aColor == null)
		{
			return false;
		}
		
		if( aColor.hue != this.hue ||
			aColor.saturation != this.saturation ||
			aColor.brightness != this.brightness ||
			aColor.kelvin != this.kelvin) 
		{
			return false;
		}
		
		return true;
	}
}
