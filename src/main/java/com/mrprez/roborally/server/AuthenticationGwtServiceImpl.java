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
			return null;
		}
		UserGwt userGwt = new UserGwt();
		userGwt.setUsername(user.getUsername());
		userGwt.setRoles(user.getRoles());
		getThreadLocalRequest().getSession().setAttribute(UserGwt.KEY, userGwt);
		return userGwt;
	}
	
	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserGwt register(String username, String password, String eMail, String token) throws Exception {
		User user = userService.register(username, password, eMail, token);
		if(user==null){
			throw new Exception("Enregistrement échoué - token invalide");
		}
		UserGwt userGwt = new UserGwt();
		userGwt.setUsername(user.getUsername());
		getThreadLocalRequest().getSession().setAttribute(UserGwt.KEY, userGwt);
		return userGwt;
	}


}
