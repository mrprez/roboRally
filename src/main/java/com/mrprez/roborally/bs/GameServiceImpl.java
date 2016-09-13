package com.mrprez.roborally.bs;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.mail.internet.AddressException;

import com.mrprez.roborally.ai.IARobot;
import com.mrprez.roborally.dao.GameDao;
import com.mrprez.roborally.dao.MailResource;
import com.mrprez.roborally.dao.UserDao;
import com.mrprez.roborally.model.Card;
import com.mrprez.roborally.model.Game;
import com.mrprez.roborally.model.Invitation;
import com.mrprez.roborally.model.PowerDownState;
import com.mrprez.roborally.model.Robot;
import com.mrprez.roborally.model.User;
import com.mrprez.roborally.model.board.BuildingBoard;
import com.mrprez.roborally.model.board.GameBoard;
import com.mrprez.roborally.model.history.Round;
import com.mrprez.roborally.model.square.EmptySquare;
import com.mrprez.roborally.push.PushEventServiceServlet;

public class GameServiceImpl implements GameService {

	private GameDao gameDao;
	private UserDao userDao;
	private MailResource mailResource;
	private PushEventServiceServlet pushEventServiceServlet;
	
	
	@Override
	public List<Game> getUserGames(String username) {
		return gameDao.selectGameList(username);
	}

	@Override
	public Game getGame(Integer id, String username) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Game game = gameDao.loadGame(id);
		for(Robot robot : game.getRobotList()){
			if(username.equals(robot.getUsername())){
				return game;
			}
		}
		
