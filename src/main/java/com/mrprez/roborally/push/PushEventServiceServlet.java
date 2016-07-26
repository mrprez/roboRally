package com.mrprez.roborally.push;

import com.mrprez.roborally.shared.RoundGwt;

public interface PushEventServiceServlet {
	
	
	void sendRefreshOrder();
	
	
	void sendNewRoundEvent(RoundGwt newRoundGwt);
	

}
