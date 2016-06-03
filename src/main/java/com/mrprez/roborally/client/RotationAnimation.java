package com.mrprez.roborally.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;
import com.google.web.bindery.event.shared.EventBus;
import com.mrprez.roborally.shared.RobotGwt;

public class RotationAnimation extends QueuedAnimation {
	
	private int rotation;
	private Canvas robotCanvas;
	private double currentAngle = 0;
	private RobotGwt robot;
	
	
	public RotationAnimation(RobotGwt robot, Canvas robotCanvas, int rotation, EventBus eventBus){
		super(eventBus);
		this.robotCanvas = robotCanvas;
		this.robot = robot;
		this.rotation = rotation;
	}

	@Override
	protected void onUpdate(double progress) {
		robotCanvas.getContext2d().clearRect(0, 0, robotCanvas.getCoordinateSpaceWidth(), robotCanvas.getCoordinateSpaceHeight());
		robotCanvas.getContext2d().translate(robotCanvas.getCoordinateSpaceWidth()/2, robotCanvas.getCoordinateSpaceHeight()/2);
		robotCanvas.getContext2d().rotate(progress*Math.PI/2*rotation - currentAngle);
		currentAngle = progress*Math.PI/2*rotation;
		robotCanvas.getContext2d().translate(-robotCanvas.getCoordinateSpaceWidth()/2, -robotCanvas.getCoordinateSpaceHeight()/2);
		Image img = new Image(robot.getImageName());
		ImageElement imageEl = ImageElement.as(img.getElement());
		robotCanvas.getContext2d().drawImage(imageEl, 25, 25);
	}
	
	@Override
	protected void onComplete(){
		super.onComplete();
		robot.setDirection(getEndDirection());
		Image img = new Image(robot.getImageName());
		ImageElement imageEl = ImageElement.as(img.getElement());
		robotCanvas.getContext2d().drawImage(imageEl, 25, 25);
	}
	
	
	private char getEndDirection(){
		int r = rotation;
		while(r<0){
			r = r+4;
		}
		char endDirection = robot.getDirection();
		for(; r>0; r--){
			switch (endDirection) {
			case 'H':
				endDirection = 'D';
				break;
			case 'D':
				endDirection = 'B';
				break;
			case 'B':
				endDirection = 'G';
				break;
			case 'G':
				endDirection = 'H';
				break;
			}
		}
		return endDirection;
	}


}
