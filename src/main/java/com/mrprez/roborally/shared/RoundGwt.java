package com.mrprez.roborally.shared;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RoundGwt implements IsSerializable {
	private int number;
	private List<StageGwt> stageList;
	private Map<Integer,RobotStateGwt> robotStateMap;

	
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

	public Map<Integer,RobotStateGwt> getRobotStateMap() {
		return robotStateMap;
	}

	public void setRobotStateMap(Map<Integer,RobotStateGwt> robotStateMap) {
		this.robotStateMap = robotStateMap;
	}
	
}
