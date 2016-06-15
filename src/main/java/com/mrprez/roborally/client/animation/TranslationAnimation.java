package com.mrprez.roborally.client.animation;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class TranslationAnimation extends MoveAnimation {
	
	private int x;
	private int y;
	private int startLeft;
	private int startTop;
	private Canvas robotCanvas;
	private AbsolutePanel absolutePanel;
	
	
	public TranslationAnimation(int[] direction){
		this.x = direction[0];
		this.y = direction[1];
	}
	
	@Override
	public void init(Canvas robotCanvas) {
		this.robotCanvas = robotCanvas;
		absolutePanel = (AbsolutePanel) robotCanvas.getParent();
		startLeft = absolutePanel.getWidgetLeft(robotCanvas);
		startTop = absolutePanel.getWidgetTop(robotCanvas);
	}

	@Override
	public void update(double progress) {
		absolutePanel.setWidgetPosition(robotCanvas, (int)(startLeft+x*97*progress), (int)(startTop+y*97*progress));
	}



	

}
