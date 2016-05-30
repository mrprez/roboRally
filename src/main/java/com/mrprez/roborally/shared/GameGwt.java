package com.mrprez.roborally.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GameGwt implements IsSerializable{
	private int id;
	private String name;
	private GameBoardGwt board;
		
	
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
	public GameBoardGwt getBoard() {
		return board;
	}
	public void setBoard(GameBoardGwt board) {
		this.board = board;
	}
	
}
