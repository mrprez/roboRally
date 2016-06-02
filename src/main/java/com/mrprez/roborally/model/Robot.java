package com.mrprez.roborally.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.mrprez.roborally.model.board.GameBoard;


public class Robot {
	private int number;
	private Square square;
	private int health = 9;
	private Direction direction = Direction.UP;
	private List<Card> cards = new ArrayList<Card>(9);
	private boolean ghost = true;
	private int targetNumber = 0;
	
	
	public Robot(Square square) {
		super();
		this.square = square;
	}


	public Square getSquare() {
		return square;
	}
	public void setSquare(Square square) {
		this.square = square;
		if(square.getRobot()!=this && !ghost){
			square.setRobot(this);
		}
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public boolean isGhost() {
		return ghost;
	}
	public void setGhost(boolean ghost) {
		this.ghost = ghost;
	}
	public int getTargetNumber() {
		return targetNumber;
	}
	public void setTargetNumber(int targetNumber) {
		this.targetNumber = targetNumber;
	}


	public void initCards(Collection<Card> initCards){
		cards.addAll(initCards);
	}
	
	public Card getCard(int turn){
		return cards.get(turn);
	}
	
	protected void setCards(List<Card> cards){
		this.cards = cards;
	}
	
	public Collection<Card> changeCards(Collection<Card> newCards){
		Collection<Card> garbage = new HashSet<Card>();
		int i=0;
		for(Card newCard : newCards){
			garbage.add(cards.get(i));
			cards.set(i, newCard);
			i++;
		}
		return garbage;
	}
	
	public List<Step> playCard(int turn){
		List<Step> stepList = new ArrayList<Step>();
//		Card card = cards.get(turn);
//		Step step = new Step(card);
//		for(int i=0; i<card.getTranslation(); i++){
//			if(canMove(direction)){
//				step.addAllMove(move(direction));
//			}else{
//				step.addMove(new Move(this, direction, false));
//			}
//		}
//		for(int i=0; i>card.getTranslation(); i--){
//			if(canMove(direction.getOpposite())){
//				step.addAllMove(move(direction.getOpposite()));
//			}else{
//				step.addMove(new Move(this, direction.getOpposite(), false));
//			}
//		}
//		step.addMove(rotate(card.getRotation()));
		return stepList;
	}
	
	public boolean canMove(Direction direction){
		if(square.getWall(direction)){
			return false;
		}
		Square destination = square.getNextSquare(direction);
		if(destination == null){
			return true;
		}
		if(destination.getWall(direction.getOpposite())){
			return false;
		}
		if(destination.getRobot()!=null && !ghost){
			return destination.getRobot().canMove(direction);
		}
		return true;
	}
	
	public List<Move> move(Direction direction){
		List<Move> moveList = new ArrayList<Move>(); 
		moveList.add(new Move(this, direction, true));
		Square destination = square.getNextSquare(direction);
		if(destination.getRobot()!=null && !ghost){
			moveList.addAll(destination.getRobot().move(direction));
		}
		square.setRobot(null);
		setSquare(destination);
		return moveList;
	}
	
	public Move rotate(int r){
		direction = direction.rotate(r);
		return new Move(this, r);
	}
	
	public boolean isOnTarget(){
		Square target = getTarget();
		if(target == null){
			return false;
		}
		return target.getX() == square.getX() && target.getY() == square.getY();
	}
	
	public Square getTarget(){
		GameBoard gameBoard = (GameBoard)square.getBoard();
		if(targetNumber < gameBoard.getTargetSquares().size()){
			return gameBoard.getTargetSquares().get(targetNumber);
		}
		return null;
	}
	
	

}
