package com.mrprez.roborally.model;

public class Card {
	private int initiative;
	private int translation;
	private int rotation;
	
	
	public Card(int initiative, int translation, int rotation) {
		super();
		this.initiative = initiative;
		this.translation = translation;
		this.rotation = rotation;
	}

	public int getInitiative() {
		return initiative;
	}
	public int getTranslation() {
		return translation;
	}
	public int getRotation() {
		return rotation;
	}
	public String toString(){
		if(translation>0){
			return "T"+translation;
		}
		if(translation<0){
			return "T"+translation;
		}
		if(rotation>0){
			return "R+"+rotation;
		}
		if(rotation<0){
			return "R"+rotation;
		}
		return "   ";
	}

}
