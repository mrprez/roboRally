package com.mrprez.roborally.bs;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.thirdparty.guava.common.base.Function;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.mrprez.roborally.builder.GameBuilder;
import com.mrprez.roborally.builder.RobotBuilder;
import com.mrprez.roborally.dao.GameDao;
import com.mrprez.roborally.model.Card;
import com.mrprez.roborally.model.Game;
import com.mrprez.roborally.model.Robot;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {
	@InjectMocks
	private GameServiceImpl gameService;
	
	@Mock
	private GameDao gameDao;
	
	
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
		//gameService.saveRobotCards(gameId, 1, cardList);
		List<Card> originCards = robot.getCards();
		List<Card> cardList = Lists.newArrayList(originCards.get(1), originCards.get(0), originCards.get(2), originCards.get(4), originCards.get(8), originCards.get(7), originCards.get(5), originCards.get(6), originCards.get(3));
		List<Integer> saveList = Lists.transform(cardList, new Function<Card, Integer>() {
			@Override
			public Integer apply(Card card) {
				return card.getRapidity();
			}
		});
		gameService.saveRobotCards(gameId, robotNb, saveList);
		
		// THEN
		
	}
	
	
	
	
	
	
}
