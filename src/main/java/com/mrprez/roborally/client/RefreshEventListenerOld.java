package com.mrprez.roborally.client;

import com.google.gwt.core.client.GWT;
import com.mrprez.roborally.shared.RefreshEvent;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.listener.RemoteEventListener;

public class RefreshEventListenerOld implements RemoteEventListener {

	@Override
	public void apply(Event event) {
		if(event instanceof RefreshEvent){
			GWT.log("RefreshEvent received");
		}
	}

}
