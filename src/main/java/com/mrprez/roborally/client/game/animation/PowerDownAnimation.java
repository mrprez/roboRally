package com.mrprez.roborally.client.game.animation;

public class PowerDownAnimation extends MoveAnimation {
	private String powerDownState;
	
	public PowerDownAnimation(String powerDownState){
		super();
		this.powerDownState = powerDownState;
	}
	
	@Override
	public void onStart(){
		String imageUri = boardPanel.getRobotImageUri(robotNb);
		if(powerDownState.equals("ONGOING")){
			imageUri = imageUri.replace(".gif", "HT.gif");
		}else{
			imageUri = imageUri.replace("HT.gif", ".gif");
		}
		boardPanel.setRobotImageUri(robotNb, imageUri);
	}

	public double getTimeCoefficient(){
		return 0.1;
	}
	
	@Override
	public void update(double progress) {}
	
	

}
