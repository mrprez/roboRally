package com.mrprez.roborally.client.service;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mrprez.roborally.model.User;
import com.mrprez.roborally.shared.BuildingBoardGwt;
import com.mrprez.roborally.shared.SquareGwt;

@RolesAllowed(User.USER_ROLE)
@RemoteServiceRelativePath("boardGwtService")
public interface BoardGwtService extends RemoteService {
	
	int createNewBoard(String name, int sizeX, int sizeY) throws Exception;
	
	BuildingBoardGwt loadBuildingBoard(int boardId) throws Exception;

	List<BuildingBoardGwt> getBoardList() throws Exception;
	
	List<SquareGwt> getAvailableSquareList();
	
	List<String> validAndSaveBuildingBoard(BuildingBoardGwt buildingBoardGwt) throws Exception;

	List<BuildingBoardGwt> listUserValidBuildingBoard() throws Exception;
}
