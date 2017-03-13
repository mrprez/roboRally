package com.mrprez.roborally.client.edit;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class SaveBoardEvent extends GwtEvent<SaveBoardEvent.Handler> {

	public static interface Handler extends EventHandler {
		void onEvent(SaveBoardEvent event);
	}

	public static Type<Handler> TYPE = new Type<SaveBoardEvent.Handler>();

	@Override
	public Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.onEvent(this);
	}

}
