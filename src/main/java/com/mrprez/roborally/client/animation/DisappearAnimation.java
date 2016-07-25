package com.mrprez.roborally.client.animation;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.ui.Image;

public class DisappearAnimation extends MoveAnimation {
	private ImageElement imageEl;
	
	
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
			robotCanvas.getContext2d().scale(ratio, ratio);
			robotCanvas.getContext2d().drawImage(imageEl, (25+(97-2*25)/2*progress)/ratio, (25+(97-2*25)/2*progress)/ratio);
			robotCanvas.getContext2d().scale(1/ratio, 1/ratio);
		}
	}
	
	
	public void onComplete(){
		robotCanvas.getCanvasElement().getStyle().setDisplay(Display.NONE);
		robotCanvas.getContext2d().clearRect(0, 0, robotCanvas.getCoordinateSpaceWidth(), robotCanvas.getCoordinateSpaceHeight());
		robotCanvas.getContext2d().drawImage(imageEl, 25, 25);
	}
	

}
