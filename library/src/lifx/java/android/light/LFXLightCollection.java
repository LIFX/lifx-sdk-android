//
//  LFXLightCollection.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.light;

import java.util.ArrayList;

import lifx.java.android.entities.LFXHSBKColor;
import lifx.java.android.entities.LFXLightTarget;
import lifx.java.android.entities.LFXTypes.LFXFuzzyPowerState;
import lifx.java.android.entities.LFXTypes.LFXPowerState;
import lifx.java.android.entities.internal.LFXMessage;
import lifx.java.android.entities.internal.LFXTarget;
import lifx.java.android.entities.internal.LFXTarget.LFXTargetType;
import lifx.java.android.light.LFXLight.LFXLightListener;
import lifx.java.android.network_context.LFXNetworkContext;

public abstract class LFXLightCollection extends LFXLightTarget implements LFXLightListener
{
	public interface LFXLightCollectionListener
	{
		public void lightCollectionDidAddLight( LFXLightCollection lightCollection, LFXLight light);
		public void lightCollectionDidRemoveLight( LFXLightCollection lightCollection,  LFXLight light);

		public void lightCollectionDidChangeLabel( LFXLightCollection lightCollection, String label);
		public void lightCollectionDidChangeColor( LFXLightCollection lightCollection, LFXHSBKColor color);
		public void lightCollectionDidChangeFuzzyPowerState( LFXLightCollection lightCollection, LFXFuzzyPowerState fuzzyPowerState);
	}
	
	protected LFXNetworkContext networkContext;

	private ArrayList<LFXLightCollectionListener> listeners = new ArrayList<LFXLightCollectionListener>();
	
	protected ArrayList<LFXLight> lights;
	
	// Light State
	public String label;
	public LFXHSBKColor color;
	public LFXFuzzyPowerState fuzzyPowerState;

	public LFXTarget getTarget()
	{
		return null;
	}

	public LFXTargetType getTargetType()
	{
		return LFXTargetType.TAG;
	}

	public LFXLight getLightWithDeviceID( String deviceID)
	{
		for( LFXLight aLight : lights)
		{
			if( aLight.getDeviceID().equals( deviceID)) 
			{
				return aLight;	
			}
		}
		return null;
	}

	@SuppressWarnings( "unchecked")
	public ArrayList<LFXLight> getLights()
	{
		return (ArrayList<LFXLight>) lights.clone();
	}

	// Light State
	public String getLabel()
	{
		return null;
	}

	public LFXHSBKColor getColor()
	{
		LFXHSBKColor[] lightColors = new LFXHSBKColor[lights.size()];
		
		for( int lightIndex = 0; lightIndex < lights.size(); lightIndex++)
		{
			lightColors[lightIndex] = lights.get( lightIndex).getColor();
		}
		
		return LFXHSBKColor.averageOfColors( lightColors);
	}

	public LFXFuzzyPowerState getFuzzyPowerState()
	{
		boolean isOn = false;
		boolean isOff = false;
		for( LFXLight aLight : lights)
		{
			if( aLight.getPowerState() == LFXPowerState.OFF) 
			{
				isOff = true;
			}
			
			if( aLight.getPowerState() == LFXPowerState.ON)
			{
				isOn = true;
			}
		}
		
		if( isOn && isOff) 
		{
			return LFXFuzzyPowerState.MIXED;
		}
		
		if( isOn) 
		{
			return LFXFuzzyPowerState.ON;
		}
		
		if( isOff) 
		{
			return LFXFuzzyPowerState.OFF;
		}
		
		return LFXFuzzyPowerState.OFF;
	}


	public LFXLight getFirstLightForLabel( String label)
	{
		for( LFXLight aLight : getLights())
		{
			if( aLight.getLabel() != null && aLight.getLabel().equals( label))
			{
				return aLight;
			}
		}
		
		return null;
	}

	public ArrayList<LFXLight> getLightsForLabel( String label)
	{
		ArrayList<LFXLight> lights = new ArrayList<LFXLight>();
		
		for( LFXLight aLight : getLights())
		{
			if( aLight.getLabel() != null && aLight.getLabel().equals( label))
			{
				lights.add( aLight);
			}
		}
		
		return lights;
	}
	
	// Light Control
	public void setLabel( String label)
	{
		for( LFXLightCollectionListener aListener : listeners)
		{
			aListener.lightCollectionDidChangeLabel( this, label);
		}
	}

	public void setColor( LFXHSBKColor color)
	{
	}

	public void setColorOverDuration( LFXHSBKColor color, long duration)
	{
		for( LFXLightCollectionListener aListener : listeners)
		{
			aListener.lightCollectionDidChangeColor( this, color);
		}
	}

	public abstract void handleMessage( LFXMessage message);

	public void addLight( LFXLight light)
	{
		lights.add( light);
		
		for( LFXLightCollectionListener aListener : listeners)
		{
			aListener.lightCollectionDidAddLight( this, light);
		}
	}

	public void removeLight( LFXLight light)
	{
		lights.remove( light);
		
		for( LFXLightCollectionListener aListener : listeners)
		{
			aListener.lightCollectionDidRemoveLight( this, light);
		}
	}

	public void removeAllLights()
	{
		for( LFXLight aLight : lights)
		{
			for( LFXLightCollectionListener aListener : listeners)
			{
				aListener.lightCollectionDidRemoveLight( this, aLight);
			}
		}
		
		lights.clear();
	}
	
	public void addLightCollectionListener( LFXLightCollectionListener listener)
	{
		if( !listeners.contains( listener))
		{
			listeners.add( listener);
		}
	} 
	
	public void removeAllLightCollectionListeners( LFXLightCollection lightCollection )
	{
		listeners.clear();
	}
	
	public void removeLightCollectionListener( LFXLightCollectionListener listener)
	{
		listeners.remove( listener);
	}
	
	@Override
	public void lightDidChangeLabel( LFXLight light, String label)
	{
		
	}

	@Override
	public void lightDidChangeColor( LFXLight light, LFXHSBKColor color)
	{
		
	}

	@Override
	public void lightDidChangePowerState( LFXLight light, LFXPowerState powerState)
	{
		
	}
}
