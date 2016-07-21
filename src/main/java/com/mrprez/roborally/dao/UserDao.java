package com.mrprez.roborally.dao;

import java.sql.SQLException;
import java.util.Map;

import com.mrprez.roborally.model.User;

public interface UserDao {
	
	User checkUser(String login, String md5) throws SQLException;
	
	void saveInvitation(int gameId, String eMail, String token);

	User getUserByEMail(String eMail);

	Map<Integer, String> getInvitations(String eMail);

	void saveUser(User user, String buildMD5Digest);

}
