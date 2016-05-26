package com.mrprez.roborally.server;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;

import com.mrprez.roborally.bs.GameService;
import com.mrprez.roborally.client.GameGwtService;
import com.mrprez.roborally.model.Game;
import com.mrprez.roborally.shared.GameGwt;
import com.mrprez.roborally.shared.UserGwt;

public class GameGwtServiceImpl extends AbstractGwtService implements GameGwtService {
	private static final long serialVersionUID = 1L;

	private GameService gameService;
	
	
	@Override
	public List<GameGwt> getGameList() {
		UserGwt user = (UserGwt) getThreadLocalRequest().getSession().getAttribute(UserGwt.KEY);
		List<Game> gameList = gameService.getUserGames(user.getUsername());
		List<GameGwt> result = new ArrayList<GameGwt>(gameList.size());
		DozerBeanMapper dozerMapper = new DozerBeanMapper();
		for(Game game : gameList){
			result.add(dozerMapper.map(game, GameGwt.class));
		}
		return result;
	}

	@Override
	public GameGwt getGame(Integer id, String username) {
		// TODO Auto-generated method stub
		return null;
	}


	public GameService getGameService() {
		return gameService;
	}

	public void setGameService(GameService gameService) {
		this.gameService = gameService;
	}
	
}
