package com.mrprez.roborally.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserGwt implements IsSerializable {
	public static final String KEY = "user";
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
