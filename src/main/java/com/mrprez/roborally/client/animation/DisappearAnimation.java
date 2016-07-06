package com.mrprez.roborally.client.animation;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.ui.Image;

public class DisappearAnimation extends MoveAnimation {
	private ImageElement imageEl;
	private double lastRatio = 1;
	
	
	public DisappearAnimation(){
		super();
	}
	
	@Override
	public void onStart(){
		Image img = new Image(robotCanvas.getCanvasElement().getAttribute("imageName"));
		imageEl = ImageElement.as(img.getElement());
	}

	@Override
	public void update(double progress) {
		double ratio = 1 - progress;
		if(ratio>0.1){
			robotCanvas.getContext2d().clearRect(0, 0, robotCanvas.getCoordinateSpaceWidth(), robotCanvas.getCoordinateSpaceHeight());
			robotCanvas.getContext2d().scale(ratio/lastRatio, ratio/lastRatio);
			robotCanvas.getContext2d().drawImage(imageEl, 25, 25);
			lastRatio = ratio;
		}
	}
	
	
	public void onComplete(){
		robotCanvas.getCanvasElement().getStyle().setDisplay(Display.NONE);
		robotCanvas.getContext2d().clearRect(0, 0, robotCanvas.getCoordinateSpaceWidth(), robotCanvas.getCoordinateSpaceHeight());
		robotCanvas.getContext2d().scale(1/lastRatio, 1/lastRatio);
		robotCanvas.getContext2d().drawImage(imageEl, 25, 25);
	}
	

	


}
