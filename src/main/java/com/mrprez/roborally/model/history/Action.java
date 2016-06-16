package com.mrprez.roborally.model.history;

import java.util.ArrayList;
import java.util.List;

import com.mrprez.roborally.model.Card;
import com.mrprez.roborally.model.Square;

public class Action {
	
	private List<Step> stepList = new ArrayList<Step>();
	private int cardRapidity;
	private int squareX;
	private int squareY;
	
	
	public Action(Card card){
		super();
		this.cardRapidity = card.getRapidity();
	}
	
	public Action(Square square){
		super();
		this.squareX = square.getX();
		this.squareY = square.getY();
	}
	
	public Action(int cardRapidity){
		super();
		this.cardRapidity = cardRapidity;
	}
	
	public Action(int squareX, int squareY){
		super();
		this.squareX = squareX;
		this.squareY = squareY;
	}

	
	
	public void addStep(Step step){
		stepList.add(step);
	}

	public List<Step> getStepList() {
		return stepList;
	}
	
	public int getCardRapidity() {
		return cardRapidity;
	}

	public int getSquareX() {
		return squareX;
	}

	public int getSquareY() {
		return squareY;
	}

}
