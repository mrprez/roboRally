package com.mrprez.roborally.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.dozer.DozerBeanMapper;

import com.mrprez.roborally.dto.GameBoardDto;
import com.mrprez.roborally.dto.SquareDto;
import com.mrprez.roborally.model.Game;
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
		GameBoardDto gameBoardDto = getSession().selectOne("selectGame", id);
		GameBoard gameBoard = new GameBoard(gameBoardDto.getBoardId(), gameBoardDto.getSizeX(), gameBoardDto.getSizeY());
		Game game = new Game();
		game.setName(gameBoardDto.getName());
		game.setBoard(gameBoard);
		
		List<SquareDto> squareDtoList = getSession().selectList("selectSquareList", gameBoard.getId());
		DozerBeanMapper dozerMapper = new DozerBeanMapper();
		for(SquareDto squareDto : squareDtoList){
			@SuppressWarnings("unchecked")
			Constructor<Square> squareConstructor = (Constructor<Square>) Class.forName(squareDto.getClazz()).getConstructor(Integer.class, Integer.class, Board.class);;
			Square square = squareConstructor.newInstance(squareDto.getX(), squareDto.getY(), gameBoard);
			square.setArgs(squareDto.getArgs());
			dozerMapper.map(squareDto, square);
			gameBoard.addSquare(square);
		}
		
		
		
		return game;
	}

}
