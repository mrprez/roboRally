package com.mrprez.roborally.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import com.mrprez.roborally.model.board.Board;
import com.mrprez.roborally.model.history.Action;
import com.mrprez.roborally.model.history.Move;


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
	
	
	public static Square buildSquare(String className, Board board, Integer x, Integer y, String args, Direction... walls) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException, ClassNotFoundException {
		@SuppressWarnings("unchecked")
		Constructor<Square> squareConstructor = (Constructor<Square>) Class.forName(className).getConstructor(Integer.class, Integer.class, Board.class);
		Square square = squareConstructor.newInstance(x, y, board);
		square.setArgs(args);
		for (Direction wallDirection : walls) {
			if (wallDirection != null) {
				square.setWall(wallDirection, true);
			}
		}
		return square;
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
	public Move host(Robot robot) {
		this.robot = robot;
		if(robot!=null && robot.getSquare()!=this){
			robot.walkOn(this);
		}
		return null;
	}
	public Square getNextSquare(Direction direction){
		switch(direction){
			case UP   : return board.getSquare(x, y-1);
			case DOWN : return board.getSquare(x, y+1);
			case LEFT : return board.getSquare(x-1, y);
			case RIGHT: return board.getSquare(x+1, y);
		}
		throw new IllegalArgumentException("Unknown directrion: "+direction);
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
