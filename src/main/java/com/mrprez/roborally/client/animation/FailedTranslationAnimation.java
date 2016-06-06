package com.mrprez.roborally.client.animation;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class FailedTranslationAnimation implements UnitAnimation {
	private int x;
	private int y;
	private int startLeft;
	private int startTop;
	private Canvas robotCanvas;
	private AbsolutePanel absolutePanel;
	
	
	public FailedTranslationAnimation(Canvas robotCanvas, int[] direction){
		this.robotCanvas = robotCanvas;
		absolutePanel = (AbsolutePanel) robotCanvas.getParent();
		this.x = direction[0];
		this.y = direction[1];
	}
	
	@Override
	public void update(double progress) {
		if(progress < 0.5){
			absolutePanel.setWidgetPosition(robotCanvas, (int)(startLeft+x*60*progress), (int)(startTop+y*60*progress));
		}else{
			absolutePanel.setWidgetPosition(robotCanvas, (int)(startLeft+x*60*(1-progress)), (int)(startTop+y*60*(1-progress)));
		}
	}

	@Override
	public void onStart() {
		startLeft = absolutePanel.getWidgetLeft(robotCanvas);
		startTop = absolutePanel.getWidgetTop(robotCanvas);
	}

	@Override
	public void onComplete() {}

}
