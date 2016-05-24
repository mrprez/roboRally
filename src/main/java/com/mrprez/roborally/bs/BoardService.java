package com.mrprez.roborally.bs;

import java.util.List;

import com.mrprez.roborally.model.board.GameBoard;

public interface BoardService {
	
	
	List<GameBoard> getUserGameBoards(String username);

}
