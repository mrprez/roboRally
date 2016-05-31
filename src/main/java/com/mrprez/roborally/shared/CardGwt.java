package com.mrprez.roborally.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CardGwt implements IsSerializable{
	private int rapidity;
	private int translation;
	private int rotation;
	
	
	public String getImageName(){
		if(translation!=0){
			return "img/card/translation"+translation+".gif";
		}else{
			return "img/card/rotation"+rotation+".gif";
		}
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
}
