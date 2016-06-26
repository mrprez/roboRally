package com.mrprez.roborally.dto;

import com.mrprez.roborally.model.Square;

public class SquareDto {
	private int boardId;
	private int x;
	private int y;
	private String clazz;
	private String args;
	
	
	public SquareDto(){
		super();
	}
	
	public SquareDto(Square square){
		super();
		this.boardId = square.getBoard().getId();
		this.x = square.getX();
		this.y = square.getY();
		this.clazz = square.getClass().getName();
		this.args = square.getArgs();
	}
	
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getArgs() {
		return args;
	}
	public void setArgs(String args) {
		this.args = args;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
}
