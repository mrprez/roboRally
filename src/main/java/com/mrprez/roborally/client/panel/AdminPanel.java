package com.mrprez.roborally.client.panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.mrprez.roborally.client.AbstractAsyncCallback;
import com.mrprez.roborally.client.GameGwtService;
import com.mrprez.roborally.client.GameGwtServiceAsync;
import com.mrprez.roborally.shared.GameGwt;
import com.mrprez.roborally.shared.RoundGwt;

public class AdminPanel extends FlowPanel {
	
	private GameGwtServiceAsync gameGwtService = GWT.create(GameGwtService.class);
	private Integer gameId;
	private AnimationPlayerPanel animationPlayerPanel;
	private HandCardsPanel handCardsPanel;
	
	
	public void init(GameGwt game, AnimationPlayerPanel animationPlayerPanel, HandCardsPanel handCardsPanel){
		this.gameId = game.getId();
		this.animationPlayerPanel = animationPlayerPanel;
		this.handCardsPanel = handCardsPanel;
		Button playButton = new Button("Jouer un tour !");
		add(playButton);
		playButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				gameGwtService.playNewRound(gameId, playRoundCallback());
				
			}
		});
	}
	
	private AbstractAsyncCallback<RoundGwt> playRoundCallback(){
		return new AbstractAsyncCallback<RoundGwt>(){
			@Override
			public void onSuccess(RoundGwt round) {
				animationPlayerPanel.addAndPlay(round);
				handCardsPanel.reloadCards();
			}
		};
	}
	
	
	

}
