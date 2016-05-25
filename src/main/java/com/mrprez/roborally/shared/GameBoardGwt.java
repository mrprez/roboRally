package com.mrprez.roborally.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GameBoardGwt implements IsSerializable{
	private int id;
	private String name;
	private int sizeX;
	private int sizeY;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSizeX() {
		return sizeX;
	}
	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}
	public int getSizeY() {
		return sizeY;
	}
	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}
}
