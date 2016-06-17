package com.mrprez.roborally.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RoundGwt implements IsSerializable {
	private int number;
	private List<StageGwt> stageList;

	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public List<StageGwt> getStageList() {
		return stageList;
	}

	public void setStageList(List<StageGwt> stageList) {
		this.stageList = stageList;
	}
	
}
