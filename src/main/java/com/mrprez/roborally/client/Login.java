package com.mrprez.roborally.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mrprez.roborally.shared.UserGwt;

public class Login implements EntryPoint {
	private Label errorLabel = new Label();
	private Label usernameLabel = new Label("Login:");
	private Label passwordLabel = new Label("Mot de passe:");
	private TextBox usernameTextBox = new TextBox();
	private PasswordTextBox passwordTextBox = new PasswordTextBox();
	private Button validateButton = new Button("Connexion");
	
	
	public void onModuleLoad() {
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setStyleName("authenticationPanel");
		RootPanel.get().add(verticalPanel);
		verticalPanel.add(errorLabel);
		errorLabel.setVisible(false);
		verticalPanel.add(usernameLabel);
		verticalPanel.add(usernameTextBox);
		verticalPanel.add(passwordLabel);
		verticalPanel.add(passwordTextBox);
		verticalPanel.add(validateButton);
		validateButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
				errorLabel.setVisible(false);
				AuthenticationGwtServiceAsync authenticationGwtService = GWT.create(AuthenticationGwtService.class);
				authenticationGwtService.authenticate(usernameTextBox.getText(), passwordTextBox.getText(), new AsyncCallback<UserGwt>() {
					
					@Override
					public void onSuccess(UserGwt result) {
						UrlBuilder urlBuilder = new UrlBuilder();
						urlBuilder.setPath("GameBord.html");
					}
					
					@Override
					public void onFailure(Throwable caught) {
						PopupPanel popupPanel = new PopupPanel(true, true);
						popupPanel.add(new Label(caught.getMessage()));
						popupPanel.setGlassEnabled(true);
						popupPanel.center();
					}
				});
				
			}
		});
	}
}
