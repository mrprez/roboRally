package com.mrprez.roborally.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class StageGwt implements IsSerializable {
	private int number;
	private List<ActionGwt> action;
	
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public List<ActionGwt> getActionList() {
		return action;
	}
	public void setActionList(List<ActionGwt> stepList) {
		this.action = stepList;
	}

}
