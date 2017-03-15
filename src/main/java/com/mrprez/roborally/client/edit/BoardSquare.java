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
	private Image topWallImg;
	private Image bottomWallImg;
	private Image leftWallImg;
	private Image rightWallImg;

	public BoardSquare(SquareGwt square, HandlerManager handlerManager) {
		super();
		this.handlerManager = handlerManager;
		this.squareType = square.getType();
		this.squareArgs = square.getArgs();
		addStyleName("squareDiv");
		squareImage = new Image(square.getImageUrl());
		add(squareImage);

		if (square.isWallDown()) {
			addWallImage("img/HorizontalWall.gif", "wallBottom");
		}
		if (square.isWallUp()) {
			addWallImage("img/HorizontalWall.gif", "wallTop");
		}
		if (square.isWallLeft()) {
			addWallImage("img/VerticalWall.gif", "wallLeft");
		}
		if (square.isWallRight()) {
			addWallImage("img/VerticalWall.gif", "wallRight");
		}

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
			int dropY = event.getNativeEvent().getClientY() - squareImage.getAbsoluteTop();
			int dropX = event.getNativeEvent().getClientX() - squareImage.getAbsoluteLeft();
			if (event.getData("wallImgUrl").equals("img/HorizontalWall.gif") && dropY < 97 / 2 && topWallImg == null) {
				topWallImg = addWallImage(event.getData("wallImgUrl"), "wallTop");
			} else if (event.getData("wallImgUrl").equals("img/HorizontalWall.gif") && dropY >= 97 / 2 && bottomWallImg == null) {
				bottomWallImg = addWallImage(event.getData("wallImgUrl"), "wallBottom");
			} else if (event.getData("wallImgUrl").equals("img/VerticalWall.gif") && dropX < 97 / 2 && leftWallImg == null) {
				leftWallImg = addWallImage(event.getData("wallImgUrl"), "wallLeft");
			} else if (event.getData("wallImgUrl").equals("img/VerticalWall.gif") && dropX >= 97 / 2 && rightWallImg == null) {
				rightWallImg = addWallImage(event.getData("wallImgUrl"), "wallRight");
			}
		}
		squareImage.removeStyleName("dropping");
	}

	private Image addWallImage(final String url, String styleName) {
		final Image wallImage = new Image(url);
		wallImage.addStyleName(styleName);
		wallImage.addDragStartHandler(new DragStartHandler() {
			@Override
			public void onDragStart(DragStartEvent event) {
				event.setData("wallImgUrl", url);
			}
		});
		add(wallImage);
		return wallImage;
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
		square.setWallDown(bottomWallImg != null);
		square.setWallUp(topWallImg != null);
		square.setWallLeft(leftWallImg != null);
		square.setWallRight(rightWallImg != null);
		return square;
	}

}
