//
//  LFXLight.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.light;

import java.util.ArrayList;

import lifx.java.android.entities.LFXHSBKColor;
import lifx.java.android.entities.LFXLightTarget;
import lifx.java.android.entities.LFXTypes.LFXDeviceReachability;
import lifx.java.android.entities.LFXTypes.LFXFuzzyPowerState;
import lifx.java.android.entities.LFXTypes.LFXPowerState;
import lifx.java.android.entities.LFXTypes;
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

public class LFXLight extends LFXLightTarget
{
	public interface LFXLightListener
	{
		public void lightDidChangeLabel( LFXLight light, String label);
		public void lightDidChangeColor( LFXLight light, LFXHSBKColor color);
		public void lightDidChangePowerState( LFXLight light, LFXPowerState powerState);
	}
	
	private LFXNetworkContext networkContext;

	private String deviceID;

	private ArrayList<String> tags = new ArrayList<String>();													
	private ArrayList<LFXTaggedLightCollection> taggedCollections = new ArrayList<LFXTaggedLightCollection>();	

	//private LFXDeviceReachability reachability;

	private ArrayList<LFXLightListener> listeners = new ArrayList<LFXLightListener>();
	
	// Light State
	private LFXPowerState powerState;
	
	private long mostRecentMessageTimestamp;
	
	/**
	 * 
	 */
	public LFXTargetType getTargetType()
	{
		return LFXTargetType.DEVICE;
	}
	
	public LFXPowerState getPowerState()
	{
		if( powerState == null)
		{
			return LFXPowerState.OFF;
		}
		
		return powerState;
	}
	
	public ArrayList<LFXTaggedLightCollection> getTaggedCollections()
	{
		return taggedCollections;
	}
	
	public String getDeviceID()
	{
		return deviceID;
	}

	private long getMostRecentMessageTimestamp()
	{
		return mostRecentMessageTimestamp;
	}
	
	private void setMostRecentMessageTimestamp( long mostRecentMessageTimestamp)
	{
		this.mostRecentMessageTimestamp = mostRecentMessageTimestamp;
	}
	
	public LFXDeviceReachability getReachability()
	{
		if( (System.currentTimeMillis() - getMostRecentMessageTimestamp()) < 35000) 
		{
			return LFXDeviceReachability.REACHABLE;
		}
		
		return LFXDeviceReachability.UNREACHABLE;
	}

	public LFXFuzzyPowerState getFuzzyPowerState()
	{
		return LFXTypes.getLFXFuzzyPowerStateFromPowerState( getPowerState());
	}

	public void setLabel( String label)
	{
		LFXMessage setLabel = LFXMessage.messageWithTypeAndTarget( Type.LX_PROTOCOL_DEVICE_SET_LABEL, getTarget());
		LxProtocolDevice.SetLabel payload = new LxProtocolDevice.SetLabel( new Object(), label);
		setLabel.setPayload( payload);
		networkContext.sendMessage( setLabel);
	}
	
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

	private void notifyListenerLabelDidChange( String label)
	{
		for( LFXLightListener aListener : listeners)
		{
			aListener.lightDidChangeLabel( this, label);
		}
	}
	
	private void notifyListenersColorDidChange( LFXHSBKColor color)
	{
		for( LFXLightListener aListener : listeners)
		{
			aListener.lightDidChangeColor( this, color);
		}
	}
	
	private void notifyListenersPowerStateDidChange( LFXPowerState powerState)
	{
		for( LFXLightListener aListener : listeners)
		{
			aListener.lightDidChangePowerState( this, powerState);
		}
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

	public static LFXLight lightWithDeviceID( String deviceID, LFXNetworkContext networkContext)
	{
		LFXLight light = new LFXLight();
		light.deviceID = deviceID;
		light.setTarget( LFXTarget.getDeviceTargetWithDeviceID( deviceID));
		light.networkContext = networkContext;
		return light;
	}

	public void handleMessage( LFXMessage message)
	{
		if( message.isAResponseMessage())
		{
			setMostRecentMessageTimestamp( System.currentTimeMillis());
		}
		
		switch( message.getType())
		{
			case LX_PROTOCOL_LIGHT_SET:
			{
				LxProtocolLight.Set payload = (LxProtocolLight.Set) message.getPayload();
				colorDidChangeTo( LFXBinaryTypes.getLFXHSBKColorFromLXProtocolLightHsbk( payload.getColor()));
				break;
			}
			case LX_PROTOCOL_LIGHT_STATE:
			{
				LxProtocolLight.State payload = (LxProtocolLight.State) message.getPayload();
				labelDidChangeTo( payload.getLabel());
				colorDidChangeTo( LFXBinaryTypes.getLFXHSBKColorFromLXProtocolLightHsbk( payload.getColor()));
				powerDidChangeTo( LFXBinaryTypes.getLFXPowerStateFromLFXProtocolPowerLevel( payload.getPower()));
				break;
			}
			case LX_PROTOCOL_DEVICE_SET_LABEL:
			{
				LxProtocolDevice.SetLabel payload = (LxProtocolDevice.SetLabel) message.getPayload();
				labelDidChangeTo( payload.getLabel());
				break;
			}
			case LX_PROTOCOL_DEVICE_STATE_LABEL:
			{
				LxProtocolDevice.StateLabel payload = (LxProtocolDevice.StateLabel) message.getPayload();
				labelDidChangeTo( payload.getLabel());
				break;
			}
			case LX_PROTOCOL_DEVICE_SET_POWER:
			{
				LxProtocolDevice.SetPower payload = (LxProtocolDevice.SetPower) message.getPayload();
				powerDidChangeTo( LFXBinaryTypes.getLFXPowerStateFromLFXProtocolPowerLevel( payload.getLevel()));
				break;
			}
			case LX_PROTOCOL_DEVICE_STATE_POWER:
			{
				LxProtocolDevice.StatePower payload = (LxProtocolDevice.StatePower) message.getPayload();
				powerDidChangeTo( LFXBinaryTypes.getLFXPowerStateFromLFXProtocolPowerLevel( payload.getLevel()));
				break;
			}
			default:
				break;
		}
	}

	public void labelDidChangeTo( String label)
	{
		notifyListenerLabelDidChange( label);
		
		this.label = label;
	}
	
	public void colorDidChangeTo( LFXHSBKColor color)
	{
		notifyListenersColorDidChange( color);
		
		this.color = color;
	}
	
	public void powerDidChangeTo( LFXPowerState powerState)
	{
		notifyListenersPowerStateDidChange( powerState);
		
		this.powerState = powerState;
	}
	
	public ArrayList<String> getTags()
	{
		return tags;
	}
	
	public void setTags( ArrayList<String> tags)
	{
		this.tags = tags;
	}

	public void setTaggedCollections( ArrayList<LFXTaggedLightCollection> taggedCollections)
	{
		this.taggedCollections = taggedCollections;
	}
	
	public void addLightListener( LFXLightListener listener)
	{
		if( !listeners.contains( listener))
		{
			listeners.add( listener);
		}
	} 
	
	public void removeAllLightListeners()
	{
		listeners.clear();
	}
	
	public void removeLightListener( LFXLightListener listener)
	{
		listeners.remove( listener);
	}
}
