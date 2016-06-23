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
import com.mrprez.roborally.shared.RobotStateGwt;
import com.mrprez.roborally.shared.RoundGwt;
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
					rotate(robotCanvas, image, robot.getAngle());
					robotCanvaMap.put(robot.getNumber(), robotCanvas);
				}
			});
		}
	}
	
	
	private void rotate(Canvas canvas, Image image, double angle){
		int direction = 0;
		if(canvas.getElement().getAttribute("direction")!=null && !canvas.getElement().getAttribute("direction").isEmpty()){
			direction = Integer.valueOf(canvas.getElement().getAttribute("direction"));
		}
		ImageElement imageEl = ImageElement.as(image.getElement());
		canvas.getContext2d().clearRect(0, 0, canvas.getCoordinateSpaceWidth(), canvas.getCoordinateSpaceHeight());
		canvas.getContext2d().translate(canvas.getCoordinateSpaceWidth()/2, canvas.getCoordinateSpaceHeight()/2);
		canvas.getContext2d().rotate(angle);
		canvas.getContext2d().translate(-canvas.getCoordinateSpaceWidth()/2, -canvas.getCoordinateSpaceHeight()/2);
		canvas.getContext2d().drawImage(imageEl, 25, 25);
		direction = direction + (int) (angle / Math.PI * 2);
		direction = (direction + 4) % 4;
		canvas.getElement().setAttribute("direction", String.valueOf(direction));
	}
	
	
	public void setRoundState(RoundGwt round){
		for(RobotStateGwt robotState : round.getRobotStateList()){
			Canvas robotCanva = robotCanvaMap.get(robotState.getRobotNb());
			setWidgetPosition(robotCanva, robotState.getX()*97, robotState.getY()*97);
			Image image = new Image(robotCanva.getCanvasElement().getAttribute("imageName"));
			rotate(robotCanva,image,  - Integer.parseInt(robotCanva.getCanvasElement().getAttribute("direction")) * Math.PI/2);
			rotate(robotCanva, image, robotState.getAngle());
		}
	}
	
	
	public Canvas getRobotCanvas(int robotNb){
		return robotCanvaMap.get(robotNb);
	}
	
}
