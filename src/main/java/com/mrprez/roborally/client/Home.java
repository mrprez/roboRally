package com.mrprez.roborally.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.mrprez.roborally.shared.GameBoardGwt;

public class Home implements EntryPoint {

	private BoardGwtServiceAsync boardGwtService = GWT.create(BoardGwtService.class);
	
	public void onModuleLoad() {
		final Tree gameBoardList = new Tree();
		RootPanel.get().add(gameBoardList);
		boardGwtService.getGameBoardList(new AsyncCallback<List<GameBoardGwt>>() {
			@Override
			public void onSuccess(List<GameBoardGwt> result) {
				for(GameBoardGwt gameBoard : result){
					gameBoardList.add(new Label(gameBoard.getName()));
				}
			}
			@Override
			public void onFailure(Throwable caught) {}
		});
	}
}
