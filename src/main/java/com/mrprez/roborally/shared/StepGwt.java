package com.mrprez.roborally.shared;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class StepGwt implements IsSerializable {
	private List<MoveGwt> moveList = new ArrayList<MoveGwt>();
	
	
	
	public List<MoveGwt> getMoveList() {
		return moveList;
	}
	public void setMoveList(List<MoveGwt> moveList) {
		this.moveList = moveList;
	}
	
}
