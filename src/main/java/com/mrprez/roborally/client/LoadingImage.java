package com.mrprez.roborally.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Image;

public class LoadingImage {
	private Image image;
	private List<ImageLoaderCallback> callbackList = new ArrayList<ImageLoaderCallback>();
	
	
	public LoadingImage(Image image) {
		super();
		this.image = image;
	}


	public Image getImage() {
		return image;
	}
	
	
	public void addCallback(ImageLoaderCallback callback){
		callbackList.add(callback);
	}
	
	
	public void fireAllCallbacks(){
		for(ImageLoaderCallback callback : callbackList){
			callback.onImageLoaded(image);
		}
	}
	
	

}
