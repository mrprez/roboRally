package com.mrprez.roborally.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mrprez.roborally.shared.GameGwt;

public class Home implements EntryPoint {

	private GameGwtServiceAsync gameGwtService = GWT.create(GameGwtService.class);
	private Tree gameList = new Tree();
	private UrlBuilder urlBuilder = Window.Location.createUrlBuilder();
	
	
	public void onModuleLoad() {
		urlBuilder.setPath("roboRally/Game.html");
		VerticalPanel verticalPanel = new VerticalPanel();
		RootPanel.get().add(verticalPanel);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setWidth("100%");
		verticalPanel.add(gameList);
		gameGwtService.getGameList(new AbstractAsyncCallback<List<GameGwt>>() {
			@Override
			public void onSuccess(List<GameGwt> result) {
				for(GameGwt game : result){
					urlBuilder.setParameter("gameId", String.valueOf(game.getId()));
					gameList.add(new Anchor(game.getName(), urlBuilder.buildString()));
				}
				gameList.add(buildNewGameFormPanel());
			}
		});
	}
	
	
	
	private FormPanel buildNewGameFormPanel(){
		final FormPanel formPanel = new FormPanel();
		FlexTable flexTable = new FlexTable();
		formPanel.add(flexTable);
		
		flexTable.setWidget(0,0,new Label("Nom"));
		TextBox nameField = new TextBox();
		flexTable.setWidget(0,1,nameField);
		
		flexTable.setWidget(1,0,new Label("Taille"));
		FlowPanel sizePanel = new FlowPanel();
		IntegerBox sizeXField = new IntegerBox();
		sizeXField.getElement().setAttribute("type", "number");
		IntegerBox sizeYField = new IntegerBox();
		sizeYField.getElement().setAttribute("type", "number");
		sizePanel.add(sizeXField);
		sizePanel.add(new InlineLabel("x"));
		sizePanel.add(sizeYField);
		flexTable.setWidget(1, 1, sizePanel);
		
		Button submitButton = new Button("Créer");
		submitButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				formPanel.submit();
			}
		});
		flexTable.setWidget(2,1,submitButton);
		
		formPanel.addSubmitHandler(buildSubmitHandler(nameField, sizeXField, sizeYField));
		
		return formPanel;
	}
	
	private SubmitHandler buildSubmitHandler(final TextBox nameField, final IntegerBox sizeXBox, final IntegerBox sizeYBox){
		return new SubmitHandler() {
			@Override
			public void onSubmit(SubmitEvent event) {
				gameGwtService.createNewGame(nameField.getText(), sizeXBox.getValue(), sizeYBox.getValue(), 
					new AbstractAsyncCallback<Integer>() {
						@Override
						public void onSuccess(Integer result) {
							urlBuilder.setParameter("gameId", String.valueOf(result));
							gameList.add(new Anchor(nameField.getText(), urlBuilder.buildString()));
						}
					}
				);
			}
		};
		
	}
	
}
