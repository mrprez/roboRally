package com.mrprez.roborally.dao;

import java.util.List;

import com.mrprez.roborally.model.board.GameBoard;

public class GameBoardDaoImpl extends AbstractDao implements GameBoardDao {
	
	
	public GameBoard loadGameBoard(Integer id){
		return getSession().selectOne("loadGameBoard", id);
	}

	@Override
	public List<GameBoard> selectGameBoardList(String username) {
		return getSession().selectList("selectUserGameBoards", username);
	}

}
