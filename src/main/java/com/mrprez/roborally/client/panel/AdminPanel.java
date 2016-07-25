package com.mrprez.roborally.client.panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.mrprez.roborally.client.AbstractAsyncCallback;
import com.mrprez.roborally.client.GameGwtService;
import com.mrprez.roborally.client.GameGwtServiceAsync;
import com.mrprez.roborally.shared.GameGwt;
import com.mrprez.roborally.shared.RobotGwt;
import com.mrprez.roborally.shared.RoundGwt;

public class AdminPanel extends FlowPanel {
	
	private GameGwtServiceAsync gameGwtService = GWT.create(GameGwtService.class);
	private Integer gameId;
	private AnimationPlayerPanel animationPlayerPanel;
	private HandCardsPanel handCardsPanel;
	private FlexTable robotTable = new FlexTable();
	
	
	public void init(GameGwt game, AnimationPlayerPanel animationPlayerPanel, HandCardsPanel handCardsPanel){
		this.gameId = game.getId();
		this.animationPlayerPanel = animationPlayerPanel;
		this.handCardsPanel = handCardsPanel;
		refreshRobotTable();
		add(robotTable);
		Button playButton = new Button("Jouer un tour !");
		add(playButton);
		playButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				gameGwtService.playNewRound(gameId, playRoundCallback());
			}
		});
	}
	
	
	public void refreshRobotTable(){
		gameGwtService.getGame(gameId, new AbstractAsyncCallback<GameGwt>() {
			@Override
			public void onSuccess(GameGwt game) {
				robotTable.clear();
				for(RobotGwt robot : game.getRobotList()){
					robotTable.setWidget(robot.getNumber(), 0, new Image(robot.getImageName()));
					robotTable.setWidget(robot.getNumber(), 1, new Image("img/Target"+robot.getTargetNb()+".png"));
					if(!robot.isHasPlayed()){
						robotTable.setWidget(robot.getNumber(), 2, new Image("img/hourglass.jpg"));
					}
					FlowPanel damagePanel = new FlowPanel();
					if(robot.getHealth()!=0){
						for(int i=robot.getHealth(); i<RobotGwt.MAX_HEALTH; i++){
							damagePanel.add(new Image("img/damageMark.jpg"));
						}
					}
					robotTable.setWidget(robot.getNumber(), 3, damagePanel);
				}
			}
		});
	}
	
	private AbstractAsyncCallback<RoundGwt> playRoundCallback(){
		return new AbstractAsyncCallback<RoundGwt>(){
			@Override
			public void onSuccess(RoundGwt round) {
				animationPlayerPanel.addAndPlay(round);
				handCardsPanel.reload();
				refreshRobotTable();
			}
		};
	}
	
	
	

}
