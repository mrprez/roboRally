package com.mrprez.roborally.model;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mrprez.roborally.model.board.GameBoard;
import com.mrprez.roborally.model.history.Action;
import com.mrprez.roborally.model.history.Move;
import com.mrprez.roborally.model.history.MoveType;
import com.mrprez.roborally.model.history.Round;
import com.mrprez.roborally.model.history.Stage;
import com.mrprez.roborally.model.history.Step;


public class Game {
	public final static int STAGE_NB = 5;
	
	private Integer id;
	private String name;
	private GameState state = GameState.INITIALIZATION;
	private String ownername;
	private GameBoard board;
	private List<Robot> robotList = new ArrayList<Robot>();
	private CardStock cardStock;
	private List<Round> history = new ArrayList<Round>();
	
	
	public Robot addRobot(){
		Robot robot = new Robot(board.getStartSquare(), true);
		robot.setNumber(robotList.size());
		robotList.add(robot);
		return robot;
	}
	
	public void start(){
		cardStock = new CardStock();
		for(Robot robot: robotList){
			List<Card> initCards = new ArrayList<Card>();
			for(int i=0; i<robot.getHealth(); i++){
				initCards.add(cardStock.takeCard());
			}
			robot.initCards(initCards);
		}
		state = GameState.ONGOING;
	}
	
