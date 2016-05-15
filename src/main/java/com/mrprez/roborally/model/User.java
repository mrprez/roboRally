package com.mrprez.roborally.model;

import java.io.Serializable;



public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private String login;
	private String password;
	
	
	public User(String login) {
		super();
		this.login = login;
	}
	public String getLogin() {
		return login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
