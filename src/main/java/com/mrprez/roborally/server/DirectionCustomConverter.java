package com.mrprez.roborally.server;

import org.dozer.DozerConverter;

import com.mrprez.roborally.model.Direction;

public class DirectionCustomConverter extends DozerConverter<Direction, Character> {
	
	
	public DirectionCustomConverter() {
		super(Direction.class, Character.class);
	}

	
	@Override
	public Direction convertFrom(Character character, Direction direction) {
		switch (character) {
		case 'B':
			return Direction.DOWN;
		case 'G':
			return Direction.LEFT;
		case 'D':
			return Direction.RIGHT;
		default:
			return Direction.UP;
		}
	}

	@Override
	public Character convertTo(Direction direction, Character character) {
		switch (direction) {
		case DOWN:
			return 'B';
		case LEFT:
			return 'G';
		case RIGHT:
			return 'D';
		default:
			return 'H';
		}
	}

}
