package com.mrprez.roborally.model.board;

import com.mrprez.roborally.model.Square;

public class GameBoard extends Board {
	
	

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

	

	
	
	

}
