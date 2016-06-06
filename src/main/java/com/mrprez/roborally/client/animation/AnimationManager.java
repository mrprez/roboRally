package com.mrprez.roborally.client.animation;

import java.util.LinkedList;
import java.util.Queue;

import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class AnimationManager {
	
	private EventBus eventBus;
	private Queue<StepAnimation> animationQueue = new LinkedList<StepAnimation>();
	private int animationDuration;
	private boolean pause = false;
	
	
	public AnimationManager(int animationDuration){
		super();
		eventBus = new SimpleEventBus();
		eventBus.addHandler(StepAnimationEvent.TYPE, this);
		this.animationDuration = animationDuration;
	}
	

	public void onEvent(){
		if( ! pause ){
			StepAnimation animation = animationQueue.poll();
			if( animation!=null ){
				animation.run((int) (animationDuration * animation.getTimeCoefficiant()));
			}
		}
	}
	
	public void addAnimation(StepAnimation animation){
		animation.setEventBus(eventBus);
		animationQueue.add(animation);
	}
	
	
	public void stop(){
		pause = false;
		animationQueue.clear();
	}
	
	
	public void pause(){
		pause = true;
	}


	public void play() {
		pause = false;
		eventBus.fireEvent(new StepAnimationEvent());
	}
	
	
	public boolean isPaused(){
		return pause;
	}

}
