package com.mrprez.roborally.model.history;

public class Round {
	
	private int number;
	private Stage[] stageList = new Stage[5];
	
	
	public Round(int number) {
		super();
		this.number = number;
	}

	public void setStage(int stageNb, Stage stage){
		stageList[stageNb] = stage;
	}

	public int getNumber() {
		return number;
	}
	
	public Stage[] getStageList(){
		return stageList;
	}

}
