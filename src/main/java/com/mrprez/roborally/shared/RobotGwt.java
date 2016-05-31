package com.mrprez.roborally.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RobotGwt implements IsSerializable {
	private int number;
	private int x;
	private int y;
	private int health;
	private char direction;
	
	
	public String getImageName(){
		return "img/robot/robot"+number+"_"+direction+".gif";
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
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
	public char getDirection() {
		return direction;
	}
	public void setDirection(char direction) {
		this.direction = direction;
	}
	

}
