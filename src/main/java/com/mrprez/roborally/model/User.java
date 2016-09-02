package com.mrprez.roborally.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String USER_ROLE = "user";
	
	private String username;
	private String eMail;
	private Set<String> roles = new HashSet<String>();
	
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

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	
	
}
