package com.mrprez.roborally.model;

import java.io.Serializable;


public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private String username;
	private String eMail;

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	
	
}
