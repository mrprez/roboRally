package com.mrprez.roborally.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.mrprez.roborally.model.User;

public class UserDaoImpl extends AbstractDao implements UserDao {
	

	@Override
	public User checkUser(String login, String md5) throws SQLException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("username", login);
		parameters.put("password", md5);
		return getSession().selectOne("checkUser", parameters);
	}


	
	
	

}
