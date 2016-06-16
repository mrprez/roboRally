package com.mrprez.roborally.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dozer.DozerBeanMapper;

import com.mrprez.roborally.dto.GameBoardDto;
import com.mrprez.roborally.dto.MoveDto;
import com.mrprez.roborally.dto.RobotDto;
import com.mrprez.roborally.dto.SquareDto;
import com.mrprez.roborally.dto.StepDto;
import com.mrprez.roborally.model.Card;
import com.mrprez.roborally.model.Game;
import com.mrprez.roborally.model.Robot;
import com.mrprez.roborally.model.Square;
import com.mrprez.roborally.model.board.Board;
import com.mrprez.roborally.model.board.GameBoard;
import com.mrprez.roborally.model.history.FailedTranslation;
import com.mrprez.roborally.model.history.Rotation;
import com.mrprez.roborally.model.history.Round;
import com.mrprez.roborally.model.history.Step;
import com.mrprez.roborally.model.history.Translation;
import com.mrprez.roborally.model.history.Stage;

public class GameDaoImpl extends AbstractDao implements GameDao {
	
	private DozerBeanMapper dozerMapper = new DozerBeanMapper();
	
	
	@Override
	public List<Game> selectGameList(String username) {
		return getSession().selectList("selectUserGames", username);
	}

	@Override
	public Game loadGame(Integer id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Game game = new Game();
		GameBoardDto gameBoardDto = getSession().selectOne("selectGame", id);
		GameBoard gameBoard = new GameBoard(gameBoardDto.getBoardId(), gameBoardDto.getSizeX(), gameBoardDto.getSizeY());
		game.setId(id);
		game.setName(gameBoardDto.getName());
		game.setBoard(gameBoard);
		
		// Squares
		loadSquares(gameBoard);
		
		// Start square
		game.getBoard().setStartSquare(gameBoardDto.getStartX(), gameBoardDto.getStartY());
		
		// Robot
		game.getRobotList().addAll(loadRobots(gameBoard));
		
		// History
		game.getHistory().addAll(loadHistory(game));
		
		return game;
	}
	
	
	private void loadSquares(GameBoard gameBoard) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException{
		List<SquareDto> squareDtoList = getSession().selectList("selectSquareList", gameBoard.getId());
		for(SquareDto squareDto : squareDtoList){
			@SuppressWarnings("unchecked")
			Constructor<Square> squareConstructor = (Constructor<Square>) Class.forName(squareDto.getClazz()).getConstructor(Integer.class, Integer.class, Board.class);;
			Square square = squareConstructor.newInstance(squareDto.getX(), squareDto.getY(), gameBoard);
			square.setArgs(squareDto.getArgs());
			dozerMapper.map(squareDto, square);
			gameBoard.addSquare(square);
		}
	}
	
	private List<Robot> loadRobots(GameBoard gameBoard){
		List<Robot> robotList = new ArrayList<Robot>();
		List<RobotDto> robotDtoList = getSession().selectList("selectRobotList", gameBoard.getId());
		for(RobotDto robotDto : robotDtoList){
			Square square = gameBoard.getSquare(robotDto.getX(), robotDto.getY());
			Robot robot = new Robot(square);
			dozerMapper.map(robotDto, robot);
			robotList.add(robot);
		}
		return robotList;
	}
	
	private List<Round> loadHistory(Game game){
		List<Round> history = new ArrayList<Round>();
		Round round = null;
		Stage turn = null;
		Step step = null;
		for(Object dtoObject : getSession().selectList("selectStepList", game.getId())){
			StepDto stepDto = (StepDto) dtoObject;
			if(round==null || round.getNumber()!=stepDto.getRoundNb()){
				round = new Round(stepDto.getRoundNb());
				history.add(round);
				turn = null;
			}
			if(turn==null || turn.getNumber()!=stepDto.getTurnNb()){
				turn = new Stage(stepDto.getTurnNb());
				round.addTurn(turn);
				step = null;
			}
			if(step==null || step.getNumber()!=stepDto.getStepNb()){
				if(stepDto.getCardRapidity()!=null){
					step = new Step(stepDto.getStepNb(), stepDto.getCardRapidity());
				} else {
					step = new Step(stepDto.getStepNb(), stepDto.getSquareX(), stepDto.getSquareY());
				}
				turn.addStep(step);
			}
			for(MoveDto moveDto : stepDto.getMoveList()){
				if(moveDto.getTranslation()!=null){
					if(moveDto.isSuccess()){
						step.addMove(new Translation(game.getRobot(moveDto.getRobotNb()), moveDto.getTranslation()));
					}else{
						step.addMove(new FailedTranslation(game.getRobot(moveDto.getRobotNb()), moveDto.getTranslation()));
					}
				} else {
					step.addMove(new Rotation(game.getRobot(moveDto.getRobotNb()), moveDto.getRotation()));
				}
			}
		}
		
		return history;
	}

	
	@Override
	public Robot loadPlayerRobot(Integer gameId, String username) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("gameId", gameId);
		params.put("username", username);
		return getSession().selectOne("selectPlayerRobot", params);
	}

	@Override
	public void saveHandCards(Integer gameId, int robotNumber, List<Card> cardList) {
		for(int index=0; index<cardList.size(); index++){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("gameId", gameId);
			params.put("rapidity", cardList.get(index).getRapidity());
			params.put("index", index);
			getSession().update("updateHandCard", params);
		}
	}

	

}
