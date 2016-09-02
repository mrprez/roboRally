package com.mrprez.roborally.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mrprez.roborally.shared.GameGwt;

public class Home implements EntryPoint {
	private GameGwtServiceAsync gameGwtService = GWT.create(GameGwtService.class);
	private Tree gameList = new Tree();
	private UrlBuilder urlBuilder = Window.Location.createUrlBuilder();
	
	
	public void onModuleLoad() {
		urlBuilder.setPath("roboRally/Game.html");
		VerticalPanel verticalPanel = new VerticalPanel();
		RootPanel.get().add(verticalPanel);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setWidth("100%");
		verticalPanel.add(gameList);
		gameGwtService.getGameList(new AbstractAsyncCallback<List<GameGwt>>() {
			@Override
			public void onSuccess(List<GameGwt> result) {
				for(GameGwt game : result){
					urlBuilder.setParameter("gameId", String.valueOf(game.getId()));
					gameList.add(new Anchor(game.getName(), urlBuilder.buildString()));
				}
			}
		});
		
		final DialogBox newGameDialogBox = new NewGameDialogBox();
		
		Button newGameButton = new Button("Nouvelle partie");
		newGameButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				newGameDialogBox.center();
			}
		});
		verticalPanel.add(newGameButton);
		
		final DialogBox newBoardDialogBox = new NewBoardDialogBox();
		
		Button newBoardButton = new Button("Créer un nouveau plateau");
		newBoardButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				newBoardDialogBox.center();
			}
		});
		verticalPanel.add(newBoardButton);
		
	}
	
	
	
	
}
