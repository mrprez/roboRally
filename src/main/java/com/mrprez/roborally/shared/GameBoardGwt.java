package com.mrprez.roborally.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GameBoardGwt implements IsSerializable {
	private int sizeX;
	private int sizeY;
	private int startX;
	private int startY;
	private SquareGwt[][] squares;
	
	
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
	public int getStartX() {
		return startX;
	}
	public void setStartX(int startX) {
		this.startX = startX;
	}
	public int getStartY() {
		return startY;
	}
	public void setStartY(int startY) {
		this.startY = startY;
	}
	public SquareGwt[][] getSquares() {
		return squares;
	}
	public void setSquares(SquareGwt[][] squares) {
		this.squares = squares;
	}
	
	public SquareGwt getSquare(int x, int y){
		return squares[x][y];
	}
	
	
}
