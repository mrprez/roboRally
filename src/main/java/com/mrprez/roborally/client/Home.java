package com.mrprez.roborally.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mrprez.roborally.shared.GameGwt;

public class Home implements EntryPoint {

	private GameGwtServiceAsync gameGwtService = GWT.create(GameGwtService.class);
	
	public void onModuleLoad() {
		VerticalPanel verticalPanel = new VerticalPanel();
		RootPanel.get().add(verticalPanel);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setWidth("100%");
		final Tree gameList = new Tree();
		verticalPanel.add(gameList);
		gameGwtService.getGameList(new AbstractAsyncCallback<List<GameGwt>>() {
			@Override
			public void onSuccess(List<GameGwt> result) {
				for(GameGwt game : result){
					UrlBuilder urlBuilder = Window.Location.createUrlBuilder();
					urlBuilder.setPath("roboRally/Game.html");
					urlBuilder.setParameter("gameId", String.valueOf(game.getId()));
					gameList.add(new Anchor(game.getName(), urlBuilder.buildString()));
				}
			}
		});
	}
}
