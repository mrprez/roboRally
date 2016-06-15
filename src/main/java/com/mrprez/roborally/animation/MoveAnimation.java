package com.mrprez.roborally.animation;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.user.client.rpc.IsSerializable;

public abstract class MoveAnimation implements IsSerializable {
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
