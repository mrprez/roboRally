package com.mrprez.roborally.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RoundGwt implements IsSerializable {
	private int number;
	private List<TurnGwt> turnList;

	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public List<TurnGwt> getTurnList() {
		return turnList;
	}

	public void setTurnList(List<TurnGwt> turnList) {
		this.turnList = turnList;
	}
	
}