	public Round play(){
		Logger logger = LoggerFactory.getLogger("GamePlay");
		Round round = new Round(history.size());
		for(Robot robot : robotList){
			if(robot.getSquare()!=null){
				logger.debug("Robot "+robot.getNumber()+" is on "
						+robot.getSquare().getX()+","+robot.getSquare().getY()+"("+robot.getDirection()+") "
						+"with "+robot.getHealth()+"PV");
				round.setState(robot, robot.getState());
			}
		}
		logger.debug("Start play round "+round.getNumber());
		for(int stageNb=0; stageNb<STAGE_NB; stageNb++){
			logger.debug("Start stage "+stageNb);
			Stage stage = round.getStage(stageNb);
			
			// On filtre et ordonne les robots  en fonction de leur health (pour le filtre) et de l'initiative des cartes (pour les ordonner)
			TreeSet<Robot> robotOrderedList = new TreeSet<Robot>(new InitComparator(stageNb));
			for(Robot robot : robotList){
				if(robot.getHealth()>0 && robot.getPowerDownState()!=PowerDownState.ONGOING){
					robotOrderedList.add(robot);
				}
			}
			
			// On joue les cartes
			for(Robot robot : robotOrderedList){
				logger.debug("Robot "+robot.getNumber()+" play card "+robot.getCard(stageNb));
				stage.addAction(robot.playCard(stageNb));
			}
			
			// on vérifie les fantomes
			checkGhosts(stage);
			
			// On joue le terrain
			for(Robot robot : robotList){
				if(robot.getSquare()!=null && !robot.isGhost()){
					stage.addAction( robot.getSquare().play());
				}
			}
			
			// on vérifie les fantomes
			checkGhosts(stage);
			
			// on tire des laser
			for(Robot robot : robotOrderedList){
				if( ! robot.isGhost() ){
					stage.addAction( robot.fireLaser() );
				}
			}
						
			// on vérifie si les robot ont atteind une cible
			for(Robot robot : robotList){
				if(robot.isOnTarget()){
					logger.info("Robot "+robot.getNumber()+" has reached its target number "+robot.getTargetNumber());
					robot.setTargetNumber(robot.getTargetNumber()+1);
					if(robot.getTarget()==null){
						if(robot.getSquare().getRobot()==robot){
							robot.getSquare().host(null);
						}
						robot.walkOn(null);
						robot.setHealth(0);
						robot.setPowerDownState(PowerDownState.NONE);
						stage.addAction(buildWinAction(robot));
					}else{
						stage.addAction(buildReachedTargetAction(robot));
					}
				}
			}
		}
		
		// On repositionne les robots sans PV
		for(Robot robot : robotList){
			if(robot.getHealth()==0 && robot.getTargetNumber()<board.getTargetSquares().size()){
				logger.debug("Robot "+robot.getNumber()+" is dead");
				round.getStage(STAGE_NB-1).addAction(setRobotOnCheckpoint(robot));
			}
		}
		
		// On gère le power down
		for(Robot robot : robotList){
			if(robot.getPowerDownState()==PowerDownState.ONGOING){
				logger.debug("End of power down for robot "+robot.getNumber());
				robot.setPowerDownState(PowerDownState.NONE);
				round.getStage(STAGE_NB-1).addAction(new Action(new Step(new Move(MoveType.POWER_DOWN, PowerDownState.NONE.name(), robot))));
			}
			if(robot.getPowerDownState()==PowerDownState.PLANNED){
				logger.debug("Start of power down for robot "+robot.getNumber());
				robot.setHealth(9);
				robot.setPowerDownState(PowerDownState.ONGOING);
				round.getStage(STAGE_NB-1).addAction(new Action(new Step(new Move(MoveType.POWER_DOWN, PowerDownState.ONGOING.name(), robot))));
			}
		}
		
		// On redistribue les cartes
		for(Robot robot : robotList){
			changeCards(robot);
			robot.setHasPlayed(false);
		}
		
		history.add(round);
		
		return round;
	}
	
	
	private void changeCards(Robot robot){
		if(robot.getTarget()==null){
			cardStock.discard(robot.giveBackCards());
			return;
		}
		if(robot.getPowerDownState()==PowerDownState.ONGOING){
			cardStock.discard(robot.giveBackCards());
			return;
		}
		
		robot.changeCards(cardStock);
	}
	
	
	private Action setRobotOnCheckpoint(Robot robot){
		Action action = new Action();
		action.addStep(new Step(new Move(MoveType.DISAPPEAR, null, robot)));
		robot.setGhost(true);
		int checkPointNb = robot.getTargetNumber()-1;
		Square checkpointSquare = board.getTargetSquares().get(checkPointNb);
		robot.walkOn(checkpointSquare);
		robot.setHealth(9);
		action.addStep(new Step(new Move(MoveType.APPEAR, checkpointSquare.getX()+","+checkpointSquare.getY(), robot)));
		return action;
	}
	
	
	private void checkGhosts(Stage stage){
		Logger logger = LoggerFactory.getLogger("GamePlay");
		for(Robot robot : robotList){
			if(robot.getSquare()!=null && robot.isGhost()){
				boolean ghost = false;
				for(Robot otherRobot : robotList){
					if(robot.getNumber() != otherRobot.getNumber()){
						if(otherRobot.getSquare()!=null && otherRobot.getSquare().getX() == robot.getSquare().getX()
								&& otherRobot.getSquare().getY() == robot.getSquare().getY()){
							logger.debug("Robot "+robot.getNumber()+" is still ghost due to robot "+otherRobot.getNumber());
							ghost = true;
						}
					}
				}
				if(!ghost){
					logger.debug("Robot "+robot.getNumber()+" is no more ghost on square "+robot.getSquare());
					robot.setGhost(false);
					robot.getSquare().host(robot);
					stage.addAction(buildUnghostAction(robot));
				}
			}
		}
	}
	
	
	private Action buildWinAction(Robot robot){
		Move targetMove = new Move(MoveType.WIN, null, robot);
		return new Action(new Step(targetMove));
	}
	
	
	private Action buildReachedTargetAction(Robot robot){
		Action targetAction = new Action();
		Step targetStep = new Step();
		targetAction.addStep(targetStep);
		Move targetMove = new Move(MoveType.REACHED_TARGET, null, robot);
		targetStep.addMove(targetMove);
		return targetAction;
	}
	
	
	private Action buildUnghostAction(Robot robot){
		Move move = new Move(MoveType.UNGHOST, null, robot);
		Step step = new Step();
		step.addMove(move);
		Action action = new Action();
		action.addStep(step);
		return action;
	}
	
	public List<Robot> getRobotList(){
		return robotList;
	}
	
	public GameBoard getBoard(){
		return board;
	}
	
	public void setBoard(GameBoard board) {
		this.board = board;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Round> getHistory() {
		return history;
	}

	public Robot getRobot(int robotNb) {
		return robotList.get(robotNb);
	}

	public String getOwnername() {
		return ownername;
	}

	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}

	public CardStock getCardStock() {
		return cardStock;
	}

	public void setCardStock(CardStock cardStock) {
		this.cardStock = cardStock;
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public static enum GameState{
		INITIALIZATION, ONGOING, FINISHED
	}
	
}
