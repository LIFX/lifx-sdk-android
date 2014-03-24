//
//  LFXMessageObservationDescriptor.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.entities.internal;

public class LFXMessageObservationDescriptor
{
	public interface LFXMessageObserverCallback 
	{
		public void run( Object context, LFXMessage message);
	};
	// typedef void (^LFXMessageObserverCallback)(LFXMessage *message);
	
	// All Observation Descriptors have a callback
	private LFXMessageObserverCallback callback;

	// Observers that were created with an 'observer object' will have this property set
	private Object observingObject;

	// An unsafe_unretained reference to .observingObject is stored, and this method can
	// be used to compare the two. This is encapsulated to limit the potential scope of
	// dead reference bugs.
	private Object observingObjectUnsafeReference;
	
	public void setObservingObject( Object observingObject)
	{
		this.observingObject = observingObject;
		this.observingObjectUnsafeReference = observingObject;
	}

	public Object getObservingObject()
	{
		return observingObject;
	}
	
	public boolean observingObjectWasEqualTo( Object object)
	{
		return this.observingObjectUnsafeReference == object;
	}
	
	public LFXMessageObserverCallback getCallback()
	{
		return callback;
	}
	
	public void setCallback( LFXMessageObserverCallback callback)
	{
		this.callback = callback;
	}
}

