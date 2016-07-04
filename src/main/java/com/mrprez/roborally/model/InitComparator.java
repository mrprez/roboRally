package com.mrprez.roborally.model;

import java.util.Comparator;


public class InitComparator implements Comparator<Robot> {
	private int turn;
	
	
	public InitComparator() {
		super();
	}
	
	public InitComparator(int turn) {
		super();
		this.turn = turn;
	}


	@Override
	public int compare(Robot robot0, Robot robot1) {
		return robot1.getCard(turn).getRapidity() -robot0.getCard(turn).getRapidity();
	}
	
	public int getTurn() {
		return turn;
	}
	public void setTurn(int turn) {
		this.turn = turn;
	}
	
	
}
