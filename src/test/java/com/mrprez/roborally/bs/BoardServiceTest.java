package com.mrprez.roborally.bs;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.mrprez.roborally.dao.BuildingBoardDao;
import com.mrprez.roborally.model.board.BuildingBoard;
import com.mrprez.roborally.model.square.EmptySquare;

@RunWith(MockitoJUnitRunner.class)
public class BoardServiceTest {
	@InjectMocks
	private BoardServiceImpl boardService;
	
	@Mock
	private BuildingBoardDao buildingBoardDao;
	
	
	@Test
	public void testCreateNewBoard_Success() throws SQLException{
		// GIVEN
		String name = "boardName";
		String username = "username";
		int sizeX = 32;
		int sizeY = 16;
		
		// WHEN
		BuildingBoard returnedBuildingBoard = boardService.createNewBoard(name, username, sizeX, sizeY);
		
		// THEN
		Assert.assertEquals(name, returnedBuildingBoard.getName());
		Assert.assertEquals(username, returnedBuildingBoard.getUsername());
		Assert.assertEquals(sizeX, returnedBuildingBoard.getSizeX());
		Assert.assertEquals(sizeY, returnedBuildingBoard.getSizeY());
		for(int x=0; x<sizeX; x++){
			for(int y=0; y<sizeY; y++){
				Assert.assertTrue("Square "+x+"/"+y+" is  instance of "+returnedBuildingBoard.getSquare(x, y).getClass(),
						returnedBuildingBoard.getSquare(x, y) instanceof EmptySquare);
			}
		}
		Mockito.verify(buildingBoardDao).insertBuildingBoard(returnedBuildingBoard);
	}
	
}
