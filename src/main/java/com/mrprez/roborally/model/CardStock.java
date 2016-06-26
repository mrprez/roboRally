package com.mrprez.roborally.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CardStock {
	private LinkedList<Card> stock = new LinkedList<Card>();
	private Set<Card> discard = new HashSet<Card>();
	private static int CARD_NUMBER = 84;
	
	
	public CardStock(){
		super();
		List<Card> cards = new ArrayList<Card>(CARD_NUMBER);
		for(int i=1; i<=CARD_NUMBER; i++){
			Card card;
			if(i<=CARD_NUMBER/7){
				card = new Card(i*10, 0, 2);
			}else if(i<=CARD_NUMBER/7*2){
				card = new Card(i*10, 0, -1);
			}else if(i<=CARD_NUMBER/7*3){
				card = new Card(i*10, 0, 1);
			}else if(i<=CARD_NUMBER/7*4){
				card = new Card(i*10, -1, 0);
			}else if(i<=CARD_NUMBER/7*5){
				card = new Card(i*10, 1, 0);
			}else if(i<=CARD_NUMBER/7*6){
				card = new Card(i*10, 2, 0);
			}else{
				card = new Card(i*10, 3, 0);
			}
			cards.add(card);
		}
		for(int i=0; i<CARD_NUMBER; i++){
			int r = (int)(Math.random()*cards.size());
			Card card = cards.get(r);
			stock.add(card);
			cards.remove(r);
		}
	}
	
	public CardStock(List<Card> cardList, Collection<Card> discard) {
		super();
		this.stock.addAll(cardList);
		this.discard.addAll(discard);
	}

	public Card takeCard(){
		if(stock.isEmpty()){
			shuffleDiscard();
		}
		return stock.poll();
	}
	
	private void shuffleDiscard(){
		Iterator<Card> it = discard.iterator();
		while(it.hasNext()){
			Card card = it.next();
			int r = (int)Math.random()*stock.size();
			stock.add(r, card);
			it.remove();
		}
	}

	public void discard(Card card) {
		discard.add(card);
	}
	
	public void discard(Collection<Card> cards) {
		discard.addAll(cards);
	}
	
	public List<Card> getStock() {
		return stock;
	}
	
	public Collection<Card> getDiscard() {
		return discard;
	}
	
	

}
