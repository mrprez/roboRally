package com.mrprez.roborally.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class Game implements EntryPoint {

	private GameGwtServiceAsync gameGwtService = GWT.create(GameGwtService.class);
	
	
	@Override
	public void onModuleLoad() {
		RootPanel.get().add(new Label(Window.Location.getParameter("gameId")));
		
	}

}
