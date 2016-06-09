package com.mrprez.roborally.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mrprez.roborally.shared.CardGwt;
import com.mrprez.roborally.shared.GameGwt;

@RemoteServiceRelativePath("gameService")
public interface GameGwtService extends RemoteService {
	
	
	List<GameGwt> getGameList();
	
	GameGwt getGame(Integer id) throws Exception;
	
	List<CardGwt> getCardList(Integer gameId);
	
	void saveCards(Integer gameId, List<Integer> cardList);

}
