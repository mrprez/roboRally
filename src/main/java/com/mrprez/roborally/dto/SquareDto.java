package com.mrprez.roborally.dto;

import com.mrprez.roborally.model.Direction;
import com.mrprez.roborally.model.Square;

public class SquareDto {
	private int boardId;
	private int x;
	private int y;
	private String clazz;
	private String args;
	private boolean wallUp;
	private boolean wallDown;
	private boolean wallLeft;
	private boolean wallRight;
	
	
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
		this.wallUp = square.getWall(Direction.UP);
		this.wallDown = square.getWall(Direction.DOWN);
		this.wallLeft = square.getWall(Direction.LEFT);
		this.wallRight = square.getWall(Direction.RIGHT);
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

	public boolean isWallUp() {
		return wallUp;
	}

	public void setWallUp(boolean wallUp) {
		this.wallUp = wallUp;
	}

	public boolean isWallDown() {
		return wallDown;
	}

	public void setWallDown(boolean wallDown) {
		this.wallDown = wallDown;
	}

	public boolean isWallLeft() {
		return wallLeft;
	}

	public void setWallLeft(boolean wallLeft) {
		this.wallLeft = wallLeft;
	}

	public boolean isWallRight() {
		return wallRight;
	}

	public void setWallRight(boolean wallRight) {
		this.wallRight = wallRight;
	}
	
	
}
