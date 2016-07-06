package com.mrprez.roborally.model.history;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Step {
	
	private List<Move> moveList = new ArrayList<Move>();
	
	
	public Step(){
		super();
	}
	
	
	public Step(Move...moves){
		super();
		moveList.addAll(Arrays.asList(moves));
	}
	
	public void addMove(Move move){
		if(move!=null){
			moveList.add(move);
		}
	}
	
	public void addAllMove(List<Move> moveList){
		for(Move move : moveList){
			addMove(move);
		}
	}
	
	public List<Move> getMoveList() {
		return moveList;
	}

	

}
