package com.mrprez.roborally.client.animation;

import com.google.gwt.user.client.ui.AbsolutePanel;

public class FailedTranslationAnimation extends MoveAnimation {
	private int x;
	private int y;
	private int startLeft;
	private int startTop;
	private AbsolutePanel absolutePanel;
	
	
	public FailedTranslationAnimation(String direction){
		if(direction.equals(TranslationAnimation.UP)){
			x = 0;
			y = -1;
		}else if(direction.equals(TranslationAnimation.DOWN)){
			x = 0;
			y = 1;
		} else if(direction.equals(TranslationAnimation.LEFT)){
			x = -1;
			y = 0;
		}else if(direction.equals(TranslationAnimation.RIGHT)){
			x = 1;
			y = 0;
		}
	}
	
	
	@Override
	public void onStart(){
		absolutePanel = (AbsolutePanel) robotCanvas.getParent();
		startLeft = absolutePanel.getWidgetLeft(robotCanvas);
		startTop = absolutePanel.getWidgetTop(robotCanvas);
	}
	
	@Override
	public void update(double progress) {
		if(progress < 0.5){
			absolutePanel.setWidgetPosition(robotCanvas, (int)(startLeft+x*60*progress), (int)(startTop+y*60*progress));
		}else{
			absolutePanel.setWidgetPosition(robotCanvas, (int)(startLeft+x*60*(1-progress)), (int)(startTop+y*60*(1-progress)));
		}
	}

}
