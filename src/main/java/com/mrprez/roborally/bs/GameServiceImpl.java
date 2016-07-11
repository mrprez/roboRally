package com.mrprez.roborally.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.mrprez.roborally.ai.IARobot;
import com.mrprez.roborally.dao.GameDao;
import com.mrprez.roborally.model.Card;
import com.mrprez.roborally.model.Game;
import com.mrprez.roborally.model.PowerDownState;
import com.mrprez.roborally.model.Robot;
import com.mrprez.roborally.model.board.BuildingBoard;
import com.mrprez.roborally.model.board.GameBoard;
import com.mrprez.roborally.model.history.Round;

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

	@Override
	public Game createNewGame(String name, String username, int sizeX, int sizeY) {
		Game game = new Game();
		game.setName(name);
		game.setOwnername(username);
		GameBoard board = new GameBoard(new BuildingBoard("temp", sizeX, sizeY));
		game.setBoard(board);
		for(int i=0; i<5; i++){
			int x=(int) (Math.random()*sizeX);
			int y=(int) (Math.random()*sizeY);
			while(board.getTargetSquares().contains(board.getSquare(x, y))){
				x=(int) (Math.random()*sizeX);
				y=(int) (Math.random()*sizeY);
			}
			board.getTargetSquares().add(board.getSquare(x, y));
		}
		
		Robot playerRobot = game.addRobot();
		playerRobot.setUsername(username);
		
		for(int i=2; i<=6;i++){
			game.addRobot();
		}
		
		game.start();
		
		gameDao.insertNewGame(game);
		
		return game;
	}

	@Override
	public Round playRound(Integer gameId, String username) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InterruptedException, ExecutionException {
		Game game = gameDao.loadGame(gameId);
		// TODO check user
		
		for(Robot robot : game.getRobotList()){
			if(robot.getUsername()==null && robot.getTarget()!=null && robot.getPowerDownState()!= PowerDownState.ONGOING){
				IARobot ia = new IARobot(robot);
				List<Card> orderedCards = ia.orderCard();
				robot.getCards().clear();
				robot.getCards().addAll(orderedCards);
				if(ia.shouldPowerDown()){
					robot.setPowerDownState(PowerDownState.PLANNED);
				}
			}
		}
		
		
		Round round = game.play();
		
		gameDao.updateGame(game);
		
		gameDao.saveRound(game.getId(), round);
		
		return round;
	}

	@Override
	public void updatePowerDownState(Integer gameId, String username, PowerDownState powerDownState) {
		Robot robot = gameDao.loadPlayerRobot(gameId, username);
		if(robot.getPowerDownState()==PowerDownState.ONGOING){
			throw new IllegalArgumentException("Cannot change power down state during power down");
		}
		gameDao.updatePowerDownState(gameId, robot.getNumber(), powerDownState);
	}
	

}
