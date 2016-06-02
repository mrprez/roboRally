package com.mrprez.roborally.client;

import com.google.web.bindery.event.shared.Event;

public class AnimationEvent extends Event<AnimationEventHandler> {
	
	public static Type<AnimationEventHandler> TYPE = new Type<AnimationEventHandler>();
	

	@Override
	public Event.Type<AnimationEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AnimationEventHandler handler) {
		handler.onEvent();
	}

}
