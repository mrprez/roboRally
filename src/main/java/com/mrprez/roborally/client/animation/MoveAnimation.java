package com.mrprez.roborally.client.animation;

import com.google.gwt.canvas.client.Canvas;

public abstract class MoveAnimation {
	private int robotNb;
	

	public abstract void update(double progress);
	
	public abstract void init(Canvas robotCanvas);

	public int getRobotNb() {
		return robotNb;
	}

	public void setRobotNb(int robotNb) {
		this.robotNb = robotNb;
	}


}
