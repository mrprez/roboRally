package com.mrprez.roborally.bs;

import java.util.List;

import com.mrprez.roborally.model.Square;
import com.mrprez.roborally.model.board.BuildingBoard;

public interface BoardService {

	BuildingBoard createNewBoard(String name, String username, int sizeX, int sizeY) throws Exception;

	BuildingBoard loadBuildingBoard(int boardId, String username) throws Exception;

	List<BuildingBoard> getUserBuildingBoards(String username) throws Exception;

	List<Square> getAvailableSquareList();
	
}
