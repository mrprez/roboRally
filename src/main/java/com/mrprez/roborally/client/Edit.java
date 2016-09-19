package com.mrprez.roborally.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.mrprez.roborally.shared.BuildingBoardGwt;
import com.mrprez.roborally.shared.GameGwt;

public class Edit implements EntryPoint {
	private BoardGwtServiceAsync boardGwtService = GWT.create(BoardGwtService.class);
	private int boardId;
	private Grid grid = new Grid();
	

	@Override
	public void onModuleLoad() {
		boardId = Integer.parseInt(Window.Location.getParameter("boardId"));
		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.PX);
		dockLayoutPanel.add(grid);
		boardGwtService.loadBuildingBoard(boardId, new AbstractAsyncCallback<BuildingBoardGwt>() {
			@Override
			public void onSuccess(BuildingBoardGwt result) {
				initGrid(result);
			}
		});
		
		
	}
	
	
	private void initGrid(BuildingBoardGwt buildingBoard){
		for(int y=0; y<buildingBoard.getSizeY(); y++){
			for(int x=0; x<buildingBoard.getSizeX(); x++){
				grid.setWidget(y, x, new Image(buildingBoard.getSquare(x, y).getImageName()));
			}
		}
	}
	
	

}
