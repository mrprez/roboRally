package com.mrprez.roborally.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import com.mrprez.roborally.dto.SquareDto;
import com.mrprez.roborally.model.board.BuildingBoard;

public class BuildingBoardDaoImpl extends AbstractDao implements BuildingBoardDao {

	@Override
	public void insertBuildingBoard(BuildingBoard buildingBoard) throws SQLException {
		getSession().insert("insertBuildingBoard", buildingBoard);
		for(int x=0; x<buildingBoard.getSizeX(); x++){
			for(int y=0; y<buildingBoard.getSizeY(); y++){
				getSession().insert("insertSquare", new SquareDto(buildingBoard.getSquare(x, y)));
			}
		}
		getSession().insert("insertBuildingBoardOwner", buildingBoard);
	}

	@Override
	public List<BuildingBoard> loadBuildingBoardList(String username) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuildingBoard loadBuildingBoard(Integer id)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException,
			SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateBuildingBoard(BuildingBoard board) throws SQLException {
		// TODO Auto-generated method stub

	}

}
