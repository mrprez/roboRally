package com.mrprez.roborally.client.panel;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.mrprez.roborally.client.ImageLoader;
import com.mrprez.roborally.client.ImageLoaderCallback;
import com.mrprez.roborally.shared.GameGwt;
import com.mrprez.roborally.shared.RobotGwt;
import com.mrprez.roborally.shared.SquareGwt;

public class BoardPanel extends AbsolutePanel {
	
	private Map<Integer, Canvas> robotCanvaMap;
	
	
	public void init(GameGwt game){
		setWidth(game.getBoard().getSizeX()*97+"px");
		setHeight(game.getBoard().getSizeY()*97+"px");
		
		loadSquares(game);
		loadRobots(game);
	}
	
	
	private void loadSquares(GameGwt game) {
		Canvas squaresCanvas = Canvas.createIfSupported();
		squaresCanvas.setCoordinateSpaceWidth(game.getBoard().getSizeX()*97);
		squaresCanvas.setCoordinateSpaceHeight(game.getBoard().getSizeY()*97);
		squaresCanvas.setStyleName("gameCanvas");
		add(squaresCanvas);
		for (int y = 0; y < game.getBoard().getSizeY(); y++) {
			for (int x = 0; x < game.getBoard().getSizeX(); x++) {
				loadSquare(squaresCanvas.getContext2d(), game.getBoard().getSquare(x, y), x, y);
			}
		}
	}
	
	
	private void loadSquare(final Context2d context2d, SquareGwt square, final int x, final int y) {
		ImageLoader.getInstance().loadImage(square.getImageName(), new ImageLoaderCallback() {
			@Override
			public void onImageLoaded(Image image) {
				ImageElement imageEl = ImageElement.as(image.getElement());
				context2d.drawImage(imageEl, x*97, y*97);
			}
		});
		
		if(square.getTargetNumber()!=null){
			ImageLoader.getInstance().loadImage(square.getTargetImgName(), new ImageLoaderCallback() {
				@Override
				public void onImageLoaded(Image image) {
					ImageElement imageEl = ImageElement.as(image.getElement());
					context2d.drawImage(imageEl, x*97, y*97);
				}
			});
		}
	}
	
	
	private void loadRobots(final GameGwt game) {
		robotCanvaMap = new HashMap<Integer, Canvas>();
		for(final RobotGwt robot : game.getRobotList()){
			ImageLoader.getInstance().loadImage(robot.getImageName(), new ImageLoaderCallback() {
				@Override
				public void onImageLoaded(Image image) {
					Canvas robotCanvas = Canvas.createIfSupported();
					robotCanvas.setCoordinateSpaceWidth(97);
					robotCanvas.setCoordinateSpaceHeight(97);
					robotCanvas.setStyleName("gameCanvas");
					robotCanvas.getCanvasElement().setAttribute("imageName", robot.getImageName());
					if(robot.isGhost()){
						robotCanvas.getCanvasElement().getStyle().setOpacity(0.5);
					}
					add(robotCanvas, robot.getX()*97, robot.getY()*97);
					ImageElement imageEl = ImageElement.as(image.getElement());
					robotCanvas.getContext2d().translate(robotCanvas.getCoordinateSpaceWidth()/2, robotCanvas.getCoordinateSpaceHeight()/2);
					robotCanvas.getContext2d().rotate(robot.getAngle());
					robotCanvas.getContext2d().translate(-robotCanvas.getCoordinateSpaceWidth()/2, -robotCanvas.getCoordinateSpaceHeight()/2);
					robotCanvas.getContext2d().drawImage(imageEl, 25, 25);
					robotCanvaMap.put(robot.getNumber(), robotCanvas);
				}
			});
		}
	}
	
	
	public Canvas getRobotCanvas(int robotNb){
		return robotCanvaMap.get(robotNb);
	}
	
}
