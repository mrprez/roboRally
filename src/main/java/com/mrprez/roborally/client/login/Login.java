package com.mrprez.roborally.client.login;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mrprez.roborally.client.service.AbstractAsyncCallback;
import com.mrprez.roborally.client.service.AuthenticationGwtService;
import com.mrprez.roborally.client.service.AuthenticationGwtServiceAsync;
import com.mrprez.roborally.shared.UserGwt;

public class Login implements EntryPoint {
	private Label errorLabel = new Label();
	private Label titleLabel = new Label("Authentication");
	private Label usernameLabel = new Label("Login:");
	private Label passwordLabel = new Label("Mot de passe:");
	private TextBox usernameTextBox = new TextBox();
	private PasswordTextBox passwordTextBox = new PasswordTextBox();
	private Button validateButton = new Button("Connexion");
	
	
	public void onModuleLoad() {
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setStyleName("authenticationPanel");
		RootPanel.get().add(verticalPanel);

		verticalPanel.add(titleLabel);
		titleLabel.addStyleName("title");

		verticalPanel.add(errorLabel);
		errorLabel.setVisible(false);

		verticalPanel.add(usernameLabel);
		usernameLabel.addStyleName("label");

		verticalPanel.add(usernameTextBox);
		usernameTextBox.addStyleName("field");
		usernameTextBox.setFocus(true);

		verticalPanel.add(passwordLabel);
		passwordLabel.addStyleName("label");

		verticalPanel.add(passwordTextBox);
		passwordTextBox.addStyleName("field");

		verticalPanel.add(validateButton);
		validateButton.addStyleName("validateButton");
		validateButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
				errorLabel.setVisible(false);
				AuthenticationGwtServiceAsync authenticationGwtService = GWT.create(AuthenticationGwtService.class);
				authenticationGwtService.authenticate(usernameTextBox.getText(), passwordTextBox.getText(), new AbstractAsyncCallback<UserGwt>() {
					@Override
					public void onSuccess(UserGwt result) {
						if(result==null){
							DialogBox dialogBox = new DialogBox(true, true);
							dialogBox.setText("Echec d'authentification");
							dialogBox.add(new Label("L'authentification a échoué. Vérifiez votre login et votre mot de passe"));
							dialogBox.center();
						} else {
							UrlBuilder urlBuilder = Window.Location.createUrlBuilder();
							urlBuilder.setPath("roboRally/Home.html");
							Window.Location.assign(urlBuilder.buildString());
						}
					}
				});
				
			}
		});
	}
}
