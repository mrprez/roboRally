package com.mrprez.roborally.shared;

import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserGwt implements IsSerializable {
	public static final String KEY = "user";
	private String username;
	private Set<String> roles;
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	
}
