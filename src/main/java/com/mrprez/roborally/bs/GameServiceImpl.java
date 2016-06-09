package com.mrprez.roborally.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mrprez.roborally.dao.GameDao;
import com.mrprez.roborally.model.Card;
import com.mrprez.roborally.model.Game;
import com.mrprez.roborally.model.Robot;

public class GameServiceImpl implements GameService {

	private GameDao gameDao;
	
	@Override
	public List<Game> getUserGames(String username) {
		return gameDao.selectGameList(username);
	}

	@Override
	public Game getGame(Integer id, String username) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Game game = gameDao.loadGame(id);
		// TODO check user
		
		return game;
	}

	@Override
	public Robot getPlayerRobot(Integer gameId, String username) {
		return gameDao.loadPlayerRobot(gameId, username);
	}

	
	public GameDao getGameDao() {
		return gameDao;
	}

	public void setGameDao(GameDao gameDao) {
		this.gameDao = gameDao;
	}

	@Override
	public void saveRobotCards(Integer gameId, String username, List<Integer> cardList) {
		
		Map<Integer, Card> cardMap = new HashMap<Integer, Card>();
		Robot robot = gameDao.loadPlayerRobot(gameId, username);
		for(Card card : robot.getCards()){
			cardMap.put(card.getRapidity(), card);
		}
		List<Card> sortedCardList = new ArrayList<Card>();
		for(Integer rapidity : cardList){
			Card card = cardMap.get(rapidity);
			if(card==null){
				throw new IllegalStateException("No card found for user "+username+" in game "+gameId+" with rapidity "+rapidity);
			}
			sortedCardList.add(card);
		}
		// TODO vérifier si les cartes modifiés sont cohhérentes vis à vis du health.
		
		gameDao.saveHandCards(gameId, robot.getNumber(), sortedCardList);
	}
	

}
