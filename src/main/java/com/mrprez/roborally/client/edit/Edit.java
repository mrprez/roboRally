package com.mrprez.roborally.client.edit;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
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
	private List<Image> targetList = new ArrayList<Image>();
	
	

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
				FlowPanel squarePanel = buildBoardSquare(buildingBoard.getSquare(x, y));
				grid.setWidget(y, x, squarePanel);
				squarePanel.getElement().setAttribute("x", String.valueOf(x));
				squarePanel.getElement().setAttribute("y", String.valueOf(y));
			}
		}
	}

	private void initElementPanel() {
		final StackLayoutPanel stackLayoutPanel = new StackLayoutPanel(Unit.EM);
		stackLayoutPanel.addStyleName("elementPanel");
		dockLayoutPanel.addEast(stackLayoutPanel, 300);

		final FlowPanel commonSquarePanel = new FlowPanel();
		stackLayoutPanel.add(commonSquarePanel, "Divers", 2.5);
		final FlowPanel convoyerSquarePanel = new FlowPanel();
		stackLayoutPanel.add(convoyerSquarePanel, "Convoyeur", 2.5);
		
		boardGwtService.getAvailableSquareList(new AbstractAsyncCallback<List<SquareGwt>>() {
			@Override
			public void onSuccess(List<SquareGwt> result) {
				for(SquareGwt square : result) {
					if(square.getImageName().startsWith("ConveyorBelt")){
						convoyerSquarePanel.add(buildElementImage(square));
					} else {
						commonSquarePanel.add(buildElementImage(square));
					}
				}
			}
		});
		
		stackLayoutPanel.add(buildTargetPanel(), "Parcours", 2.5);
		
		stackLayoutPanel.add(buildCommandPanel(), "Commandes", 2.5);
		
	}
	
	
	private FlowPanel buildCommandPanel(){
		final FlowPanel commandPanel = new FlowPanel();
		Button saveButton = new Button("Sauvegarder");
		commandPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				BuildingBoardGwt board = new BuildingBoardGwt();
				board.setSizeX(grid.getColumnCount());
				board.setSizeY(grid.getRowCount());
				board.setId(boardId);
				SquareGwt squareTab[][] = new SquareGwt[grid.getColumnCount()][grid.getRowCount()];
				for(int row=0; row<grid.getRowCount(); row++) {
					for(int col=0; col<grid.getColumnCount(); col++){
						FlowPanel squarePanel = (FlowPanel) grid.getWidget(row, col);
						Image squareImage = (Image) squarePanel.getWidget(0);
						SquareGwt squareGwt = new SquareGwt();
						squareGwt.useUrl(squareImage.getUrl());
						squareTab[col][row] = squareGwt;
					}
				}
				board.setSquares(squareTab);
				boardGwtService.saveBuildingBoard(board, new AbstractAsyncCallback<Void>() {
					@Override
					public void onSuccess(Void result) {
						final DialogBox dialogBox = new DialogBox(true);
						dialogBox.setText("Sauvegarde réussie");
						dialogBox.showRelativeTo(grid);
					}
				});
			}
		});
		return commandPanel;
	}
	
	
	private FlowPanel buildTargetPanel(){
		final FlowPanel targetPanel = new FlowPanel();
		for(int targetNb=0; targetNb<=8; targetNb++) {
			Image targetImg = buildTargetImage(targetNb);
			targetPanel.add(targetImg);
			targetList.add(targetImg);
		}
		targetPanel.addDomHandler(new DropHandler() {
			@Override
			public void onDrop(DropEvent event) {
				targetPanel.add(targetList.get(Integer.parseInt(event.getData("targetNb"))));
			}
		}, DropEvent.getType());
		targetPanel.addDomHandler(new DragOverHandler() {
			@Override
			public void onDragOver(DragOverEvent event) {}
		}, DragOverEvent.getType());
		return targetPanel;
	}
	
	
	private Image buildTargetImage(final int targetNb){
		Image image = targetNb==0 ? new Image("img/Start.png") : new Image("img/Target"+targetNb+".png");
		image.addDragStartHandler(new DragStartHandler() {
			@Override
			public void onDragStart(DragStartEvent event) {
				event.setData("targetNb", String.valueOf(targetNb));
			}
		});
		image.addStyleName("targetImage");
		return image;
	}

	
	private FlowPanel buildBoardSquare(SquareGwt square) {
		final FlowPanel flowPanel = new FlowPanel();
		flowPanel.addStyleName("squareDiv");
		final Image image = new Image(square.getImageUrl());
		flowPanel.add(image);
		image.addDragStartHandler(new DragStartHandler() {
			@Override
			public void onDragStart(DragStartEvent event) {
				event.setData("imageUrl", image.getUrl());
			}
		});
		flowPanel.addDomHandler(new DragOverHandler() {
			@Override
			public void onDragOver(DragOverEvent event) {
				image.addStyleName("dropping");
			}
		}, DragOverEvent.getType());
		flowPanel.addDomHandler(new DragLeaveHandler() {
			@Override
			public void onDragLeave(DragLeaveEvent event) {
				image.removeStyleName("dropping");
			}
		}, DragLeaveEvent.getType());
		
		flowPanel.addDomHandler(new DropHandler() {
			@Override
			public void onDrop(DropEvent event) {
				if (event.getData("imageUrl") != null && event.getData("imageUrl").length() > 0) {
					image.setUrl(event.getData("imageUrl"));
				}
				if (event.getData("targetNb") !=null && event.getData("targetNb").length() > 0) {
					flowPanel.add(targetList.get(Integer.parseInt(event.getData("targetNb"))));
				}
				image.removeStyleName("dropping");
			}
		}, DropEvent.getType());
		
		return flowPanel;
	}
	private Image buildElementImage(final SquareGwt square) {
		Image image = new Image(square.getImageUrl());
		image.addDragStartHandler(new DragStartHandler() {
			@Override
			public void onDragStart(DragStartEvent event) {
				event.setData("imageUrl", square.getImageUrl());
			}
		});

		return image;
	}

}
