package com.mrprez.roborally.model.move;

import com.mrprez.roborally.model.Direction;
import com.mrprez.roborally.model.Robot;

public class Translation extends Move{
	private Direction direction;
	

	public Translation(Robot robot, Direction direction) {
		super(robot);
		this.direction = direction;
	}


	public Direction getDirection() {
		return direction;
	}
	
	

}
