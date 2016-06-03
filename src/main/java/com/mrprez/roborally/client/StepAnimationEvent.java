package com.mrprez.roborally.client;

import com.google.web.bindery.event.shared.Event;

public class StepAnimationEvent extends Event<StepAnimationEventHandler> {
	
	public static Type<StepAnimationEventHandler> TYPE = new Type<StepAnimationEventHandler>();
	

	@Override
	public Event.Type<StepAnimationEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(StepAnimationEventHandler handler) {
		handler.onEvent();
	}

}
