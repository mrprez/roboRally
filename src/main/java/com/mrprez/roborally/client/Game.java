package com.mrprez.roborally.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.mrprez.roborally.client.panel.AnimationPlayerPanel;
import com.mrprez.roborally.client.panel.BoardPanel;
import com.mrprez.roborally.client.panel.HandCardsPanel;
import com.mrprez.roborally.shared.CardGwt;
import com.mrprez.roborally.shared.GameGwt;

public class Game implements EntryPoint {
	
	private GameGwtServiceAsync gameGwtService = GWT.create(GameGwtService.class);
	private Integer gameId;
	private FlowPanel eastPanel = new FlowPanel();
	private BoardPanel boardPanel = new BoardPanel();
	private HandCardsPanel handCardsPanel = new HandCardsPanel();
	private AnimationPlayerPanel animationPlayerPanel = new AnimationPlayerPanel();
	
	@Override
	public void onModuleLoad() {
		gameId = Integer.parseInt(Window.Location.getParameter("gameId"));
		final DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.PX);
		eastPanel.add(animationPlayerPanel);
		dockLayoutPanel.addEast(eastPanel, 200);
		dockLayoutPanel.addSouth(handCardsPanel, 150);
		dockLayoutPanel.add(new ScrollPanel(boardPanel));
		RootLayoutPanel.get().add(dockLayoutPanel);
		
		gameGwtService.getGame(gameId, new AbstractAsyncCallback<GameGwt>() {
			@Override
			public void onSuccess(GameGwt loadedGame) {
				boardPanel.init(loadedGame);
				animationPlayerPanel.init(loadedGame.getHistory(), boardPanel);
			}
		});
		
		gameGwtService.getCardList(gameId, new AbstractAsyncCallback<List<CardGwt>>(){
			@Override
			public void onSuccess(List<CardGwt> cardList) {
				handCardsPanel.init(gameId, cardList);
			}		
		});
		
	}
	
	
	
	
	

}
