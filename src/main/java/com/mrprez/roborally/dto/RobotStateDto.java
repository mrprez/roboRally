package com.mrprez.roborally.dto;

import com.mrprez.roborally.model.Direction;

public class RobotStateDto {
	private int robotNb;
	private int roundNb;
	private int health;
	private Direction direction;
	private int x;
	private int y;
	private boolean ghost;
	
	
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getRobotNb() {
		return robotNb;
	}
	public void setRobotNb(int robotNb) {
		this.robotNb = robotNb;
	}
	public int getRoundNb() {
		return roundNb;
	}
	public void setRoundNb(int roundNb) {
		this.roundNb = roundNb;
	}
	public boolean isGhost() {
		return ghost;
	}
	public void setGhost(boolean ghost) {
		this.ghost = ghost;
	}
}
