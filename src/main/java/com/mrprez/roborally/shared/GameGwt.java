package com.mrprez.roborally.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GameGwt implements IsSerializable{
	private int id;
	private String name;
	private String state;
	private String ownername;
	private GameBoardGwt board;
	private List<RobotGwt> robotList;
	private List<RoundGwt> history;
	private boolean userOwner = false;
	
	
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
	public List<RobotGwt> getRobotList() {
		return robotList;
	}
	public void setRobotList(List<RobotGwt> robotList) {
		this.robotList = robotList;
	}
	public List<RoundGwt> getHistory() {
		return history;
	}
	public void setHistory(List<RoundGwt> history) {
		this.history = history;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getOwnername() {
		return ownername;
	}
	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}
	public boolean isUserOwner() {
		return userOwner;
	}
	public void setUserOwner(boolean userOwner) {
		this.userOwner = userOwner;
	}
	
}
