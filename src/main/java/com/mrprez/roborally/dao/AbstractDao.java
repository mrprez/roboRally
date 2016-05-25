package com.mrprez.roborally.dao;

import org.apache.ibatis.session.SqlSession;

public class AbstractDao {
	
	private SqlSession session;

	
	public SqlSession getSession() {
		return session;
	}

	public void setSession(SqlSession session) {
		this.session = session;
	}

}
