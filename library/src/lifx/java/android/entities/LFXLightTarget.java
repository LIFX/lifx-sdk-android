//
//  LFXLightTarget.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.entities;

import java.util.ArrayList;

import lifx.java.android.entities.LFXTypes.LFXFuzzyPowerState;
import lifx.java.android.entities.LFXTypes.LFXPowerState;
import lifx.java.android.entities.internal.LFXTarget;
import lifx.java.android.entities.internal.LFXTarget.LFXTargetType;
import lifx.java.android.light.LFXLight;

public abstract class LFXLightTarget
{
	private LFXTarget target;
	private LFXTargetType targetType;
	private ArrayList<LFXLight> lights;
	protected String label;
	protected LFXHSBKColor color;
	protected LFXFuzzyPowerState fuzzyPowerState;
	
	public LFXTarget getTarget()
	{
		return target;
	}

	public void setTarget( LFXTarget target)
	{
		this.target = target;
	}
	
	public LFXTargetType getTargetType()
	{
		return targetType;
	}

	public ArrayList<LFXLight> getLights()
	{
		return lights;
	};
	
	// Light State
	public String getLabel()
	{
		if( label == null)
		{
			return "Unknown";
		}
		
		return label;
	}
	
	public LFXHSBKColor getColor()
	{		
		return color;
	}
	
	public LFXFuzzyPowerState getFuzzyPowerState()
	{
		return fuzzyPowerState;
	}

	// Light Control
	public abstract void setLabel( String label);
	public abstract void setColor( LFXHSBKColor color);
	public abstract void setColorOverDuration( LFXHSBKColor color, long duration);
	public abstract void setPowerState( LFXPowerState powerState);
}
