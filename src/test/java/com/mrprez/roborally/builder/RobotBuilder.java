package com.mrprez.roborally.builder;

import com.mrprez.roborally.model.Robot;
import com.mrprez.roborally.model.board.GameBoard;

public class RobotBuilder {
	private String username;
	private boolean ghost = false;
	private int x = 0;
	private int y = 0;
	
	
	private RobotBuilder(){
		super();
	}
	
	public static RobotBuilder newRobot(){
		return new RobotBuilder();
	}
	
	public RobotBuilder withUsername(String username){
		this.username = username;
		return this;
	}
	
	public RobotBuilder ghost(){
		ghost = true;
		return this;
	}
	
	public RobotBuilder withPosition(int x, int y){
		this.x = x;
		this.y = y;
		return this;
	}
	
	Robot buildOnGameBoard(GameBoard gameBoard){
		Robot robot = new Robot(gameBoard.getSquare(x, y), ghost);
		robot.setUsername(username);
		return robot;
	}

}
