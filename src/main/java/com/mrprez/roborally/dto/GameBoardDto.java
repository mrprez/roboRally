package com.mrprez.roborally.dto;

import java.util.List;

public class GameBoardDto {
	private String name;
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
	
	
	
	

}
