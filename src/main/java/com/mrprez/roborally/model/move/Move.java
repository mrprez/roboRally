package com.mrprez.roborally.model.move;

import com.mrprez.roborally.model.Robot;

public abstract class Move {
	private Robot robot;
	
	
	public Move(Robot robot){
		super();
		this.robot = robot;
	}

	public Robot getRobot() {
		return robot;
	}

}
