package com.mrprez.roborally.client;

import java.util.LinkedList;
import java.util.Queue;

import com.google.gwt.animation.client.Animation;

public class AnimationEventHandler {
	
	private Queue<Animation> animationQueue = new LinkedList<Animation>();
	private int animationDuration;
	
	
	public AnimationEventHandler(int animationDuration) {
		super();
		this.animationDuration = animationDuration;
	}

	public void onEvent(){
		Animation animation = animationQueue.poll();
		if( animation!=null ){
			animation.run(animationDuration);
		}
	}
	
	public void addAnimation(Animation animation){
		animationQueue.add(animation);
	}

}
