package com.mrprez.roborally.bs;

import java.util.List;

import com.mrprez.roborally.model.board.BuildingBoard;

public interface BoardService {
	
	
	
	BuildingBoard createNewBuildingBoard(String name, int sizeX, int sizeY) throws Exception;
	
	List<BuildingBoard> getBuildingBoardList(String userName) throws Exception;

	BuildingBoard getBuildingBoard(Integer id) throws Exception;

	void updateBuildingBoard(BuildingBoard board) throws Exception;

}
