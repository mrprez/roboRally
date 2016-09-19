package com.mrprez.roborally.server;

import com.mrprez.roborally.bs.BoardService;
import com.mrprez.roborally.client.BoardGwtService;
import com.mrprez.roborally.model.board.BuildingBoard;
import com.mrprez.roborally.shared.UserGwt;

public class BoardGwtServiceImpl extends AbstractGwtService implements BoardGwtService {
	private static final long serialVersionUID = 1L;
	
	private BoardService boardService;

	@Override
	public int createNewBoard(String name, int sizeX, int sizeY) throws Exception {
		if(sizeX<=0 || sizeY<=0 || sizeX*sizeY>1000){
			throw new IllegalArgumentException();
		}
		UserGwt user = (UserGwt) getThreadLocalRequest().getSession().getAttribute(UserGwt.KEY);
		BuildingBoard board = boardService.createNewBoard(name, user.getUsername(), sizeX, sizeY);
		return board.getId();
	}

	
	public BoardService getBoardService() {
		return boardService;
	}

	public void setBoardService(BoardService boardService) {
		this.boardService = boardService;
	}
	

}
