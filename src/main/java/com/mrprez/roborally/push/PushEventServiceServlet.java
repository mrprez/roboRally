package com.mrprez.roborally.push;

import com.mrprez.roborally.model.history.Round;

public interface PushEventServiceServlet {
	
	
	void sendRefreshOrder();
	
	
	void sendNewRoundEvent(Round newRound);
	

}
