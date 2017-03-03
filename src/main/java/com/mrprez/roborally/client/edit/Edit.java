package com.mrprez.roborally.client.edit;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.mrprez.roborally.client.service.AbstractAsyncCallback;
import com.mrprez.roborally.client.service.BoardGwtService;
import com.mrprez.roborally.client.service.BoardGwtServiceAsync;
import com.mrprez.roborally.shared.BuildingBoardGwt;
import com.mrprez.roborally.shared.SquareGwt;

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
		initElementPanel();
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
				grid.setWidget(y, x, buildImageSquare(buildingBoard.getSquare(x, y)));
			}
		}
	}
	
	private Image buildImageSquare(SquareGwt square) {
		final Image image = new Image(square.getImageName());
		image.addDragStartHandler(new DragStartHandler() {
			@Override
			public void onDragStart(DragStartEvent event) {
				event.setData("imageUrl", image.getUrl());
			}
		});
		image.addDragOverHandler(new DragOverHandler() {
			@Override
			public void onDragOver(DragOverEvent event) {
				event.getRelativeElement().addClassName("dropping");
			}
		});
		image.addDropHandler(new DropHandler() {
			@Override
			public void onDrop(DropEvent event) {
				if (event.getData("imageName") != null && event.getData("imageName").length() > 0) {
					image.setUrl("img/square/" + event.getData("imageName") + ".png");
				}
				if (event.getData("imageUrl") != null && event.getData("imageUrl").length() > 0) {
					image.setUrl(event.getData("imageUrl"));
				}
			}
		});
		return image;
	}

	private void initElementPanel() {
		StackLayoutPanel stackLayoutPanel = new StackLayoutPanel(Unit.EM);
		stackLayoutPanel.addStyleName("elementPanel");
		dockLayoutPanel.addEast(stackLayoutPanel, 300);

		FlowPanel simpleSquarePanel = new FlowPanel();
		stackLayoutPanel.add(simpleSquarePanel, "Simple Squares", 2.5);
		simpleSquarePanel.add(buildElementImage("EmptySquare"));
		simpleSquarePanel.add(buildElementImage("Hole"));

		stackLayoutPanel.add(new Image("img/square/EmptySquare.png"), "Other Squares", 2.5);
	}

	private Image buildElementImage(final String imageName) {
		Image image = new Image("img/square/" + imageName + ".png");
		image.addDragStartHandler(new DragStartHandler() {
			@Override
			public void onDragStart(DragStartEvent event) {
				event.setData("imageName", imageName);
			}
		});

		return image;
	}

}
