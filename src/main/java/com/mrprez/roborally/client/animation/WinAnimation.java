package com.mrprez.roborally.client.animation;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.ui.Image;

public class WinAnimation extends MoveAnimation {
	private Canvas rocketCanvas = Canvas.createIfSupported();
	private static Image image = new Image("img/WinnerFlag.png");
	private static ImageElement imageEl = ImageElement.as(image.getElement());
	
	
	public WinAnimation(){
		super();
	}
	
	@Override
	public void onStart(){
		boardPanel.add(image, boardPanel.getWidgetLeft(robotCanvas), boardPanel.getWidgetTop(robotCanvas));
		imageEl.getStyle().setDisplay(Display.BLOCK);
		
		rocketCanvas.setCoordinateSpaceHeight(97*3);
		rocketCanvas.setCoordinateSpaceWidth(97*3);
		boardPanel.add(rocketCanvas, boardPanel.getWidgetLeft(robotCanvas)-97, boardPanel.getWidgetTop(robotCanvas)-97);
	}

	@Override
	public void update(double progress) {
		Context2d context2d = rocketCanvas.getContext2d();
		context2d.clearRect(0, 0, 97*3, 97*3);
		if(progress<0.3){
			context2d.fillRect(97*(1.5-progress/0.3*0.5), 97*(3-progress/0.3*2), 2, 8);
			context2d.fillRect(97*(1.5+progress/0.3*0.5), 97*(3-progress/0.3*2), 2, 8);
		}else{
			for(int i=0; i<20; i++){
				double r1 = 97*(progress-0.3)/0.7;
				double r2 = r1/2;
				
				context2d.setFillStyle("red");
				context2d.beginPath();
				context2d.arc(97+r1*Math.cos(2*Math.PI/20*i), 97+r1*Math.sin(2*Math.PI/20*i), 2, 0, 2*Math.PI);
				context2d.arc(97+r2*Math.cos(2*Math.PI/20*i), 97+r2*Math.sin(2*Math.PI/20*i), 2, 0, 2*Math.PI);
				context2d.fill();
				
				context2d.setFillStyle("yellow");
				context2d.beginPath();
				context2d.arc(2*97+r1*Math.cos(2*Math.PI/20*i), 97+r1*Math.sin(2*Math.PI/20*i), 2, 0, 2*Math.PI);
				context2d.arc(2*97+r2*Math.cos(2*Math.PI/20*i), 97+r2*Math.sin(2*Math.PI/20*i), 2, 0, 2*Math.PI);
				context2d.fill();
			}
		}
		
		int intProgress = (int)(progress*6);
		if(intProgress % 2 == 0){
			imageEl.getStyle().setDisplay(Display.BLOCK);
		}else{
			imageEl.getStyle().setDisplay(Display.NONE);
		}
	}
	
	@Override
	public double getTimeCoefficient(){
		return 4.0;
	}
	
	@Override
	public void onComplete(){
		robotCanvas.getCanvasElement().getStyle().setDisplay(Display.NONE);
		rocketCanvas.removeFromParent();
		imageEl.getStyle().setDisplay(Display.NONE);
	}
	
}
