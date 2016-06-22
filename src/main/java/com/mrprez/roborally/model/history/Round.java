package com.mrprez.roborally.model.history;

import java.util.HashMap;
import java.util.Map;

import com.mrprez.roborally.model.Game;
import com.mrprez.roborally.model.Robot;
import com.mrprez.roborally.model.RobotState;

public class Round {
	
	private int number;
	private Stage[] stageList;
	private Map<Robot, RobotState> stateMap = new HashMap<Robot, RobotState>();
	
	
	public Round(int number) {
		super();
		this.number = number;
		stageList = new Stage[Game.STAGE_NB];
		for(int i=0; i<Game.STAGE_NB; i++){
			stageList[i] = new Stage(i);
		}
	}

	public Stage getStage(int stageNb){
		return stageList[stageNb];
	}

	public int getNumber() {
		return number;
	}
	
	public Stage[] getStageList(){
		return stageList;
	}

	public Map<Robot, RobotState> getStateMap() {
		return stateMap;
	}
	
	public void setState(Robot robot, RobotState state){
		stateMap.put(robot, state);
	}

}
