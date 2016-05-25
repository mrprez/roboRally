package com.mrprez.roborally.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mrprez.roborally.shared.GameBoardGwt;

@RemoteServiceRelativePath("boardService")
public interface BoardGwtService extends RemoteService {
	
	
	List<GameBoardGwt> getGameBoardList();

}
