package com.mrprez.roborally.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mrprez.roborally.client.AuthenticationGwtService;
import com.mrprez.roborally.shared.UserGwt;

public class AuthenticationGwtServiceImpl extends RemoteServiceServlet implements AuthenticationGwtService {
	private static final long serialVersionUID = 1L;

	
	@Override
	public UserGwt authenticate(String username, String password) throws Exception {
		if(username.equals("az")){
			UserGwt userGwt = new UserGwt();
			userGwt.setUsername(username);
			return userGwt;
		} else {
			throw new Exception("Authentification échoué - Login ou mot de passe incorrect");
		}
	}

}
