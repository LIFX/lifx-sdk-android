package com.example.lifx_sdk_samples;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import lifx.java.android.client.LFXClient;
import lifx.java.android.entities.LFXHSBKColor;
import lifx.java.android.entities.LFXTypes.LFXPowerState;
import lifx.java.android.light.LFXLight;
import lifx.java.android.light.LFXTaggedLightCollection;
import lifx.java.android.network_context.LFXNetworkContext;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class LightPowerActivity extends Activity
{
	private LFXNetworkContext networkContext;
	private LightListAdapter listAdapter;
	private Timer updateTimer;
	
	@Override
	protected void onCreate( Bundle savedInstanceState)
	{
		super.onCreate( savedInstanceState);
		setContentView( R.layout.activity_sample);
		
		networkContext = LFXClient.getSharedInstance( getApplicationContext()).getLocalNetworkContext();
		
		ListView listView = (ListView) findViewById( R.id.light_list_view);
		listAdapter = new LightListAdapter();
		listView.setAdapter( listAdapter);
		
		listView.setOnItemClickListener( new OnItemClickListener()
		{

			@Override
			public void onItemClick( AdapterView<?> arg0, View arg1, int position, long arg3)
			{
				LFXLight light = (LFXLight) listAdapter.getItem( position);
				
				if( light.getPowerState() == LFXPowerState.ON)
				{
					light.setPowerState( LFXPowerState.OFF);
				}
				else
				{
					light.setPowerState( LFXPowerState.ON);
				}

//			    LFXHSBKColor color = LFXHSBKColor.getColor( (float)(Math.random() * 360), 1.0f, 1.0f, 3500);
//			    light.setColor( color);
			}
			
		});
		
//		LFXNetworkContext localNetworkContext = LFXClient.getSharedInstance( this).getLocalNetworkContext();
//		localNetworkContext.getAllLightsCollection().setPowerState( LFXPowerState.OFF);
//		
//		//Turn on the light named "Hallway":
//		localNetworkContext = LFXClient.getSharedInstance( this).getLocalNetworkContext();
//		LFXLight hallway = localNetworkContext.getAllLightsCollection().getFirstLightForLabel( "Hallway");
//		hallway.setPowerState( LFXPowerState.ON);
//		
//		//Turn on all lights that are tagged "Kitchen":
//		localNetworkContext = LFXClient.getSharedInstance( this).getLocalNetworkContext();
//		LFXTaggedLightCollection kitchen = localNetworkContext.getTaggedLightCollectionForTag( "Kitchen");
//		kitchen.setPowerState( LFXPowerState.ON);
//		
//		//Set every light to a random color:
//		localNetworkContext = LFXClient.getSharedInstance( this).getLocalNetworkContext();
//		for( LFXLight aLight : localNetworkContext.getAllLightsCollection().getLights())
//		{
//		    LFXHSBKColor color = LFXHSBKColor.getColor( (float)(Math.random() * 360), 1.0f, 1.0f, 3500);
//		    aLight.setColor( color);
//		}
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		startUpdateTask();
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		stopUpdateTask();
		networkContext.disconnect();
	}
	
	private void startUpdateTask()
	{
		updateTimer = new Timer();
		updateTimer.scheduleAtFixedRate( getUpdateTask(), 0, 2000);
	}
	
	private void stopUpdateTask()
	{
		updateTimer.cancel();
	}
	
	private TimerTask getUpdateTask()
	{
		return new TimerTask()
		{
			@Override
			public void run()
			{
				runOnUiThread( new Runnable(){

					@Override
					public void run()
					{
						// TODO Auto-generated method stub
						updateStateFromLIFX();
					}
					
				});
			}
		};
	}
	
	private void updateStateFromLIFX()
	{
//		ArrayList<LFXLight> allLights = new ArrayList<LFXLight>();
//		
//		for( LFXTaggedLightCollection aCollection : networkContext.getTaggedLightCollections())
//		{
//			for( LFXLight aLight: aCollection.getLights())
//			{
//				if( !allLights.contains( aLight))
//				{
//					allLights.add( aLight);
//				}
//			}
//		}
				
		ArrayList<LFXLight> allLights = networkContext.getAllLightsCollection().getLights();
		
		listAdapter.updateWithLights( allLights);
	}
	
	@Override
	public boolean onCreateOptionsMenu( Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate( R.menu.sample, menu);
		return true;
	}

	private class LightListAdapter extends BaseAdapter
	{
		private ArrayList<LFXLight> lights = new ArrayList<LFXLight>();
		
		public void updateWithLights( ArrayList<LFXLight> newLights)
		{
			lights.clear();
			
			lights.addAll( newLights);
			
			notifyDataSetChanged();
		}
		
		@Override
		public int getCount()
		{
			return lights.size();
		}

		@Override
		public Object getItem( int position)
		{
			return lights.get( position);
		}

		@Override
		public long getItemId( int position)
		{
			return position;
		}

		@Override
		public View getView( int position, View convertView, ViewGroup listView)
		{
			if( convertView == null)
			{
				convertView = getLayoutInflater().inflate( R.layout.list_item_layout, null);
			}
			
			LFXLight light = (LFXLight) getItem( position);
			
			TextView labelView = (TextView) convertView.findViewById( R.id.light_label);
			labelView.setText( light.getLabel() + ": " + light.getPowerState());
			
			return convertView;
		}
	}
	
}
