package com.mrprez.roborally.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TurnResult implements Iterable<Step>{
	private int number;
	
	private List<Step> stepList = new ArrayList<Step>();

	
	public TurnResult(int number) {
		super();
		this.number = number;
	}

	@Override
	public Iterator<Step> iterator() {
		return stepList.iterator();
	}
	
	public void addStep(Step step){
		if(step != null){
			stepList.add(step);
		}
	}

	public int getNumber() {
		return number;
	}
	
	

}
