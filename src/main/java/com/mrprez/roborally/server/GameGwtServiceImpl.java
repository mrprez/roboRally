package com.mrprez.roborally.server;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.dozer.DozerBeanMapper;

import com.mrprez.roborally.bs.GameService;
import com.mrprez.roborally.client.GameGwtService;
import com.mrprez.roborally.model.Card;
import com.mrprez.roborally.model.Direction;
import com.mrprez.roborally.model.Game;
import com.mrprez.roborally.model.Robot;
import com.mrprez.roborally.model.RobotState;
import com.mrprez.roborally.model.Square;
import com.mrprez.roborally.model.history.Round;
import com.mrprez.roborally.shared.CardGwt;
import com.mrprez.roborally.shared.GameGwt;
import com.mrprez.roborally.shared.RobotGwt;
import com.mrprez.roborally.shared.RobotStateGwt;
import com.mrprez.roborally.shared.RoundGwt;
import com.mrprez.roborally.shared.SquareGwt;
import com.mrprez.roborally.shared.UserGwt;

public class GameGwtServiceImpl extends AbstractGwtService implements GameGwtService {
	private static final long serialVersionUID = 1L;
	
	private DozerBeanMapper dozerMapper;
	private GameService gameService;
	
	
	@Override
	public List<GameGwt> getGameList() {
		UserGwt user = (UserGwt) getThreadLocalRequest().getSession().getAttribute(UserGwt.KEY);
		List<Game> gameList = gameService.getUserGames(user.getUsername());
		List<GameGwt> result = new ArrayList<GameGwt>(gameList.size());
		for(Game game : gameList){
			result.add(dozerMapper.map(game, GameGwt.class));
		}
		return result;
	}

	@Override
	public GameGwt getGame(Integer id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		UserGwt user = (UserGwt) getThreadLocalRequest().getSession().getAttribute(UserGwt.KEY);
		Game game = gameService.getGame(id, user.getUsername());
		GameGwt gameGwt = new GameGwt();
		dozerMapper.map(game, gameGwt);
		SquareGwt[][] squareGwtTab = new SquareGwt[game.getBoard().getSizeX()][game.getBoard().getSizeY()]; 
		for(int y=0; y<game.getBoard().getSizeY(); y++){
			for(int x=0; x<game.getBoard().getSizeX(); x++){
				squareGwtTab[x][y] = new SquareGwt();
				squareGwtTab[x][y].setImageName(game.getBoard().getSquare(x, y).getImageName());
			}
		}
		gameGwt.getBoard().setSquares(squareGwtTab);
		
		int targetNumber = 0;
		for(Square targetSquare : game.getBoard().getTargetSquares()){
			squareGwtTab[targetSquare.getX()][targetSquare.getY()].setTargetNumber(targetNumber);
			targetNumber++;
		}
		
		gameGwt.setRobotList(new ArrayList<RobotGwt>());
		for(Robot robot : game.getRobotList()){
			RobotGwt robotGwt = dozerMapper.map(robot, RobotGwt.class);
			robotGwt.setDirection(getDirectionChar(robot.getDirection()));
			gameGwt.getRobotList().add(robotGwt);
		}
		
		for(Round round : game.getHistory()){
			RoundGwt roundGwt = gameGwt.getHistory().get(round.getNumber());
			mapStates(roundGwt, round.getStateMap());
		}
		
		return gameGwt;
	}

	
	@Override
	public List<CardGwt> getCardList(Integer gameId) {
		UserGwt user = (UserGwt) getThreadLocalRequest().getSession().getAttribute(UserGwt.KEY);
		List<CardGwt> gwtCardList = new ArrayList<CardGwt>();
		Robot robot = gameService.getPlayerRobot(gameId, user.getUsername());
		for(Card card : robot.getCards()){
			gwtCardList.add(dozerMapper.map(card, CardGwt.class));
		}
		return gwtCardList;
	}
	
	
	private char getDirectionChar(Direction direction){
		switch (direction) {
		case DOWN:
			return 'B';
		case LEFT:
			return 'G';
		case RIGHT:
			return 'D';
		default:
			return 'H';
		}
		
	}


	public GameService getGameService() {
		return gameService;
	}

	public void setGameService(GameService gameService) {
		this.gameService = gameService;
	}
	
	public DozerBeanMapper getDozerMapper() {
		return dozerMapper;
	}

	public void setDozerMapper(DozerBeanMapper dozerMapper) {
		this.dozerMapper = dozerMapper;
	}

	@Override
	public void saveCards(Integer gameId, List<Integer> cardList) {
		UserGwt user = (UserGwt) getThreadLocalRequest().getSession().getAttribute(UserGwt.KEY);
		gameService.saveRobotCards(gameId, user.getUsername(), cardList);
	}

	@Override
	public int createNewGame(String name, int sizeX, int sizeY) {
		UserGwt user = (UserGwt) getThreadLocalRequest().getSession().getAttribute(UserGwt.KEY);
		Game game = gameService.createNewGame(name, user.getUsername(), sizeX, sizeY);
		return game.getId();
	}

	@Override
	public RoundGwt playNewRound(Integer gameId) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InterruptedException, ExecutionException {
		UserGwt user = (UserGwt) getThreadLocalRequest().getSession().getAttribute(UserGwt.KEY);
		Round newRound = gameService.playRound(gameId, user.getUsername());
		
		RoundGwt newRoundGwt = dozerMapper.map(newRound, RoundGwt.class);
		mapStates(newRoundGwt, newRound.getStateMap());
		
		return newRoundGwt;
	}

	
	private void mapStates(RoundGwt roundGwt, Map<Robot, RobotState> stateMap){
		roundGwt.setRobotStateList(new ArrayList<RobotStateGwt>());
		for(Robot robot : stateMap.keySet()){
			RobotStateGwt stateGwt = dozerMapper.map(stateMap.get(robot), RobotStateGwt.class);
			stateGwt.setDirection(getDirectionChar(stateMap.get(robot).getDirection()));
			stateGwt.setRobotNb(robot.getNumber());
			roundGwt.getRobotStateList().add(stateGwt);
		}
	}
	
	
}
