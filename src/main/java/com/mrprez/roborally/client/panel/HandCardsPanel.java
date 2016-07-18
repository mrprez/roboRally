package com.mrprez.roborally.client.panel;

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
	private static final int CARD_NUMBER = 9;
	private GameGwtServiceAsync gameGwtService = GWT.create(GameGwtService.class);
	private int gameId;
	private Image powerDownPlannedImg = new Image("img/PowerDownPlanned.png");
	private Image powerDownOffImg = new Image("img/PowerDownOff.png");
	private Image powerDownOngoingImg = new Image("img/PowerDownOnGoing.png");
	
	
	public void init(int gameId){
		this.gameId = gameId;
		addStyleName("cardPanel");
		this.getElement().getStyle().clearPosition();
		Button saveButton = new Button("Sauvegarder");
		saveButton.addClickHandler(buildSaveHandHandler());
		setWidget(2, 0, saveButton);
		getFlexCellFormatter().setRowSpan(1, 0, 2);
		getFlexCellFormatter().setColSpan(2, 0, CARD_NUMBER);
		getFlexCellFormatter().addStyleName(2, 0, "saveCardsLine");
		
		for(int index=0; index<CARD_NUMBER; index++){
			Label label = new Label(index<5 ? String.valueOf(index+1) : "X");
			label.addStyleName("cardTurnNb");
			setWidget(1, index+1, label);
		}
		
		powerDownPlannedImg.addStyleName("powerDownButton");
		powerDownOffImg.addStyleName("powerDownButton");
		powerDownOffImg.addClickHandler(buildPowerDownOffHandler());
		powerDownPlannedImg.addClickHandler(buildPowerDownOnHandler());
		getFlexCellFormatter().setRowSpan(0, CARD_NUMBER+1, 3);
		
		reload();
	}
	
	
	public void reload(){
		gameGwtService.getPlayerRobot(gameId, new AbstractAsyncCallback<RobotGwt>() {
			@Override
			public void onSuccess(RobotGwt robot) {
				setWidget(0, 0, new Image(robot.getImageName()));
				if(robot.getTargetNb()!=null){
					setWidget(1, 0, new Image("img/Target"+robot.getTargetNb()+".png"));
				}else{
					clearCell(1, 0);
				}
				for(int index=0; index<CARD_NUMBER; index++){
					if(index<robot.getCards().size()){
						Canvas cardCanvas = CardCanvasFactory.build(robot.getCards().get(index), index);
						setWidget(0, index+1, cardCanvas);
					}else{
						setWidget(0, index+1, new Image("img/card/noCard.jpg"));
					}
				}
				if(robot.getPowerDownState().equals("ONGOING")){
					setWidget(0, CARD_NUMBER+1, powerDownOngoingImg);
				}else if(robot.getPowerDownState().equals("PLANNED")){
					setWidget(0, CARD_NUMBER+1, powerDownPlannedImg);
				}else{
					setWidget(0, CARD_NUMBER+1, powerDownOffImg);
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
				for(int i=1; i<=CARD_NUMBER; i++){
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
						flexTable.setWidget(0, CARD_NUMBER+1, powerDownPlannedImg);
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
						flexTable.setWidget(0, CARD_NUMBER+1, powerDownOffImg);
					}
				});
			}
		};
	}

}
