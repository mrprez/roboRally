package com.mrprez.roborally.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import com.mrprez.roborally.dto.SquareDto;
import com.mrprez.roborally.model.Direction;
import com.mrprez.roborally.model.Square;
import com.mrprez.roborally.model.board.Board;
import com.mrprez.roborally.model.board.BuildingBoard;

public class BuildingBoardDaoImpl extends AbstractDao implements BuildingBoardDao {

	@Override
	public void insertBuildingBoard(BuildingBoard buildingBoard) throws SQLException {
		getSession().insert("insertBuildingBoard", buildingBoard);
		for(int x=0; x<buildingBoard.getSizeX(); x++){
			for(int y=0; y<buildingBoard.getSizeY(); y++){
				getSession().insert("insertSquare", new SquareDto(buildingBoard.getSquare(x, y)));
			}
		}
		getSession().insert("insertBuildingBoardOwner", buildingBoard);
	}

	@Override
	public List<BuildingBoard> loadBuildingBoardList(String username) throws SQLException {
		return getSession().selectList("selectUserBuildingBoards", username);
	}

	@Override
	public BuildingBoard loadBuildingBoard(Integer id) throws Exception {
		BuildingBoard buildingBoard = getSession().selectOne("selectBuildingBoard", id);
		loadSquares(buildingBoard);
		return buildingBoard;
	}

	@Override
	public void updateBuildingBoard(BuildingBoard board) throws SQLException {
		// TODO Auto-generated method stub

	}
	
	
	private void loadSquares(BuildingBoard buildingBoard) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException{
		List<SquareDto> squareDtoList = getSession().selectList("selectSquareList", buildingBoard.getId());
		for(SquareDto squareDto : squareDtoList){
			@SuppressWarnings("unchecked")
			Constructor<Square> squareConstructor = (Constructor<Square>) Class.forName(squareDto.getClazz()).getConstructor(Integer.class, Integer.class, Board.class);;
			Square square = squareConstructor.newInstance(squareDto.getX(), squareDto.getY(), buildingBoard);
			square.setArgs(squareDto.getArgs());
			square.setWall(Direction.DOWN, squareDto.isWallDown());
			square.setWall(Direction.UP, squareDto.isWallUp());
			square.setWall(Direction.LEFT, squareDto.isWallLeft());
			square.setWall(Direction.RIGHT, squareDto.isWallRight());
			buildingBoard.addSquare(square);
		}
	}

}
