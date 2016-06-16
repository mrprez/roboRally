package com.mrprez.roborally.model.history;

import java.util.ArrayList;
import java.util.List;

public class Stage {
	private int number;
	
	private List<Action> actionList = new ArrayList<Action>();

	
	public Stage(int number) {
		super();
		this.number = number;
	}
	
	public void addAction(Action action){
		if(action != null){
			actionList.add(action);
		}
	}
	
	public void addAllAction(List<Action> actionList){
		this.actionList.addAll(actionList);
	}

	public List<Action> getActionList() {
		return actionList;
	}
	
	public int getNumber() {
		return number;
	}
	
	

}
