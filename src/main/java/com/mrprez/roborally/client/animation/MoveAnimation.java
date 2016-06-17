package com.mrprez.roborally.client.animation;

import com.google.gwt.canvas.client.Canvas;
import com.mrprez.roborally.client.panel.BoardPanel;
import com.mrprez.roborally.shared.MoveGwt;

public abstract class MoveAnimation {
	private static final String TRANSLATION = "TRANSLATION";
	private static final String ROTATION = "ROTATION";
	private static final String FAILED_TRANSLATION = "FAILED_TRANSLATION";
	private int robotNb;
	protected Canvas robotCanvas;
	

	public abstract void update(double progress);
	
	public abstract void onStart();
	
	public static MoveAnimation build(MoveGwt move, BoardPanel boardPanel){
		MoveAnimation moveAnimation;
		if(move.getType().equals(TRANSLATION)){
			moveAnimation = new TranslationAnimation(move.getArgs());
		} else if(move.getType().equals(ROTATION)){
			moveAnimation = new RotationAnimation(Integer.valueOf(move.getArgs()));
		} else if(move.getType().equals(FAILED_TRANSLATION)){
			moveAnimation = new FailedTranslationAnimation(move.getArgs());
		} else {
			throw new IllegalStateException("Invalide Move.type: "+move.getType());
		}
		moveAnimation.robotCanvas = boardPanel.getRobotCanvas(move.getRobotNb());
		return moveAnimation;
	}
	
	public double getTimeCoefficient(){
		return 1.0;
	}
	
	
	public int getRobotNb() {
		return robotNb;
	}

	public void setRobotNb(int robotNb) {
		this.robotNb = robotNb;
	}
	


}
