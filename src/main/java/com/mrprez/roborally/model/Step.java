package com.mrprez.roborally.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Step implements Iterable<Move>{
	
	private List<Move> moveList = new ArrayList<Move>();
	
	private int cardRapidity;
	private int squareX;
	private int squareY;
	private int number;

	
	public Step(int number, Card card){
		super();
		this.number = number;
		this.cardRapidity = card.getRapidity();
	}
	
	public Step(int number, Square square){
		super();
		this.number = number;
		this.squareX = square.getX();
		this.squareY = square.getY();
	}
	
	public Step(int number, int cardRapidity){
		super();
		this.number = number;
		this.cardRapidity = cardRapidity;
	}
	
	public Step(int number, int squareX, int squareY){
		super();
		this.number = number;
		this.squareX = squareX;
		this.squareY = squareY;
	}

	@Override
	public Iterator<Move> iterator() {
		return moveList.iterator();
	}
	
	public void addMove(Move move){
		if(move!=null){
			if(move.getTranslation()!=null || move.getRotation()!=0){
				moveList.add(move);
			}
		}
	}
	
	public void addAllMove(List<Move> moveList){
		for(Move move : moveList){
			addMove(move);
		}
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

	public int getNumber() {
		return number;
	}
	

}
