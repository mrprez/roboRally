package com.mrprez.roborally.client.game;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mrprez.roborally.client.service.AbstractAsyncCallback;
import com.mrprez.roborally.client.service.GameGwtService;
import com.mrprez.roborally.client.service.GameGwtServiceAsync;
import com.mrprez.roborally.shared.GameGwt;
import com.mrprez.roborally.shared.RobotGwt;

public class AdminPanel extends FlowPanel {
	
	private GameGwtServiceAsync gameGwtService = GWT.create(GameGwtService.class);
	private Integer gameId;
	private FlexTable robotTable = new FlexTable();
	private Button playButton;
	private boolean gameFinished;
	private boolean everyRobotHasPlayed = false;
	
	
	public void init(GameGwt game){
		this.gameId = game.getId();
		add(robotTable);
		refreshRobotTable();
		if(game.isUserOwner() && !gameFinished){
			playButton = new Button("Jouer un tour !");
			add(playButton);
			playButton.addClickHandler(buildPlayNewRoundHandler());
		}
		add(buildHomeAnchor());
		add(buildRefreshAnchor());
	}
	
	
	public void refreshRobotTable(){
		gameGwtService.getGame(gameId, new AbstractAsyncCallback<GameGwt>() {
			@Override
			public void onSuccess(GameGwt game) {
				robotTable.clear();
				gameFinished = true;
				everyRobotHasPlayed = true;
				for(RobotGwt robot : game.getRobotList()){
					AbsolutePanel robotPanel = new AbsolutePanel();
					robotPanel.setPixelSize(47, 46);
					robotPanel.add(new Image(robot.getImageName()), 0, 0);
					robotTable.setWidget(robot.getNumber(), 0, robotPanel);
					if(robot.getHealth()!=0){
						gameFinished = false;
						robotTable.setWidget(robot.getNumber(), 1, new Image("img/Target"+robot.getTargetNb()+".png"));
						if(!robot.isHasPlayed() && !robot.getPowerDownState().equals("ONGOING")){
							robotPanel.add(new Image("img/hourglass.png"), 0, 0);
							everyRobotHasPlayed = false;
						}
						FlowPanel damagePanel = new FlowPanel();
						for(int i=robot.getHealth(); i<RobotGwt.MAX_HEALTH; i++){
							damagePanel.add(new Image("img/damageMark.jpg"));
						}
						robotTable.setWidget(robot.getNumber(), 2, damagePanel);
					}else{
						robotTable.setWidget(robot.getNumber(), 1, new Image("img/WinnerFlag.png"));
					}
				}
				
				if(gameFinished && playButton!=null){
					playButton.removeFromParent();
					playButton = null;
				}
			}
		});
	}
	
	
	private ClickHandler buildPlayNewRoundHandler(){
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if( ! everyRobotHasPlayed){
					buildConfirmDialogBox().center();
				}else{
					gameGwtService.playNewRound(gameId, new AbstractAsyncCallback<Void>() {
						public void onSuccess(Void result) {}
					});
				}
			}
		};
	}
	
	
	private DialogBox buildConfirmDialogBox(){
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Confirmation?");
		VerticalPanel mainPanel = new VerticalPanel();
		dialogBox.add(mainPanel);
		mainPanel.add(new Label("Certains joueurs n'ont pas encore programmé leur robot. Voulez-vous quand même jouer un nouveau tour?"));
		FlowPanel buttonPanel = new FlowPanel();
		buttonPanel.addStyleName("dialogButtonPanel");
		mainPanel.add(buttonPanel);
		Button noButton = new Button("Non");
		Button yesButton = new Button("Oui");
		buttonPanel.add(noButton);
		buttonPanel.add(yesButton);
		noButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}
		});
		yesButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				gameGwtService.playNewRound(gameId, new AbstractAsyncCallback<Void>() {
					public void onSuccess(Void result) {}
				});
			}
		});
		return dialogBox;
	}
	
	private Anchor buildHomeAnchor(){
		String homeUrl = Window.Location.createUrlBuilder().setPath("roboRally/Home.html").buildString();
		Anchor homeAnchor = new Anchor("Retour à l'accueil", homeUrl);
		homeAnchor.addStyleName("adminPanelLink");
		return homeAnchor;
	}
	
	private Anchor buildRefreshAnchor(){
		String refreshUrl = Window.Location.createUrlBuilder().setPath("roboRally/Game.html")
				.setParameter("gameId", String.valueOf(gameId)).buildString();
		Anchor refreshAnchor = new Anchor("Rafraichir la page", refreshUrl);
		refreshAnchor.addStyleName("adminPanelLink");
		return refreshAnchor;
	}

}
