package com.mrprez.roborally.shared;

import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GameGwt implements IsSerializable{
	private int id;
	private String name;
	private GameBoardGwt board;
	private Set<RobotGwt> robotList;
	private List<RoundGwt> history;
		
	
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
	public Set<RobotGwt> getRobotList() {
		return robotList;
	}
	public void setRobotList(Set<RobotGwt> robotList) {
		this.robotList = robotList;
	}
	public List<RoundGwt> getHistory() {
		return history;
	}
	public void setHistory(List<RoundGwt> history) {
		this.history = history;
	}
	
}
