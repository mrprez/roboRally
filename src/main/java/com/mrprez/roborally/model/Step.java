package com.mrprez.roborally.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mrprez.roborally.model.move.Move;

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
			moveList.add(move);
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
	
	public List<Move> getMoveList() {
		return moveList;
	}

	

}
