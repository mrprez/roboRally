package com.mrprez.roborally.client.service;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mrprez.roborally.model.User;
import com.mrprez.roborally.shared.CardGwt;
import com.mrprez.roborally.shared.GameGwt;
import com.mrprez.roborally.shared.RobotGwt;

@RolesAllowed(User.USER_ROLE)
@RemoteServiceRelativePath("gameGwtService")
public interface GameGwtService extends RemoteService {
	
	
	List<GameGwt> getGameList();
	
	GameGwt getGame(Integer id) throws Exception;
	
	RobotGwt getPlayerRobot(Integer gameId);
	
	void saveCards(Integer gameId, List<Integer> cardList);
	
	int createNewGame(String name, int sizeX, int sizeY, int aiNb, List<String> invitedPlayerEMails) throws Exception;
	
	void playNewRound(Integer gameId) throws Exception;
	
	void savePowerDownState(Integer gameId, String powerDownState);
	
	CardGwt getCard(Integer gameId, Integer cardRapidity);

}
