package com.mrprez.roborally.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.mrprez.roborally.model.Game;
import com.mrprez.roborally.model.Robot;
import com.mrprez.roborally.model.history.Round;

public interface GameService {
	
	
	List<Game> getUserGames(String username);
	
	Game getGame(Integer id, String username) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
	
	Robot getPlayerRobot(Integer gameId, String username);

	void saveRobotCards(Integer gameId, String username, List<Integer> cardList);
	
	Game createNewGame(String name, String username, int sizeX, int sizeY);

	Round playRound(Integer gameId, String username) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException;

}
