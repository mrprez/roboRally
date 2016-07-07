package com.mrprez.roborally.client.animation;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class AppearAnimation extends MoveAnimation {
	private int x;
	private int y;
	
	
	public AppearAnimation(String args){
		super();
		x = Integer.parseInt(args.split(",")[0]);
		y = Integer.parseInt(args.split(",")[1]);
	}
	
	@Override
	public void onStart(){
		robotCanvas.getCanvasElement().getStyle().setDisplay(Display.NONE);
		robotCanvas.getCanvasElement().getStyle().setOpacity(0.5);
		AbsolutePanel absolutePanel = (AbsolutePanel) robotCanvas.getParent();
		absolutePanel.setWidgetPosition(robotCanvas, 97*x, 97*y);
	}

	@Override
	public void update(double progress) {
		int intProgress = (int) (10*progress);
		if(intProgress%2==0){
			robotCanvas.getCanvasElement().getStyle().setDisplay(Display.INITIAL);
		}else{
			robotCanvas.getCanvasElement().getStyle().setDisplay(Display.NONE);
		}
	}
	
	
	public void onComplete(){
		robotCanvas.getCanvasElement().getStyle().setDisplay(Display.INITIAL);
	}
	

	


}