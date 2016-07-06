package com.mrprez.roborally.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CardGwt implements IsSerializable{
	private int rapidity;
	private int translation;
	private int rotation;
	private boolean blocked = false;
	
	
	public String getImageName(){
		StringBuilder imageName = new StringBuilder("img/card/");
		if(translation!=0){
			imageName.append("translation"+translation);
		}else{
			imageName.append("rotation"+rotation);
		}
		if(blocked){
			imageName.append("-blocked");
		}
		imageName.append(".gif");
		return imageName.toString();
	}
	
	public int getRapidity() {
		return rapidity;
	}
	public void setRapidity(int rapidity) {
		this.rapidity = rapidity;
	}
	public int getTranslation() {
		return translation;
	}
	public void setTranslation(int translation) {
		this.translation = translation;
	}
	public int getRotation() {
		return rotation;
	}
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
}
