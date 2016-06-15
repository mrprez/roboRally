package com.mrprez.roborally.server;

import org.dozer.CustomConverter;

import com.mrprez.roborally.animation.FailedTranslationAnimation;
import com.mrprez.roborally.animation.MoveAnimation;
import com.mrprez.roborally.animation.RotationAnimation;
import com.mrprez.roborally.animation.TranslationAnimation;
import com.mrprez.roborally.model.Direction;
import com.mrprez.roborally.model.move.FailedTranslation;
import com.mrprez.roborally.model.move.Move;
import com.mrprez.roborally.model.move.Rotation;
import com.mrprez.roborally.model.move.Translation;

public class MoveConverter implements CustomConverter {

	@Override
	public Object convert(Object destination, Object source, Class<?> destClass, Class<?> sourceClass) {
		if(source==null){
			return null;
		}
		Move move = (Move) source;
		MoveAnimation moveAnimation;
		if(move instanceof Translation){
			Translation translation = (Translation) move;
			moveAnimation = new TranslationAnimation(convertTranslation(translation.getDirection()));
		}else if(move instanceof Rotation){
			Rotation rotation = (Rotation) move;
			moveAnimation = new RotationAnimation(rotation.getAngle());
		} else if(move instanceof FailedTranslation){
			FailedTranslation failedTranslation = (FailedTranslation) move;
			moveAnimation = new FailedTranslationAnimation(convertTranslation(failedTranslation.getDirection()));
		} else {
			throw new IllegalArgumentException(sourceClass+" is not castable into "+Move.class);
		}
		moveAnimation.setRobotNb(move.getRobot().getNumber());
		return moveAnimation;
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
