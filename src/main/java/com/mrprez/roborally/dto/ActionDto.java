package com.mrprez.roborally.dto;

import java.util.List;

public class ActionDto {
	
	private int roundNb;
	private int stageNb;
	private int actionNb;
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
	public int getStageNb() {
		return stageNb;
	}
	public void setStageNb(int stageNb) {
		this.stageNb = stageNb;
	}
	public int getActionNb() {
		return actionNb;
	}
	public void setActionNb(int actionNb) {
		this.actionNb = actionNb;
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
