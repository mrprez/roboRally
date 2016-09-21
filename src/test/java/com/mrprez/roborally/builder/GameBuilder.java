package com.mrprez.roborally.builder;

import com.mrprez.roborally.model.Game;
import com.mrprez.roborally.model.Robot;

public class GameBuilder {
	
	private Game game = new Game();
	
	private GameBuilder(){
		super();
	}
	
	public static GameBuilder newGame(){
		return new GameBuilder();
	}
	
	public GameBuilder withId(Integer id){
		game.setId(id);
		return this;
	}
	
	public GameBuilder withName(String name){
		game.setName(name);
		return this;
	}
	
	public GameBuilder withPlayer(String... usernames){
		for(String username : usernames){
			Robot robot = game.addRobot();
			robot.setUsername(username);
		}
		return this;
	}
	
	public Game get(){
		return game;
	}

}
