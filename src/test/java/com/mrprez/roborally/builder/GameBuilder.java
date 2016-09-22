package com.mrprez.roborally.builder;

import java.util.ArrayList;
import java.util.List;

import com.mrprez.roborally.model.Game;
import com.mrprez.roborally.model.board.GameBoard;

public class GameBuilder {
	
	private int id = 0;
	private String name = "new game";
	private List<RobotBuilder> robotBuilders = new ArrayList<RobotBuilder>();
	private int sizeX = 12;
	private int sizeY = 12;
	private int startX = 0;
	private int startY = 0;
	
	
	private GameBuilder(){
		super();
	}
	
	public static GameBuilder newGame(){
		return new GameBuilder();
	}
	
	public GameBuilder withId(int id){
		this.id = id;
		return this;
	}
	
	public GameBuilder withName(String name){
		this.name = name;
		return this;
	}
	
	public GameBuilder withSize(int sizeX, int sizeY){
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		return this;
	}
	
	public GameBuilder withRobot(RobotBuilder... robotBuilders){
		for(RobotBuilder robotBuilder : robotBuilders){
			this.robotBuilders.add(robotBuilder);
		}
		return this;
	}
	
	public Game build(){
		Game game = new Game();
		GameBoard board = new GameBoard((int) (Math.random()*100), sizeX, sizeY);
		game.setBoard(board);
		game.setId(id);
		game.setName(name);
		board.getTargetSquares().add(board.getSquare(startX, startY));
		for(RobotBuilder robotBuilder : robotBuilders){
			game.getRobotList().add(robotBuilder.buildOnGameBoard(board));
		}
		return game;
	}

}
