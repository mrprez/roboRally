package com.mrprez.roborally.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import com.google.gwt.thirdparty.guava.common.base.Function;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.mrprez.roborally.bs.GameService;
import com.mrprez.roborally.model.Card;
import com.mrprez.roborally.model.PowerDownState;
import com.mrprez.roborally.model.Robot;
import com.mrprez.roborally.model.board.GameBoard;


public class IARobot extends Thread {
	private int gameId;
	private Robot robot;
	private double bestNote = Double.MAX_VALUE;
	private List<Card> bestCardOrder;
	private GameService gameService;
	
	
	public IARobot(int gameId, Robot robot, GameService gameService) {
		super();
		this.gameId = gameId;
		this.robot = robot;
		this.gameService = gameService;
	}
	
	
	public void run() {
		LoggerFactory.getLogger("IA").debug("Robot "+robot.getNumber()+" is on "+robot.getSquare().getX()+"-"+robot.getSquare().getY()+"("+robot.getDirection()+") and want to go to "+robot.getTarget().getX()+"-"+robot.getTarget().getY());
		List<Card> fixCards = new ArrayList<Card>();
		Collection<Card> possibilities = new HashSet<Card>();
		for(int i=0; i<robot.getHealth(); i++){
			possibilities.add(robot.getCard(i));
		}
		for(int i=robot.getHealth(); i<9; i++){
			fixCards.add(robot.getCard(i));
		}
		variateOneCard(fixCards, possibilities );
		
		LoggerFactory.getLogger("IA").debug("Robot "+robot.getNumber()+" best hand is :"+StringUtils.join(bestCardOrder, ", "));
		
		gameService.saveRobotCards(gameId, robot.getNumber(), Lists.transform(bestCardOrder, new Function<Card, Integer>() {
			@Override
			public Integer apply(Card card) {
				return card.getRapidity();
			}
		}));
		
		if(shouldPowerDown()){
			gameService.updatePowerDownState(gameId, robot.getNumber(), PowerDownState.PLANNED);
		}
	}
	
	
	private void variateOneCard(List<Card> fixCards, Collection<Card> possibilities){
		if(fixCards.size()>=5){
			List<Card> proposal = new ArrayList<Card>();
			proposal.addAll(fixCards);
			proposal.addAll(possibilities);
			GameBoard gameBoard = (GameBoard) robot.getSquare().getBoard();
			DummyBoard dummyBoard = new DummyBoard(gameBoard, robot, proposal);
			double note = dummyBoard.evaluate();
			if(note < bestNote){
				bestNote = note;
				bestCardOrder = proposal;
			}
		} else {
			for(Card possibility : possibilities){
				fixCards.add(0, possibility);
				Collection<Card> newPossibilities = new HashSet<Card>(possibilities);
				newPossibilities.remove(possibility);
				variateOneCard(fixCards, newPossibilities);
				fixCards.remove(0);
			}
		}
	}
	
	
	public boolean shouldPowerDown(){
		if(Math.abs(robot.getSquare().getX()-robot.getTarget().getX()) + Math.abs(robot.getSquare().getY()-robot.getTarget().getY()) <= bestNote
				&& robot.getHealth()<7){
			return true;
		}
		return false;
	}
	
	

}
