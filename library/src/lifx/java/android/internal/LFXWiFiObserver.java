//
//  LFXWiFiObserver.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;

import lifx.java.android.util.LFXNetworkUtils;

public class LFXWiFiObserver
{
	public interface LFXWiFiObserverCallback
	{
		public void run( Object context, LFXWiFiObserver message);
	}
	
	public class LFXWiFiObservationDescriptor
	{
		private Object observingObject;
		private LFXWiFiObserverCallback callback;
		
		public void setObservingObject( Object observingObject)
		{
			this.observingObject = observingObject;
		}
		
		public Object getObservingObject()
		{
			return observingObject;
		}
		
		public void setCallback( LFXWiFiObserverCallback callback)
		{
			this.callback = callback;
		}
		
		public LFXWiFiObserverCallback getCallback()
		{
			return callback;
		}
	}
	
	private static LFXWiFiObserver sharedInstance;
	private String currentSSID;
	private Context context;

	public static LFXWiFiObserver getSharedInstance( Context context)
	{
		if( sharedInstance == null)
		{
			sharedInstance = new LFXWiFiObserver();
			sharedInstance.context = context;
		}
		
		return sharedInstance;
	}
	
	public static String getWiFiBroadcastAddress()
	{
		if( sharedInstance != null && sharedInstance.context != null)
		{
			return LFXNetworkUtils.getBroadcastAddress( sharedInstance.context);
		}
		else
		{
			return "255.255.255.255";
		}
	}
	
	@SuppressWarnings( "unused")
	private Runnable getWifiPollTimerTask()
	{
		Runnable wifiPollTimerTask = new TimerTask() 
		{
		    public void run() 
		    {
		    	wifiPollingTimerDidFire();
		    }
		};
		
		return wifiPollTimerTask;
	}
		
	private LFXWiFiObserver()
	{
		observationDescriptors = new ArrayList<LFXWiFiObservationDescriptor>();
		//wifiStatePollingTimer = LFXTimerUtils.getTimerTaskWithPeriod( getWifiPollTimerTask(), 1000, false); 
	}

	@SuppressWarnings( "unused")
	private Timer wifiStatePollingTimer;
	private ArrayList<LFXWiFiObservationDescriptor> observationDescriptors;

	@SuppressWarnings( { "unused", "rawtypes" })
	private HashMap cachedNetworkInfo;

	private void wifiPollingTimerDidFire()
	{
	}

	@SuppressWarnings( { "rawtypes", "unused" })
	private HashMap getFreshNetworkInfo()
	{
		return null;
	}

	@SuppressWarnings( "unused")
	private String getCurrentSSID()
	{
		return null;
	}

	public boolean isConnectedToLIFXSoftAP()
	{
		return currentSSID.equals("LIFX Bulb");
	}

	public void addObserverObjectWithCallback( Object object, LFXWiFiObserverCallback callback)
	{
		LFXWiFiObservationDescriptor observationDescriptor = new LFXWiFiObservationDescriptor();
		observationDescriptor.setObservingObject( object);
		observationDescriptor.setCallback( callback);
		observationDescriptors.add( observationDescriptor);
	}

	@SuppressWarnings( "unchecked")
	public void removeObserverObject( Object object)
	{
		for( LFXWiFiObservationDescriptor anObservationDescriptor : (ArrayList<LFXWiFiObservationDescriptor>) observationDescriptors.clone())
		{
			if( anObservationDescriptor.getObservingObject() == object)
			{
				observationDescriptors.remove( anObservationDescriptor);
			}
		}
	}

	@SuppressWarnings( "unused")
	private void sendObservationCallbacks()
	{
		for( LFXWiFiObservationDescriptor anObservationDescriptor : observationDescriptors)
		{
			anObservationDescriptor.getCallback().run( anObservationDescriptor.getObservingObject(), this);
		}
	}
}
