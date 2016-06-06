package com.mrprez.roborally.client.animation;

import com.google.web.bindery.event.shared.Event;

public class StepAnimationEvent extends Event<AnimationManager> {
	
	public static Type<AnimationManager> TYPE = new Type<AnimationManager>();
	

	@Override
	public Event.Type<AnimationManager> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AnimationManager handler) {
		handler.onEvent();
	}

}
