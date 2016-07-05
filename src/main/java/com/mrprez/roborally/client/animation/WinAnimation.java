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
		label.addStyleName("winMessage");
		absolutePanel.setWidgetPosition(robotCanvas, 
				scrollPanel.getOffsetWidth()/2-robotCanvas.getOffsetWidth()/2+scrollPanel.getHorizontalScrollPosition(),
				scrollPanel.getOffsetHeight()/2-robotCanvas.getOffsetHeight()/2+scrollPanel.getVerticalScrollPosition()+30);
		absolutePanel.add(label,
				scrollPanel.getOffsetWidth()/2-label.getOffsetWidth()/2+scrollPanel.getHorizontalScrollPosition(),
				scrollPanel.getOffsetHeight()/2-label.getOffsetHeight()/2+scrollPanel.getVerticalScrollPosition()-30);
		label.addStyleName("redWinMessage");
		absolutePanel.setWidgetPosition(label,
				scrollPanel.getOffsetWidth()/2-label.getOffsetWidth()/2+scrollPanel.getHorizontalScrollPosition(),
				scrollPanel.getOffsetHeight()/2-label.getOffsetHeight()/2+scrollPanel.getVerticalScrollPosition()-30);
	}

	@Override
	public void update(double progress) {
		int intProgress = (int) (progress*10);
		if(intProgress%2==0){
			label.removeStyleName("redWinMessage");
			label.addStyleName("yellowWinMessage");
			robotCanvas.removeStyleName("redBackGround");
			robotCanvas.addStyleName("yellowBackGround");
		}else{
			label.removeStyleName("yellowWinMessage");
			label.addStyleName("redWinMessage");
			robotCanvas.removeStyleName("yellowBackGround");
			robotCanvas.addStyleName("redBackGround");
		}
	}
	
	@Override
	public double getTimeCoefficient(){
		return 4.0;
	}
	
	@Override
	public void onComplete(){
		robotCanvas.getCanvasElement().getStyle().setDisplay(Display.NONE);
		label.removeFromParent();
	}
	
}
