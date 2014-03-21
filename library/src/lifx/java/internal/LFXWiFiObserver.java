package lifx.java.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

import android.content.Context;

import lifx.java.android.util.LFXNetworkUtils;
import lifx.java.android.util.LFXTimerUtils;

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
	
	private Runnable wifiPollTimerTask = new Runnable() 
	{
	    public void run() 
	    {
	    	wifiPollingTimerDidFire();
	    }
	};
	
	private LFXWiFiObserver()
	{
		observationDescriptors = new ArrayList<LFXWiFiObservationDescriptor>();
		wifiStatePollingTimer = LFXTimerUtils.getTimerTaskWithPeriod( wifiPollTimerTask, 1000); 
		//wifiStatePollingTimer = [NSTimer scheduledTimerWithTimeInterval:1.0 target:self selector:@selector(wifiPollingTimerDidFire:) userInfo:nil repeats:YES];
	}

	private Timer wifiStatePollingTimer;
	private ArrayList<LFXWiFiObservationDescriptor> observationDescriptors;

	private HashMap cachedNetworkInfo;

	private void wifiPollingTimerDidFire()
	{
//		NSDictionary *freshNetworkInfo = [self freshNetworkInfo];
//		if ((!self.cachedNetworkInfo && freshNetworkInfo) ||
//			(self.cachedNetworkInfo && ![self.cachedNetworkInfo isEqual:freshNetworkInfo]))
//		{
//			self.cachedNetworkInfo = freshNetworkInfo;
//			[self sendObservationCallbacks];
//		}
	}

	private HashMap getFreshNetworkInfo()
	{
//		NSArray *interfaceNames = (__bridge NSArray *)CNCopySupportedInterfaces();
//		if (interfaceNames.firstObject)
//		{
//			NSDictionary *infoDictionary = (__bridge NSDictionary *)CNCopyCurrentNetworkInfo((__bridge CFStringRef)interfaceNames.firstObject);
//			return infoDictionary;
//		}
//		return nil;
		
		return null;
	}

	private String getCurrentSSID()
	{
		//return self.freshNetworkInfo[(NSString *)kCNNetworkInfoKeySSID];
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

	private void sendObservationCallbacks()
	{
		for( LFXWiFiObservationDescriptor anObservationDescriptor : observationDescriptors)
		{
			anObservationDescriptor.getCallback().run( anObservationDescriptor.getObservingObject(), this);
		}
	}
}
