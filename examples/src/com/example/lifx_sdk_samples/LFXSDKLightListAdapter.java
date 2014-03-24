//
//  LFXSDKLightListAdapter.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package com.example.lifx_sdk_samples;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import lifx.java.android.light.LFXLight;

public class LFXSDKLightListAdapter extends BaseAdapter
{
	private ArrayList<LFXLight> lights = new ArrayList<LFXLight>();
	private SoftReference<Activity> activity;
	
	public LFXSDKLightListAdapter( Activity activity)
	{
		this.activity = new SoftReference<Activity>( activity);
	}
	
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
			convertView = activity.get().getLayoutInflater().inflate( R.layout.lifx_list_item_layout, null);
		}
		
		LFXLight light = (LFXLight) getItem( position);
		
		TextView labelView = (TextView) convertView.findViewById( R.id.light_label);
		labelView.setText( light.getLabel());
		
		return convertView;
	}
}