package com.mrprez.roborally.client.animation;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

public class LaserAnimation extends MoveAnimation {
	private int startX;
	private int startY;
	private int endX;
	private int endY;
	private SimplePanel laserPanel = new SimplePanel();
	private Image explosionImg;
	
	
	public LaserAnimation(String args){
		String start = args.split("->")[0];
		String end = args.split("->")[1];
		startX = Integer.parseInt(start.split(",")[0]);
		startY = Integer.parseInt(start.split(",")[1]);
		endX = Integer.parseInt(end.split(",")[0]);
		endY = Integer.parseInt(end.split(",")[1]);
	}
	
	@Override
	public void onStart() {
		laserPanel.addStyleName("laser");
		boardPanel.add(laserPanel);
	}

	@Override
	public void update(double progress) {
		double lineProgress = progress / 0.7;
		if(lineProgress<=1.0){
			double minX = Math.min(startX*97, (startX+(endX-startX)*lineProgress)*97)+47;
			double maxX = Math.max(startX*97, (startX+(endX-startX)*lineProgress)*97)+50;
			double minY = Math.min(startY*97, (startY+(endY-startY)*lineProgress)*97)+47;
			double maxY = Math.max(startY*97, (startY+(endY-startY)*lineProgress)*97)+50;
			
			boardPanel.setWidgetPosition(laserPanel, (int)minX, (int)minY);
			laserPanel.setWidth(Math.round(maxX-minX)+"px");
			laserPanel.setHeight(Math.round(maxY-minY)+"px");
		}else if(explosionImg==null){
			explosionImg = new Image("img/explosion.png");
			boardPanel.add(explosionImg, endX*97+10, endY*97+10);
		}
	}

	@Override
	public void onComplete() {
		laserPanel.removeFromParent();
		explosionImg.removeFromParent();
	}

	

}
