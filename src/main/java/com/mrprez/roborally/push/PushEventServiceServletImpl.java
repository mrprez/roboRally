package com.mrprez.roborally.push;

import com.mrprez.roborally.shared.RefreshEvent;

import de.novanic.eventservice.service.RemoteEventServiceServlet;

public class PushEventServiceServletImpl extends RemoteEventServiceServlet implements PushEventServiceServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void sendRefreshOrder() {
		this.addEvent(RefreshEvent.DOMAIN, new RefreshEvent());
	}
	
	
	
	
	

}
