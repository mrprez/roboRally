package com.mrprez.roborally.model;

public class Invitation {
	private String eMail;
	private String token;
	private int gameId;
	
	
	public Invitation(String eMail, String token, Integer gameId) {
		super();
		this.eMail = eMail;
		this.token = token;
		this.gameId = gameId;
	}

	public static Invitation newInvitation(String eMail, int gameId){
		String tokenChars = "abcdefghijklmopqrstuvwxyz0123456789ABCDEFGHIJKLMOPQRSTUVWXYZ";
		StringBuilder token = new StringBuilder();
		for(int i=0; i<20; i++){
			token.append(tokenChars.charAt((int) (tokenChars.length() * Math.random())));
		}
		return new Invitation(eMail, token.toString(), gameId);
	}
	
	public String getEMail() {
		return eMail;
	}
	public void setEMail(String eMail) {
		this.eMail = eMail;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

}
