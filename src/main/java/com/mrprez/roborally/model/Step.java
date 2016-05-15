package com.mrprez.roborally.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Step implements Iterable<Move>{
	
	private List<Move> moveList = new ArrayList<Move>();
	
	private Card card;
	private Square square;
	
	public Step(Card card){
		super();
		this.card = card;
	}
	
	public Step(Square square){
		super();
		this.square = square;
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

	
	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public Square getSquare() {
		return square;
	}

	public void setSquare(Square square) {
		this.square = square;
	}
	

}
