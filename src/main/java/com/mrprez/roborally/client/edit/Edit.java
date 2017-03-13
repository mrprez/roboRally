package com.mrprez.roborally.client.edit;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.mrprez.roborally.client.service.AbstractAsyncCallback;
import com.mrprez.roborally.client.service.BoardGwtService;
import com.mrprez.roborally.client.service.BoardGwtServiceAsync;
import com.mrprez.roborally.shared.BuildingBoardGwt;

public class Edit implements EntryPoint, SaveBoardEvent.Handler, SetTargetEvent.Handler {
	private BoardGwtServiceAsync boardGwtService = GWT.create(BoardGwtService.class);
	private int boardId;
	private DockLayoutPanel dockLayoutPanel;
	private BoardGrid boardGrid;
	private ElementPanel elementPanel;
	private HandlerManager handlerManager = new HandlerManager(this);
	

	@Override
	public void onModuleLoad() {
		boardId = Integer.parseInt(Window.Location.getParameter("boardId"));
		dockLayoutPanel = new DockLayoutPanel(Unit.PX);
		RootLayoutPanel.get().add(dockLayoutPanel);
		this.elementPanel = new ElementPanel(handlerManager);
		dockLayoutPanel.addEast(elementPanel, 300);
		boardGwtService.loadBuildingBoard(boardId, new AbstractAsyncCallback<BuildingBoardGwt>() {
			@Override
			public void onSuccess(BuildingBoardGwt result) {
				boardGrid = new BoardGrid(result, handlerManager);
				dockLayoutPanel.add(new ScrollPanel(boardGrid));
			}
		});
		handlerManager.addHandler(SaveBoardEvent.TYPE, this);
		handlerManager.addHandler(SetTargetEvent.TYPE, this);

	}

	@Override
	public void onEvent(SetTargetEvent event) {
		Image targetImg = elementPanel.getTarget(event.getTargetNb());
		event.getBoardSquare().setTargetImage(targetImg);
	}
	

	@Override
	public void onEvent(SaveBoardEvent event) {
		BuildingBoardGwt board = boardGrid.buildBuildingBoardGwt();
		boardGwtService.saveBuildingBoard(board, new AbstractAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				final DialogBox dialogBox = new DialogBox(true);
				dialogBox.setText("Sauvegarde réussie");
				dialogBox.show();
			}
		});
	}

}
