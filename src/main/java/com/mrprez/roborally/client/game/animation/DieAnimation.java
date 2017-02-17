package com.mrprez.roborally.client.game.animation;

public class DieAnimation extends MoveAnimation {
	
	
	public DieAnimation(){
		super();
	}
	
	@Override
	public void onStart(){
		String imageUri = boardPanel.getRobotImageUri(robotNb);
		imageUri = imageUri.replace("HT.gif", ".gif").replace(".gif", "M.gif");
		boardPanel.setRobotImageUri(robotNb, imageUri);
	}

	public double getTimeCoefficient(){
		return 0.1;
	}
	
	@Override
	public void update(double progress) {}
	
	

}
