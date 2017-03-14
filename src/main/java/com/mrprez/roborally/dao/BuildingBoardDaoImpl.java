package com.mrprez.roborally.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.list.GrowthList;

import com.mrprez.roborally.dto.SquareDto;
import com.mrprez.roborally.dto.TargetDto;
import com.mrprez.roborally.model.Direction;
import com.mrprez.roborally.model.Square;
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
		loadTargets(buildingBoard);
		return buildingBoard;
	}

	@Override
	public void updateBuildingBoard(BuildingBoard buildingBoard) throws SQLException {
		getSession().update("updateBuildingBoardValidity", buildingBoard);
		for (int x = 0; x < buildingBoard.getSizeX(); x++) {
			for (int y = 0; y < buildingBoard.getSizeY(); y++) {
				getSession().update("updateSquare", new SquareDto(buildingBoard.getSquare(x, y)));
			}
		}
		getSession().delete("deleteTargets", buildingBoard.getId());
		int targetNb = 0;
		for (Square target : buildingBoard.getTargetSquares()) {
			if (target != null) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("boardId", buildingBoard.getId());
				params.put("targetNb", targetNb);
				params.put("x", target.getX());
				params.put("y", target.getY());
				getSession().insert("insertTarget", params);
			}
			targetNb++;
		}
	}
	
	
	private void loadSquares(BuildingBoard buildingBoard) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException{
		List<SquareDto> squareDtoList = getSession().selectList("selectSquareList", buildingBoard.getId());
		for(SquareDto squareDto : squareDtoList){
			Square square = Square.buildSquare(squareDto.getClazz(), buildingBoard, squareDto.getX(), squareDto.getY(), squareDto.getArgs(),
					squareDto.isWallDown() ? Direction.DOWN : null,
					squareDto.isWallUp() ? Direction.UP : null,
					squareDto.isWallLeft() ? Direction.LEFT : null,
					squareDto.isWallRight() ? Direction.RIGHT : null);
			buildingBoard.addSquare(square);
		}
	}

	private void loadTargets(BuildingBoard buildingBoard) {
		List<TargetDto> targetDtoList = getSession().selectList("selectBoardTargetList", buildingBoard.getId());
		@SuppressWarnings("unchecked")
		List<Square> targetList = GrowthList.decorate(buildingBoard.getTargetSquares());
		for (TargetDto targetDto : targetDtoList) {
			targetList.add(targetDto.getIndex(), buildingBoard.getSquare(targetDto.getX(), targetDto.getY()));
		}
	}

	@Override
	public List<BuildingBoard> listUserValidBuildingBoard(String username) {
		return getSession().selectList("selectValidUserBuildingBoards", username);
	}

}
