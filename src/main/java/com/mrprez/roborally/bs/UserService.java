package com.mrprez.roborally.bs;

import java.sql.SQLException;

import com.mrprez.roborally.model.User;

public interface UserService {
	
	User authenticate(String login, String password) throws SQLException;

}
