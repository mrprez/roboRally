package com.mrprez.roborally.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RobotStateGwt implements IsSerializable {
	private int robotNb;
	private int health;
	private char direction;
	private int x;
	private int y;
	
	
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public char getDirection() {
		return direction;
	}
	public void setDirection(char direction) {
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
	public double getAngle(){
		switch (direction) {
		case 'D':
			return Math.PI/2;
		case 'B':
			return Math.PI;
		case 'G':
			return -Math.PI/2;
		default:
			return 0;
		}
	}
}
