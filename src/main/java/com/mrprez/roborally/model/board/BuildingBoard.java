package com.mrprez.roborally.model.board;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.mrprez.roborally.model.Direction;
import com.mrprez.roborally.model.Square;
import com.mrprez.roborally.model.square.ConveyorBelt;
import com.mrprez.roborally.model.square.EmptySquare;
import com.mrprez.roborally.model.square.HoleSquare;
import com.mrprez.roborally.model.square.RotationSquare;

public class BuildingBoard extends Board {
	
	private String name;
	private String username;
	
	
	public BuildingBoard(String name, String username, Integer sizeX, Integer sizeY){
		super();
		this.name = name;
		this.username = username;
		setSizeX( sizeX );
		setSizeY( sizeY );
		squares = new Square[sizeX][sizeY];
		for(int x=0; x<sizeX; x++){
			for(int y=0; y<sizeY; y++){
				squares[x][y] = new EmptySquare(x, y, this);
			}
		}
	}
	
	
	public static BuildingBoard buildRandomBoard(String name, int sizeX, int sizeY){
		BuildingBoard board = new BuildingBoard(name, null, sizeX, sizeY);
		for(int x=0; x<sizeX; x++){
			for(int y=0; y<sizeY; y++){
				switch((int)(Math.random()*17)){
				case 0:
					board.squares[x][y] = new ConveyorBelt(x, y, board, Direction.UP);
					break;
				case 1:
					board.squares[x][y] = new ConveyorBelt(x, y, board, Direction.LEFT);
					break;
				case 2:
					board.squares[x][y] = new ConveyorBelt(x, y, board, Direction.RIGHT);
					break;
				case 3:
					board.squares[x][y] = new ConveyorBelt(x, y, board, Direction.DOWN);
					break;
				case 4:
					board.squares[x][y] = new HoleSquare(x, y, board);
					break;
				case 5:
					board.squares[x][y] = new RotationSquare(x, y, board, 1);
					break;
				case 6:
					board.squares[x][y] = new RotationSquare(x, y, board, -1);
					break;
				default:
					board.squares[x][y] = new EmptySquare(x, y, board);
				}
				for(Direction direction : Direction.values()){
					if(Math.random()*15<1.0){
						board.squares[x][y].setWall(direction, true);
					}
				}
			}
		}
		return board;
	}
	
	public void setSquare(String className, int x, int y, String args) throws SecurityException, IllegalArgumentException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException{
		Constructor<?> constructor = Square.class.getClassLoader().loadClass(className).getConstructor(Integer.class, Integer.class, Board.class);
		Square square = (Square) constructor.newInstance(x, y, this);
		square.setArgs(args);
		addSquare(square);
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}



}
