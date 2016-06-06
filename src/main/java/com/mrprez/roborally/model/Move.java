package com.mrprez.roborally.model;

public class Move {
	private Robot robot;
	private int rotation = 0;
	private Direction translation;
	private boolean success = true;
	
	
	public Move(Robot robot, Direction translation, boolean success){
		super();
		this.robot = robot;
		this.translation = translation;
		this.success = success;
	}
	
	public Move(Robot robot, int rotation){
		super();
		this.robot = robot;
		this.rotation = rotation;
	}

	public Robot getRobot() {
		return robot;
	}

	public int getRotation() {
		return rotation;
	}

	public Direction getTranslation() {
		return translation;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
