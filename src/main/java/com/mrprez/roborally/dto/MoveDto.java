package com.mrprez.roborally.dto;

public class MoveDto {
	
	private int robotNb;
	private Integer translation;
	private Integer rotation;
	
	
	public int getRobotNb() {
		return robotNb;
	}
	public void setRobotNb(int robotNb) {
		this.robotNb = robotNb;
	}
	public Integer getTranslation() {
		return translation;
	}
	public void setTranslation(Integer translation) {
		this.translation = translation;
	}
	public Integer getRotation() {
		return rotation;
	}
	public void setRotation(Integer rotation) {
		this.rotation = rotation;
	}
	
}
