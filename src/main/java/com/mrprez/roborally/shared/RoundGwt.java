package com.mrprez.roborally.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RoundGwt implements IsSerializable {
	private int number;
	private List<StageGwt> stageList;
	private List<RobotStateGwt> robotStateList;

	
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

	public List<RobotStateGwt> getRobotStateList() {
		return robotStateList;
	}

	public void setRobotStateList(List<RobotStateGwt> robotStateList) {
		this.robotStateList = robotStateList;
	}
	
	
}
