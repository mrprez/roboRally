package com.mrprez.roborally.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.mrprez.roborally.model.Card;
import com.mrprez.roborally.model.Game;
import com.mrprez.roborally.model.Robot;

public interface GameDao {

	List<Game> selectGameList(String username);

	Game loadGame(Integer id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
	
	Robot loadPlayerRobot(Integer gameId, String username);

	void saveHandCards(Integer gameId, int robotNumber, List<Card> cardList);

}
