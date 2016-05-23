package com.mrprez.roborally.dao;

import com.mrprez.roborally.model.board.GameBoard;

public class GameBoardDaoImpl extends AbstractDao implements GameBoardDao {
	
	
	public GameBoard loadGameBoard(Integer id){
		return getSession().selectOne("loadGameBoard", id);
	}

}
