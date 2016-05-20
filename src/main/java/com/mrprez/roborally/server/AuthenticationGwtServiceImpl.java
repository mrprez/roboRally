package com.mrprez.roborally.server;

import com.mrprez.roborally.bs.UserService;
import com.mrprez.roborally.client.AuthenticationGwtService;
import com.mrprez.roborally.model.User;
import com.mrprez.roborally.shared.UserGwt;

public class AuthenticationGwtServiceImpl extends AbstractGwtService implements AuthenticationGwtService {
	private static final long serialVersionUID = 1L;
	
	private UserService userService;

	
	@Override
	public UserGwt authenticate(String username, String password) throws Exception {
		User user = userService.authenticate(username, password);
		if(user==null){
			throw new Exception("Authentification échoué - Login ou mot de passe incorrect");
		}
		UserGwt userGwt = new UserGwt();
		userGwt.setUsername(user.getUsername());
		return userGwt;
	}
	
	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}


}
