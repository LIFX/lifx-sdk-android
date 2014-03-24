//
//  LFXSDKTaggedLightCollectionListAdapter.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package com.example.lifx_sdk_samples;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

import lifx.java.android.light.LFXLightCollection;
import lifx.java.android.light.LFXTaggedLightCollection;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LFXSDKTaggedLightCollectionListAdapter extends BaseAdapter
{
	private ArrayList<LFXLightCollection> lightCollections = new ArrayList<LFXLightCollection>();
	private SoftReference<Activity> activity;
	
	public LFXSDKTaggedLightCollectionListAdapter( Activity activity)
	{
		this.activity = new SoftReference<Activity>( activity);
	}
	
	public void updateWithLightCollections( ArrayList<LFXTaggedLightCollection> newLights)
	{
		lightCollections.clear();
		
		lightCollections.addAll( newLights);
		
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount()
	{
		return lightCollections.size();
	}

	@Override
	public Object getItem( int position)
	{
		return lightCollections.get( position);
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
		
		LFXLightCollection light = (LFXLightCollection) getItem( position);
		
		TextView labelView = (TextView) convertView.findViewById( R.id.light_label);
		labelView.setText( light.getLabel());
		
		return convertView;
	}

}
