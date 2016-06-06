package com.mrprez.roborally.client.animation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.canvas.client.Canvas;
import com.google.web.bindery.event.shared.EventBus;
import com.mrprez.roborally.shared.MoveGwt;
import com.mrprez.roborally.shared.RobotGwt;
import com.mrprez.roborally.shared.StepGwt;

public class StepAnimation extends Animation {
	private static final double DELAY = 0.6;
	
	private EventBus eventBus;
	private List<UnitAnimation> animationList = new ArrayList<UnitAnimation>();
	
	
	public StepAnimation(StepGwt step, List<RobotGwt> robotList, Map<Integer,Canvas> robotCanvaMap){
		super();
		for(MoveGwt move : step.getMoveList()){
			RobotGwt robot = robotList.get(move.getRobotNb());
			Canvas robotCanvas = robotCanvaMap.get(move.getRobotNb());
			if(move.getTranslation()!=null){
				if(move.isSuccess()){
					animationList.add(new TranslationAnimation(robotCanvas, move.getTranslation()));
				} else {
					animationList.add(new FailedTranslationAnimation(robotCanvas, move.getTranslation()));
				}
			}
			if(move.getRotation()!=0){
				animationList.add(new RotationAnimation(robot.getImageName(), robotCanvas, move.getRotation()));
			}
		}
	}
	
	
	public double getTimeCoefficiant(){
		if(animationList.isEmpty()){
			return 0;
		}
		return 1 + (animationList.size()-1) * DELAY;
	}
	
	@Override
	protected void onStart(){
		for(UnitAnimation animation : animationList){
			animation.onStart();
		}
	}
	

	@Override
	protected void onUpdate(double progress) {
		double delay = 0;
		double coefficient = getTimeCoefficiant();
		for(UnitAnimation animation : animationList){
			double currentProgress = progress * coefficient - delay;
			delay = delay + DELAY;
			currentProgress = Math.max(currentProgress, 0);
			currentProgress = Math.min(currentProgress, 1);
			animation.update(currentProgress);
		}
		
	}


	@Override
	protected void onComplete(){
		for(UnitAnimation animation : animationList){
			animation.onComplete();
		}
		eventBus.fireEvent(new StepAnimationEvent());
	}
	
	
	public EventBus getEventBus() {
		return eventBus;
	}


	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}


}
