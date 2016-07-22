package com.mrprez.roborally.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mrprez.roborally.model.Invitation;
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
	public void saveInvitation(Invitation invitation) {
		getSession().insert("insertInvitation", invitation);
	}

	@Override
	public User getUserByEMail(String eMail) {
		return getSession().selectOne("getUserByEMail", eMail);
	}

	@Override
	public List<Invitation> getInvitationsForEMail(String eMail) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("eMail", eMail);
		return getSession().selectList("selectInvitations", parameters);
	}

	@Override
	public void saveUser(User user, String md5Digest) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("user", user);
		parameters.put("password", md5Digest);
		getSession().insert("insertUser", parameters);
	}

	@Override
	public List<Invitation> getInvitationsForGame(Integer gameId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("gameId", gameId);
		return getSession().selectList("selectInvitations", parameters);
	}

	@Override
	public void removeInvitation(Invitation invitation) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("gameId", invitation.getGameId());
		parameters.put("eMail", invitation.getEMail());
		getSession().insert("deleteInvitation", parameters);
	}
	

}
