package com.mrprez.roborally.client.edit;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.mrprez.roborally.client.service.AbstractAsyncCallback;
import com.mrprez.roborally.client.service.BoardGwtService;
import com.mrprez.roborally.client.service.BoardGwtServiceAsync;
import com.mrprez.roborally.shared.SquareGwt;

public class ElementPanel extends StackLayoutPanel {
	private BoardGwtServiceAsync boardGwtService = GWT.create(BoardGwtService.class);
	private HandlerManager handlerManager;
	private List<Image> targetList = new ArrayList<Image>();


	public ElementPanel(HandlerManager handlerManager) {
		super(Unit.EM);
		this.handlerManager = handlerManager;
		addStyleName("elementPanel");

		final FlowPanel commonSquarePanel = new FlowPanel();
		add(commonSquarePanel, "Divers", 2.5);
		final FlowPanel convoyerSquarePanel = new FlowPanel();
		add(convoyerSquarePanel, "Convoyeur", 2.5);
		add(buildTargetPanel(), "Parcours", 2.5);
		add(buildCommandPanel(), "Commandes", 2.5);

		boardGwtService.getAvailableSquareList(new AbstractAsyncCallback<List<SquareGwt>>() {
			@Override
			public void onSuccess(List<SquareGwt> result) {
				for (SquareGwt square : result) {
					if (square.getImageName().startsWith("ConveyorBelt")) {
						convoyerSquarePanel.add(buildElementImage(square));
					} else {
						commonSquarePanel.add(buildElementImage(square));
					}
				}
			}
		});

	}

	public Image getTarget(int targetNb) {
		return targetList.get(targetNb);
	}

	private Image buildElementImage(final SquareGwt square) {
		Image image = new Image(square.getImageUrl());
		image.addDragStartHandler(new DragStartHandler() {
			@Override
			public void onDragStart(DragStartEvent event) {
				event.setData("imageUrl", square.getImageUrl());
				event.setData("squareType", square.getType());
				event.setData("squareArgs", square.getArgs());
			}
		});

		return image;
	}

	private FlowPanel buildCommandPanel() {
		final FlowPanel commandPanel = new FlowPanel();

		Button saveButton = new Button("Sauvegarder");
		commandPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				handlerManager.fireEvent(new SaveBoardEvent());
			}
		});

		Button homeButton = new Button("Accueil");
		commandPanel.add(homeButton);
		homeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				UrlBuilder urlBuilder = Window.Location.createUrlBuilder();
				urlBuilder.setPath("roboRally/Home.html");
				Window.Location.assign(urlBuilder.buildString());
			}
		});

		return commandPanel;
	}


	private FlowPanel buildTargetPanel() {
		final FlowPanel targetPanel = new FlowPanel();
		for (int targetNb = 0; targetNb <= 8; targetNb++) {
			Image targetImg = buildTargetImage(targetNb);
			targetImg.getElement().setAttribute("targetNb", String.valueOf(targetNb));
			targetPanel.add(targetImg);
			targetList.add(targetImg);
		}
		targetPanel.addDomHandler(new DropHandler() {
			@Override
			public void onDrop(DropEvent event) {
				targetPanel.add(targetList.get(Integer.parseInt(event.getData("targetNb"))));
			}
		}, DropEvent.getType());
		targetPanel.addDomHandler(new DragOverHandler() {
			@Override
			public void onDragOver(DragOverEvent event) {
			}
		}, DragOverEvent.getType());
		return targetPanel;
	}


	private Image buildTargetImage(final int targetNb) {
		Image image = targetNb == 0 ? new Image("img/Start.png") : new Image("img/Target" + targetNb + ".png");
		image.addDragStartHandler(new DragStartHandler() {
			@Override
			public void onDragStart(DragStartEvent event) {
				event.setData("targetNb", String.valueOf(targetNb));
			}
		});
		image.addStyleName("targetImage");
		return image;
	}

}
