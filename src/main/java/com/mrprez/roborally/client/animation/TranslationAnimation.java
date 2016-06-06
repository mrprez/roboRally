package com.mrprez.roborally.client.animation;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class TranslationAnimation implements UnitAnimation {
	
	private int x;
	private int y;
	private int startLeft;
	private int startTop;
	private Canvas robotCanvas;
	private AbsolutePanel absolutePanel;
	
	
	public TranslationAnimation(Canvas robotCanvas, int[] direction){
		this.robotCanvas = robotCanvas;
		absolutePanel = (AbsolutePanel) robotCanvas.getParent();
		this.x = direction[0];
		this.y = direction[1];
	}

	@Override
	public void update(double progress) {
		absolutePanel.setWidgetPosition(robotCanvas, (int)(startLeft+x*97*progress), (int)(startTop+y*97*progress));
	}

	@Override
	public void onStart(){
		startLeft = absolutePanel.getWidgetLeft(robotCanvas);
		startTop = absolutePanel.getWidgetTop(robotCanvas);
	}
	
	@Override
	public void onComplete(){}

}
