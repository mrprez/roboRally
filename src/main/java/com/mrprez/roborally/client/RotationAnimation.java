package com.mrprez.roborally.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;
import com.google.web.bindery.event.shared.EventBus;
import com.mrprez.roborally.shared.RobotGwt;

public class RotationAnimation extends QueuedAnimation {
	
	private double angle;
	private Canvas robotCanvas;
	private double currentAngle = 0;
	private String imageName;
	
	
	public RotationAnimation(RobotGwt robotGwt, Canvas robotCanvas, int rotation, EventBus eventBus){
		super(eventBus);
		this.robotCanvas = robotCanvas;
		angle = Math.PI/2*rotation;
		imageName = robotGwt.getImageName();
	}

	@Override
	protected void onUpdate(double progress) {
		robotCanvas.getContext2d().clearRect(0, 0, robotCanvas.getCoordinateSpaceWidth(), robotCanvas.getCoordinateSpaceHeight());
		robotCanvas.getContext2d().translate(robotCanvas.getCoordinateSpaceWidth()/2, robotCanvas.getCoordinateSpaceHeight()/2);
		robotCanvas.getContext2d().rotate(progress*angle - currentAngle);
		currentAngle = progress*angle;
		robotCanvas.getContext2d().translate(-robotCanvas.getCoordinateSpaceWidth()/2, -robotCanvas.getCoordinateSpaceHeight()/2);
		Image img = new Image(imageName);
		ImageElement imageEl = ImageElement.as(img.getElement());
		robotCanvas.getContext2d().drawImage(imageEl, 25, 25);
	}


}
