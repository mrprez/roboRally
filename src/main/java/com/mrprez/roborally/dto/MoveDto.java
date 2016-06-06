package com.mrprez.roborally.dto;

import com.mrprez.roborally.model.Direction;

public class MoveDto {
	
	private int robotNb;
	private Direction translation;
	private Integer rotation;
	private boolean success;
	
	
	public int getRobotNb() {
		return robotNb;
	}
	public void setRobotNb(int robotNb) {
		this.robotNb = robotNb;
	}
	public Direction getTranslation() {
		return translation;
	}
	public void setTranslation(Direction translation) {
		this.translation = translation;
	}
	public Integer getRotation() {
		return rotation;
	}
	public void setRotation(Integer rotation) {
		this.rotation = rotation;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
