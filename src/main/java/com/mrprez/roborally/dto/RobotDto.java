package com.mrprez.roborally.dto;

import java.util.List;

import com.mrprez.roborally.model.Direction;
import com.mrprez.roborally.model.PowerDownState;

public class RobotDto {
	private int number;
	private Integer x;
	private Integer y;
	private int health;
	private boolean ghost;
	private Direction direction;
	private List<CardDto> cardList;
	private Integer targetNumber;
	private String username;
	private PowerDownState powerDownState;
	private boolean hasPlayed;
	
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Integer getX() {
		return x;
	}
	public void setX(Integer x) {
		this.x = x;
	}
	public Integer getY() {
		return y;
	}
	public void setY(Integer y) {
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
	public PowerDownState getPowerDownState() {
		return powerDownState;
	}
	public void setPowerDownState(PowerDownState powerDownState) {
		this.powerDownState = powerDownState;
	}
	public boolean isHasPlayed() {
		return hasPlayed;
	}
	public void setHasPlayed(boolean hasPlayed) {
		this.hasPlayed = hasPlayed;
	}

}
