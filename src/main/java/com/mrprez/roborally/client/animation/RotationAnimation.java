package com.mrprez.roborally.client.animation;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

public class RotationAnimation extends MoveAnimation {
	
	private int rotation;
	private double currentAngle = 0;
	private ImageElement imageEl;
	
	
	public RotationAnimation(int rotation){
		super();
		this.rotation = rotation;
	}
	
	@Override
	public void onStart(){
		Image img = new Image(boardPanel.getRobotImageUri(robotNb));
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
	
	
	public void onComplete(){
		int direction = Integer.valueOf(robotCanvas.getCanvasElement().getAttribute("direction")) + rotation;
		direction = (direction + 4) % 4;
		robotCanvas.getCanvasElement().setAttribute("direction", String.valueOf(direction));
	}
	

	


}
