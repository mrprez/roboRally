package com.mrprez.roborally.model;

public enum Direction {
	UP,
	DOWN,
	LEFT,
	RIGHT;
	
	public Direction getOpposite(){
		switch(this){
		case UP:
			return DOWN;
		case DOWN:
			return UP;
		case LEFT:
			return RIGHT;
		default:
			return LEFT;
		}
	}
	
	public Direction rotate(int r){
		Direction result = this;
		while(r<0){
			r = r + 4;
		}
		while(r>=4){
			r = r - 4;
		}
		for(int i=0; i<r; i++){
			switch(result){
			case UP:
				result = Direction.RIGHT;
				break;
			case RIGHT:
				result = Direction.DOWN;
				break;
			case DOWN:
				result = Direction.LEFT;
				break;
			case LEFT:
				result = Direction.UP;
				break;
			}
		}
		return result;
	}
}
