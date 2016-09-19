package com.mrprez.roborally.bs;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

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
	public BuildingBoard loadBuildingBoard(int boardId, String username) throws SecurityException, IllegalArgumentException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
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



}
