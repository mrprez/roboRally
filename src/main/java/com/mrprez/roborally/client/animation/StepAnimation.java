package com.mrprez.roborally.client.animation;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.animation.client.Animation;
import com.google.web.bindery.event.shared.EventBus;
import com.mrprez.roborally.client.panel.BoardPanel;
import com.mrprez.roborally.shared.StepGwt;
import com.mrprez.roborally.client.animation.MoveAnimation;

public class StepAnimation extends Animation {
	private static final double DELAY = 0.6;
	
	private EventBus eventBus;
	private List<MoveAnimation> animationList = new ArrayList<MoveAnimation>();
	
	
	public StepAnimation(StepGwt step, BoardPanel boardPanel){
		super();
		for(MoveAnimation moveAnimation : step.getMoveList()){
			// TODO convert shared Move from gwt web service into Client moveAnimation
			moveAnimation.init(boardPanel.getRobotCanvas(moveAnimation.getRobotNb()));
		}
	}
	
	
	public double getTimeCoefficiant(){
		if(animationList.isEmpty()){
			return 0;
		}
		return 1 + (animationList.size()-1) * DELAY;
	}
	
	@Override
	protected void onStart(){}
	

	@Override
	protected void onUpdate(double progress) {
		double delay = 0;
		double coefficient = getTimeCoefficiant();
		for(MoveAnimation animation : animationList){
			double currentProgress = progress * coefficient - delay;
			delay = delay + DELAY;
			currentProgress = Math.max(currentProgress, 0);
			currentProgress = Math.min(currentProgress, 1);
			animation.update(currentProgress);
		}
		
	}


	@Override
	protected void onComplete(){
		eventBus.fireEvent(new StepAnimationEvent());
	}
	
	
	public EventBus getEventBus() {
		return eventBus;
	}


	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}


}
