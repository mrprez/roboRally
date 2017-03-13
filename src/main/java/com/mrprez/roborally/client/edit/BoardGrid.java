package com.mrprez.roborally.client.edit;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Grid;
import com.mrprez.roborally.shared.BuildingBoardGwt;
import com.mrprez.roborally.shared.SquareGwt;

public class BoardGrid extends Grid {
	private int boardId;

	public BoardGrid(BuildingBoardGwt buildingBoard, HandlerManager handlerManager) {
		super(buildingBoard.getSizeX(), buildingBoard.getSizeY());
		this.boardId = buildingBoard.getId();
		addStyleName("editTable");
		for (int y = 0; y < buildingBoard.getSizeY(); y++) {
			for (int x = 0; x < buildingBoard.getSizeX(); x++) {
				BoardSquare squarePanel = new BoardSquare(buildingBoard.getSquare(x, y), handlerManager);
				setWidget(y, x, squarePanel);
			}
		}
	}

	public BuildingBoardGwt buildBuildingBoardGwt() {
		BuildingBoardGwt board = new BuildingBoardGwt();
		board.setSizeX(getColumnCount());
		board.setSizeY(getRowCount());
		board.setId(boardId);
		SquareGwt squareTab[][] = new SquareGwt[getColumnCount()][getRowCount()];
		for (int row = 0; row < getRowCount(); row++) {
			for (int col = 0; col < getColumnCount(); col++) {
				BoardSquare squarePanel = (BoardSquare) getWidget(row, col);
				squareTab[col][row] = squarePanel.buildSquare();
			}
		}
		board.setSquares(squareTab);
		return board;
	}
}
