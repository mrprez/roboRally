package com.mrprez.roborally.client.animation;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

public class RotationAnimation implements UnitAnimation {
	
	private int rotation;
	private Canvas robotCanvas;
	private double currentAngle = 0;
	private ImageElement imageEl;
	
	
	public RotationAnimation(String imageName, Canvas robotCanvas, int rotation){
		this.robotCanvas = robotCanvas;
		Image img = new Image(imageName);
		imageEl = ImageElement.as(img.getElement());
		this.rotation = rotation;
	}
	
	@Override
	public void onStart() {}

	@Override
	public void update(double progress) {
		robotCanvas.getContext2d().clearRect(0, 0, robotCanvas.getCoordinateSpaceWidth(), robotCanvas.getCoordinateSpaceHeight());
		robotCanvas.getContext2d().translate(robotCanvas.getCoordinateSpaceWidth()/2, robotCanvas.getCoordinateSpaceHeight()/2);
		robotCanvas.getContext2d().rotate(progress*Math.PI/2*rotation - currentAngle);
		currentAngle = progress*Math.PI/2*rotation;
		robotCanvas.getContext2d().translate(-robotCanvas.getCoordinateSpaceWidth()/2, -robotCanvas.getCoordinateSpaceHeight()/2);
		robotCanvas.getContext2d().drawImage(imageEl, 25, 25);
	}

	@Override
	public void onComplete() {}
	
	

	


}
