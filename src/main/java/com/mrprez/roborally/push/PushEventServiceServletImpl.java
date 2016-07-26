package com.mrprez.roborally.push;

import org.dozer.DozerBeanMapper;

import com.mrprez.roborally.model.history.Round;
import com.mrprez.roborally.shared.NewRoundEvent;
import com.mrprez.roborally.shared.RefreshEvent;
import com.mrprez.roborally.shared.RoundGwt;

import de.novanic.eventservice.service.RemoteEventServiceServlet;

public class PushEventServiceServletImpl extends RemoteEventServiceServlet implements PushEventServiceServlet {
	private static final long serialVersionUID = 1L;
	private DozerBeanMapper dozerMapper;
	

	@Override
	public void sendRefreshOrder() {
		this.addEvent(RefreshEvent.DOMAIN, new RefreshEvent());
	}

	@Override
	public void sendNewRoundEvent(Round newRound) {
		NewRoundEvent newRoundEvent = new NewRoundEvent();
		RoundGwt newRoundGwt = dozerMapper.map(newRound, RoundGwt.class);
		newRoundEvent.setRound(newRoundGwt);
		this.addEvent(RefreshEvent.DOMAIN, newRoundEvent);
	}

	public DozerBeanMapper getDozerMapper() {
		return dozerMapper;
	}

	public void setDozerMapper(DozerBeanMapper dozerMapper) {
		this.dozerMapper = dozerMapper;
	}
	

}
