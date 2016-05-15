package com.mrprez.roborally.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.datasource.DataSourceUtils;

import com.mrprez.roborally.model.Square;
import com.mrprez.roborally.model.board.BuildingBoard;

public class BoardDaoImpl extends AbstractDao implements BoardDao {
	
	private IdGenerator idGenerator;
	
	
	public void createBuildingBoard(BuildingBoard buildingBoard) throws SQLException{
		Connection connection = DataSourceUtils.getConnection(getDataSource());
		
		PreparedStatement boardPreparedStatement = connection.prepareStatement("insert into BOARD (ID,SIZE_X,SIZE_Y,START_X,START_Y) values (?,?,?,?,?)");
		int id = idGenerator.getId();
		boardPreparedStatement.setInt(1, id);
		boardPreparedStatement.setInt(2, buildingBoard.getSizeX());
		boardPreparedStatement.setInt(3, buildingBoard.getSizeY());
		boardPreparedStatement.setInt(4, buildingBoard.getStartSquare().getX());
		boardPreparedStatement.setInt(5, buildingBoard.getStartSquare().getY());
		boardPreparedStatement.executeUpdate();

		buildingBoard.setId(id);
		
		PreparedStatement buildingBoardPreparedStatement = connection.prepareStatement("insert into BUILDING_BOARD (ID,NAME) values (?,?)");
		buildingBoardPreparedStatement.setInt(1, id);
		buildingBoardPreparedStatement.setString(2, buildingBoard.getName());
		buildingBoardPreparedStatement.executeUpdate();
		
		for(int x=0; x<buildingBoard.getSizeX(); x++){
			for(int y=0; y<buildingBoard.getSizeY(); y++){
				PreparedStatement squarePreparedStatement = connection.prepareStatement("insert into SQUARE (BOARD_ID,X,Y,TYPE,ARGS) values (?,?,?,?,?)");
				squarePreparedStatement.setInt(1, id);
				squarePreparedStatement.setInt(2, x);
				squarePreparedStatement.setInt(3, y);
				squarePreparedStatement.setString(4, buildingBoard.getSquare(x, y).getClass().getName());
				squarePreparedStatement.setString(5, buildingBoard.getSquare(x, y).getArgs());
				squarePreparedStatement.executeUpdate();
			}
		}
		
	}
	
	
	@Override
	public List<BuildingBoard> loadBuildingBoardList(String username) throws SQLException {
		List<BuildingBoard> buildingBoardList = new ArrayList<BuildingBoard>();
		
		Connection connection = DataSourceUtils.getConnection(getDataSource());
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT BOARD.ID,SIZE_X,SIZE_Y,NAME FROM BOARD JOIN BUILDING_BOARD ON BOARD.ID=BUILDING_BOARD.ID");
		
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			BuildingBoard buildingBoard = new BuildingBoard(resultSet.getString("NAME"), resultSet.getInt("SIZE_X"), resultSet.getInt("SIZE_Y"));
			buildingBoard.setId(resultSet.getInt("ID"));
			buildingBoardList.add(buildingBoard);
		}
		
		return buildingBoardList;
	}


	public IdGenerator getIdGenerator() {
		return idGenerator;
	}


	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}


	@Override
	public BuildingBoard loadBuildingBoard(Integer id) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
		Connection connection = DataSourceUtils.getConnection(getDataSource());
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT BOARD.ID,SIZE_X,SIZE_Y,START_X,START_Y,NAME FROM BOARD JOIN BUILDING_BOARD ON BOARD.ID=BUILDING_BOARD.ID WHERE BOARD.ID=?");
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if(resultSet.next()){
			BuildingBoard buildingBoard = new BuildingBoard(resultSet.getString("NAME"), resultSet.getInt("SIZE_X"), resultSet.getInt("SIZE_Y"));
			buildingBoard.setId(resultSet.getInt("ID"));
			
			PreparedStatement squareStatement = connection.prepareStatement("SELECT * FROM SQUARE WHERE BOARD_ID=?");
			squareStatement.setInt(1, id);
			ResultSet squareResultSet = squareStatement.executeQuery();
			while(squareResultSet.next()){
				buildingBoard.setSquare(squareResultSet.getString("TYPE"), squareResultSet.getInt("X"), squareResultSet.getInt("Y"), squareResultSet.getString("ARGS"));
			}
			
			buildingBoard.setStartSquare(resultSet.getInt("START_X"), resultSet.getInt("START_Y"));
			
			PreparedStatement targetStatement = connection.prepareStatement("SELECT * FROM TARGET WHERE BOARD_ID=? ORDER BY NUMBER ASC");
			targetStatement.setInt(1, id);
			ResultSet targetResultSet = targetStatement.executeQuery();
			while(targetResultSet.next()){
				buildingBoard.getTargetSquares().add(buildingBoard.getSquare(targetResultSet.getInt("X"), targetResultSet.getInt("Y")));
			}
			
			return buildingBoard;
		}
		
		return null;
	}


	@Override
	public void updateBuildingBoard(BuildingBoard board) throws SQLException {
		Connection connection = DataSourceUtils.getConnection(getDataSource());
		for(int x=0; x<board.getSizeX(); x++){
			for(int y=0; y<board.getSizeY(); y++){
				Square square = board.getSquare(x, y);
				PreparedStatement preparedStatement = connection.prepareStatement("UPDATE SQUARE SET TYPE=?, ARGS=? WHERE X=? AND Y=?");
				preparedStatement.setString(1, square.getClass().getName());
				preparedStatement.setString(2, square.getArgs());
				preparedStatement.setInt(3, x);
				preparedStatement.setInt(4, y);
				preparedStatement.execute();
			}
		}
		
		PreparedStatement deleteTargetStatement = connection.prepareStatement("DELETE FROM TARGET WHERE BOARD_ID=?");
		deleteTargetStatement.setInt(1, board.getId());
		deleteTargetStatement.execute();
		
		int number = 1;
		for(Square target : board.getTargetSquares()){
			PreparedStatement insertTargetStatement = connection.prepareStatement("INSERT INTO TARGET VALUES (?,?,?,?)");
			insertTargetStatement.setInt(1, board.getId());
			insertTargetStatement.setInt(2, number++);
			insertTargetStatement.setInt(3, target.getX());
			insertTargetStatement.setInt(4, target.getY());
			insertTargetStatement.execute();
		}
		
	}


	

}
