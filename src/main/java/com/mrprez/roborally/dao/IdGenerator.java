package com.mrprez.roborally.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class IdGenerator {
	
	private Integer id;
	
	
	public IdGenerator(DataSource dataSource, String table, String column) throws SQLException{
		super();
		Connection connection = dataSource.getConnection();
		ResultSet resultSet = connection.prepareCall("SELECT MAX("+column+") FROM "+table).executeQuery();
		if(resultSet.next()){
			id = resultSet.getInt(1);
		}else{
			id = 1;
		}
	}
	
	
	public synchronized int getId(){
		id = id+1;
		return id;
	}
	
	
}
