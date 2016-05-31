package com.mrprez.roborally.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.mrprez.roborally.model.Card;
import com.mrprez.roborally.model.Robot;
import com.mrprez.roborally.model.Square;
import com.mrprez.roborally.model.board.GameBoard;


public class AIRobot extends Robot {
	private List<Card> bestOrder;
	private double noteOfBestOrder;
	private DummyBoard dummyBoard;
	
	
	public AIRobot(Square square) {
		super(square);
		dummyBoard = new DummyBoard((GameBoard) square.getBoard());
	}
	
	public void orderCard(){
		noteOfBestOrder = ((double)getSquare().getBoard().getSizeX()) * ((double)getSquare().getBoard().getSizeY());
		List<Card> fixCards = new ArrayList<Card>();
		Collection<Card> possibilities = new HashSet<Card>();
		for(int i=0; i<getHealth(); i++){
			possibilities.add(getCard(i));
		}
		for(int i=getHealth(); i<9; i++){
			fixCards.add(getCard(i));
		}
		variateOneCard(fixCards, possibilities );
		setCards(bestOrder);
	}
	
	private void variateOneCard(List<Card> fixCards, Collection<Card> possibilities){
		if(possibilities.isEmpty()){
			double note = evaluate(fixCards);
			if(noteOfBestOrder > note){
				noteOfBestOrder = note;
				bestOrder = new ArrayList<Card>();
				bestOrder.addAll(fixCards);
			}
		}
		for(Card possibility : possibilities){
			fixCards.add(0, possibility);
			Collection<Card> newPossibilities = new HashSet<Card>(possibilities);
			newPossibilities.remove(possibility);
			variateOneCard(fixCards, newPossibilities);
			fixCards.remove(0);
		}
	}
	
	private double evaluate(List<Card> order){
		return dummyBoard.evaluate(getSquare().getX(), getSquare().getY(), getDirection(), getHealth(), getTargetNumber(), order);
	}

}
