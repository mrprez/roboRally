package com.mrprez.roborally.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.web.bindery.event.shared.EventBus;

public class TranslationAnimation extends QueuedAnimation {
	
	private int x;
	private int y;
	private int startLeft;
	private int startTop;
	private Canvas robotCanvas;
	private AbsolutePanel absolutePanel;
	
	
	public TranslationAnimation(Canvas robotCanvas, int[] direction, EventBus eventBus){
		super(eventBus);
		this.robotCanvas = robotCanvas;
		absolutePanel = (AbsolutePanel) robotCanvas.getParent();
		this.x = direction[0];
		this.y = direction[1];
	}

	@Override
	protected void onUpdate(double progress) {
		absolutePanel.setWidgetPosition(robotCanvas, (int)(startLeft+x*97*progress), (int)(startTop+y*97*progress));
	}

	@Override
	protected void onStart(){
		startLeft = absolutePanel.getWidgetLeft(robotCanvas);
		startTop = absolutePanel.getWidgetTop(robotCanvas);
	}
	

}
