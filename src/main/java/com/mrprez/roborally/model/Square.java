package com.mrprez.roborally.model;

import java.util.HashSet;
import java.util.Set;

import com.mrprez.roborally.model.board.Board;
import com.mrprez.roborally.model.history.Action;


public abstract class Square {
	
	private int x;
	private int y;
	private Board board;
	private Robot robot;
	private Set<Direction> walls = new HashSet<Direction>();
	
	
	public Square(Integer x, Integer y, Board board) {
		super();
		this.x = x;
		this.y = y;
		this.board = board;
	}
	
	
	public abstract Action play();
	protected abstract Square copy();
	public abstract String getImageName();
	public abstract String getArgs();
	public abstract void setArgs(String args);
	
	public Square copyForNewBoard(Board newBoard){
		Square copy = copy();
		for(Direction d : walls){
			copy.walls.add(d);
		}
		copy.board = newBoard;
		return copy;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Board getBoard() {
		return board;
	}
	public Robot getRobot() {
		return robot;
	}
	public void setRobot(Robot robot) {
		this.robot = robot;
		if(robot!=null && robot.getSquare()!=this){
			robot.setSquare(this);
		}
	}
	public Square getNextSquare(Direction direction){
		switch(direction){
			case UP   : return board.getSquare(x, y-1);
			case DOWN : return board.getSquare(x, y+1);
			case LEFT : return board.getSquare(x-1, y);
			case RIGHT: return board.getSquare(x+1, y);
		}
		return null;
	}
	public boolean getWall(Direction direction){
		return walls.contains(direction);
	}
	public void setWall(Direction direction, boolean wall){
		if(wall){
			walls.add(direction);
		}else{
			walls.remove(direction);
		}
	}
	
	public boolean hasSameXY(Square otherSquare){
		return otherSquare.x==x && otherSquare.y==y;
	}

}
