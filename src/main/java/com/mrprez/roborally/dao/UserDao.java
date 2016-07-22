package com.mrprez.roborally.dao;

import java.sql.SQLException;
import java.util.List;

import com.mrprez.roborally.model.Invitation;
import com.mrprez.roborally.model.User;

public interface UserDao {
	
	User checkUser(String login, String md5) throws SQLException;
	
	void saveInvitation(Invitation invitation);

	User getUserByEMail(String eMail);

	List<Invitation> getInvitationsForEMail(String eMail);

	void saveUser(User user, String buildMD5Digest);

	List<Invitation> getInvitationsForGame(Integer id);

	void removeInvitation(Invitation gameInvitation);

}
