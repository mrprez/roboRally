package com.mrprez.roborally.dao;

import java.util.List;

import com.mrprez.roborally.model.Game;

public class GameDaoImpl extends AbstractDao implements GameDao {
	
	
	@Override
	public List<Game> selectGameList(String username) {
		return getSession().selectList("selectUserGames", username);
	}

	@Override
	public Game loadGame(Integer id) {
		Game game = getSession().selectOne("selectGame", id);
		
		
		return game;
	}

}
