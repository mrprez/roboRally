package com.mrprez.roborally.ai;

import java.util.List;

import com.mrprez.roborally.model.Card;
import com.mrprez.roborally.model.Direction;
import com.mrprez.roborally.model.Game;
import com.mrprez.roborally.model.Robot;
import com.mrprez.roborally.model.Square;
import com.mrprez.roborally.model.board.Board;
import com.mrprez.roborally.model.board.GameBoard;

public class DummyBoard extends Board {
	
	private List<Square> targetSquares;

	
	public DummyBoard(GameBoard board){
		super();
		sizeX = board.getSizeX();
		sizeY = board.getSizeY();
		squares = new Square[sizeX][sizeY];
		for(int x=0; x<sizeX; x++){
			for(int y=0; y<sizeY; y++){
				squares[x][y] = board.getSquare(x, y).copyForNewBoard(this);
			}
		}
		for(Square target : board.getTargetSquares()){
			targetSquares.add(getSquare(target.getX(), target.getY()));
		}
	}
	
	public double evaluate(int x, int y, Direction direction, int health, int targetNumber, List<Card> cards){
		Square square = getSquare(x, y);
		Robot robot = new Robot(square);
		robot.setHealth(health);
		robot.setTargetNumber(targetNumber);
		robot.setDirection(direction);
		robot.initCards(cards);
		for(int turn=0; turn<Game.STAGE_NB; turn++){
			robot.playCard(turn);
			robot.getSquare().play();
			if(robot.isOnTarget()){
				robot.setTargetNumber(robot.getTargetNumber()+1);
				if(robot.getTarget() != null){
					return evaluate(robot.getSquare().getX(), robot.getSquare().getY(), robot.getDirection(), robot.getHealth(), robot.getTargetNumber(), cards) / (Math.pow(getSizeX(), 2) + Math.pow(getSizeY(), 2));
				}else{
					return 0.0;
				}
			}
		}
		double result =  Math.pow(robot.getTarget().getX() - robot.getSquare().getX(), 2)
				+ Math.pow(robot.getTarget().getY() - robot.getSquare().getY(), 2);
		
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
	



}
