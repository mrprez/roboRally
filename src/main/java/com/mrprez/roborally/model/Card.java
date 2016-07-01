package com.mrprez.roborally.model;

public class Card {
	private int rapidity;
	private int translation;
	private int rotation;
	
	
	public Card(Integer initiative, Integer translation, Integer rotation) {
		super();
		this.rapidity = initiative;
		this.translation = translation;
		this.rotation = rotation;
	}

	public int getRapidity() {
		return rapidity;
	}
	public int getTranslation() {
		return translation;
	}
	public int getRotation() {
		return rotation;
	}
	@Override
	public String toString(){
		if(rotation!=0){
			return "Rotation "+rotation+" (rapidity "+rapidity+")";
		}
		return "Translation "+translation+" (rapidity "+rapidity+")";
	}

}
