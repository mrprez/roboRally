package com.mrprez.roborally.bs;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.mrprez.roborally.dao.BuildingBoardDao;
import com.mrprez.roborally.model.Direction;
import com.mrprez.roborally.model.Square;
import com.mrprez.roborally.model.board.BuildingBoard;
import com.mrprez.roborally.model.square.ConveyorBelt;
import com.mrprez.roborally.model.square.EmptySquare;
import com.mrprez.roborally.model.square.HoleSquare;
import com.mrprez.roborally.model.square.RotationSquare;

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

	@Override
	public List<Square> getAvailableSquareList() {
		return Arrays.asList(
				new EmptySquare(0, 0, null),
				new HoleSquare(0, 0, null), 
				new RotationSquare(0, 0, null, 1),
				new RotationSquare(0, 0, null, -1),
				new ConveyorBelt(0, 0, null, Direction.UP),
				new ConveyorBelt(0, 0, null, Direction.RIGHT),
				new ConveyorBelt(0, 0, null, Direction.DOWN),
				new ConveyorBelt(0, 0, null, Direction.LEFT));
	}

	@Override
	public void updateBuildingBoard(BuildingBoard buildingBoard) throws Exception {
		BuildingBoard existingBuildingBoard = buildingBoardDao.loadBuildingBoard(buildingBoard.getId());
		if( ! existingBuildingBoard.getUsername().equals(buildingBoard.getUsername())){
			throw new IllegalAccessError("User "+buildingBoard.getUsername()+" cannot access to building board "+buildingBoard.getId());
		}
		buildingBoardDao.updateBuildingBoard(existingBuildingBoard);
	}



}
