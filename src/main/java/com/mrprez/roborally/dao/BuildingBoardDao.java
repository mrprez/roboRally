package com.mrprez.roborally.dao;

import java.sql.SQLException;
import java.util.List;

import com.mrprez.roborally.model.board.BuildingBoard;

public interface BuildingBoardDao {
	
	void insertBuildingBoard(BuildingBoard buildingBoard) throws SQLException;
	
	List<BuildingBoard> loadBuildingBoardList(String username) throws SQLException;

	BuildingBoard loadBuildingBoard(Integer id) throws Exception;

	void updateBuildingBoard(BuildingBoard board) throws SQLException;

	List<BuildingBoard> listUserValidBuildingBoard(String username);

}
