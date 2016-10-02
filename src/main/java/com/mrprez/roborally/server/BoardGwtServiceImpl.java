package com.mrprez.roborally.server;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;

import com.mrprez.roborally.bs.BoardService;
import com.mrprez.roborally.client.BoardGwtService;
import com.mrprez.roborally.model.Direction;
import com.mrprez.roborally.model.Square;
import com.mrprez.roborally.model.board.BuildingBoard;
import com.mrprez.roborally.shared.BuildingBoardGwt;
import com.mrprez.roborally.shared.SquareGwt;
import com.mrprez.roborally.shared.UserGwt;

public class BoardGwtServiceImpl extends AbstractGwtService implements BoardGwtService {
	private static final long serialVersionUID = 1L;
	
	private BoardService boardService;
	private DozerBeanMapper dozerMapper;
	

	@Override
	public int createNewBoard(String name, int sizeX, int sizeY) throws Exception {
		if(sizeX<=0 || sizeY<=0 || sizeX*sizeY>1000){
			throw new IllegalArgumentException();
		}
		UserGwt user = (UserGwt) getThreadLocalRequest().getSession().getAttribute(UserGwt.KEY);
		BuildingBoard board = boardService.createNewBoard(name, user.getUsername(), sizeX, sizeY);
		return board.getId();
	}

	@Override
	public List<BuildingBoardGwt> getBoardList() throws Exception {
		UserGwt user = (UserGwt) getThreadLocalRequest().getSession().getAttribute(UserGwt.KEY);
		List<BuildingBoard> buildingBoardList = boardService.getUserBuildingBoards(user.getUsername());
		List<BuildingBoardGwt> result = new ArrayList<BuildingBoardGwt>(buildingBoardList.size());
		for(BuildingBoard buildingBoard : buildingBoardList){
			result.add(dozerMapper.map(buildingBoard, BuildingBoardGwt.class));
		}
		return result;
	}
	
	
	@Override
	public BuildingBoardGwt loadBuildingBoard(int boardId) throws Exception {
		UserGwt user = (UserGwt) getThreadLocalRequest().getSession().getAttribute(UserGwt.KEY);
		BuildingBoard board =  boardService.loadBuildingBoard(boardId, user.getUsername());
		BuildingBoardGwt boardGwt = new BuildingBoardGwt();
		dozerMapper.map(board, boardGwt);
		
		SquareGwt[][] squareGwtTab = new SquareGwt[board.getSizeX()][board.getSizeY()]; 
		for(int y=0; y<board.getSizeY(); y++){
			for(int x=0; x<board.getSizeX(); x++){
				squareGwtTab[x][y] = new SquareGwt();
				Square square = board.getSquare(x, y);
				squareGwtTab[x][y].setImageName(square.getImageName());
				squareGwtTab[x][y].setWallUp(square.getWall(Direction.UP));
				squareGwtTab[x][y].setWallDown(square.getWall(Direction.DOWN));
				squareGwtTab[x][y].setWallLeft(square.getWall(Direction.LEFT));
				squareGwtTab[x][y].setWallRight(square.getWall(Direction.RIGHT));
			}
		}
		boardGwt.setSquares(squareGwtTab);
		
		return boardGwt;
	}

	
	public BoardService getBoardService() {
		return boardService;
	}

	public void setBoardService(BoardService boardService) {
		this.boardService = boardService;
	}

	public DozerBeanMapper getDozerMapper() {
		return dozerMapper;
	}

	public void setDozerMapper(DozerBeanMapper dozerMapper) {
		this.dozerMapper = dozerMapper;
	}
	

}
