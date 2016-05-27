package com.mrprez.roborally.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.mrprez.roborally.dao.GameDao;
import com.mrprez.roborally.model.Game;

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

	
	public GameDao getGameDao() {
		return gameDao;
	}

	public void setGameDao(GameDao gameDao) {
		this.gameDao = gameDao;
	}
	

}
