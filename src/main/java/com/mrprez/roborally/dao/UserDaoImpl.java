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

	@Override
	public void saveInvitation(int gameId, String eMail, String token) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("gameId", gameId);
		parameters.put("eMail", eMail);
		parameters.put("token", token);
		getSession().insert("saveInvitation", parameters);
	}

	@Override
	public User getUserByEMail(String eMail) {
		return getSession().selectOne("getUserByEMail", eMail);
	}

	@Override
	public Map<Integer, String> getInvitations(String eMail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveUser(User user, String buildMD5Digest) {
		// TODO Auto-generated method stub
		
	}
	

}
