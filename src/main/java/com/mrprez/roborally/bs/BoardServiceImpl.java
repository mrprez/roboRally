package com.mrprez.roborally.bs;

import java.util.List;

import com.mrprez.roborally.dao.GameBoardDao;
import com.mrprez.roborally.model.board.GameBoard;

public class BoardServiceImpl implements BoardService {

	private GameBoardDao gameBoardDao;
	
	@Override
	public List<GameBoard> getUserGameBoards(String username) {
		return gameBoardDao.selectGameBoardList(username);
	}
	

	public GameBoardDao getGameBoardDao() {
		return gameBoardDao;
	}

	public void setGameBoardDao(GameBoardDao gameBoardDao) {
		this.gameBoardDao = gameBoardDao;
	}
	

}
