package lifx.java.android.light;

import java.util.ArrayList;

import android.text.GetChars;

import lifx.java.android.entities.LFXHSBKColor;
import lifx.java.android.entities.LFXLightTarget;
import lifx.java.android.entities.LFXTypes.LFXFuzzyPowerState;
import lifx.java.android.entities.LFXTypes.LFXPowerState;
import lifx.java.android.entities.internal.LFXMessage;
import lifx.java.android.entities.internal.LFXTarget;
import lifx.java.android.entities.internal.LFXTarget.LFXTargetType;
import lifx.java.android.network_context.LFXNetworkContext;

public abstract class LFXLightCollection extends LFXLightTarget 
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
	
	protected ArrayList<LFXLight> lights; // LFXLight

	// Convenience method - will return the LFXLight in .lights that matches <deviceID> if it exists
	
	// Light State
	public String label;
	public LFXHSBKColor color;
	public LFXFuzzyPowerState fuzzyPowerState;

	// Light Control
	public abstract void setLabel( String label);
	public abstract void setColor( LFXHSBKColor color);
	public abstract void setColorOverDuration( LFXHSBKColor color, long overDuration);

	public LFXTarget getTarget()
	{
		return null;
	}

	public LFXTargetType getTargetType()
	{
		return LFXTargetType.TAG;
	}

//	- (NSString *)description
//	{
//		return [self lfx_descriptionWithPropertyKeys:@[SelfKey(label)]];
//	}

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

	public ArrayList<LFXLight> getLights()
	{
		return (ArrayList<LFXLight>) lights.clone();
	}

	// Light State
	public String getLabel()
	{
		//LFXLogImplementMethod();
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
	
//	// Light Control
//	public void setLabel( String label)
//	{
//		//LFXLogImplementMethod();
//	}
//
//	public void setColor( LFXHSBKColor color)
//	{
//		//LFXLogImplementMethod();
//	}

//	public void setColorOverDuration( LFXHSBKColor color, long duration)
//	{
//		// LFXLogImplementMethod();
//	}
//
//	public void setPowerState( LFXPowerState powerState)
//	{
//		// LFXLogImplementMethod();
//	}

	// TODO: CHeck out the initialisation, temp moved to LFXTaggedLightCollection
//	public static LFXLightCollection lightCollectionWithNetworkContext( LFXNetworkContext networkContext)
//	{
//		LFXLightCollection collection = new LFXLightCollection();
//		collection.networkContext = networkContext;
//		collection.lights = new ArrayList<LFXLight>();
//		return collection;
//	}

	public void handleMessage( LFXMessage message)
	{
		//LFXLogError( "Light: " + this.toString() + "received message: " + message.toString());
	}

	public void addLight( LFXLight light)
	{
		lights.add( light);
	}

	public void removeLight( LFXLight light)
	{
		lights.remove( light);
	}

	public void removeAllLights()
	{
		lights.clear();
	}
	
	public void addLightCollectionListener( LFXLightCollection lightCollection, LFXLightCollectionListener listener)
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
	
	public void removeLightCollectionListener( LFXLightCollection lightCollection, LFXLightCollectionListener listener)
	{
		listeners.remove( listener);
	}
}
