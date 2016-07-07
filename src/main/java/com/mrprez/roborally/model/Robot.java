package com.mrprez.roborally.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.mrprez.roborally.model.board.GameBoard;
import com.mrprez.roborally.model.history.Action;
import com.mrprez.roborally.model.history.Move;
import com.mrprez.roborally.model.history.MoveType;
import com.mrprez.roborally.model.history.Step;

public class Robot {
	private int number;
	private Square square;
	private int health = 9;
	private Direction direction = Direction.UP;
	private List<Card> cards = new ArrayList<Card>(9);
	private boolean ghost = true;
	private Integer targetNumber = 1;
	private String username;
	
	
	protected Robot(){
		super();
	}
	
	public Robot(Square square, boolean ghost) {
		super();
		this.ghost = ghost;
		setSquare(square);
	}


	public Square getSquare() {
		return square;
	}
	public void setSquare(Square square) {
		this.square = square;
		if(square!=null  && square.getRobot()!=this && !ghost){
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
	public Integer getTargetNumber() {
		return targetNumber;
	}
	public void setTargetNumber(Integer targetNumber) {
		this.targetNumber = targetNumber;
	}


	public void initCards(Collection<Card> initCards){
		cards.addAll(initCards);
	}
	
	public Card getCard(int turn){
		return cards.get(turn);
	}
	
	public List<Card> getCards(){
		return cards;
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
	
	public Action playCard(int stageNb){
		if(health==0){
			return null;
		}
		Card card = cards.get(stageNb);
		Action action = new Action(card);
		for(int i=0; i<card.getTranslation(); i++){
			Step step = new Step();
			if(canMove(direction)){
				step.addAllMove(move(direction));
			}else{
				step.addMove(new Move(MoveType.FAILED_TRANSLATION, direction.name(), this));
			}
			action.addStep(step);
		}
		for(int i=0; i>card.getTranslation(); i--){
			Step step = new Step();
			if(canMove(direction.getOpposite())){
				step.addAllMove(move(direction.getOpposite()));
			}else{
				step.addMove(new Move(MoveType.FAILED_TRANSLATION, direction.getOpposite().name(), this));
			}
			action.addStep(step);
		}
		if(card.getRotation()!=0){
			Step step = new Step();
			step.addMove(rotate(card.getRotation()));
			action.addStep(step);
		}
		return action;
	}
	
	public boolean canMove(Direction direction){
		if(square.getWall(direction)){
			return false;
		}
		Square destination = square.getNextSquare(direction);
		if(destination == null){
			// TODO return true et gérer la desctruction du robot dans la méthode move
			return false;
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
		moveList.add(new Move(MoveType.TRANSLATION, direction.name(), this));
		Square destination = square.getNextSquare(direction);
		if(destination.getRobot()!=null && !ghost){
			moveList.addAll(destination.getRobot().move(direction));
		}
		if(square.getRobot()==this){
			square.setRobot(null);
		}
		setSquare(destination);
		return moveList;
	}
	
	public Move rotate(int r){
		direction = direction.rotate(r);
		return new Move(MoveType.ROTATION, String.valueOf(r), this);
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public RobotState getState() {
		RobotState state = new RobotState();
		state.setDirection(direction);
		state.setGhost(ghost);
		state.setHealth(health);
		state.setX(square.getX());
		state.setY(square.getY());
		return state;
	}

	public Action fireLaser() {
		Square currentSquare = square;
		while(currentSquare!=null){
			if(currentSquare.getWall(direction)){
				return null;
			}
			Square nextSquare = currentSquare.getNextSquare(direction);
			if(nextSquare==null){
				return null;
			}
			if(nextSquare.getWall(direction.getOpposite())){
				return null;
			}
			if(nextSquare.getRobot()!=null){
				Action fireAction = new Action();
				fireAction.addStep(new Step(new Move(MoveType.LASER, square.getX()+","+square.getY()+"->"+nextSquare.getX()+","+nextSquare.getY(), this)));
				fireAction.addStep(nextSquare.getRobot().decreaseHealth());
				return fireAction;
			}
			currentSquare = nextSquare;
		}
		return null;
	}

	private Step decreaseHealth() {
		if(health>0){
			LoggerFactory.getLogger("GamePlay").trace("Robot "+number+" ("+health+"PV) is injuried");
			health--;
			if(health==0){
				return new Step(new Move(MoveType.DIE, null, this));
			}
		}
		return null;
	}
	
	

}
