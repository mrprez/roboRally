package com.mrprez.roborally.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mrprez.roborally.shared.UserGwt;

@RemoteServiceRelativePath("authenticationService")
public interface AuthenticationGwtService extends RemoteService {
	
	UserGwt authenticate(String username, String password) throws Exception;
	
	UserGwt register(String username, String password, String eMail, String token) throws Exception;

}
