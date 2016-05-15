package com.mrprez.roborally.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Round implements Iterable<TurnResult> {
	private List<TurnResult> turnResultList = new ArrayList<TurnResult>();
	
	
	public void addTurnResult(TurnResult turnResult){
		turnResultList.add(turnResult);
	}

	@Override
	public Iterator<TurnResult> iterator() {
		return turnResultList.iterator();
	}

}
