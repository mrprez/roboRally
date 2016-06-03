package com.mrprez.roborally.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.web.bindery.event.shared.EventBus;
import com.mrprez.roborally.shared.RobotGwt;

public class TranslationAnimation extends QueuedAnimation {
	
	private int x;
	private int y;
	private int startLeft;
	private int startTop;
	private Canvas robotCanvas;
	private RobotGwt robot;
	private AbsolutePanel absolutePanel;
	
	
	public TranslationAnimation(RobotGwt robot, Canvas robotCanvas, int[] direction, EventBus eventBus){
		super(eventBus);
		this.robotCanvas = robotCanvas;
		this.robot = robot;
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
	
	@Override
	protected void onComplete(){
		super.onComplete();
		robot.setX(robot.getX()+x);
		robot.setY(robot.getY()+y);
	}

}
