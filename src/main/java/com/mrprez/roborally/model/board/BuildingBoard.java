package com.mrprez.roborally.model.board;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.mrprez.roborally.model.Direction;
import com.mrprez.roborally.model.Square;
import com.mrprez.roborally.model.square.ConveyorBelt;
import com.mrprez.roborally.model.square.EmptySquare;

public class BuildingBoard extends Board {
	
	private String name;
	
	public BuildingBoard(String name, int sizeX, int sizeY) {
		super();
		this.name = name;
		setSizeX( sizeX );
		setSizeY( sizeY );
		squares = new Square[sizeX][sizeY];
		for(int x=0; x<sizeX; x++){
			for(int y=0; y<sizeY; y++){
				switch((int)(Math.random()*16)){
				case 0:
					squares[x][y] = new ConveyorBelt(x, y, this, Direction.UP);
					break;
				case 1:
					squares[x][y] = new ConveyorBelt(x, y, this, Direction.LEFT);
					break;
				case 2:
					squares[x][y] = new ConveyorBelt(x, y, this, Direction.RIGHT);
					break;
				case 3:
					squares[x][y] = new ConveyorBelt(x, y, this, Direction.DOWN);
					break;
				default:
					squares[x][y] = new EmptySquare(x, y, this);
				}
				for(Direction direction : Direction.values()){
					if(Math.random()*8<1.0){
						squares[x][y].setWall(direction, true);
					}
				}
			}
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSquare(String className, int x, int y, String args) throws SecurityException, IllegalArgumentException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException{
		Constructor<?> constructor = Square.class.getClassLoader().loadClass(className).getConstructor(Integer.class, Integer.class, Board.class);
		Square square = (Square) constructor.newInstance(x, y, this);
		square.setArgs(args);
		addSquare(square);
	}

}
