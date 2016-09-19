package com.mrprez.roborally.client.animation;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.animation.client.Animation;
import com.google.web.bindery.event.shared.EventBus;
import com.mrprez.roborally.client.game.BoardPanel;
import com.mrprez.roborally.shared.MoveGwt;
import com.mrprez.roborally.shared.StepGwt;

public class StepAnimation extends Animation {
	private static final double DELAY = 0.6;
	private EventBus eventBus;
	private List<MoveAnimation> animationList = new ArrayList<MoveAnimation>();
	private double timeCoefficient;
	
	
	public StepAnimation(StepGwt step, BoardPanel boardPanel){
		super();
		double maxMoveCoefficient = 0;
		for(MoveGwt move : step.getMoveList()){
			MoveAnimation moveAnimation = MoveAnimation.build(move, boardPanel);
			animationList.add(moveAnimation);
			maxMoveCoefficient = Math.max(maxMoveCoefficient, moveAnimation.getTimeCoefficient());
		}
		timeCoefficient = maxMoveCoefficient + DELAY*(step.getMoveList().size()-1);
	}
	
	public double getTimeCoefficient(){
		return timeCoefficient;
	}
	
	
	@Override
	protected void onComplete() {
		super.onComplete();
		for(MoveAnimation moveAnimation : animationList){
			moveAnimation.onComplete();
		}
		eventBus.fireEvent(new AnimationManager.StepAnimationEvent());
	}

	@Override
	protected void onStart() {
		for(MoveAnimation moveAnimation : animationList){
			moveAnimation.onStart();
		}
		super.onStart();
	}

	@Override
	public void onUpdate(double progress) {
		double delay = 0;
		for(MoveAnimation moveAnimation : animationList){
			double currentProgress = progress * timeCoefficient - delay;
			delay = delay + DELAY;
			currentProgress = Math.max(currentProgress, 0);
			currentProgress = Math.min(currentProgress, 1);
			moveAnimation.update(currentProgress);
		}
	}

	public EventBus getEventBus() {
		return eventBus;
	}

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	

}
