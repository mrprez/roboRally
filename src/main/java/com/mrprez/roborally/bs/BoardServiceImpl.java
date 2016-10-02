package com.mrprez.roborally.bs;

import java.sql.SQLException;
import java.util.List;

import com.mrprez.roborally.dao.BuildingBoardDao;
import com.mrprez.roborally.model.board.BuildingBoard;

public class BoardServiceImpl implements BoardService {

	private BuildingBoardDao buildingBoardDao;
	
	@Override
	public BuildingBoard createNewBoard(String name, String username, int sizeX, int sizeY) throws SQLException {
		BuildingBoard buildingBoard = new BuildingBoard(name, username, sizeX, sizeY);
		buildingBoardDao.insertBuildingBoard(buildingBoard);
		return buildingBoard;
	}
	
	@Override
	public BuildingBoard loadBuildingBoard(int boardId, String username) throws Exception {
		BuildingBoard buildingBoard = buildingBoardDao.loadBuildingBoard(boardId);
		if( ! buildingBoard.getUsername().equals(username)){
			throw new IllegalAccessError("User "+username+" cannot access to building board "+boardId);
		}
		return buildingBoard;
	}
	

	public BuildingBoardDao getBuildingBoardDao() {
		return buildingBoardDao;
	}

	public void setBuildingBoardDao(BuildingBoardDao buildingBoardDao) {
		this.buildingBoardDao = buildingBoardDao;
	}

	@Override
	public List<BuildingBoard> getUserBuildingBoards(String username) throws SQLException {
		return buildingBoardDao.loadBuildingBoardList(username);
	}



}
