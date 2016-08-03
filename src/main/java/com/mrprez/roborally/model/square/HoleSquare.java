package com.mrprez.roborally.model.square;

import com.mrprez.roborally.model.Square;
import com.mrprez.roborally.model.board.Board;
import com.mrprez.roborally.model.history.Action;

public class HoleSquare extends Square {

	public HoleSquare(Integer x, Integer y, Board board) {
		super(x, y, board);
	}

	@Override
	public Action play() {
		return new Action(this);
	}

	@Override
	protected Square copy() {
		return new HoleSquare(getX(), getY(), getBoard());
	}

	@Override
	public String getImageName() {
		return "Hole";
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
