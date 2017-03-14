package com.mrprez.roborally.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.mail.internet.AddressException;

import com.mrprez.roborally.model.Card;
import com.mrprez.roborally.model.Game;
import com.mrprez.roborally.model.PowerDownState;
import com.mrprez.roborally.model.Robot;
import com.mrprez.roborally.model.history.Round;

public interface GameService {
	
	
	List<Game> getUserGames(String username);
	
	Game getGame(Integer id, String username) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
	
	Robot getPlayerRobot(Integer gameId, String username);

	void saveRobotCards(Integer gameId, String username, List<Integer> cardList);
	
	Game createNewGame(String name, String username, int sizeX, int sizeY, int aiNb, List<String> invitedPlayerEMails) throws AddressException, Exception;

	Game createNewGame(String name, String username, Integer buildingBoardId, int aiNb, List<String> invitedPlayerEMails) throws Exception;

	Round playRound(Integer gameId, String username) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InterruptedException, ExecutionException;

	void updatePowerDownState(Integer gameId, String username, PowerDownState valueOf);

	void updatePowerDownState(Integer gameId, Integer robotNb, PowerDownState powerDownState);

	void addRobotToGame(int gameId, String username) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;

	void saveRobotCards(Integer gameId, Integer robotNb, List<Integer> cardList);

	Card getCard(Integer gameId, Integer cardRapidity);


}
