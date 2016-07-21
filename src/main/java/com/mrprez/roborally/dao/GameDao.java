package com.mrprez.roborally.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.mrprez.roborally.model.Card;
import com.mrprez.roborally.model.CardStock;
import com.mrprez.roborally.model.Game;
import com.mrprez.roborally.model.PowerDownState;
import com.mrprez.roborally.model.Robot;
import com.mrprez.roborally.model.history.Round;

public interface GameDao {

	List<Game> selectGameList(String username);

	Game loadGame(Integer id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
	
	Robot loadPlayerRobot(Integer gameId, String username);

	void saveHandCards(Integer gameId, int robotNumber, List<Card> cardList);

	void insertNewGame(Game game);
	
	void insertCardStock(CardStock cardStock, int gameId);

	void updateGame(Game game);

	void saveRound(Integer gameId, Round round);
	
	void updatePowerDownState(Integer gameId, Integer robotNb, PowerDownState powerDownState);

	void insertHandCards(int gameId, int number, List<Card> cards);

}
