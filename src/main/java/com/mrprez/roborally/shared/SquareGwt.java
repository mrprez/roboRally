package com.mrprez.roborally.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SquareGwt implements IsSerializable {
	private String imageName;

	
	public String getImageName() {
		return "img/"+imageName+".png";
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

}
