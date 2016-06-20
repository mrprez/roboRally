package com.mrprez.roborally.client.panel;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
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
		
		loadStartSquare(squaresCanvas.getContext2d(), game.getBoard().getStartX(), game.getBoard().getStartY());
	}
	
	
	private void loadSquare(final Context2d context2d, SquareGwt square, final int x, final int y) {
		final Image img = new Image(square.getImageName());
		img.addLoadHandler(new LoadHandler() {
			@Override
			public void onLoad(LoadEvent event) {
				ImageElement imageEl = ImageElement.as(img.getElement());
				context2d.drawImage(imageEl, x*97, y*97);
			}
		});
		img.setVisible(false);
		RootPanel.get().add(img);
	}
	
	
	private void loadStartSquare(final Context2d context2d, final int x, final int y){
		final Image img = new Image("img/Start.png");
		img.addLoadHandler(new LoadHandler() {
			@Override
			public void onLoad(LoadEvent event) {
				ImageElement imageEl = ImageElement.as(img.getElement());
				context2d.drawImage(imageEl, x*97, y*97);
			}
		});
		img.setVisible(false);
		RootPanel.get().add(img);
	}
	
	
	private void loadRobots(final GameGwt game) {
		robotCanvaMap = new HashMap<Integer, Canvas>();
		for(final RobotGwt robot : game.getRobotList()){
			final Image img = new Image(robot.getImageName());
			img.addLoadHandler(new LoadHandler() {
				@Override
				public void onLoad(LoadEvent event) {
					Canvas robotCanvas = Canvas.createIfSupported();
					robotCanvas.setCoordinateSpaceWidth(97);
					robotCanvas.setCoordinateSpaceHeight(97);
					robotCanvas.setStyleName("gameCanvas");
					robotCanvas.getCanvasElement().setAttribute("imageName", robot.getImageName());
					if(robot.isGhost()){
						robotCanvas.getCanvasElement().getStyle().setOpacity(0.5);
					}
					add(robotCanvas, robot.getX()*97, robot.getY()*97);
					ImageElement imageEl = ImageElement.as(img.getElement());
					robotCanvas.getContext2d().drawImage(imageEl, 25, 25);
					robotCanvaMap.put(robot.getNumber(), robotCanvas);
				}
			});
			img.setVisible(false);
			RootPanel.get().add(img);
		}
	}
	
	
	public Canvas getRobotCanvas(int robotNb){
		return robotCanvaMap.get(robotNb);
	}
	
}
