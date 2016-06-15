package com.mrprez.roborally.client.panel;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.mrprez.roborally.client.animation.AnimationManager;
import com.mrprez.roborally.client.animation.StepAnimation;
import com.mrprez.roborally.shared.RoundGwt;
import com.mrprez.roborally.shared.StepGwt;
import com.mrprez.roborally.shared.TurnGwt;

public class AnimationPlayerPanel extends FlexTable {
	public static final int ANIMATION_DURATION = 1000;
	private BoardPanel boardPanel;
	
	private AnimationManager animationManager;
	private List<RoundGwt> history;
	
	private PushButton playButton;
	private PushButton pauseButton;
	private PushButton stopButton;
	
	public AnimationPlayerPanel(List<RoundGwt> history, BoardPanel boardPanel){
		super();
		this.history = history;
		this.boardPanel = boardPanel;
		animationManager = new AnimationManager(ANIMATION_DURATION);
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
		for(TurnGwt turn : round.getTurnList()){
			for(StepGwt step : turn.getStepList()){
				animationManager.addAnimation(new StepAnimation(step, boardPanel));
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
