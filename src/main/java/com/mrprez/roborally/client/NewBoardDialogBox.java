package com.mrprez.roborally.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NewBoardDialogBox extends DialogBox {
	
	private BoardGwtServiceAsync boardGwtService = GWT.create(BoardGwtService.class);
	
	
	public NewBoardDialogBox(){
		super(false, true);
		setGlassEnabled(true);
		addStyleName("newBoardDialogBox");
		setText("Nouveau plateau");
		add(buildNewGameFormPanel());
	}
	
	private FormPanel buildNewGameFormPanel(){
		final FormPanel formPanel = new FormPanel();
		final VerticalPanel verticalPanel = new VerticalPanel();
		formPanel.add(verticalPanel);
		
		FlowPanel nameFlowPanel = new FlowPanel();
		verticalPanel.add(nameFlowPanel);
		Label nameLabel = new Label("Nom");
		nameLabel.addStyleName("newGameFormLabel");
		nameFlowPanel.add(nameLabel);
		TextBox nameField = new TextBox();
		nameField.addStyleName("gameNameField");
		nameFlowPanel.add(nameField);
		
		FlowPanel sizeFlowPanel = new FlowPanel();
		verticalPanel.add(sizeFlowPanel);
		Label sizeLabel = new Label("Taille");
		sizeLabel.addStyleName("newGameFormLabel");
		sizeFlowPanel.add(sizeLabel);
		IntegerBox sizeXField = new IntegerBox();
		sizeXField.setValue(12);
		sizeXField.getElement().setAttribute("type", "number");
		sizeXField.addStyleName("sizeField");
		IntegerBox sizeYField = new IntegerBox();
		sizeYField.setValue(12);
		sizeYField.getElement().setAttribute("type", "number");
		sizeYField.addStyleName("sizeField");
		sizeFlowPanel.add(sizeXField);
		sizeFlowPanel.add(new InlineLabel("x"));
		sizeFlowPanel.add(sizeYField);
		
		FlowPanel buttonPanel = new FlowPanel();
		buttonPanel.addStyleName("dialogButtonPanel");
		verticalPanel.add(buttonPanel);
		Button submitButton = new Button("Créer");
		Button cancelButton = new Button("Annuler");
		buttonPanel.add(submitButton);
		buttonPanel.add(cancelButton);
		submitButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				formPanel.submit();
			}
		});
		final DialogBox dialogBox = this;
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialogBox.removeFromParent();
			}
		});
		
		formPanel.addSubmitHandler(buildSubmitHandler(nameField, sizeXField, sizeYField));
		
		return formPanel;
	}
	
	
	private SubmitHandler buildSubmitHandler(final TextBox nameField, final IntegerBox sizeXBox, final IntegerBox sizeYBox){
		return new SubmitHandler() {
			@Override
			public void onSubmit(SubmitEvent event) {
				if(nameField.getText().isEmpty()){
					ErrorDialogBox.display("Vous devez renseigner un nom");
					return;
				}
				if(sizeXBox.getValue()==null || sizeYBox.getValue()==null
						|| sizeXBox.getValue()<=0 || sizeYBox.getValue()<=0){
					ErrorDialogBox.display("Taille invalide");
					return;
				}
				if(sizeXBox.getValue() * sizeYBox.getValue() > 1000){
					ErrorDialogBox.display("Taille trop grande. Limite de 1000 cases dépassée");
					return;
				}
				
				boardGwtService.createNewBoard(nameField.getText(), sizeXBox.getValue(), sizeYBox.getValue(), 
					new AbstractAsyncCallback<Integer>() {
						@Override
						public void onSuccess(Integer boardId) {
							UrlBuilder urlBuilder = Window.Location.createUrlBuilder();
							urlBuilder.setPath("roboRally/Edit.html");
							urlBuilder.setParameter("boardId", String.valueOf(boardId));
							Window.Location.assign(urlBuilder.buildString());
						}
					}
				);
			}
		};
		
	}

}
