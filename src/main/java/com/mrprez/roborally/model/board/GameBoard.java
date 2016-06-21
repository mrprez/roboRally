package com.mrprez.roborally.model.board;

import java.util.ArrayList;
import java.util.List;

import com.mrprez.roborally.model.Square;

public class GameBoard extends Board {
	
	protected List<Square> targetSquares = new ArrayList<Square>();

	
	public GameBoard(BuildingBoard buildingBoard){
		super();
		sizeX = buildingBoard.getSizeX();
		sizeY = buildingBoard.getSizeY();
		
		squares = new Square[sizeX][sizeY];
		for(int x=0; x<sizeX; x++){
			for(int y=0; y<sizeY; y++){
				squares[x][y] = buildingBoard.getSquare(x, y).copyForNewBoard(this);
			}
		}
	}
	
	public GameBoard(Integer id, Integer sizeX, Integer sizeY){
		super();
		setId(id);
		setSizeX(sizeX);
		setSizeY(sizeY);
		squares = new Square[sizeX][sizeY];
	}

	
	public List<Square> getTargetSquares() {
		return targetSquares;
	}

	public Square getStartSquare() {
		return targetSquares.get(0);
	}

	

	
	
	

}
