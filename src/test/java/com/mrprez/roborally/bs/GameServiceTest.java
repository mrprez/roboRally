package com.mrprez.roborally.bs;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.dev.util.collect.Lists;
import com.mrprez.roborally.builder.GameBuilder;
import com.mrprez.roborally.builder.RobotBuilder;
import com.mrprez.roborally.dao.GameDao;
import com.mrprez.roborally.dao.UserDao;
import com.mrprez.roborally.model.Card;
import com.mrprez.roborally.model.Game;
import com.mrprez.roborally.model.Invitation;
import com.mrprez.roborally.model.Robot;
import com.mrprez.roborally.model.Game.GameState;
import com.mrprez.roborally.push.PushEventServiceServlet;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {
	@InjectMocks
	private GameServiceImpl gameService;
	
	@Mock
	private GameDao gameDao;
	@Mock
	private UserDao userDao;
	@Mock
	private PushEventServiceServlet pushEventServiceServlet;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	
	@Test
	public void testGetUserGames_Success() throws SQLException{
		// GIVEN
		String username = "username";
		List<Game> gameList = Arrays.asList(new Game(), new Game());
		Mockito.when(gameDao.selectGameList(username)).thenReturn(gameList);
		
		// WHEN
		List<Game> resultGameList = gameService.getUserGames(username);
		
		// THEN
		Assert.assertEquals(gameList, resultGameList);
	}
	
	@Test
	public void testGetGame_Success() throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		// GIVEN
		String username = "username";
		Integer id = 42;
		Game game = GameBuilder.newGame().withId(id).withRobot(RobotBuilder.newRobot().withUsername(username)).build();
		Mockito.when(gameDao.loadGame(id)).thenReturn(game);
		
		// WHEN
		Game resultGame = gameService.getGame(id, username);
		
		// THEN
		Assert.assertEquals(game, resultGame);
	}
	
	@Test(expected=IllegalAccessError.class)
	public void testGetGame_Fail_BadUser() throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		// GIVEN
		String username = "username";
		Integer id = 42;
		Game game = new Game();
		game.setOwnername("otherUsername");
		Mockito.when(gameDao.loadGame(id)).thenReturn(game);
		
		// WHEN
		gameService.getGame(id, username);
	}
	
	@Test
	public void testGetPlayerRobot() {
		// GIVEN
		String username = "username";
		Integer gameId = 42;
		Game game = GameBuilder.newGame().withRobot(RobotBuilder.newRobot().withUsername(username)).build();
		Robot robot = game.getRobot(0);
		Mockito.when(gameDao.loadPlayerRobot(gameId, username)).thenReturn(robot);
		
		// WHEN
		Robot returnedRobot = gameService.getPlayerRobot(gameId, username);
		
		// THEN
		Assert.assertEquals(robot, returnedRobot);
	}
	
	@Test
	public void testSaveRobotCards_Success_RobotNb() {
		// GIVEN
		Integer gameId = 42;
		int robotNb = 0;
		Game game = GameBuilder.newGame().started().withId(gameId).withRobot(
				RobotBuilder.newRobot(),
				RobotBuilder.newRobot()
				).build();
		Robot robot = game.getRobot(robotNb);
		Mockito.when(gameDao.loadRobot(gameId, robotNb)).thenReturn(robot);
		
		// WHEN
		List<Card> originCardList = new ArrayList<Card>(robot.getCards());
		List<Card> cardList = new ArrayList<Card>();
		List<Integer> rapidityList = new ArrayList<Integer>();
		while(!originCardList.isEmpty()){
			int index = (int) (Math.random() * originCardList.size());
			Card card = originCardList.remove(index);
			cardList.add(card);
			rapidityList.add(card.getRapidity());
		}
		gameService.saveRobotCards(gameId, robotNb, rapidityList);
		
		// THEN
		Mockito.verify(gameDao).saveHandCards(gameId, robotNb, cardList);
		Mockito.verify(gameDao).updateRobotHasPlayed(robotNb, gameId, true);
		Mockito.verify(pushEventServiceServlet).sendRefreshOrder();
	}
	
	
	@Test
	public void testSaveRobotCards_Fail_NoRobotCard() {
		// GIVEN
		Integer gameId = 42;
		int robotNb = 0;
		Game game = GameBuilder.newGame().started().withId(gameId).withRobot(
				RobotBuilder.newRobot(),
				RobotBuilder.newRobot()
				).build();
		Robot robot = game.getRobot(robotNb);
		Mockito.when(gameDao.loadRobot(gameId, robotNb)).thenReturn(robot);
		
		// WHEN
		List<Card> originCardList = new ArrayList<Card>(robot.getCards());
		List<Card> cardList = new ArrayList<Card>();
		List<Integer> rapidityList = new ArrayList<Integer>();
		while(originCardList.size()>1){
			int index = (int) (Math.random() * originCardList.size());
			Card card = originCardList.remove(index);
			cardList.add(card);
			rapidityList.add(card.getRapidity());
		}
		Integer cheatCardRapidity = game.getCardStock().takeCard().getRapidity();
		rapidityList.add(cheatCardRapidity);
		thrown.expect(IllegalStateException.class);
		thrown.expectMessage("No card found for robot "+robot.getNumber()+" in game "+gameId+" with rapidity "+cheatCardRapidity);
		gameService.saveRobotCards(gameId, robotNb, rapidityList);
		
		// THEN
		// Exception thrown
	}
	
	
	@Test
	public void testAddRobotToGame_Success_WithInvitations() throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException{
		// GIVEN
		Integer gameId = 42;
		final String username = "username";
		Game game = GameBuilder.newGame().withId(gameId).withRobot(RobotBuilder.newRobot()).build();
		Mockito.when(gameDao.loadGame(gameId)).thenReturn(game);
		Mockito.when(userDao.getInvitationsForGame(gameId)).thenReturn(Lists.create(Mockito.mock(Invitation.class)));
		
		// WHEN
		gameService.addRobotToGame(gameId, username);
		
		// THEN
		Mockito.verify(gameDao).saveRobot(
				Mockito.argThat(new BaseMatcher<Robot>() {
					@Override
					public boolean matches(Object arg) {
						return ((Robot)arg).getUsername().equals(username);
					}
					@Override
					public void describeTo(Description arg0) {
						arg0.appendText(username);
					}
				}),
				Mockito.eq(gameId));
	}
	
	
	@Test
	public void testAddRobotToGame_Success_WithoutInvitations() throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException{
		// GIVEN
		Integer gameId = 42;
		final String username = "username";
		Game game = GameBuilder.newGame().withId(gameId).withRobot(RobotBuilder.newRobot()).build();
		Mockito.when(gameDao.loadGame(gameId)).thenReturn(game);
		Mockito.when(userDao.getInvitationsForGame(gameId)).thenReturn(new ArrayList<Invitation>());
		
		// WHEN
		gameService.addRobotToGame(gameId, username);
		
		// THEN
		Mockito.verify(gameDao).saveRobot(
				Mockito.argThat(new BaseMatcher<Robot>() {
					@Override
					public boolean matches(Object arg) {
						return ((Robot)arg).getUsername().equals(username);
					}
					@Override
					public void describeTo(Description arg0) {
						arg0.appendText(username);
					}
				}),
				Mockito.eq(gameId)
		);
		checkInitGame(game);
	}
	
	
	private void checkInitGame(Game game){
		Assert.assertEquals(GameState.ONGOING, game.getState());
		for(Robot robot : game.getRobotList()){
			Assert.assertEquals(9, robot.getCards().size());
			gameDao.insertHandCards(game.getId(), robot.getNumber(), robot.getCards());
		}
		Mockito.verify(gameDao).saveGameState(game);
		Mockito.verify(gameDao).insertCardStock(game.getCardStock(), game.getId());
	}
	
	
	
	
}
