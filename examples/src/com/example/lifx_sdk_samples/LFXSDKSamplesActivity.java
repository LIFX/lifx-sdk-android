//
//  LFXSDKSamplesActivity.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package com.example.lifx_sdk_samples;

import lifx.java.android.client.LFXClient;
import lifx.java.android.network_context.LFXNetworkContext;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;
import android.os.Bundle;
import android.view.View;

public class LFXSDKSamplesActivity extends Activity
{
	private boolean shouldStopLifxOnPause;
	private LFXNetworkContext networkContext;
	private MulticastLock ml = null;
	
	@Override
	protected void onCreate( Bundle savedInstanceState)
	{
		super.onCreate( savedInstanceState);
		
		setContentView( R.layout.lifx_main_activity_layout);
		
		// A Multicast lock should be acquired, as some phones disable UDP broadcast / recieve
		WifiManager wifi;
      	wifi = (WifiManager) getSystemService( Context.WIFI_SERVICE);
      	ml = wifi.createMulticastLock( "lifx_samples_tag");
      	ml.acquire();
		
		networkContext = LFXClient.getSharedInstance( getApplicationContext()).getLocalNetworkContext();
		networkContext.connect();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		shouldStopLifxOnPause = true;
	}
	
	public void pressedLabelChange( View v)
	{
		Intent intent = new Intent( getApplicationContext(), LFXSDKLightEditLabelActivity.class);
		shouldStopLifxOnPause = false;
		startActivity( intent);
	}
	
	public void pressedRandomColor( View v)
	{
		Intent intent = new Intent( getApplicationContext(), LFXSDKLightRandomColorActivity.class);
		shouldStopLifxOnPause = false;
		startActivity( intent);
	}
	
	public void pressedPowerChange( View v)
	{
		Intent intent = new Intent( getApplicationContext(), LFXSDKLightPowerActivity.class);
		shouldStopLifxOnPause = false;
		startActivity( intent);
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		
		if( shouldStopLifxOnPause)
		{
			System.out.println( "Stop LIFX");
			networkContext.disconnect();
			
			if( ml != null)
			{
				ml.release();
			}
		}
		else
		{
			System.out.println( "Don't Stop LIFX");
		}
	}
}
