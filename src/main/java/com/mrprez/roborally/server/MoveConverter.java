package com.mrprez.roborally.server;

import org.dozer.CustomConverter;

import com.mrprez.roborally.model.Direction;
import com.mrprez.roborally.model.Move;
import com.mrprez.roborally.shared.MoveGwt;

public class MoveConverter implements CustomConverter {

	@Override
	public Object convert(Object destination, Object source, Class<?> destClass, Class<?> sourceClass) {
		if(source==null){
			return null;
		}
		if(sourceClass.isAssignableFrom(Move.class)){
			Move move = (Move) source;
			MoveGwt moveGwt;
			if(destination==null){
				moveGwt = new MoveGwt();
			} else {
				moveGwt = (MoveGwt) destination;
			}
			moveGwt.setRobotNb(move.getRobot().getNumber());
			moveGwt.setRotation(move.getRotation());
			moveGwt.setSuccess(move.isSuccess());
			if(move.getTranslation()!=null){
				moveGwt.setTranslation(convertTranslation(move.getTranslation()));
			}
			
			return moveGwt;
		}
		throw new IllegalArgumentException(sourceClass+" is not asiignable from "+Move.class);
	}
	
	
	private int[] convertTranslation(Direction direction){
		switch (direction) {
		case RIGHT:
			return new int[]{1,0};
		case LEFT:
			return new int[]{-1,0};
		case UP:
			return new int[]{0,-1};
		case DOWN:
			return new int[]{0,1};
		default:
			return null;
		}
	}

}
