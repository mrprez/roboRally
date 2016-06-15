package com.mrprez.roborally.model.move;

import com.mrprez.roborally.model.Robot;

public class Rotation extends Move {
	private int angle;
	
	
	public Rotation(Robot robot, int angle) {
		super(robot);
		this.angle = angle;
	}


	public int getAngle() {
		return angle;
	}
}
