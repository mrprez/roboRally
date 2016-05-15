package com.mrprez.roborally.dao;

import javax.sql.DataSource;

public class AbstractDao {
	
	private DataSource dataSource;

	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
		


	

}
