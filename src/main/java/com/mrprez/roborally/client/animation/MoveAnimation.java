package com.mrprez.roborally.client.animation;

import com.google.gwt.canvas.client.Canvas;
import com.mrprez.roborally.client.panel.BoardPanel;
import com.mrprez.roborally.shared.MoveGwt;

public abstract class MoveAnimation {
	private static final String TRANSLATION = "TRANSLATION";
	private static final String ROTATION = "ROTATION";
	private static final String FAILED_TRANSLATION = "FAILED_TRANSLATION";
	private static final String UNGHOST = "UNGHOST";
	private static final String REACHED_TARGET = "REACHED_TARGET";
	private static final String WIN = "WIN";
	private static final String LASER = "LASER";
	private static final String DISAPPEAR = "DISAPPEAR";
	private static final String APPEAR = "APPEAR";
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
		} else if(move.getType().equals(UNGHOST)){
			moveAnimation = new UnghostAnimation();
		} else if(move.getType().equals(REACHED_TARGET)){
			moveAnimation = new ReachedTargetAnimation();
		} else if(move.getType().equals(WIN)){
			moveAnimation = new WinAnimation();
		} else if(move.getType().equals(LASER)){
			moveAnimation = new LaserAnimation(move.getArgs());
		} else if(move.getType().equals(DISAPPEAR)){
			moveAnimation = new DisappearAnimation();
		} else if(move.getType().equals(APPEAR)){
			moveAnimation = new AppearAnimation(move.getArgs());
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

	public void onComplete() {}
	


}
