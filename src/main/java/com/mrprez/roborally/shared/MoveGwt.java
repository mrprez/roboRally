package com.mrprez.roborally.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MoveGwt implements IsSerializable {
	private int robotNb;
	private int rotation;
	private int[] translation;
	private boolean success;
	
	
	public int getRobotNb() {
		return robotNb;
	}
	public void setRobotNb(int robotNb) {
		this.robotNb = robotNb;
	}
	public int getRotation() {
		return rotation;
	}
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public int[] getTranslation() {
		return translation;
	}
	public void setTranslation(int[] translation) {
		this.translation = translation;
	}
	
}
