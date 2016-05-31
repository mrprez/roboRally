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

	public int getInitiative() {
		return rapidity;
	}
	public int getTranslation() {
		return translation;
	}
	public int getRotation() {
		return rotation;
	}

}
