package com.mrprez.roborally.shared;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class StepGwt implements IsSerializable {
	private List<MoveGwt> moveList = new ArrayList<MoveGwt>();
	
	private int cardRapidity;
	private int squareX;
	private int squareY;
	private int number;
	
	
	public List<MoveGwt> getMoveList() {
		return moveList;
	}
	public void setMoveList(List<MoveGwt> moveList) {
		this.moveList = moveList;
	}
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
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
}
