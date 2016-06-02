package com.mrprez.roborally.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TurnGwt implements IsSerializable {
	private int number;
	private List<StepGwt> stepList;
	
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public List<StepGwt> getStepList() {
		return stepList;
	}
	public void setStepList(List<StepGwt> stepList) {
		this.stepList = stepList;
	}

}
