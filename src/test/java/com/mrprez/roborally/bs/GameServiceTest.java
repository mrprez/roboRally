package com.mrprez.roborally.bs;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

import com.mrprez.roborally.builder.GameBuilder;
import com.mrprez.roborally.builder.RobotBuilder;
import com.mrprez.roborally.dao.GameDao;
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
		Game game = GameBuilder.newGame().withId(gameId).withRobot(
				RobotBuilder.newRobot(),
				RobotBuilder.newRobot()
				).build();
		
		// WHEN
		gameService.saveRobotCards(gameId, 1, cardList);
		
		// THEN
		
	}
	
	
	
	
	
	
}
