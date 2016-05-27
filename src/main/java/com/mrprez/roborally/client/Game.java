package com.mrprez.roborally.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.mrprez.roborally.shared.GameGwt;

public class Game implements EntryPoint {

	private GameGwtServiceAsync gameGwtService = GWT.create(GameGwtService.class);
	private Integer gameId;
	
	
	@Override
	public void onModuleLoad() {
		gameId = Integer.parseInt(Window.Location.getParameter("gameId"));
		gameGwtService.getGame(gameId, new AsyncCallback<GameGwt>() {
			@Override
			public void onSuccess(GameGwt game) {
				Canvas squaresCanvas = Canvas.createIfSupported();
				RootPanel.get().add(squaresCanvas);
				for(int y=0; y<game.getBoard().getSizeY(); y++){
					for(int x=0; x<game.getBoard().getSizeX(); x++){
						squaresCanvas.getContext2d().fillText("S", x*32, y*32);
					}
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
