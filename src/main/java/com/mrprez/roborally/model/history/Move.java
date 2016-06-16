package com.mrprez.roborally.model.history;

import com.mrprez.roborally.model.Robot;

public abstract class Move {
	private MoveType type;
	private String args;
	
	private Robot robot;
	
	
	public Move(MoveType type, String args, Robot robot) {
		super();
		this.type = type;
		this.args = args;
		this.robot = robot;
	}


	public Robot getRobot() {
		return robot;
	}

	public MoveType getType() {
		return type;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}
	
	

}
