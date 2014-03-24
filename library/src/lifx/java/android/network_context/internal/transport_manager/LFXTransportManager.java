//
//  LFXTransportManager.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.network_context.internal.transport_manager;

import java.util.ArrayList;
import lifx.java.android.entities.internal.LFXGatewayDescriptor;
import lifx.java.android.entities.internal.LFXMessage;
import lifx.java.android.entities.internal.LFXMessageObservationDescriptor;
import lifx.java.android.entities.internal.LFXSiteID;
import lifx.java.android.entities.internal.LFXMessageObservationDescriptor.LFXMessageObserverCallback;
import lifx.java.android.network_context.LFXNetworkContext;

public abstract class LFXTransportManager
{	
	public interface LFXTransportManagerListener
	{
		public void transportManagerDidConnect( LFXTransportManager transportManager);
		public void transportManagerDidDisconnect( LFXTransportManager transportManager);

		public void transportManagerDidConnectToGateway( LFXTransportManager transportManager, LFXGatewayDescriptor gatewayDescriptor);
		public void transportManagerDidDisconnectFromGateway( LFXTransportManager transportManager, LFXGatewayDescriptor gatewayDescriptor);
	}

	private LFXNetworkContext networkContext;
	protected LFXTransportManagerListener listener;

	// May have no meaning for some Network Contexts
	public abstract void connect();
	
	public abstract void disconnect();

	private boolean isConnected;

	// After sending, -sendObserverCallbacksForMessage: should be called with the message.
	public abstract void sendMessage( LFXMessage message);

	public void setNetworkContext( LFXNetworkContext networkContext)
	{
		this.networkContext = networkContext;
	}
	
	public void setListener( LFXTransportManagerListener listener)
	{
		this.listener = listener;
	}
	
	// Token Based subscriptions
//	- (id)addMessageObserverWithCallback:(LFXMessageObserverCallback)callback;	// returns an observer "token"
//	- (void)removeMessageObserverToken:(id)observerToken;	// This will remove the single observation created with the above method
//	public Object addMessageObserverWithCallback( LFXMessageObserverCallback callback);
//	public void removeMessageObserverToken( Object observerToken);
	
	// Observer Based subscriptions
//	- (void)addMessageObserverObject:(id)observingObject withCallback:(LFXMessageObserverCallback)callback;
//	- (void)removeMessageObserversForObject:(id)anObserverObject;	// This will remove all observations created with the above method
//	public void addMessageObserverObjectWithCallback( Object observerObject, LFXMessageObserverCallback callback);
//	public void removeMessageObserversForObject( Object anObserverObject);

	// This should be called (not overidden) for every incoming _and_ outgoing message
	//- (void)sendObserverCallbacksForMessage:(LFXMessage *)message;
//	public void sendObserverCallbacksForMessage( LFXMessage message);

	// This is used by the Network Context to work out what exact messages should be sent
	
	private ArrayList<LFXSiteID> visibleSiteIDs;
	private ArrayList<LFXMessageObservationDescriptor> observationDescriptors;

	public LFXTransportManager()
	{
		super();
		observationDescriptors = new ArrayList<LFXMessageObservationDescriptor>();
	}

	public boolean isConnected()
	{
		return isConnected;
	}
	
	public void setIsConnected( boolean isConnected)
	{
		if( isConnected() == isConnected) 
		{
			return;
		}

		if( isConnected)
		{
			listener.transportManagerDidConnect( this);
		}
		else
		{
			listener.transportManagerDidDisconnect( this);
		}
		
		this.isConnected = isConnected;
	}

	// Token Based subscriptions
	public Object addMessageObserverWithCallback( LFXMessageObserverCallback callback)
	{
		LFXMessageObservationDescriptor observationDescriptor = new LFXMessageObservationDescriptor();
		observationDescriptor.setCallback( callback);
		observationDescriptors.add( observationDescriptor);
		return observationDescriptor;
	}

	public void removeMessageObserverToken( Object observerToken)
	{
		observationDescriptors.remove( observerToken);
	}

	// Observer Based subscriptions
	public void addMessageObserverObjectWithCallback( Object observingObject, LFXMessageObserverCallback callback)
	{
		LFXMessageObservationDescriptor observationDescriptor = new LFXMessageObservationDescriptor();
		observationDescriptor.setCallback( callback);
		observationDescriptor.setObservingObject( observingObject);
		observationDescriptors.add( observationDescriptor);
	}

	@SuppressWarnings( "unchecked")
	public void removeMessageObserversForObject( Object anObserverObject)
	{
		for( LFXMessageObservationDescriptor observationDescriptor : (ArrayList<LFXMessageObservationDescriptor>) observationDescriptors.clone())
		{
			if( observationDescriptor.observingObjectWasEqualTo( anObserverObject))
			{
				observationDescriptors.remove( observationDescriptor);
			}
		}
	}

	@SuppressWarnings( "unchecked")
	public void sendObserverCallbacksForMessage( LFXMessage message)
	{
		for( LFXMessageObservationDescriptor observationDescriptor : (ArrayList<LFXMessageObservationDescriptor>) observationDescriptors.clone())
		{
			observationDescriptor.getCallback().run( observationDescriptor.getObservingObject(), message);
		}
	}
	
	public ArrayList<LFXSiteID> getVisibleSiteIDs()
	{
		return visibleSiteIDs;
	}
	
	public LFXNetworkContext getNetworkContext()
	{
		return networkContext;
	}
}
