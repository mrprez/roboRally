package com.mrprez.roborally.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Round implements Iterable<Turn> {
	
	private int number;
	private List<Turn> turnList = new ArrayList<Turn>();
	
	
	public Round(int number) {
		super();
		this.number = number;
	}

	public void addTurn(Turn turnResult){
		turnList.add(turnResult);
	}

	@Override
	public Iterator<Turn> iterator() {
		return turnList.iterator();
	}

	public int getNumber() {
		return number;
	}

}
