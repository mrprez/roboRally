package com.mrprez.roborally.server;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;

import com.mrprez.roborally.bs.BoardService;
import com.mrprez.roborally.client.BoardGwtService;
import com.mrprez.roborally.model.board.GameBoard;
import com.mrprez.roborally.shared.GameBoardGwt;
import com.mrprez.roborally.shared.UserGwt;

public class BoardGwtServiceImpl extends AbstractGwtService implements BoardGwtService {
	private static final long serialVersionUID = 1L;

	private BoardService boardService;
	
	
	@Override
	public List<GameBoardGwt> getGameBoardList() {
		UserGwt user = (UserGwt) getThreadLocalRequest().getSession().getAttribute(UserGwt.KEY);
		List<GameBoard> gameBoardList = boardService.getUserGameBoards(user.getUsername());
		List<GameBoardGwt> result = new ArrayList<GameBoardGwt>(gameBoardList.size());
		DozerBeanMapper dozerMapper = new DozerBeanMapper();
		for(GameBoard gameBoard : gameBoardList){
			result.add(dozerMapper.map(gameBoard, GameBoardGwt.class));
		}
		return result;
	}


	public BoardService getBoardService() {
		return boardService;
	}


	public void setBoardService(BoardService boardService) {
		this.boardService = boardService;
	}
	
	

}
