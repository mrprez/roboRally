package com.mrprez.roborally.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TurnResult implements Iterable<Step>{
	
	private List<Step> stepList = new ArrayList<Step>();

	
	@Override
	public Iterator<Step> iterator() {
		return stepList.iterator();
	}
	
	public void addStep(Step step){
		if(step != null){
			stepList.add(step);
		}
	}
	
	

}
