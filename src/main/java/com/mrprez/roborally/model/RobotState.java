package com.mrprez.roborally.model;

public class RobotState {
	private int health;
	private Direction direction;
	private int x;
	private int y;
	private boolean ghost = false;
	private PowerDownState powerDownState;
	
	
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
	public boolean isGhost() {
		return ghost;
	}
	public void setGhost(boolean ghost) {
		this.ghost = ghost;
	}
	public PowerDownState getPowerDownState() {
		return powerDownState;
	}
	public void setPowerDownState(PowerDownState powerDownState) {
		this.powerDownState = powerDownState;
	}
}
