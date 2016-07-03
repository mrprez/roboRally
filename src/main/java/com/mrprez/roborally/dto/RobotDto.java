package com.mrprez.roborally.dto;

import java.util.List;

import com.mrprez.roborally.model.Direction;

public class RobotDto {
	private int number;
	private int x;
	private int y;
	private int health;
	private boolean ghost;
	private Direction direction;
	private List<CardDto> cardList;
	private Integer targetNumber;
	private String username;
	
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
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
	public boolean getGhost() {
		return ghost;
	}
	public void setGhost(boolean ghost) {
		this.ghost = ghost;
	}
	public List<CardDto> getCardList() {
		return cardList;
	}
	public void setCardList(List<CardDto> cardList) {
		this.cardList = cardList;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getTargetNumber() {
		return targetNumber;
	}
	public void setTargetNumber(Integer targetNumber) {
		this.targetNumber = targetNumber;
	}

}
