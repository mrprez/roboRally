package com.mrprez.roborally.model.square;

import com.mrprez.roborally.model.Direction;
import com.mrprez.roborally.model.Square;
import com.mrprez.roborally.model.board.Board;
import com.mrprez.roborally.model.history.Action;
import com.mrprez.roborally.model.history.Move;
import com.mrprez.roborally.model.history.MoveType;
import com.mrprez.roborally.model.history.Step;

public class ConveyorBelt extends Square {
	private Direction direction;

	
	public ConveyorBelt(Integer x, Integer y, Board board) {
		super(x, y, board);
	}
	
	public ConveyorBelt(Integer x, Integer y, Board board, Direction direction) {
		super(x, y, board);
		this.direction = direction;
	}

	@Override
	public Action play() {
		Action action = new Action(this);
		Step step = new Step();
		action.addStep(step);
		if(getRobot().canMove(direction)){
			step.addAllMove(getRobot().move(direction));
		}else{
			step.addMove(new Move(MoveType.FAILED_TRANSLATION, direction.name(), getRobot()));
		}
		return action;
	}

	@Override
	protected Square copy() {
		ConveyorBelt copy = new ConveyorBelt(getX(), getY(), getBoard());
		copy.direction = direction;
		return copy;
	}

	@Override
	public String getImageName() {
		return "ConveyorBelt"+direction.name();
	}

	@Override
	public String getArgs() {
		return direction.name();
	}

	@Override
	public void setArgs(String args) {
		direction = Direction.valueOf(args);
	}

}
