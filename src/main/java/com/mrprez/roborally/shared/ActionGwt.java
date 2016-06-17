package com.mrprez.roborally.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ActionGwt implements IsSerializable {
	private int cardRapidity;
	private int squareX;
	private int squareY;
	
	private List<StepGwt> stepList;
	
	
	public int getCardRapidity() {
		return cardRapidity;
	}
	public void setCardRapidity(int cardRapidity) {
		this.cardRapidity = cardRapidity;
	}
	public int getSquareX() {
		return squareX;
	}
	public void setSquareX(int squareX) {
		this.squareX = squareX;
	}
	public int getSquareY() {
		return squareY;
	}
	public void setSquareY(int squareY) {
		this.squareY = squareY;
	}
	public List<StepGwt> getStepList() {
		return stepList;
	}
	public void setStepList(List<StepGwt> stepList) {
		this.stepList = stepList;
	}
	
}
