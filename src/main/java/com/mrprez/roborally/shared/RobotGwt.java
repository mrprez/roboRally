package com.mrprez.roborally.shared;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RobotGwt implements IsSerializable {
	public final static int MAX_HEALTH = 9;
	private int number;
	private int x;
	private int y;
	private int health;
	private char direction;
	private boolean ghost;
	private Integer targetNb;
	private String powerDownState;
	private List<CardGwt> cards = new ArrayList<CardGwt>();
	
	
	public String getImageName(){
		StringBuilder imageName= new StringBuilder("img/robot/robot");
		imageName.append(number);
		if(powerDownState.equals("ONGOING")){
			imageName.append("HT");
		}
		imageName.append(".gif");
		return imageName.toString();
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
	public boolean isGhost() {
		return ghost;
	}
	public void setGhost(boolean ghost) {
		this.ghost = ghost;
	}
	public Integer getTargetNb() {
		return targetNb;
	}
	public void setTargetNb(Integer targetNb) {
		this.targetNb = targetNb;
	}
	public List<CardGwt> getCards() {
		return cards;
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
	public String getPowerDownState() {
		return powerDownState;
	}
	public void setPowerDownState(String powerDownState) {
		this.powerDownState = powerDownState;
	}
	

}