		throw new IllegalAccessError(username+" has no robot on game "+id);
	}

	@Override
	public Robot getPlayerRobot(Integer gameId, String username) {
		return gameDao.loadPlayerRobot(gameId, username);
	}
	
	@Override
	public void saveRobotCards(Integer gameId, Integer robotNb, List<Integer> cardList) {
		Robot robot = gameDao.loadRobot(gameId, robotNb);
		saveRobotCards(gameId, robot, cardList);
	}

	@Override
	public void saveRobotCards(Integer gameId, String username, List<Integer> cardList) {
		Robot robot = gameDao.loadPlayerRobot(gameId, username);
		saveRobotCards(gameId, robot, cardList);
	}
	
	private void saveRobotCards(Integer gameId, Robot robot, List<Integer> cardList) {
		Map<Integer, Card> cardMap = new HashMap<Integer, Card>();
		for(Card card : robot.getCards()){
			cardMap.put(card.getRapidity(), card);
		}
		List<Card> sortedCardList = new ArrayList<Card>();
		for(Integer rapidity : cardList){
			Card card = cardMap.get(rapidity);
			if(card==null){
				throw new IllegalStateException("No card found for robot "+robot.getNumber()+" in game "+gameId+" with rapidity "+rapidity);
			}
			sortedCardList.add(card);
		}
		// TODO vérifier si les cartes modifiés sont cohérentes vis à vis du health.
		
		gameDao.saveHandCards(gameId, robot.getNumber(), sortedCardList);
		
		gameDao.updateRobotHasPlayed(robot.getNumber(), gameId, true);
		
		pushEventServiceServlet.sendRefreshOrder();
	}

	@Override
	public Game createNewGame(String name, String username, int sizeX, int sizeY, int aiNb, List<String> invitedEMails) throws AddressException, Exception {
		Game game = new Game();
		game.setName(name);
		game.setOwnername(username);
		GameBoard board = new GameBoard(BuildingBoard.buildRandomBoard("temp", sizeX, sizeY));
		game.setBoard(board);
		for(int i=0; i<5; i++){
			int x=(int) (Math.random()*sizeX);
			int y=(int) (Math.random()*sizeY);
			while(board.getTargetSquares().contains(board.getSquare(x, y)) || ! (board.getSquare(x, y) instanceof EmptySquare)){
				x=(int) (Math.random()*sizeX);
				y=(int) (Math.random()*sizeY);
			}
			board.getTargetSquares().add(board.getSquare(x, y));
		}
		
		gameDao.insertNewGame(game);
		
		Robot robot = game.addRobot();
		robot.setUsername(username);
		gameDao.saveRobot(robot, game.getId());
		for(int i=0; i<aiNb;i++){
			Robot aiRobot = game.addRobot();
			gameDao.saveRobot(aiRobot, game.getId());
		}
		for(String invitedEMail : invitedEMails){
			User user = userDao.getUserByEMail(invitedEMail);
			if(user!=null){
				Robot invitedRobot = game.addRobot();
				invitedRobot.setUsername(user.getUsername());
				gameDao.saveRobot(invitedRobot, game.getId());
				mailResource.send(invitedEMail, "Nouvelle partie de RobotRally", username+" vous a invité à jouer à la partie "+name);
			}else{
				Invitation invitation = Invitation.newInvitation(invitedEMail, game.getId());
				userDao.saveInvitation(invitation);
				mailResource.send(invitedEMail, "Invitation à RobotRally", username+" vous a invité à sur RobotRally en ligne."
						+ " Pour valider votre inscription, cliquez sur le lien: <br/>"
						+ " http://www.mrprez.fr/roboRally/Register.html?eMail="+invitedEMail+"&token="+invitation.getToken());
			}
		}
		
		if(userDao.getInvitationsForGame(game.getId()).isEmpty()){
			initGame(game);
		}
		
		return game;
	}
	
	
	@Override
	public void addRobotToGame(int gameId, String username) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Game game = gameDao.loadGame(gameId);
		Robot robot = game.addRobot();
		robot.setUsername(username);
		gameDao.saveRobot(robot, game.getId());
		
		if(userDao.getInvitationsForGame(gameId).isEmpty()){
			initGame(gameDao.loadGame(gameId));
		}
	}
	
	
	private void initGame(Game game) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		
		game.start();
		
		gameDao.saveGameState(game);
		
		gameDao.insertCardStock(game.getCardStock(), game.getId());
		for(Robot robot : game.getRobotList()){
			gameDao.insertHandCards(game.getId(), robot.getNumber(), robot.getCards());
			if(robot.getUsername()==null){
				IARobot ia = new IARobot(game.getId(), robot, this);
				ia.start();
			}
		}
	}

	@Override
	public Round playRound(Integer gameId, String username) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InterruptedException, ExecutionException {
		Game game = gameDao.loadGame(gameId);
		// TODO check game owner
		
		Round round = game.play();
		
		gameDao.updateGame(game);
		
		gameDao.saveRound(game.getId(), round);
		
		for(Robot robot : game.getRobotList()){
			if(robot.getUsername()==null && robot.getTarget()!=null && robot.getPowerDownState()!= PowerDownState.ONGOING){
				IARobot ia = new IARobot(game.getId(), robot, this);
				ia.start();
			}
		}
		
		return round;
	}

	@Override
	public Card getCard(Integer gameId, Integer cardRapidity) {
		return gameDao.loadCard(gameId, cardRapidity);
	}
	
	@Override
	public void updatePowerDownState(Integer gameId, Integer robotNb, PowerDownState powerDownState) {
		Robot robot = gameDao.loadRobot(gameId, robotNb);
		updatePowerDownState(gameId, robot, powerDownState);
	}

	@Override
	public void updatePowerDownState(Integer gameId, String username, PowerDownState powerDownState) {
		Robot robot = gameDao.loadPlayerRobot(gameId, username);
		updatePowerDownState(gameId, robot, powerDownState);
	}
	
	private void updatePowerDownState(Integer gameId, Robot robot, PowerDownState powerDownState) {
		if(robot.getPowerDownState()==PowerDownState.ONGOING){
			throw new IllegalArgumentException("Cannot change power down state during power down");
		}
		gameDao.updatePowerDownState(gameId, robot.getNumber(), powerDownState);
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public GameDao getGameDao() {
		return gameDao;
	}

	public void setGameDao(GameDao gameDao) {
		this.gameDao = gameDao;
	}

	public MailResource getMailResource() {
		return mailResource;
	}

	public void setMailResource(MailResource mailResource) {
		this.mailResource = mailResource;
	}

	public PushEventServiceServlet getPushEventServiceServlet() {
		return pushEventServiceServlet;
	}

	public void setPushEventServiceServlet(PushEventServiceServlet pushEventServiceServlet) {
		this.pushEventServiceServlet = pushEventServiceServlet;
	}
	
}
