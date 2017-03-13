package com.mrprez.roborally.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SquareGwt implements IsSerializable {
	private String imageName;
	private String type;
	private String args;
	private Integer targetNumber;
	private boolean wallUp;
	private boolean wallDown;
	private boolean wallLeft;
	private boolean wallRight;

	
	public String getImageUrl() {
		return "img/square/"+imageName+".png";
	}
	
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Integer getTargetNumber() {
		return targetNumber;
	}

	public void setTargetNumber(Integer targetNumber) {
		this.targetNumber = targetNumber;
	}
	
	public String getTargetImgName(){
		if(targetNumber==null){
			return null;
		}
		if(targetNumber==0){
			return "img/Start.png";
		}
		return "img/Target"+targetNumber+".png";
	}

	public boolean isWallUp() {
		return wallUp;
	}

	public void setWallUp(boolean wallUp) {
		this.wallUp = wallUp;
	}

	public boolean isWallDown() {
		return wallDown;
	}

	public void setWallDown(boolean wallDown) {
		this.wallDown = wallDown;
	}

	public boolean isWallLeft() {
		return wallLeft;
	}

	public void setWallLeft(boolean wallLeft) {
		this.wallLeft = wallLeft;
	}

	public boolean isWallRight() {
		return wallRight;
	}

	public void setWallRight(boolean wallRight) {
		this.wallRight = wallRight;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
