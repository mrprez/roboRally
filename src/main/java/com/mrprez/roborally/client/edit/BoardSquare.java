package com.mrprez.roborally.client.edit;

import com.google.common.base.Strings;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.mrprez.roborally.shared.SquareGwt;

public class BoardSquare extends FlowPanel implements DropHandler, DragStartHandler, DragOverHandler, DragLeaveHandler {
	private Image squareImage;
	private HandlerManager handlerManager;
	private String squareType;
	private String squareArgs;

	public BoardSquare(SquareGwt square, HandlerManager handlerManager) {
		super();
		this.handlerManager = handlerManager;
		this.squareType = square.getType();
		this.squareArgs = square.getArgs();
		addStyleName("squareDiv");
		squareImage = new Image(square.getImageUrl());
		add(squareImage);

		squareImage.addDragStartHandler(this);
		addDomHandler(this, DragOverEvent.getType());
		addDomHandler(this, DragLeaveEvent.getType());
		addDomHandler(this, DropEvent.getType());
		if (square.getTargetNumber() != null) {
			handlerManager.fireEvent(new SetTargetEvent(this, square.getTargetNumber()));
		}
	}

	@Override
	public void onDrop(DropEvent event) {
		if (event.getData("imageUrl") != null && event.getData("imageUrl").length() > 0) {
			squareImage.setUrl(event.getData("imageUrl"));
			squareType = event.getData("squareType");
			squareArgs = event.getData("squareArgs");
		}
		if (event.getData("targetNb") != null && event.getData("targetNb").length() > 0) {
			handlerManager.fireEvent(new SetTargetEvent(this, Integer.parseInt(event.getData("targetNb"))));
		}
		if (event.getData("wallImgUrl") != null && event.getData("wallImgUrl").length() > 0) {
			Image wallImage = new Image(event.getData("wallImgUrl"));
			wallImage.addStyleName("wallImage");
			add(wallImage);
		}
		squareImage.removeStyleName("dropping");
	}

	public void setTargetImage(Image targetImage) {
		for (int widgetIndex = 0; widgetIndex < getWidgetCount(); widgetIndex++) {
			Widget widget = getWidget(widgetIndex);
			if (!Strings.isNullOrEmpty(widget.getElement().getAttribute("targetNb"))) {
				return;
			}
		}
		add(targetImage);
	}

	@Override
	public void onDragStart(DragStartEvent event) {
		event.setData("imageUrl", squareImage.getUrl());
		event.setData("squareType", squareType);
		event.setData("squareArgs", squareArgs);
	}

	@Override
	public void onDragOver(DragOverEvent event) {
		squareImage.addStyleName("dropping");
	}

	@Override
	public void onDragLeave(DragLeaveEvent event) {
		squareImage.removeStyleName("dropping");
	}

	public SquareGwt buildSquare() {
		SquareGwt square = new SquareGwt();
		square.setArgs(squareArgs);
		square.setType(squareType);
		for (int widgetIndex = 0; widgetIndex < getWidgetCount(); widgetIndex++) {
			Widget widget = getWidget(widgetIndex);
			if (!Strings.isNullOrEmpty(widget.getElement().getAttribute("targetNb"))) {
				square.setTargetNumber(Integer.valueOf(widget.getElement().getAttribute("targetNb")));
			}
		}
		return square;
	}

}
