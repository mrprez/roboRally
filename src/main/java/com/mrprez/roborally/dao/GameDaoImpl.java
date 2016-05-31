package com.mrprez.roborally.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dozer.DozerBeanMapper;

import com.mrprez.roborally.dto.GameBoardDto;
import com.mrprez.roborally.dto.RobotDto;
import com.mrprez.roborally.dto.SquareDto;
import com.mrprez.roborally.model.Card;
import com.mrprez.roborally.model.Game;
import com.mrprez.roborally.model.Robot;
import com.mrprez.roborally.model.Square;
import com.mrprez.roborally.model.board.Board;
import com.mrprez.roborally.model.board.GameBoard;

public class GameDaoImpl extends AbstractDao implements GameDao {
	
	
	@Override
	public List<Game> selectGameList(String username) {
		return getSession().selectList("selectUserGames", username);
	}

	@Override
	public Game loadGame(Integer id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		DozerBeanMapper dozerMapper = new DozerBeanMapper();
		Game game = new Game();
		GameBoardDto gameBoardDto = getSession().selectOne("selectGame", id);
		GameBoard gameBoard = new GameBoard(gameBoardDto.getBoardId(), gameBoardDto.getSizeX(), gameBoardDto.getSizeY());
		game.setName(gameBoardDto.getName());
		game.setBoard(gameBoard);
		
		// Squares
		List<SquareDto> squareDtoList = getSession().selectList("selectSquareList", gameBoard.getId());
		for(SquareDto squareDto : squareDtoList){
			@SuppressWarnings("unchecked")
			Constructor<Square> squareConstructor = (Constructor<Square>) Class.forName(squareDto.getClazz()).getConstructor(Integer.class, Integer.class, Board.class);;
			Square square = squareConstructor.newInstance(squareDto.getX(), squareDto.getY(), gameBoard);
			square.setArgs(squareDto.getArgs());
			dozerMapper.map(squareDto, square);
			gameBoard.addSquare(square);
		}
		
		// Start square
		game.getBoard().setStartSquare(gameBoardDto.getStartX(), gameBoardDto.getStartY());
		
		// Robot
		List<RobotDto> robotDtoList = getSession().selectList("selectRobotList", gameBoard.getId());
		for(RobotDto robotDto : robotDtoList){
			Square square = game.getBoard().getSquare(robotDto.getX(), robotDto.getY());
			Robot robot = new Robot(square);
			dozerMapper.map(robotDto, robot);
			game.getRobotList().add(robot);
		}
		
		return game;
	}

	@Override
	public List<Card> loadHandCards(Integer gameId, String username) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("gameId", gameId);
		params.put("username", username);
		return getSession().selectList("selectHandCardList", params);
	}

}
