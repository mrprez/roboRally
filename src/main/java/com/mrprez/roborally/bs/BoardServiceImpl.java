package com.mrprez.roborally.bs;

import java.util.List;

import com.mrprez.roborally.dao.BuildingBoardDao;
import com.mrprez.roborally.model.board.BuildingBoard;

public class BoardServiceImpl implements BoardService {
	
	private BuildingBoardDao boardDao;
	

	@Override
	public BuildingBoard createNewBuildingBoard(String name, int sizeX, int sizeY) throws Exception {
		BuildingBoard buildingBoard = new BuildingBoard(name, sizeX, sizeY);
		
		boardDao.createBuildingBoard(buildingBoard);
		
		return buildingBoard;
	}
	
	@Override
	public List<BuildingBoard> getBuildingBoardList(String username) throws Exception {
		return boardDao.loadBuildingBoardList(username);
	}


	public BuildingBoardDao getBoardDao() {
		return boardDao;
	}


	public void setBoardDao(BuildingBoardDao boardDao) {
		this.boardDao = boardDao;
	}

	@Override
	public BuildingBoard getBuildingBoard(Integer id) throws Exception {
		return boardDao.loadBuildingBoard(id);
	}

	@Override
	public void updateBuildingBoard(BuildingBoard board) throws Exception {
		boardDao.updateBuildingBoard(board);
	}


	
	
	

}
