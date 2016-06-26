package com.mrprez.roborally.model.square;

import com.mrprez.roborally.model.Square;
import com.mrprez.roborally.model.board.Board;
import com.mrprez.roborally.model.history.Action;

public class EmptySquare extends Square {

	public EmptySquare(Integer x, Integer y, Board board) {
		super(x, y, board);
	}

	@Override
	public Action play() {
		return new Action(this);
	}

	@Override
	protected Square copy() {
		return new EmptySquare(getX(), getY(), getBoard());
	}

	@Override
	public String getImageName() {
		return "EmptySquare";
	}

	@Override
	public String getArgs() {
		return null;
	}

	@Override
	public void setArgs(String args) {
		;
	}

}
