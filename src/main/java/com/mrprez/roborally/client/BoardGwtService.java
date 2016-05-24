package com.mrprez.roborally.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mrprez.roborally.model.board.GameBoard;

@RemoteServiceRelativePath("boardService")
public interface BoardGwtService extends RemoteService {
	
	
	List<GameBoard> getGameBoardList();

}
