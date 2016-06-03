package com.mrprez.roborally.client;

import java.util.LinkedList;
import java.util.Queue;

public class StepAnimationEventHandler {
	
	private Queue<StepAnimation> animationQueue = new LinkedList<StepAnimation>();
	private int animationDuration;
	
	
	public StepAnimationEventHandler(int animationDuration) {
		super();
		this.animationDuration = animationDuration;
	}

	public void onEvent(){
		StepAnimation animation = animationQueue.poll();
		if( animation!=null ){
			animation.run((int) (animationDuration * animation.getTimeCoefficiant()));
		}
	}
	
	public void addAnimation(StepAnimation animation){
		animationQueue.add(animation);
	}

}
