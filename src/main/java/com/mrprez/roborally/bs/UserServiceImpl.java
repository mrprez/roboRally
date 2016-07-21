package com.mrprez.roborally.bs;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;

import com.mrprez.roborally.dao.UserDao;
import com.mrprez.roborally.model.User;

public class UserServiceImpl implements UserService {
	
	private UserDao userDao;
	

	@Override
	public User authenticate(String login, String password) throws SQLException {
		String md5 = buildMD5Digest(password);
		return userDao.checkUser(login, md5);
	}
	
	private String buildMD5Digest(String string) {
		MessageDigest msgDigest;
		try {
			msgDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		msgDigest.update(string.getBytes());
		byte digest[] = msgDigest.digest();
		StringBuilder digestStringBuffer = new StringBuilder(digest.length*2);
		for(int i=0; i<digest.length; i++){
			String element = String.format("%x", digest[i]);
			if(element.length()<2){
				digestStringBuffer.append("0");
			}
			digestStringBuffer.append(element);
		}
		return digestStringBuffer.toString();
	}

	@Override
	public User register(String username, String password, String eMail, String token) {
		String md5 = buildMD5Digest(password);
		Map<Integer, String> tokens = userDao.getInvitations(eMail);
		if( ! tokens.containsValue(token)){
			return null;
		}
		User user = new User();
		user.seteMail(eMail);
		user.setUsername(username);
		userDao.saveUser(user, buildMD5Digest(password));
		for(int gameId : tokens.keySet()){
			
		}
		return user;
	}
	

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
