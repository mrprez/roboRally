package com.mrprez.roborally.model.square;

import com.mrprez.roborally.model.PowerDownState;
import com.mrprez.roborally.model.Robot;
import com.mrprez.roborally.model.Square;
import com.mrprez.roborally.model.board.Board;
import com.mrprez.roborally.model.history.Action;
import com.mrprez.roborally.model.history.Move;
import com.mrprez.roborally.model.history.MoveType;

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
	
	@Override
	public Move host(Robot robot) {
		if(robot==null){
			return null;
		}
		robot.setHealth(0);
		robot.walkOn(null);
		robot.setPowerDownState(PowerDownState.NONE);
		robot.setGhost(true);
		return new Move(MoveType.DISAPPEAR, null, robot);
	}

}
