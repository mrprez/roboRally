package com.mrprez.roborally.dao;

import java.util.List;

import com.mrprez.roborally.model.board.GameBoard;

public interface GameBoardDao {

	List<GameBoard> selectGameBoardList(String username);
	
	
	

}
