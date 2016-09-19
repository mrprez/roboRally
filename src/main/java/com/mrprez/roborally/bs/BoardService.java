package com.mrprez.roborally.bs;

import com.mrprez.roborally.model.board.BuildingBoard;

public interface BoardService {

	BuildingBoard createNewBoard(String name, String username, int sizeX, int sizeY) throws Exception;

	BuildingBoard loadBuildingBoard(int boardId, String username) throws Exception;
	
}
