package com.mrprez.roborally.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import com.mrprez.roborally.model.board.BuildingBoard;

public interface BuildingBoardDao {
	
	void insertBuildingBoard(BuildingBoard buildingBoard) throws SQLException;
	
	List<BuildingBoard> loadBuildingBoardList(String username) throws SQLException;

	BuildingBoard loadBuildingBoard(Integer id) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException;

	void updateBuildingBoard(BuildingBoard board) throws SQLException;

}
