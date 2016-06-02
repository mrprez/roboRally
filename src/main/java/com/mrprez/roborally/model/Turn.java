package com.mrprez.roborally.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Turn implements Iterable<Step>{
	private int number;
	
	private List<Step> stepList = new ArrayList<Step>();

	
	public List<Step> getStepList() {
		return stepList;
	}

	public Turn(int number) {
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
	
	public void addAllSteps(List<Step> stepList){
		stepList.addAll(stepList);
	}

	public int getNumber() {
		return number;
	}
	
	

}
