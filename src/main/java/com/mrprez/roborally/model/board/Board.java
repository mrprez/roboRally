package com.mrprez.roborally.model.board;

import com.mrprez.roborally.model.Square;

public abstract class Board {
	private int id;
	
	protected int sizeX;
	protected int sizeY;
	protected Square[][] squares;
	
	
	
	public Square getSquare(int x, int y){
		if(x<0 || x>=sizeX || y<0 || y>=sizeY){
			return null;
		}
		return squares[x][y];
	}
	
	public int getSizeX() {
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Square[][] getSquareTab(){
		return squares;
	}
	
	
	
	public void addSquare(Square square){
		if(square.getBoard()!=this){
			throw new IllegalArgumentException("this square do not belong to this board");
		}
		squares[square.getX()][square.getY()] = square;
	}
	

}
