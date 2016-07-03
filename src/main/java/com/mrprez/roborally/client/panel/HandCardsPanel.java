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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.UIObject;
import com.mrprez.roborally.client.AbstractAsyncCallback;
import com.mrprez.roborally.client.CardCanvasFactory;
import com.mrprez.roborally.client.GameGwtService;
import com.mrprez.roborally.client.GameGwtServiceAsync;
import com.mrprez.roborally.shared.CardGwt;

public class HandCardsPanel extends FlexTable {
	private GameGwtServiceAsync gameGwtService = GWT.create(GameGwtService.class);
	private int gameId;
	
	
	public void init(int gameId){
		this.gameId = gameId;
		addStyleName("cardPanel");
		this.getElement().getStyle().clearPosition();
		Button saveButton = new Button("Sauvegarder");
		saveButton.addClickHandler(buildSaveHandHandler());
		setWidget(2, 0, saveButton);
		getFlexCellFormatter().setColSpan(2, 0, 9);
		getFlexCellFormatter().addStyleName(2, 0, "saveCardsLine");
		
		reloadCards();
	}
	
	
	public void reloadCards(){
		gameGwtService.getCardList(gameId, new AbstractAsyncCallback<List<CardGwt>>() {
			@Override
			public void onSuccess(List<CardGwt> cardList) {
				int index = 0;
				for(CardGwt card : cardList){
					Label label = new Label(index<5 ? String.valueOf(index+1) : "X");
					label.addStyleName("cardTurnNb");
					setWidget(1, index, label);
					Canvas cardCanvas = CardCanvasFactory.build(card, index);
					setWidget(0, index, cardCanvas);
					index++;
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
				for(int i=0; i<getCellCount(0); i++){
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

}
