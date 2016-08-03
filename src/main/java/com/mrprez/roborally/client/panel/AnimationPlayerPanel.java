package com.mrprez.roborally.client.panel;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.mrprez.roborally.client.AbstractAsyncCallback;
import com.mrprez.roborally.client.GameGwtService;
import com.mrprez.roborally.client.GameGwtServiceAsync;
import com.mrprez.roborally.client.ImageLoader;
import com.mrprez.roborally.client.ImageLoaderCallback;
import com.mrprez.roborally.client.animation.AnimationManager;
import com.mrprez.roborally.client.animation.AnimationManager.ActionAnimationHandler;
import com.mrprez.roborally.client.animation.AnimationManager.AnimationEndHandler;
import com.mrprez.roborally.client.animation.AnimationManager.StageAnimationHandler;
import com.mrprez.roborally.shared.ActionGwt;
import com.mrprez.roborally.shared.CardGwt;
import com.mrprez.roborally.shared.RoundGwt;
import com.mrprez.roborally.shared.StageGwt;

public class AnimationPlayerPanel extends FlexTable {
	public static final int ANIMATION_DURATION = 1000;
	
	private GameGwtServiceAsync gameGwtService = GWT.create(GameGwtService.class);
	private int gameId;
	private AnimationManager animationManager;
	private List<RoundGwt> history;
	
	private PushButton playButton;
	private PushButton pauseButton;
	private PushButton stopButton;
	private ListBox listBox;
	private BoardPanel boardPanel;
	private Label stageNbLabel;
	private FlowPanel actionElementPanel = new FlowPanel();
	
	
	public void init(int gameId, List<RoundGwt> history, BoardPanel boardPanel){
		this.gameId = gameId;
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
		animationManager.addAnimationEndHandler(buildAnimationEndHandler());
		animationManager.addStageAnimationHandler(buildStageAnimationHandler());
		animationManager.addActionAnimationHandler(buildActionAnimationHandler());
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
		
		stageNbLabel = new Label();
		stageNbLabel.addStyleName("stageNbLabel");
		stageNbLabel.setVisible(false);
		setWidget(0, 3, stageNbLabel);
		getFlexCellFormatter().setRowSpan(0, 3, 2);
		
		getFlexCellFormatter().addStyleName(2, 0, "actionElement");
		setWidget(2, 0, actionElementPanel);
		getFlexCellFormatter().setColSpan(2, 0, 4);
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
			animationManager.addStage(stage);
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
	
	
	private AnimationEndHandler buildAnimationEndHandler(){
		return new AnimationEndHandler() {
			@Override
			public void onAnimationEnd() {
				playButton.setEnabled(true);
				pauseButton.setEnabled(false);
				if(listBox.getSelectedIndex()<listBox.getItemCount()-1){
					listBox.setSelectedIndex(listBox.getSelectedIndex()+1);
				}
				stageNbLabel.setVisible(false);
			}
		};
	}
	
	
	private StageAnimationHandler buildStageAnimationHandler(){
		return new StageAnimationHandler() {
			@Override
			public void onStageStart(StageGwt stage) {
				stageNbLabel.setText(String.valueOf(stage.getNumber()+1));
				stageNbLabel.setVisible(true);
			}
		};
	}
	
	
	private ActionAnimationHandler buildActionAnimationHandler(){
		return new ActionAnimationHandler() {
			@Override
			public void onActionStart(ActionGwt action) {
				if(action.getCardRapidity()>0){
					gameGwtService.getCard(gameId, action.getCardRapidity(), new AbstractAsyncCallback<CardGwt>() {
						@Override
						public void onSuccess(CardGwt card) {
							ImageLoader.getInstance().loadImage(card.getImageName(), new ImageLoaderCallback() {
								@Override
								public void onImageLoaded(Image image) {
									actionElementPanel.add(image);
									image.setVisible(true);
								}
							});
						}
					});
				}
			}
			@Override
			public void onActionEnd() {
				actionElementPanel.clear();
			}
		};
		
	}
	
}
