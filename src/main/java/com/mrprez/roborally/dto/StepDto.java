package com.mrprez.roborally.dto;

import java.util.List;

public class StepDto {
	
	private int roundNb;
	private int turnNb;
	private int stepNb;
	private Integer cardRapidity;
	private Integer squareX;
	private Integer squareY;
	
	private List<MoveDto> moveList;
	
	public int getRoundNb() {
		return roundNb;
	}
	public void setRoundNb(int roundNb) {
		this.roundNb = roundNb;
	}
	public int getTurnNb() {
		return turnNb;
	}
	public void setTurnNb(int turnNb) {
		this.turnNb = turnNb;
	}
	public int getStepNb() {
		return stepNb;
	}
	public void setStepNb(int stepNb) {
		this.stepNb = stepNb;
	}
	public Integer getCardRapidity() {
		return cardRapidity;
	}
	public void setCardRapidity(Integer cardRapidity) {
		this.cardRapidity = cardRapidity;
	}
	public Integer getSquareX() {
		return squareX;
	}
	public void setSquareX(Integer squareX) {
		this.squareX = squareX;
	}
	public Integer getSquareY() {
		return squareY;
	}
	public void setSquareY(Integer squareY) {
		this.squareY = squareY;
	}
	public List<MoveDto> getMoveList() {
		return moveList;
	}
	public void setMoveList(List<MoveDto> moveList) {
		this.moveList = moveList;
	}
	
	
}
