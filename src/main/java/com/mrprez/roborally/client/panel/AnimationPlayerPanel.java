package com.mrprez.roborally.client.panel;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.mrprez.roborally.client.animation.AnimationManager;
import com.mrprez.roborally.client.animation.AnimationManager.AnimationEndHandler;
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
	private ListBox listBox;
	private BoardPanel boardPanel;
	
	
	public void init(List<RoundGwt> history, BoardPanel boardPanel){
		this.history = history;
		this.boardPanel = boardPanel;
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
		animationManager = new AnimationManager(ANIMATION_DURATION, boardPanel);
		animationManager.addAnimationEndHandler(new AnimationEndHandler() {
			@Override
			public void onAnimationEnd() {
				playButton.setEnabled(true);
				pauseButton.setEnabled(false);
				if(listBox.getSelectedIndex()<listBox.getItemCount()-1){
					listBox.setSelectedIndex(listBox.getSelectedIndex()+1);
				}
			}
		});
		playButton.addClickHandler(buildPlayClickHandler());
		pauseButton.addClickHandler(buildPauseClickHandler());
		stopButton.addClickHandler(buildStopClickHandler());
		pauseButton.setEnabled(false);
		setWidget(0, 0, stopButton);
		setWidget(0, 1, playButton);
		setWidget(0, 2, pauseButton);
		listBox = new ListBox();
		listBox.setStyleName("roundListBox");
		for(RoundGwt round : history){
			listBox.addItem("Tour "+(round.getNumber()+1), String.valueOf(round.getNumber()));
		}
		listBox.setSelectedIndex(listBox.getItemCount()-1);
		setWidget(1, 0, listBox);
		getFlexCellFormatter().setColSpan(1, 0, 3);
	}
	
	public void addAndPlay(RoundGwt round){
		history.add(round);
		listBox.addItem("Tour "+(round.getNumber()+1), String.valueOf(round.getNumber()));
		listBox.setSelectedIndex(listBox.getItemCount()-1);
		if(animationManager.getState()==AnimationManager.STOP){
			boardPanel.setRoundState(history.get(round.getNumber()));
			loadRoundAnimation(round);
			animationManager.play();
			playButton.setEnabled(false);
			pauseButton.setEnabled(true);
		}else{
			DialogBox dialogBox = new DialogBox(true, true);
			dialogBox.setText("Nouveau tour");
			dialogBox.add(new Label("Un nouveau tour de jeu est disponible."));
			dialogBox.center();
		}
	}
	
	
	private ClickHandler buildPlayClickHandler(){
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if( ! animationManager.isPaused()){
					int roundNb = Integer.valueOf(listBox.getSelectedValue());
					boardPanel.setRoundState(history.get(roundNb));
					loadRoundAnimation(history.get(roundNb));
				}
				animationManager.play();
				playButton.setEnabled(false);
				pauseButton.setEnabled(true);
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
			}
		};
	}
	
	private ClickHandler buildStopClickHandler(){
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(animationManager.getState()==AnimationManager.PAUSE){
					boardPanel.reloadGame();
				}else if(animationManager.getState()==AnimationManager.PLAY){
					animationManager.addAnimationEndHandler(new AnimationEndHandler() {
						@Override
						public void onAnimationEnd() {
							boardPanel.reloadGame();
							animationManager.removeAnimationEndHandler(this);
						}
					});
				}else if(animationManager.getState()==AnimationManager.STOP){
					boardPanel.reloadGame();
				}
				animationManager.stop();
				playButton.setEnabled(true);
				pauseButton.setEnabled(false);
			}
		};
	}
	
}
