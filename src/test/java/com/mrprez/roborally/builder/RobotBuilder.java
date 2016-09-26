package com.mrprez.roborally.builder;

import java.util.ArrayList;
import java.util.List;

import com.mrprez.roborally.model.Card;
import com.mrprez.roborally.model.CardStock;
import com.mrprez.roborally.model.Game;
import com.mrprez.roborally.model.Game.GameState;
import com.mrprez.roborally.model.Robot;

public class RobotBuilder {
	private String username;
	private boolean ghost = false;
	private int x = 0;
	private int y = 0;
	private List<CardPicker> cardPickerList = new ArrayList<RobotBuilder.CardPicker>();
	
	
	private RobotBuilder(){
		super();
	}
	
	public static RobotBuilder newRobot(){
		return new RobotBuilder();
	}
	
	public RobotBuilder withUsername(String username){
		this.username = username;
		return this;
	}
	
	public RobotBuilder ghost(){
		ghost = true;
		return this;
	}
	
	public RobotBuilder withTranslationCard(int translation){
		cardPickerList.add(CardPicker.newTranslationCardPicker(translation));
		return this;
	}
	
	public RobotBuilder withPosition(int x, int y){
		this.x = x;
		this.y = y;
		return this;
	}
	
	Robot buildOnGame(Game game){
		Robot robot = new Robot(game.getBoard().getSquare(x, y), ghost);
		robot.setUsername(username);
		if(game.getState()==GameState.ONGOING){
			for(CardPicker cardPicker : cardPickerList){
				robot.getCards().add(cardPicker.pickCard(game.getCardStock()));
			}
			while(robot.getCards().size()<9){
				robot.getCards().add(game.getCardStock().takeCard());
			}
		}
		return robot;
	}
	
	public static class CardPicker{
		private Integer translation;
		private Integer rotation;
		private Integer rapidity;
		
		private CardPicker(Integer translation, Integer rotation, Integer rapidity) {
			super();
			this.translation = translation;
			this.rotation = rotation;
			this.rapidity = rapidity;
		}
		
		public static CardPicker newTranslationCardPicker(Integer translation){
			return new CardPicker(translation, null, null);
		}
		
		public static CardPicker newRotationCardPicker(Integer rotation){
			return new CardPicker(null, rotation, null);
		}
		
		public static CardPicker newRapidityCardPicker(Integer rapidity){
			return new CardPicker(null, null, rapidity);
		}
		
		public Card pickCard(CardStock cardStock){
			for(int i=0; i<CardStock.CARD_NUMBER*2; i++){
				Card card = cardStock.takeCard();
				if(translation!=null && translation==card.getTranslation()){
					return card;
				}
				if(rotation!=null && rotation==card.getRotation()){
					return card;
				}
				if(rapidity!=null && rapidity==card.getRapidity()){
					return card;
				}
			}
			return null;
		}
		
	}

}
