package com.mrprez.roborally.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.mrprez.roborally.model.Game;

public interface GameDao {

	List<Game> selectGameList(String username);

	Game loadGame(Integer id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
	
	
	

}
