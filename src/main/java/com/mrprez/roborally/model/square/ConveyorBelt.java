package com.mrprez.roborally.model.square;

import com.mrprez.roborally.model.Direction;
import com.mrprez.roborally.model.Square;
import com.mrprez.roborally.model.board.Board;
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
	public Step play() {
		// TODO Auto-generated method stub
		return null;
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
