package com.mrprez.roborally.client.game;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.mrprez.roborally.client.ImageLoader;
import com.mrprez.roborally.client.service.ImageLoaderCallback;
import com.mrprez.roborally.shared.CardGwt;

public class CardCanvasFactory implements IsSerializable {
	private static Map<String, Canvas> canvasCardMap = new HashMap<String, Canvas>();
	
	public static Canvas build(CardGwt card, int index){
		final String rapidity = String.valueOf(card.getRapidity());
		Canvas cardCanvas = build(card.getImageName(), rapidity);
		canvasCardMap.put(rapidity, cardCanvas);
		cardCanvas.getElement().setAttribute("rapidity", rapidity);
		cardCanvas.getElement().setAttribute("index", String.valueOf(index));
		
		if(card.isBlocked()){
			return cardCanvas;
		}
		
		cardCanvas.getElement().setAttribute("draggable", "true");
		cardCanvas.addDragStartHandler(new DragStartHandler() {
			@Override
			public void onDragStart(DragStartEvent event) {
				event.setData("rapidity", rapidity);
			}
		});
		cardCanvas.addDragOverHandler(new DragOverHandler() {
			@Override
			public void onDragOver(DragOverEvent event) {
				event.getRelativeElement().addClassName("dropping");
			}
		});
		cardCanvas.addDragLeaveHandler(new DragLeaveHandler() {
			@Override
			public void onDragLeave(DragLeaveEvent event) {
				event.getRelativeElement().removeClassName("dropping");
			}
		});
		cardCanvas.addDropHandler(new DropHandler() {
			@Override
			public void onDrop(DropEvent event) {
				event.preventDefault();
				event.getRelativeElement().removeClassName("dropping");
				Canvas droppedCanvas = canvasCardMap.get(event.getData("rapidity"));
				Canvas receptionCanvas = canvasCardMap.get(event.getRelativeElement().getAttribute("rapidity"));
				int draggedIndex = Integer.parseInt(droppedCanvas.getElement().getAttribute("index"));
				int droppedIndex = Integer.parseInt(receptionCanvas.getElement().getAttribute("index"));
				FlexTable cardPanel = (FlexTable) droppedCanvas.getParent();
				cardPanel.clearCell(0, draggedIndex+1);
				cardPanel.clearCell(0, droppedIndex+1);
				cardPanel.setWidget(0, droppedIndex+1, droppedCanvas);
				cardPanel.setWidget(0, draggedIndex+1, receptionCanvas);
				droppedCanvas.getElement().setAttribute("index", String.valueOf(droppedIndex));
				receptionCanvas.getElement().setAttribute("index", String.valueOf(draggedIndex));
			}
		});
		
		return cardCanvas;
	}
	
	
	public static Canvas build(String imageName, final String rapidity){
		final Canvas cardCanvas = Canvas.createIfSupported();
		cardCanvas.setCoordinateSpaceWidth(50);
		cardCanvas.setCoordinateSpaceHeight(80);
		ImageLoader.getInstance().loadImage(imageName, new ImageLoaderCallback() {
			@Override
			public void onImageLoaded(Image image) {
				ImageElement imageEl = ImageElement.as(image.getElement());
				cardCanvas.getContext2d().drawImage(imageEl, 5, 5);
				cardCanvas.getContext2d().setStrokeStyle("LimeGreen");
				cardCanvas.getContext2d().setFillStyle("white");
				cardCanvas.getContext2d().rect(6, 50, 30, 19);
				cardCanvas.getContext2d().fill();
				cardCanvas.getContext2d().stroke();
				cardCanvas.getContext2d().setFont("16px Digital");
				cardCanvas.getContext2d().setFillStyle("LimeGreen");
				cardCanvas.getContext2d().fillText(rapidity, 9, 65);
			}
		});
		return cardCanvas;
	}

}
