package com.mrprez.roborally.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.mrprez.roborally.client.game.AdminPanel;
import com.mrprez.roborally.client.game.AnimationPlayerPanel;
import com.mrprez.roborally.client.game.BoardPanel;
import com.mrprez.roborally.client.game.HandCardsPanel;
import com.mrprez.roborally.shared.GameGwt;
import com.mrprez.roborally.shared.NewRoundEvent;
import com.mrprez.roborally.shared.RefreshEvent;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.RemoteEventService;
import de.novanic.eventservice.client.event.RemoteEventServiceFactory;
import de.novanic.eventservice.client.event.listener.RemoteEventListener;

public class Game implements EntryPoint {
	private GameGwtServiceAsync gameGwtService = GWT.create(GameGwtService.class);
	private Integer gameId;
	private FlowPanel eastPanel = new FlowPanel();
	private BoardPanel boardPanel = new BoardPanel();
	private HandCardsPanel handCardsPanel = new HandCardsPanel();
	private AnimationPlayerPanel animationPlayerPanel = new AnimationPlayerPanel();
	private AdminPanel adminPanel = new AdminPanel();
	
	
	@Override
	public void onModuleLoad() {
		gameId = Integer.parseInt(Window.Location.getParameter("gameId"));
		final DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.PX);
		eastPanel.add(animationPlayerPanel);
		dockLayoutPanel.addEast(eastPanel, 200);
		dockLayoutPanel.addSouth(handCardsPanel, 155);
		dockLayoutPanel.add(new ScrollPanel(boardPanel));
		RootLayoutPanel.get().add(dockLayoutPanel);
		
		gameGwtService.getGame(gameId, new AbstractAsyncCallback<GameGwt>() {
			@Override
			public void onSuccess(GameGwt loadedGame) {
				boardPanel.init(loadedGame);
				if(loadedGame.getState().equals("INITIALIZATION")){
					initIntializingGame();
				}else if(loadedGame.getState().equals("ONGOING")){
					initOngoingGame(loadedGame);
				}
			}
		});
		
		handCardsPanel.init(gameId);
		
		RemoteEventServiceFactory remoteEventServiceFactory = RemoteEventServiceFactory.getInstance();
		RemoteEventService remoteEventService = remoteEventServiceFactory.getRemoteEventService();
		remoteEventService.addListener(RefreshEvent.DOMAIN, buildRefreshRemoteEventListener());
		
	}
	
	
	private RemoteEventListener buildRefreshRemoteEventListener(){
		return new RemoteEventListener() {
			@Override
			public void apply(Event event) {
				if(event instanceof RefreshEvent){
					adminPanel.refreshRobotTable();
				} else if(event instanceof NewRoundEvent){
					NewRoundEvent newRoundEvent = (NewRoundEvent) event;
					animationPlayerPanel.addAndPlay(newRoundEvent.getRound());
					handCardsPanel.reload();
					adminPanel.refreshRobotTable();
				}
			}
		};
	}
	
	
	private void initIntializingGame(){
		eastPanel.add(new Label("Partie en cours d'initialisation"));
		boardPanel.disable();
	}
	
	
	private void initOngoingGame(GameGwt loadedGame){
		animationPlayerPanel.init(loadedGame.getId(), loadedGame.getHistory(), boardPanel);
		adminPanel.init(loadedGame);
		eastPanel.add(adminPanel);
	}
	
	
}
