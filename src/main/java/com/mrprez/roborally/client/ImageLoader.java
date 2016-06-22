package com.mrprez.roborally.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public class ImageLoader {
	private static ImageLoader instance = new ImageLoader();
	
	private Map<String, Image> loadedImageMap = new HashMap<String, Image>();
	private Map<String, LoadingImage> loadingImageMap = new HashMap<String, LoadingImage>();
	
	
	public static ImageLoader getInstance(){
		return instance;
	}
	
	
	public Image loadImage(final String uri, ImageLoaderCallback callback){
		if(loadedImageMap.containsKey(uri)){
			Image image = loadedImageMap.get(uri);
			callback.onImageLoaded(image);
			return image;
		}
		
		if(loadingImageMap.containsKey(uri)){
			LoadingImage loadingImage = loadingImageMap.get(uri);
			loadingImage.addCallback(callback);
			return loadingImage.getImage();
		}
		
		final Image image = new Image(uri);
		final LoadingImage loadingImage = new LoadingImage(image);
		loadingImage.addCallback(callback);
		loadingImageMap.put(uri, loadingImage);
		image.addLoadHandler(new LoadHandler() {
			@Override
			public void onLoad(LoadEvent event) {
				loadedImageMap.put(uri, image);
				loadingImageMap.remove(uri);
				loadingImage.fireAllCallbacks();
			}
		});
		image.setVisible(false);
		RootPanel.get().add(image);
		return image;
	}
	
	
	
	

}
