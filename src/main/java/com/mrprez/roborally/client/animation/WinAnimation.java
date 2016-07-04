package com.mrprez.roborally.client.animation;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;

public class WinAnimation extends MoveAnimation {
	private AbsolutePanel absolutePanel;
	private ScrollPanel scrollPanel;
	private Label label;
	
	
	public WinAnimation(){
		super();
	}
	
	@Override
	public void onStart(){
		absolutePanel = (AbsolutePanel) robotCanvas.getParent();
		scrollPanel = (ScrollPanel) absolutePanel.getParent();
		label = new Label("A Robot has reached the last Target! AMAZING!");
		absolutePanel.setWidgetPosition(robotCanvas, 
				absolutePanel.getOffsetWidth()/2-robotCanvas.getOffsetWidth()/2+scrollPanel.getHorizontalScrollPosition(),
				absolutePanel.getOffsetHeight()/2-robotCanvas.getOffsetHeight()/2+scrollPanel.getVerticalScrollPosition()+15);
		absolutePanel.add(label,
				absolutePanel.getOffsetWidth()/2-robotCanvas.getOffsetWidth()/2+scrollPanel.getHorizontalScrollPosition(),
				absolutePanel.getOffsetHeight()/2-robotCanvas.getOffsetHeight()/2+scrollPanel.getVerticalScrollPosition()-10);
	}

	@Override
	public void update(double progress) {
		absolutePanel.add(label,
				absolutePanel.getOffsetWidth()/2-robotCanvas.getOffsetWidth()/2+scrollPanel.getHorizontalScrollPosition(),
				absolutePanel.getOffsetHeight()/2-robotCanvas.getOffsetHeight()/2+scrollPanel.getVerticalScrollPosition()-10);
	}
	
	
	public void onComplete(){
		robotCanvas.getCanvasElement().getStyle().setDisplay(Display.NONE);
		label.removeFromParent();
	}
	

	


}
