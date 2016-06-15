package com.mrprez.roborally.animation;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

public class RotationAnimation extends MoveAnimation {
	
	private int rotation;
	private Canvas robotCanvas;
	private double currentAngle = 0;
	private ImageElement imageEl;
	
	
	public RotationAnimation(int rotation){
		this.rotation = rotation;
	}
	
	@Override
	public void init(Canvas robotCanvas){
		this.robotCanvas = robotCanvas;
		Image img = new Image(robotCanvas.getCanvasElement().getAttribute("imageName"));
		imageEl = ImageElement.as(img.getElement());
	}

	@Override
	public void update(double progress) {
		robotCanvas.getContext2d().clearRect(0, 0, robotCanvas.getCoordinateSpaceWidth(), robotCanvas.getCoordinateSpaceHeight());
		robotCanvas.getContext2d().translate(robotCanvas.getCoordinateSpaceWidth()/2, robotCanvas.getCoordinateSpaceHeight()/2);
		robotCanvas.getContext2d().rotate(progress*Math.PI/2*rotation - currentAngle);
		currentAngle = progress*Math.PI/2*rotation;
		robotCanvas.getContext2d().translate(-robotCanvas.getCoordinateSpaceWidth()/2, -robotCanvas.getCoordinateSpaceHeight()/2);
		robotCanvas.getContext2d().drawImage(imageEl, 25, 25);
	}
	
	

	


}
