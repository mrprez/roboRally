package com.mrprez.roborally.client.game;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.UIObject;
import com.mrprez.roborally.client.AbstractAsyncCallback;
import com.mrprez.roborally.client.CardCanvasFactory;
import com.mrprez.roborally.client.GameGwtService;
import com.mrprez.roborally.client.GameGwtServiceAsync;
import com.mrprez.roborally.shared.RobotGwt;

public class HandCardsPanel extends FlexTable {
	private int cardNumber = RobotGwt.MAX_HEALTH;
	private GameGwtServiceAsync gameGwtService = GWT.create(GameGwtService.class);
	private int gameId;
	private Image powerDownPlannedImg = new Image("img/PowerDownPlanned.png");
	private Image powerDownOffImg = new Image("img/PowerDownOff.png");
	private Image powerDownOngoingImg = new Image("img/PowerDownOnGoing.png");
	private Button saveButton;
	
	
	public void init(int gameId){
		this.gameId = gameId;
		addStyleName("cardPanel");
		this.getElement().getStyle().clearPosition();
		saveButton = new Button("Sauvegarder");
		saveButton.addClickHandler(buildSaveHandHandler());
		setWidget(2, 0, saveButton);
		getFlexCellFormatter().setRowSpan(1, 0, 2);
		getFlexCellFormatter().setColSpan(2, 0, cardNumber);
		getFlexCellFormatter().addStyleName(2, 0, "saveCardsLine");
		
		for(int index=0; index<cardNumber; index++){
			Label label;
			if(index<5){
				label = new Label(String.valueOf(index+1));
			} else {
				label = new Label("X");
				super.getFlexCellFormatter().addStyleName(0, index+1, "xCardCell");
				super.getFlexCellFormatter().addStyleName(1, index+1, "xCardCell");
			}
			label.addStyleName("cardTurnNb");
			setWidget(1, index+1, label);
		}
		
		powerDownPlannedImg.addStyleName("powerDownButton");
		powerDownOffImg.addStyleName("powerDownButton");
		powerDownOffImg.addClickHandler(buildPowerDownOffHandler());
		powerDownPlannedImg.addClickHandler(buildPowerDownOnHandler());
		getFlexCellFormatter().setRowSpan(0, cardNumber+1, 3);
		
		reload();
	}
	
	
	public void reload(){
		gameGwtService.getPlayerRobot(gameId, new AbstractAsyncCallback<RobotGwt>() {
			@Override
			public void onSuccess(RobotGwt robot) {
				setWidget(0, 0, new Image(robot.getImageName()));
				if(robot.getTargetNb()!=null){
					if(robot.getHealth()!=0){
						setWidget(1, 0, new Image("img/Target"+robot.getTargetNb()+".png"));
					}else{
						setWidget(1, 0, new Image("img/WinnerFlag.png"));
					}
				}else{
					clearCell(1, 0);
				}
				for(int index=0; index<cardNumber; index++){
					if(index<robot.getCards().size()){
						Canvas cardCanvas = CardCanvasFactory.build(robot.getCards().get(index), index);
						setWidget(0, index+1, cardCanvas);
					}else{
						Canvas cardCanvas = CardCanvasFactory.build("img/card/noCard.gif", "000");
						setWidget(0, index+1, cardCanvas);
					}
				}
				saveButton.setEnabled(robot.getCards().size() > 0);
				if(robot.getPowerDownState().equals("ONGOING")){
					setWidget(0, cardNumber+1, powerDownOngoingImg);
				}else if(robot.getPowerDownState().equals("PLANNED")){
					setWidget(0, cardNumber+1, powerDownPlannedImg);
				}else{
					setWidget(0, cardNumber+1, powerDownOffImg);
				}
			}
		});
	}
	
	
	private ClickHandler buildSaveHandHandler(){
		final UIObject originElement = this;
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				List<Integer> cardRapidityList = new ArrayList<Integer>();
				for(int i=1; i<=cardNumber; i++){
					Integer rapidity = Integer.valueOf(getWidget(0, i).getElement().getAttribute("rapidity"));
					cardRapidityList.add(rapidity);
				}
				gameGwtService.saveCards(gameId, cardRapidityList, new AbstractAsyncCallback<Void>() {
					@Override
					public void onSuccess(Void result) {
						final DialogBox dialogBox = new DialogBox(true);
						dialogBox.setText("Votre programmation a été sauvegardée avec succés");
						dialogBox.showRelativeTo(originElement);
					}
				});
			}
		};
	}
	
	
	private ClickHandler buildPowerDownOffHandler(){
		final FlexTable flexTable = this;
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				gameGwtService.savePowerDownState(gameId, "PLANNED", new AbstractAsyncCallback<Void>() {
					@Override
					public void onSuccess(Void result) {
						flexTable.remove(powerDownOffImg);
						flexTable.setWidget(0, cardNumber+1, powerDownPlannedImg);
					}
				});
				
			}
		};
	}
	
	private ClickHandler buildPowerDownOnHandler(){
		final FlexTable flexTable = this;
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				gameGwtService.savePowerDownState(gameId, "NONE", new AbstractAsyncCallback<Void>() {
					@Override
					public void onSuccess(Void result) {
						flexTable.remove(powerDownPlannedImg);
						flexTable.setWidget(0, cardNumber+1, powerDownOffImg);
					}
				});
			}
		};
	}

}
