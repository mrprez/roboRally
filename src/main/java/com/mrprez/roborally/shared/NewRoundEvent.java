package com.mrprez.roborally.shared;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.domain.Domain;
import de.novanic.eventservice.client.event.domain.DomainFactory;

public class NewRoundEvent implements Event {
	private static final long serialVersionUID = 1L;
	public static final Domain DOMAIN = DomainFactory.getDomain("roboDomain");
	
	private RoundGwt round;

	
	public RoundGwt getRound() {
		return round;
	}

	public void setRound(RoundGwt round) {
		this.round = round;
	}
	

}
