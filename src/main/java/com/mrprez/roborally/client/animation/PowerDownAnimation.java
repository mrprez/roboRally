package com.mrprez.roborally.client.animation;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;
import com.mrprez.roborally.client.ImageLoader;
import com.mrprez.roborally.client.ImageLoaderCallback;

public class PowerDownAnimation extends MoveAnimation {
	private String powerDownState;
	
	public PowerDownAnimation(String powerDownState){
		super();
		this.powerDownState = powerDownState;
	}
	
	@Override
	public void onStart(){
		String imageUri = robotCanvas.getCanvasElement().getAttribute("imageName");
		if(powerDownState.equals("ONGOING")){
			imageUri = imageUri.replace(".gif", "HT.gif");
		}else{
			imageUri = imageUri.replace("HT.gif", ".gif");
		}
		ImageLoader.getInstance().loadImage(imageUri, new ImageLoaderCallback() {
			@Override
			public void onImageLoaded(Image image) {
				ImageElement diedImageEl = ImageElement.as(image.getElement());
				robotCanvas.getContext2d().clearRect(0, 0, robotCanvas.getCoordinateSpaceWidth(), robotCanvas.getCoordinateSpaceHeight());
				robotCanvas.getContext2d().drawImage(diedImageEl, 25, 25);
			}
		});
	}

	public double getTimeCoefficient(){
		return 0.1;
	}
	
	@Override
	public void update(double progress) {}
	
	

}
