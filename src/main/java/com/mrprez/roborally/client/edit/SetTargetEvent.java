package com.mrprez.roborally.client.edit;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class SetTargetEvent extends GwtEvent<SetTargetEvent.Handler> {

	public static interface Handler extends EventHandler {
		void onEvent(SetTargetEvent event);
	}

	private int targetNb;
	private BoardSquare boardSquare;

	public static Type<Handler> TYPE = new Type<SetTargetEvent.Handler>();

	public SetTargetEvent(BoardSquare boardSquare, int targetNb) {
		super();
		this.targetNb = targetNb;
		this.boardSquare = boardSquare;
	}

	@Override
	public Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.onEvent(this);
	}

	public int getTargetNb() {
		return targetNb;
	}

	public BoardSquare getBoardSquare() {
		return boardSquare;
	}

}
