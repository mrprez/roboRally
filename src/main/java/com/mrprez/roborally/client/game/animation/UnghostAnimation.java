package com.mrprez.roborally.client.game.animation;

public class UnghostAnimation extends MoveAnimation {
	
	
	@Override
	public void onStart(){}
	
	@Override
	public void update(double progress) {
		robotCanvas.getCanvasElement().getStyle().setOpacity(0.5+0.5*progress);
	}

}
