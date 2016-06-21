package com.mrprez.roborally.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SquareGwt implements IsSerializable {
	private String imageName;
	private Integer targetNumber;

	
	public String getImageName() {
		return "img/"+imageName+".png";
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

}
