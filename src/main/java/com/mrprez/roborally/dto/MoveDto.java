package com.mrprez.roborally.dto;

import com.mrprez.roborally.model.history.MoveType;

public class MoveDto {
	private int stepNb;
	private int moveNb;
	private int robotNb;
	private MoveType type;
	private String args;
	
	
	public int getRobotNb() {
		return robotNb;
	}
	public void setRobotNb(int robotNb) {
		this.robotNb = robotNb;
	}
	public int getStepNb() {
		return stepNb;
	}
	public void setStepNb(int stepNb) {
		this.stepNb = stepNb;
	}
	public int getMoveNb() {
		return moveNb;
	}
	public void setMoveNb(int moveNb) {
		this.moveNb = moveNb;
	}
	public MoveType getType() {
		return type;
	}
	public void setType(MoveType type) {
		this.type = type;
	}
	public String getArgs() {
		return args;
	}
	public void setArgs(String args) {
		this.args = args;
	}
	
	
}
