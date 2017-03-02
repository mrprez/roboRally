package com.mrprez.roborally.client.edit;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.mrprez.roborally.client.service.AbstractAsyncCallback;
import com.mrprez.roborally.client.service.BoardGwtService;
import com.mrprez.roborally.client.service.BoardGwtServiceAsync;
import com.mrprez.roborally.shared.BuildingBoardGwt;

public class Edit implements EntryPoint {
	private BoardGwtServiceAsync boardGwtService = GWT.create(BoardGwtService.class);
	private int boardId;
	private DockLayoutPanel dockLayoutPanel;
	private Grid grid;
	

	@Override
	public void onModuleLoad() {
		boardId = Integer.parseInt(Window.Location.getParameter("boardId"));
		dockLayoutPanel = new DockLayoutPanel(Unit.PX);
		RootLayoutPanel.get().add(dockLayoutPanel);
		dockLayoutPanel.addEast(new Label("East"), 300);
		boardGwtService.loadBuildingBoard(boardId, new AbstractAsyncCallback<BuildingBoardGwt>() {
			@Override
			public void onSuccess(BuildingBoardGwt result) {
				initGrid(result);
			}
		});
		
	}
	
	
	private void initGrid(BuildingBoardGwt buildingBoard){
		grid = new Grid(buildingBoard.getSizeY(), buildingBoard.getSizeX());
		grid.addStyleName("editTable");
		ScrollPanel scrollPanel = new ScrollPanel(grid);
		dockLayoutPanel.add(scrollPanel);
		for(int y=0; y<buildingBoard.getSizeY(); y++){
			for(int x=0; x<buildingBoard.getSizeX(); x++){
				grid.setWidget(y, x, new Image(buildingBoard.getSquare(x, y).getImageName()));
			}
		}
	}
	
}
