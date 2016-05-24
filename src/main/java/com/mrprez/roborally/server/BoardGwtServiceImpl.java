package com.mrprez.roborally.server;

import java.util.List;

import com.mrprez.roborally.bs.BoardService;
import com.mrprez.roborally.client.BoardGwtService;
import com.mrprez.roborally.model.board.GameBoard;
import com.mrprez.roborally.shared.UserGwt;

public class BoardGwtServiceImpl extends AbstractGwtService implements BoardGwtService {
	private static final long serialVersionUID = 1L;

	private BoardService boardService;
	
	
	@Override
	public List<GameBoard> getGameBoardList() {
		UserGwt user = (UserGwt) getThreadLocalRequest().getSession().getAttribute(UserGwt.KEY);
		return boardService.getUserGameBoards(user.getUsername());
	}


	public BoardService getBoardService() {
		return boardService;
	}


	public void setBoardService(BoardService boardService) {
		this.boardService = boardService;
	}
	
	

}
