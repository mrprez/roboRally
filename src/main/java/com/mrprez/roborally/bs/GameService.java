package com.mrprez.roborally.bs;

import java.util.List;

import com.mrprez.roborally.model.Game;

public interface GameService {
	
	
	List<Game> getUserGames(String username);
	
	Game getGame(Integer id, String username);

}
