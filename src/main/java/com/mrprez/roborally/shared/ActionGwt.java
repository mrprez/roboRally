package com.mrprez.roborally.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ActionGwt implements IsSerializable {
	private int cardRapidity;
	private Integer squareX;
	private Integer squareY;
	
	private List<StepGwt> stepList;
	
	
	public int getCardRapidity() {
		return cardRapidity;
	}
	public void setCardRapidity(int cardRapidity) {
		this.cardRapidity = cardRapidity;
	}
	public List<StepGwt> getStepList() {
		return stepList;
	}
	public void setStepList(List<StepGwt> stepList) {
		this.stepList = stepList;
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
	
}
