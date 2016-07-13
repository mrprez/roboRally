package com.mrprez.roborally.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dozer.DozerBeanMapper;

import com.mrprez.roborally.dto.ActionDto;
import com.mrprez.roborally.dto.CardDto;
import com.mrprez.roborally.dto.GameBoardDto;
import com.mrprez.roborally.dto.MoveDto;
import com.mrprez.roborally.dto.RobotDto;
import com.mrprez.roborally.dto.RobotStateDto;
import com.mrprez.roborally.dto.SquareDto;
import com.mrprez.roborally.dto.TargetDto;
import com.mrprez.roborally.model.Card;
import com.mrprez.roborally.model.CardStock;
import com.mrprez.roborally.model.Direction;
import com.mrprez.roborally.model.Game;
import com.mrprez.roborally.model.PowerDownState;
import com.mrprez.roborally.model.Robot;
import com.mrprez.roborally.model.RobotState;
import com.mrprez.roborally.model.Square;
import com.mrprez.roborally.model.board.Board;
import com.mrprez.roborally.model.board.GameBoard;
import com.mrprez.roborally.model.history.Action;
import com.mrprez.roborally.model.history.Move;
import com.mrprez.roborally.model.history.Round;
import com.mrprez.roborally.model.history.Stage;
import com.mrprez.roborally.model.history.Step;

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
		
		// Targets
		for(TargetDto targetDto : gameBoardDto.getTargetList()){
			gameBoard.getTargetSquares().add(gameBoard.getSquare(targetDto.getX(), targetDto.getY()));
		}
		
		// Robot
		game.getRobotList().addAll(loadRobots(game));
		
		// History
		game.getHistory().addAll(loadHistory(game));
		
		// CardStock
		game.setCardStock(loadCardStock(game.getId()));
		
		return game;
	}
	
	
	private CardStock loadCardStock(int gameId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("gameId", gameId);
		params.put("discarded", false);
		List<Card> cardList = getSession().selectList("selectCardList", params);
		
		params.put("discarded", true);
		List<Card> discard = getSession().selectList("selectCardList", params);
		
		return new CardStock(cardList, discard);
	}
	
	
	private void loadSquares(GameBoard gameBoard) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException{
		List<SquareDto> squareDtoList = getSession().selectList("selectSquareList", gameBoard.getId());
		for(SquareDto squareDto : squareDtoList){
			@SuppressWarnings("unchecked")
			Constructor<Square> squareConstructor = (Constructor<Square>) Class.forName(squareDto.getClazz()).getConstructor(Integer.class, Integer.class, Board.class);;
			Square square = squareConstructor.newInstance(squareDto.getX(), squareDto.getY(), gameBoard);
			square.setArgs(squareDto.getArgs());
			square.setWall(Direction.DOWN, squareDto.isWallDown());
			square.setWall(Direction.UP, squareDto.isWallUp());
			square.setWall(Direction.LEFT, squareDto.isWallLeft());
			square.setWall(Direction.RIGHT, squareDto.isWallRight());
			gameBoard.addSquare(square);
		}
	}
	
	private List<Robot> loadRobots(Game game){
		List<Robot> robotList = new ArrayList<Robot>();
		List<RobotDto> robotDtoList = getSession().selectList("selectRobotList", game.getId());
		for(RobotDto robotDto : robotDtoList){
			Square square = null;
			if(robotDto.getX()!=null && robotDto.getY()!=null){
				square = game.getBoard().getSquare(robotDto.getX(), robotDto.getY());
			}
			Robot robot = new Robot(square, robotDto.getGhost());
			dozerMapper.map(robotDto, robot);
			for(CardDto cardDto : robotDto.getCardList()){
				robot.getCards().add(cardDto.buildCard());
			}
			robotList.add(robot);
		}
		return robotList;
	}
	
	private List<Round> loadHistory(Game game){
		List<Round> roundList = loadRounds(game);
		for(RobotStateDto robotStateDto : getSession().<RobotStateDto>selectList("selectState", game.getId())){
			Round round = roundList.get(robotStateDto.getRoundNb());
			RobotState robotState = new RobotState();
			dozerMapper.map(robotStateDto, robotState);
			round.setState(game.getRobot(robotStateDto.getRobotNb()), robotState);
		}
		return roundList;
	}
	
	private List<Round> loadRounds(Game game){
		List<Round> history = new ArrayList<Round>();
		Round round = null;
		for(ActionDto actionDto : getSession().<ActionDto>selectList("selectHistory", game.getId())){
			if(round==null || round.getNumber()!=actionDto.getRoundNb()){
				round = new Round(actionDto.getRoundNb());
				history.add(round);
			}
			Stage stage = round.getStage(actionDto.getStageNb());
			Action action;
			if(actionDto.getCardRapidity()!=null){
				action = new Action(actionDto.getCardRapidity());
			} else if(actionDto.getSquareX()!=null && actionDto.getSquareY()!=null) {
				action = new Action(actionDto.getSquareX(), actionDto.getSquareY());
			} else {
				action = new Action();
			}
			stage.addAction(action);
			Step step = null;
			for(MoveDto moveDto : actionDto.getMoveList()){
				if(step==null || moveDto.getStepNb()==action.getStepList().size()){
					step = new Step();
					action.addStep(step);
				}
				Robot robot = game.getRobot(moveDto.getRobotNb());
				step.addMove(new Move(moveDto.getType(), moveDto.getArgs(), robot));
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

	@Override
	public void insertNewGame(Game game) {
		getSession().insert("insertGameBoard", game.getBoard());
		for(int x=0; x<game.getBoard().getSizeX(); x++){
			for(int y=0; y<game.getBoard().getSizeY(); y++){
				getSession().insert("insertSquare", new SquareDto(game.getBoard().getSquare(x, y)));
			}
		}
		int targetNb = 0;
		for(Square target : game.getBoard().getTargetSquares()){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("boardId", game.getBoard().getId());
			params.put("targetNb", targetNb++);
			params.put("x", target.getX());
			params.put("y", target.getY());
			getSession().insert("insertTarget", params);
		}
		getSession().insert("insertGame", game);
		int index = 0;
		for(Card card : game.getCardStock().getStock()){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("gameId", game.getId());
			params.put("card", card);
			params.put("index", index++);
			getSession().insert("insertCard", params);
		}	
		for(Robot robot : game.getRobotList()){
			Map<String, Object> robotParams = new HashMap<String, Object>();
			robotParams.put("gameId", game.getId());
			robotParams.put("robot", robot);
			getSession().insert("insertRobot", robotParams);
			index = 0;
			for(Card card : robot.getCards()){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("gameId", game.getId());
				params.put("card", card);
				params.put("index", index++);
				params.put("robotNb", robot.getNumber());
				getSession().insert("insertCard", params);
			}
		}
	}

	@Override
	public void updateGame(Game game) {
		for(Robot robot : game.getRobotList()){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("robot", robot);
			params.put("gameId", game.getId());
			getSession().update("updateRobot", params);
		}
		int index=0;
		for(Card card : game.getCardStock().getStock()){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("gameId", game.getId());
			params.put("index", index++);
			params.put("card", card);
			params.put("discarded", false);
			getSession().update("updateCard", params);
		}
		for(Card card : game.getCardStock().getDiscard()){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("gameId", game.getId());
			params.put("card", card);
			params.put("discarded", true);
			getSession().update("updateCard", params);
		}
		for(Robot robot : game.getRobotList()){
			index=0;
			for(Card card : robot.getCards()){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("gameId", game.getId());
				params.put("index", index++);
				params.put("card", card);
				params.put("robotNb", robot.getNumber());
				params.put("discarded", false);
				getSession().update("updateCard", params);
			}
		}
		
	}

	@Override
	public void saveRound(Integer gameId, Round round) {
		for(Robot robot : round.getStateMap().keySet()){
			RobotState robotState = round.getStateMap().get(robot);
			Map<String, Object> stateParam = new HashMap<String, Object>();
			stateParam.put("gameId", gameId);
			stateParam.put("roundNb", round.getNumber());
			stateParam.put("robotNb", robot.getNumber());
			stateParam.put("state", robotState);
			getSession().insert("insertRobotState", stateParam);
		}
		
		for(Stage stage : round.getStageList()){
			int actionIndex = 0;
			for(Action action : stage.getActionList()){
				Map<String, Object> actionParams = new HashMap<String, Object>();
				actionParams.put("gameId", gameId);
				actionParams.put("roundNb", round.getNumber());
				actionParams.put("stageNb", stage.getNumber());
				actionParams.put("actionIndex", actionIndex);
				actionParams.put("action", action);
				getSession().insert("insertAction", actionParams);
				int stepIndex = 0;
				for(Step step : action.getStepList()){
					int moveIndex = 0;
					for(Move move : step.getMoveList()){
						Map<String, Object> moveParams = new HashMap<String, Object>();
						moveParams.put("gameId", gameId);
						moveParams.put("roundNb", round.getNumber());
						moveParams.put("stageNb", stage.getNumber());
						moveParams.put("actionIndex", actionIndex);
						moveParams.put("stepIndex", stepIndex);
						moveParams.put("moveIndex", moveIndex++);
						moveParams.put("move", move);
						getSession().insert("insertMove", moveParams);
					}
					stepIndex++;
				}
				actionIndex++;
			}
		}
	}

	@Override
	public void updatePowerDownState(Integer gameId, Integer robotNb, PowerDownState powerDownState) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("gameId", gameId);
		params.put("robotNb", robotNb);
		params.put("powerDownState", powerDownState);
		getSession().update("updateRobotPowerDownState", params);
	}

	

}
