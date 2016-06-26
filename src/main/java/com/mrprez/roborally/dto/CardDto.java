package com.mrprez.roborally.dto;

import com.mrprez.roborally.model.Card;

public class CardDto {
	private int rapidity;
	private int translation;
	private int rotation;
	
	
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
	
	public Card buildCard(){
		return new Card(rapidity, translation, rotation);
	}
	
}
