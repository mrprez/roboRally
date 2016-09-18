package com.mrprez.roborally.client;

import javax.annotation.security.RolesAllowed;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mrprez.roborally.model.User;

@RolesAllowed(User.USER_ROLE)
@RemoteServiceRelativePath("boardGwtService")
public interface BoardGwtService extends RemoteService {
	
	
	int createNewBoard(String name, int sizeX, int sizeY) throws Exception;
	

}