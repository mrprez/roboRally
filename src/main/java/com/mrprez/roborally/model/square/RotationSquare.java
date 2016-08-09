package com.mrprez.roborally.model.square;

import com.mrprez.roborally.model.Square;
import com.mrprez.roborally.model.board.Board;
import com.mrprez.roborally.model.history.Action;
import com.mrprez.roborally.model.history.Step;

public class RotationSquare extends Square {
	private int angle;

	
	public RotationSquare(Integer x, Integer y, Board board) {
		super(x, y, board);
	}
	
	public RotationSquare(Integer x, Integer y, Board board, int angle) {
		super(x, y, board);
		this.angle = angle;
	}

	@Override
	public Action play() {
		Action action = new Action(this);
		action.addStep(new Step(getRobot().rotate(angle)));
		return action;
	}

	@Override
	protected Square copy() {
		return new RotationSquare(getX(), getY(), getBoard(), angle);
	}

	@Override
	public String getImageName() {
		if(angle==1){
			return "RotationR";
		}else{
			return "RotationL";
		}
	}

	@Override
	public String getArgs() {
		return String.valueOf(angle);
	}

	@Override
	public void setArgs(String args) {
		angle = Integer.parseInt(args);
	}

}
