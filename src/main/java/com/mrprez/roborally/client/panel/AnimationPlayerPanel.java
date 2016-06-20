package com.mrprez.roborally.client.panel;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.mrprez.roborally.client.animation.AnimationManager;
import com.mrprez.roborally.shared.ActionGwt;
import com.mrprez.roborally.shared.RoundGwt;
import com.mrprez.roborally.shared.StageGwt;

public class AnimationPlayerPanel extends FlexTable {
	public static final int ANIMATION_DURATION = 1000;
	
	private AnimationManager animationManager;
	private List<RoundGwt> history;
	
	private PushButton playButton;
	private PushButton pauseButton;
	private PushButton stopButton;
	
	public void init(List<RoundGwt> history, BoardPanel boardPanel){
		this.history = history;
		animationManager = new AnimationManager(ANIMATION_DURATION, boardPanel);
		Image playImg = new Image("img/media_playback_start.png");
		Image pauseImg = new Image("img/media_playback_pause.png");
		Image stopImg = new Image("img/media_playback_stop.png");
		playImg.setStyleName("playerButtonImg");
		pauseImg.setStyleName("playerButtonImg");
		stopImg.setStyleName("playerButtonImg");
		playButton  = new PushButton(playImg);
		pauseButton = new PushButton(pauseImg);
		stopButton  = new PushButton(stopImg);
		playButton.addStyleName("playerButton");
		pauseButton.addStyleName("playerButton");
		stopButton.addStyleName("playerButton");
		playButton.addClickHandler(buildPlayClickHandler());
		pauseButton.addClickHandler(buildPauseClickHandler());
		stopButton.addClickHandler(buildStopClickHandler());
		pauseButton.setEnabled(false);
		stopButton.setEnabled(false);
		setWidget(0, 0, stopButton);
		setWidget(0, 1, playButton);
		setWidget(0, 2, pauseButton);
	}
	
	
	private ClickHandler buildPlayClickHandler(){
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if( ! animationManager.isPaused()){
					loadRoundAnimation(history.get(history.size()-1));
				}
				animationManager.play();
				playButton.setEnabled(false);
				pauseButton.setEnabled(true);
				stopButton.setEnabled(true);
			}
		};
	}
	
	private void loadRoundAnimation(RoundGwt round){
		for(StageGwt stage : round.getStageList()){
			for(ActionGwt action : stage.getActionList()){
				animationManager.addAnimation(action);
			}
		}
	}
	
	
	
	private ClickHandler buildPauseClickHandler(){
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				animationManager.pause();
				playButton.setEnabled(true);
				pauseButton.setEnabled(false);
				stopButton.setEnabled(true);
			}
		};
	}
	
	private ClickHandler buildStopClickHandler(){
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				animationManager.stop();
				playButton.setEnabled(true);
				pauseButton.setEnabled(false);
				stopButton.setEnabled(false);
			}
		};
	}
	
}
