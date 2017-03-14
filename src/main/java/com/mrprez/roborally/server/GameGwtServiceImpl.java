package com.mrprez.roborally.server;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.mail.internet.AddressException;

import org.dozer.DozerBeanMapper;

import com.mrprez.roborally.bs.GameService;
import com.mrprez.roborally.client.service.GameGwtService;
import com.mrprez.roborally.model.Card;
import com.mrprez.roborally.model.Direction;
import com.mrprez.roborally.model.Game;
import com.mrprez.roborally.model.PowerDownState;
import com.mrprez.roborally.model.Robot;
import com.mrprez.roborally.model.Square;
import com.mrprez.roborally.model.history.Round;
import com.mrprez.roborally.push.PushEventServiceServlet;
import com.mrprez.roborally.shared.CardGwt;
import com.mrprez.roborally.shared.GameGwt;
import com.mrprez.roborally.shared.RobotGwt;
import com.mrprez.roborally.shared.SquareGwt;
import com.mrprez.roborally.shared.UserGwt;

public class GameGwtServiceImpl extends AbstractGwtService implements GameGwtService {
	private static final long serialVersionUID = 1L;
	
	private DozerBeanMapper dozerMapper;
	private GameService gameService;
	private PushEventServiceServlet pushEventServiceServlet;
	
	
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
		gameGwt.setUserOwner(user.getUsername().equals(game.getOwnername()));
		SquareGwt[][] squareGwtTab = new SquareGwt[game.getBoard().getSizeX()][game.getBoard().getSizeY()]; 
		for(int y=0; y<game.getBoard().getSizeY(); y++){
			for(int x=0; x<game.getBoard().getSizeX(); x++){
				squareGwtTab[x][y] = new SquareGwt();
				Square square = game.getBoard().getSquare(x, y);
				squareGwtTab[x][y].setImageName(square.getImageName());
				squareGwtTab[x][y].setWallUp(square.getWall(Direction.UP));
				squareGwtTab[x][y].setWallDown(square.getWall(Direction.DOWN));
				squareGwtTab[x][y].setWallLeft(square.getWall(Direction.LEFT));
				squareGwtTab[x][y].setWallRight(square.getWall(Direction.RIGHT));
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
			gameGwt.getRobotList().add(robotGwt);
		}
		
		return gameGwt;
	}

	
	@Override
	public RobotGwt getPlayerRobot(Integer gameId) {
		UserGwt user = (UserGwt) getThreadLocalRequest().getSession().getAttribute(UserGwt.KEY);
		Robot robot = gameService.getPlayerRobot(gameId, user.getUsername());
		
		RobotGwt robotGwt = dozerMapper.map(robot, RobotGwt.class);
		for(Card card : robot.getCards()){
			CardGwt cardGwt = dozerMapper.map(card, CardGwt.class);
			robotGwt.getCards().add(cardGwt);
			if(robotGwt.getCards().size()>robot.getHealth()){
				cardGwt.setBlocked(true);
			}
		}
		return robotGwt;
	}


	@Override
	public void saveCards(Integer gameId, List<Integer> cardList) {
		UserGwt user = (UserGwt) getThreadLocalRequest().getSession().getAttribute(UserGwt.KEY);
		gameService.saveRobotCards(gameId, user.getUsername(), cardList);

		pushEventServiceServlet.sendRefreshOrder();
	}

	@Override
	public int createNewGame(String name, int sizeX, int sizeY, int aiNb, List<String> invitedPlayerEMails) throws AddressException, Exception {
		if(sizeX<=0 || sizeY<=0 || sizeX*sizeY>1000 || aiNb<0 || aiNb+invitedPlayerEMails.size()>7){
			throw new IllegalArgumentException();
		}
		UserGwt user = (UserGwt) getThreadLocalRequest().getSession().getAttribute(UserGwt.KEY);
		Game game = gameService.createNewGame(name, user.getUsername(), sizeX, sizeY, aiNb, invitedPlayerEMails);
		return game.getId();
	}

	@Override
	public int createNewGame(String name, Integer buildingBoardId, int aiNb, List<String> invitedPlayerEMails) throws Exception {
		UserGwt user = (UserGwt) getThreadLocalRequest().getSession().getAttribute(UserGwt.KEY);
		Game game = gameService.createNewGame(name, user.getUsername(), buildingBoardId, aiNb, invitedPlayerEMails);
		return game.getId();
	}

	@Override
	public void playNewRound(Integer gameId) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InterruptedException, ExecutionException {
		UserGwt user = (UserGwt) getThreadLocalRequest().getSession().getAttribute(UserGwt.KEY);
		Round newRound = gameService.playRound(gameId, user.getUsername());
		
		pushEventServiceServlet.sendNewRoundEvent(newRound);
	}

	
	@Override
	public void savePowerDownState(Integer gameId, String powerDownState) {
		UserGwt user = (UserGwt) getThreadLocalRequest().getSession().getAttribute(UserGwt.KEY);
		gameService.updatePowerDownState(gameId, user.getUsername(), PowerDownState.valueOf(powerDownState));
	}
	
	
	@Override
	public CardGwt getCard(Integer gameId, Integer cardRapidity) {
		return dozerMapper.map(gameService.getCard(gameId, cardRapidity), CardGwt.class);
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
	
	public PushEventServiceServlet getPushEventServiceServlet() {
		return pushEventServiceServlet;
	}

	public void setPushEventServiceServlet(PushEventServiceServlet pushEventServiceServlet) {
		this.pushEventServiceServlet = pushEventServiceServlet;
	}

}
