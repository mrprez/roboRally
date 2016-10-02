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
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.RootPanel;
import com.mrprez.roborally.shared.BuildingBoardGwt;
import com.mrprez.roborally.shared.GameGwt;

public class Home implements EntryPoint {
	private GameGwtServiceAsync gameGwtService = GWT.create(GameGwtService.class);
	private BoardGwtServiceAsync boardGwtService = GWT.create(BoardGwtService.class);
	private Grid grid = new Grid(1, 2);
	private UrlBuilder gameUrlBuilder = Window.Location.createUrlBuilder().setPath("roboRally/Game.html");
	private UrlBuilder editUrlBuilder = Window.Location.createUrlBuilder().setPath("roboRally/Edit.html");
	
	
	public void onModuleLoad() {
		RootPanel.get().add(grid);
		
		grid.setWidget(0, 0, buildNewGameButton());
		gameGwtService.getGameList(new AbstractAsyncCallback<List<GameGwt>>() {
			@Override
			public void onSuccess(List<GameGwt> result) {
				int rowNb = 1;
				for(GameGwt game : result){
					while(grid.getRowCount() <= rowNb){
						grid.insertRow(grid.getRowCount());
					}
					gameUrlBuilder.setParameter("gameId", String.valueOf(game.getId()));
					grid.setWidget(rowNb++, 0, new Anchor(game.getName(), gameUrlBuilder.buildString()));
				}
			}
		});
		
		grid.setWidget(0, 1, buildNewBoardButton());
		boardGwtService.getBoardList(new AbstractAsyncCallback<List<BuildingBoardGwt>>() {
			@Override
			public void onSuccess(List<BuildingBoardGwt> result) {
				int rowNb = 1;
				for(BuildingBoardGwt buildingBoard : result){
					while(grid.getRowCount() <= rowNb){
						grid.insertRow(grid.getRowCount());
					}
					editUrlBuilder.setParameter("boardId", String.valueOf(buildingBoard.getId()));
					grid.setWidget(rowNb++, 1, new Anchor(buildingBoard.getName(), editUrlBuilder.buildString()));
				}
			}
		});
	}
	
	
	private Button buildNewGameButton(){
		Button newGameButton = new Button("Nouvelle partie");
		newGameButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new NewBoardDialogBox().center();
			}
		});
		return newGameButton;
	}
	
	
	private Button buildNewBoardButton(){
		Button newBoardButton = new Button("Créer un nouveau plateau");
		newBoardButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new NewBoardDialogBox().center();
			}
		});
		return newBoardButton;
	}
	
	
}
