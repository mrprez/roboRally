package com.mrprez.roborally.server;

import de.novanic.eventservice.service.RemoteEventServiceServlet;

public class PushEventServiceServletImpl extends RemoteEventServiceServlet implements PushEventServiceServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void sendRefreshOrder() {
		this.addEvent(null, null);
	}
	
	
	
	
	

}
