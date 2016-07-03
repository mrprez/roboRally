package com.mrprez.roborally.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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
	}
	
	public Round play(){
		Logger logger = LoggerFactory.getLogger("GamePlay");
		Round round = new Round(history.size());
		for(Robot robot : robotList){
			round.setState(robot, robot.getState());
		}
		logger.debug("Start play round "+round.getNumber());
		for(int stageNb=0; stageNb<STAGE_NB; stageNb++){
			logger.debug("Start stage "+stageNb);
			Stage stage = round.getStage(stageNb);
			
			// On joue les cartes
			TreeSet<Robot> robotOrderedList = new TreeSet<Robot>(new InitComparator(stageNb));
			robotOrderedList.addAll(robotList);
			for(Robot robot : robotOrderedList){
				logger.debug("Robot "+robot.getNumber()+" play card "+robot.getCard(stageNb));
				stage.addAction(robot.playCard(stageNb));
			}
			
			// on vérifie les fantomes
			for(Robot robot : robotList){
				if(robot.isGhost()){
					boolean ghost = false;
					for(Robot otherRobot : robotList){
						if(robot.getNumber() != otherRobot.getNumber()){
							if(otherRobot.getSquare().getX() == robot.getSquare().getX()
									&& otherRobot.getSquare().getY() == robot.getSquare().getY()){
								logger.debug("Robot "+robot.getNumber()+" is still ghost due to robot "+otherRobot.getNumber());
								ghost = true;
							}
						}
					}
					if(!ghost){
						logger.debug("Robot "+robot.getNumber()+" is no more ghost on square "+robot.getSquare());
						robot.setGhost(false);
						robot.getSquare().setRobot(robot);
						stage.addAction(buildUnghostAction(robot));
					}
				}
			}
			
			// On joue le terrain
			for(Robot robot : robotOrderedList){
				stage.addAction( robot.getSquare().play());
			}
			
			// on vérifie si les robot ont atteind une cible
			for(Robot robot : robotOrderedList){
				if(robot.isOnTarget()){
					logger.debug("Robot "+robot.getNumber()+" has reached its target");
					robot.setTargetNumber(robot.getTargetNumber()+1);
				}
			}
		}
		
		// On redistribue les cartes
		for(Robot robot : robotList){
			Collection<Card> newCards = new HashSet<Card>();
			for(int i=0; i<robot.getHealth(); i++){
				newCards.add(cardStock.takeCard());
			}
			Collection<Card> discarded = robot.changeCards(newCards);
			cardStock.discard(discarded);
		}
		
		history.add(round);
		
		return round;
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

	
	
}
