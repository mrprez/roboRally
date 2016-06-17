package com.mrprez.roborally.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MoveGwt implements IsSerializable {
	private String type;
	private String args;
	private int robotNb;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getArgs() {
		return args;
	}
	public void setArgs(String args) {
		this.args = args;
	}
	public int getRobotNb() {
		return robotNb;
	}
	public void setRobotNb(int robotNb) {
		this.robotNb = robotNb;
	}
	
	

}
