package com.mrprez.roborally.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.mrprez.roborally.ai.AIRobot;
import com.mrprez.roborally.model.board.GameBoard;


public class Game {
	public static int TURN_NB = 5;
	
	private Integer id;
	private String name;
	private GameBoard board;
	private Set<Robot> robotList = new HashSet<Robot>();
	private CardStock cardStock = new CardStock();
	private List<Round> history = new ArrayList<Round>();
	
	
	public void addRobot(){
		Robot robot = new Robot(board.getStartSquare());
		robot.setNumber(robotList.size());
		robotList.add(robot);
	}

	public void addAIRobot(){
		Robot robot = new AIRobot(board.getStartSquare());
		robot.setNumber(robotList.size());
		robotList.add(robot);
	}
	
	public void start(){
		for(Robot robot: robotList){
			List<Card> initCards = new ArrayList<Card>();
			for(int i=0; i<robot.getHealth(); i++){
				initCards.add(cardStock.takeCard());
			}
			robot.initCards(initCards);
		}
	}
	
	public void play(){
		Round round = new Round();
		for(int turn=0; turn<TURN_NB; turn++){
			TurnResult turnResult = new TurnResult();
			round.addTurnResult(turnResult);
			
			// On joue les cartes
			TreeSet<Robot> robotOrderedList = new TreeSet<Robot>(new InitComparator(turn));
			robotOrderedList.addAll(robotList);
			for(Robot robot : robotOrderedList){
				Step step = robot.playCard(turn);
				turnResult.addStep(step);
			}
			
			// on vérifie les fantomes
			for(Robot robot : robotList){
				if(robot.isGhost()){
					boolean ghost = false;
					for(Robot otherRobot : robotList){
						if(robot.getNumber() != otherRobot.getNumber()){
							if(otherRobot.getSquare().getX() == robot.getSquare().getX()
									&& otherRobot.getSquare().getY() == robot.getSquare().getY()){
								ghost = true;
							}
						}
					}
					if(!ghost){
						robot.setGhost(false);
						robot.getSquare().setRobot(robot);
					}
				}
			}
			
			// On joue le terrain
			for(Robot robot : robotOrderedList){
				Step step = robot.getSquare().play();
				turnResult.addStep(step);
			}
			
			// on vérifie si les robot ont atteind une cible
			for(Robot robot : robotOrderedList){
				if(robot.isOnTarget()){
					robot.setTargetNumber(robot.getTargetNumber()+1);
				}
			}
		}
		history.add(round);
		
		// On redistribue les cartes
		for(Robot robot : robotList){
			Collection<Card> newCards = new HashSet<Card>();
			for(int i=0; i<robot.getHealth(); i++){
				newCards.add(cardStock.takeCard());
			}
			Collection<Card> discarded = robot.changeCards(newCards);
			cardStock.discard(discarded);
		}
	}
	
	public Set<Robot> getRobotList(){
		return robotList;
	}
	
	public GameBoard getBoard(){
		return board;
	}
	
	public void setBoard(GameBoard board) {
		this.board = board;
	}
	
	public List<Round> getHistory(){
		return history;
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
	
	
	
}
