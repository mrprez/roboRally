package com.mrprez.roborally.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.mrprez.roborally.model.Card;
import com.mrprez.roborally.model.Game;

public interface GameService {
	
	
	List<Game> getUserGames(String username);
	
	Game getGame(Integer id, String username) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
	
	List<Card> getRobotCards(Integer gameId, String username);

}
