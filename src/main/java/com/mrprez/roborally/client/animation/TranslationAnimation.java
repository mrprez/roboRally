package com.mrprez.roborally.client.animation;

public class TranslationAnimation extends MoveAnimation {
	public static final String UP = "UP";
	public static final String DOWN = "DOWN";
	public static final String LEFT = "LEFT";
	public static final String RIGHT = "RIGHT";
	
	private int x;
	private int y;
	private int startLeft;
	private int startTop;
	
	
	public TranslationAnimation(String direction){
		if(direction.equals(UP)){
			x = 0;
			y = -1;
		}else if(direction.equals(DOWN)){
			x = 0;
			y = 1;
		} else if(direction.equals(LEFT)){
			x = -1;
			y = 0;
		}else if(direction.equals(RIGHT)){
			x = 1;
			y = 0;
		}
	}
	
	@Override
	public void onStart() {
		startLeft = boardPanel.getWidgetLeft(robotCanvas);
		startTop = boardPanel.getWidgetTop(robotCanvas);
	}

	@Override
	public void update(double progress) {
		boardPanel.setWidgetPosition(robotCanvas, (int)(startLeft+x*97*progress), (int)(startTop+y*97*progress));
	}


}
