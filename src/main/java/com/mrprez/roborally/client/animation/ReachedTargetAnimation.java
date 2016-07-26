package com.mrprez.roborally.client.animation;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.ui.Image;

public class ReachedTargetAnimation extends MoveAnimation {
	
	private static Image image = new Image("img/RedTarget.png");
	private static ImageElement imageEl = ImageElement.as(image.getElement());
	
	
	public ReachedTargetAnimation(){
		super();
	}
	
	@Override
	public void onStart(){
		boardPanel.add(image, boardPanel.getWidgetLeft(robotCanvas), boardPanel.getWidgetTop(robotCanvas));
		imageEl.getStyle().setDisplay(Display.BLOCK);
	}

	@Override
	public void update(double progress) {
		int intProgress = (int)(progress*6);
		if(intProgress % 2 == 0){
			imageEl.getStyle().setDisplay(Display.BLOCK);
		}else{
			imageEl.getStyle().setDisplay(Display.NONE);
		}
	}
	
	
	public void onComplete(){
		imageEl.getStyle().setDisplay(Display.NONE);
	}
	

	


}
