package com.mrprez.roborally.model.board;

import com.mrprez.roborally.model.Square;

public class GameBoard extends Board {
	
	private String name;
	
	
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
		
		startSquare = squares[buildingBoard.getStartSquare().getX()][buildingBoard.getStartSquare().getY()];
		
		for(Square targetSquare : buildingBoard.getTargetSquares()){
			targetSquares.add(squares[targetSquare.getX()][targetSquare.getY()]);
		}
	}
	
	public GameBoard(Integer id, String name, Integer sizeX, Integer sizeY){
		super();
		setId(id);
		setName(name);
		setSizeX(sizeX);
		setSizeY(sizeY);
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	
	
	

}
