package com.mrprez.roborally.ai;

import java.util.ArrayList;
import java.util.List;

import com.mrprez.roborally.model.Card;
import com.mrprez.roborally.model.Direction;
import com.mrprez.roborally.model.Game;
import com.mrprez.roborally.model.Robot;
import com.mrprez.roborally.model.Square;
import com.mrprez.roborally.model.board.GameBoard;

public class DummyBoard extends GameBoard {
	private List<Card> cardList;
	private int x;
	private int y;
	private Direction direction;
	private int health;
	private int targetNumber;
	
	
	public DummyBoard(GameBoard board, Robot robot, List<Card> cardList){
		super(board.getId(), board.getSizeX(), board.getSizeY());
		squares = new Square[sizeX][sizeY];
		for(int x=0; x<sizeX; x++){
			for(int y=0; y<sizeY; y++){
				squares[x][y] = board.getSquare(x, y).copyForNewBoard(this);
			}
		}
		for(Square target : board.getTargetSquares()){
			targetSquares.add(getSquare(target.getX(), target.getY()));
		}
		this.x = robot.getSquare().getX();
		this.y = robot.getSquare().getY();
		this.direction = robot.getDirection();
		this.health = robot.getHealth();
		this.targetNumber = robot.getTargetNumber();
		this.cardList = new ArrayList<Card>(cardList);
	}
	
	
	public double evaluate(){
		Square square = getSquare(x, y);
		Robot robot = new Robot(square, false);
		robot.setHealth(health);
		robot.setTargetNumber(targetNumber);
		robot.setDirection(direction);
		robot.initCards(cardList);
		for(int turn=0; turn<Game.STAGE_NB; turn++){
			robot.playCard(turn);
			if(robot.getHealth()==0){
				return Math.abs(getSizeX()) + Math.abs(getSizeY());
			}
			robot.getSquare().play();
			if(robot.getHealth()==0){
				return Math.abs(getSizeX()) + Math.abs(getSizeY());
			}
			if(robot.isOnTarget()){
				robot.setTargetNumber(robot.getTargetNumber()+1);
				if(robot.getTarget() != null){
					targetNumber = robot.getTargetNumber();
					return evaluate() / (Math.abs(getSizeX()) + Math.abs(getSizeY()));
				}else{
					return 0.0;
				}
			}
		}
		double result =  Math.abs(robot.getTarget().getX() - robot.getSquare().getX())
				+ Math.abs(robot.getTarget().getY() - robot.getSquare().getY());
		
		if(result == 1.0){
			for(int i=0; i<Direction.values().length; i++){
				Square nextSquare = robot.getSquare().getNextSquare(Direction.values()[i]);
				if(nextSquare!=null && nextSquare.hasSameXY(robot.getTarget())){
					if(! robot.canMove(Direction.values()[i])){
						return 2.0;
					}
				}
			}
		}
		
		return result;
	}

	public List<Card> getCardList() {
		return cardList;
	}

	
	

}
