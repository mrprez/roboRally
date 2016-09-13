package com.mrprez.roborally.bs;

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
	

	public BuildingBoardDao getBuildingBoardDao() {
		return buildingBoardDao;
	}

	public void setBuildingBoardDao(BuildingBoardDao buildingBoardDao) {
		this.buildingBoardDao = buildingBoardDao;
	}

}
