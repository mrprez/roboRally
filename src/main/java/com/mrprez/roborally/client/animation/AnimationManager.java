package com.mrprez.roborally.client.animation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.mrprez.roborally.client.panel.BoardPanel;
import com.mrprez.roborally.shared.ActionGwt;
import com.mrprez.roborally.shared.StageGwt;
import com.mrprez.roborally.shared.StepGwt;

public class AnimationManager {
	public static final int STOP = 0;
	public static final int PLAY = 1;
	public static final int PAUSE = 2;
	
	private EventBus eventBus;
	private Queue<StageGwt> stageQueue = new LinkedList<StageGwt>();
	private Queue<ActionGwt> actionQueue;
	private Queue<StepAnimation> stepAnimationQueue;
	private int animationDuration;
	private BoardPanel boardPanel;
	private int state = STOP;
	private List<AnimationEndHandler> animationEndHandlers = new ArrayList<AnimationManager.AnimationEndHandler>();
	private List<StageAnimationHandler> stageAnimationHandlers = new ArrayList<AnimationManager.StageAnimationHandler>();
	
	
	public AnimationManager(int animationDuration, BoardPanel boardPanel){
		super();
		eventBus = new SimpleEventBus();
		eventBus.addHandler(ActionAnimationEvent.TYPE, this);
		eventBus.addHandler(StepAnimationEvent.TYPE, this);
		eventBus.addHandler(StageAnimationEvent.TYPE, this);
		this.animationDuration = animationDuration;
		this.boardPanel = boardPanel;
	}

	
	public void onStageAnimationEvent() {
		StageGwt stage = stageQueue.poll();
		if(stage!=null){
			for(StageAnimationHandler stageAnimationHandler : stageAnimationHandlers){
				stageAnimationHandler.onStageStart(stage);
			}
			actionQueue = new LinkedList<ActionGwt>();
			for(ActionGwt action : stage.getActionList()){
				actionQueue.add(action);
			}
			eventBus.fireEvent(new ActionAnimationEvent());
		}else{
			for(AnimationEndHandler animationEndHandler : animationEndHandlers){
				animationEndHandler.onAnimationEnd();
			}
			state = STOP;
		}
		
	}
	
	public void onActionAnimationEvent(){
		if( state == PLAY ){
			ActionGwt action = actionQueue.poll();
			if( action!=null ){
				stepAnimationQueue = new LinkedList<StepAnimation>();
				for(StepGwt step : action.getStepList()){
					stepAnimationQueue.add(new StepAnimation(step, boardPanel));
				}
				eventBus.fireEvent(new StepAnimationEvent());
			} else {
				eventBus.fireEvent(new StageAnimationEvent());
			}
		}
	}
	
	public void onStepAnimationEvent(){
		StepAnimation animation = stepAnimationQueue.poll();
		if( animation!=null ){
			animation.setEventBus(eventBus);
			animation.run((int) (animationDuration * animation.getTimeCoefficient()));
		}else{
			eventBus.fireEvent(new ActionAnimationEvent());
		}
	}
	
	public void addAnimationEndHandler(AnimationEndHandler animationEndHandler){
		animationEndHandlers.add(animationEndHandler);
	}
	
	public void removeAnimationEndHandler(AnimationEndHandler animationEndHandler) {
		animationEndHandlers.remove(animationEndHandler);
	}
	
	public void addStageAnimationHandler(StageAnimationHandler stageAnimationHandler){
		stageAnimationHandlers.add(stageAnimationHandler);
	}
	
	public void addStage(StageGwt stage){
		stageQueue.add(stage);
	}
	
	
	public void stop(){
		stageQueue.clear();
		actionQueue.clear();
		stepAnimationQueue.clear();
	}
	
	
	public void pause(){
		state = PAUSE;
	}


	public void play() {
		if(state==PAUSE){
			state = PLAY;
			eventBus.fireEvent(new ActionAnimationEvent());
		}else{
			state = PLAY;
			eventBus.fireEvent(new StageAnimationEvent());
		}
	}


	public int getState() {
		return state;
	}
	
	
	public boolean isPaused(){
		return state==PAUSE;
	}
	
	public static class StageAnimationEvent extends Event<AnimationManager> {
		public static Type<AnimationManager> TYPE = new Type<AnimationManager>();
		
		@Override
		public Event.Type<AnimationManager> getAssociatedType() {
			return TYPE;
		}
		@Override
		protected void dispatch(AnimationManager handler) {
			handler.onStageAnimationEvent();
		}
	}
	
	
	public static class ActionAnimationEvent extends Event<AnimationManager> {
		public static Type<AnimationManager> TYPE = new Type<AnimationManager>();
		
		@Override
		public Event.Type<AnimationManager> getAssociatedType() {
			return TYPE;
		}
		@Override
		protected void dispatch(AnimationManager handler) {
			handler.onActionAnimationEvent();
		}
	}
	
	public static class StepAnimationEvent extends Event<AnimationManager> {
		public static Type<AnimationManager> TYPE = new Type<AnimationManager>();
		
		@Override
		public Event.Type<AnimationManager> getAssociatedType() {
			return TYPE;
		}
		@Override
		protected void dispatch(AnimationManager handler) {
			handler.onStepAnimationEvent();
		}
	}
	
	
	public static interface AnimationEndHandler {
		void onAnimationEnd();
	}
	
	public static interface StageAnimationHandler {
		void onStageStart(StageGwt stage);
	}
	

}
