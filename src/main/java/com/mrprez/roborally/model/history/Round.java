package com.mrprez.roborally.model.history;

import com.mrprez.roborally.model.Game;

public class Round {
	
	private int number;
	private Stage[] stageList;
	
	
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

}
