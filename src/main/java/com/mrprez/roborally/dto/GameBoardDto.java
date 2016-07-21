package com.mrprez.roborally.dto;

import java.util.List;

import com.mrprez.roborally.model.Game.GameState;

public class GameBoardDto {
	private String name;
	private GameState gameState;
	private int boardId;
	private int sizeX;
	private int sizeY;
	
	private List<TargetDto> targetList;
	
	
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
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public List<TargetDto> getTargetList() {
		return targetList;
	}
	public void setTargetList(List<TargetDto> targetList) {
		this.targetList = targetList;
	}
	public GameState getGameState() {
		return gameState;
	}
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	

}
