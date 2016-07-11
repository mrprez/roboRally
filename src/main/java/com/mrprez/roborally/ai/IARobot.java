package com.mrprez.roborally.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import com.mrprez.roborally.model.Card;
import com.mrprez.roborally.model.Robot;
import com.mrprez.roborally.model.board.GameBoard;


public class IARobot {
	private Map<DummyBoard, FutureTask<Double>> dummyBoardMap = new HashMap<DummyBoard, FutureTask<Double>>();
	private Robot robot;
	private Executor executor;
	private double bestNote = Double.MAX_VALUE;
	
	
	public IARobot(Robot robot) {
		super();
		this.robot = robot;
		this.executor = Executors.newFixedThreadPool(10);
	}
	
	public List<Card> orderCard() throws InterruptedException, ExecutionException{
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
		
		DummyBoard bestEvaluation = null;
		for(DummyBoard dummyBoard : dummyBoardMap.keySet()){
			FutureTask<Double> futureTask = dummyBoardMap.get(dummyBoard);
			if(bestNote>futureTask.get()){
				bestNote = futureTask.get();
				bestEvaluation = dummyBoard;
			}
		}
		
		LoggerFactory.getLogger("IA").debug("Robot "+robot.getNumber()+" best hand is :"+StringUtils.join(bestEvaluation.getCardList(), ", "));
		
		
		return bestEvaluation.getCardList();
	}
	
	
	private void variateOneCard(List<Card> fixCards, Collection<Card> possibilities){
		if(fixCards.size()>=5){
			List<Card> proposal = new ArrayList<Card>();
			proposal.addAll(fixCards);
			proposal.addAll(possibilities);
			GameBoard gameBoard = (GameBoard) robot.getSquare().getBoard();
			DummyBoard dummyBoard = new DummyBoard(gameBoard, robot, proposal);
			FutureTask<Double> futureTask = new FutureTask<Double>(dummyBoard);
			dummyBoardMap.put(dummyBoard, futureTask);
			executor.execute(futureTask);
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
		if(Math.pow(robot.getSquare().getX()-robot.getTarget().getX(), 2) + Math.pow(robot.getSquare().getY()-robot.getTarget().getY(), 2) < bestNote
				&& robot.getHealth()<7){
			return true;
		}
		return false;
	}
	
	

}
