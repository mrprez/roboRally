package com.mrprez.roborally.bs;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import com.mrprez.roborally.model.User;

public interface UserService {
	
	User authenticate(String login, String password) throws SQLException;

	User register(String username, String password, String eMail, String token) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException;

}
