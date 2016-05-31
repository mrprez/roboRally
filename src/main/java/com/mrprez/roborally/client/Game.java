package com.mrprez.roborally.client;

import java.util.List;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.mrprez.roborally.shared.CardGwt;
import com.mrprez.roborally.shared.GameGwt;
import com.mrprez.roborally.shared.RobotGwt;
import com.mrprez.roborally.shared.SquareGwt;

public class Game implements EntryPoint {

	private GameGwtServiceAsync gameGwtService = GWT.create(GameGwtService.class);
	private Integer gameId;
	private FlowPanel centerPanel = new FlowPanel();
	private FlowPanel southPanel = new FlowPanel();
	

	@Override
	public void onModuleLoad() {
		gameId = Integer.parseInt(Window.Location.getParameter("gameId"));
		
		DockPanel dockPanel = new DockPanel();
		dockPanel.add(centerPanel, DockPanel.CENTER);
		dockPanel.add(southPanel, DockPanel.SOUTH);
		RootPanel.get().add(dockPanel);
		
		gameGwtService.getGame(gameId, new AsyncCallback<GameGwt>() {
			@Override
			public void onSuccess(GameGwt game) {
				loadSquares(game);
				loadRobots(game);
			}
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
		});
		
		gameGwtService.getCardList(gameId, new AsyncCallback<List<CardGwt>>(){
			@Override
			public void onSuccess(List<CardGwt> cardList) {
				loadCards(cardList);
			}
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}			
		});
		
	}
	
	
	public void loadCards(List<CardGwt> cardList){
		for(CardGwt card : cardList){
			southPanel.add(new Label(card.getImageName()));
		}
	}

	public void loadSquares(GameGwt game) {
		centerPanel.setHeight(game.getBoard().getSizeX()*97+"px");
		
		Canvas squaresCanvas = Canvas.createIfSupported();
		squaresCanvas.setCoordinateSpaceWidth(game.getBoard().getSizeX()*97);
		squaresCanvas.setCoordinateSpaceHeight(game.getBoard().getSizeY()*97);
		squaresCanvas.setStyleName("gameCanvas");
		centerPanel.add(squaresCanvas);
		for (int y = 0; y < game.getBoard().getSizeY(); y++) {
			for (int x = 0; x < game.getBoard().getSizeX(); x++) {
				loadSquare(squaresCanvas.getContext2d(), game.getBoard().getSquare(x, y), x, y);
			}
		}
		
		loadStartSquare(squaresCanvas.getContext2d(), game.getBoard().getStartX(), game.getBoard().getStartY());
	}
	
	
	public void loadStartSquare(final Context2d context2d, final int x, final int y){
		final Image img = new Image("img/Start.png");
		img.addLoadHandler(new LoadHandler() {
			@Override
			public void onLoad(LoadEvent event) {
				ImageElement imageEl = ImageElement.as(img.getElement());
				context2d.drawImage(imageEl, x*97, y*97);
			}
		});
		img.setVisible(false);
		RootPanel.get().add(img);
	}
	

	public void loadSquare(final Context2d context2d, SquareGwt square, final int x, final int y) {
		final Image img = new Image(square.getImageName());
		img.addLoadHandler(new LoadHandler() {
			@Override
			public void onLoad(LoadEvent event) {
				ImageElement imageEl = ImageElement.as(img.getElement());
				context2d.drawImage(imageEl, x*97, y*97);
			}
		});
		img.setVisible(false);
		RootPanel.get().add(img);
	}
	
	
	public void loadRobots(final GameGwt game) {
		for(final RobotGwt robot : game.getRobotList()){
			final Image img = new Image(robot.getImageName());
			img.addLoadHandler(new LoadHandler() {
				@Override
				public void onLoad(LoadEvent event) {
					Canvas robotCanvas = Canvas.createIfSupported();
					robotCanvas.setCoordinateSpaceWidth(game.getBoard().getSizeX()*97);
					robotCanvas.setCoordinateSpaceHeight(game.getBoard().getSizeY()*97);
					robotCanvas.setStyleName("gameCanvas");
					centerPanel.add(robotCanvas);
					ImageElement imageEl = ImageElement.as(img.getElement());
					robotCanvas.getContext2d().drawImage(imageEl, robot.getX()*97+25, robot.getY()*97+25);
				}
			});
			img.setVisible(false);
			RootPanel.get().add(img);
		}
	}

}
