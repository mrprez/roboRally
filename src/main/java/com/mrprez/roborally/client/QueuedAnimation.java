package com.mrprez.roborally.client;

import com.google.gwt.animation.client.Animation;
import com.google.web.bindery.event.shared.EventBus;

public abstract class QueuedAnimation extends Animation {

	private EventBus eventBus;

	
	public QueuedAnimation(EventBus eventBus) {
		super();
		this.eventBus = eventBus;
	}


	@Override
	protected void onComplete(){
		eventBus.fireEvent(new AnimationEvent());
	}

	
}
