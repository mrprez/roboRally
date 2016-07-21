package com.mrprez.roborally.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mrprez.roborally.shared.UserGwt;

public class Register implements EntryPoint {
	private String token;
	private String eMail;
	
	private Label usernameLabel = new Label("Login:");
	private Label passwordLabel = new Label("Mot de passe:");
	private Label confirmLabel = new Label("Confirmer:");
	private TextBox usernameTextBox = new TextBox();
	private PasswordTextBox passwordTextBox = new PasswordTextBox();
	private PasswordTextBox confirmTextBox = new PasswordTextBox();
	private Button validateButton = new Button("Connexion");
	
	
	@Override
	public void onModuleLoad() {
		token = Window.Location.getParameter("token");
		eMail = Window.Location.getParameter("eMail");
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.addStyleName("registerPanel");
		RootPanel.get().add(verticalPanel);
		verticalPanel.add(usernameLabel);
		verticalPanel.add(usernameTextBox);
		verticalPanel.add(passwordLabel);
		verticalPanel.add(passwordTextBox);
		verticalPanel.add(confirmLabel);
		verticalPanel.add(confirmTextBox);
		verticalPanel.add(validateButton);
		validateButton.addClickHandler(buildValidationHandler());

	}
	
	private ClickHandler buildValidationHandler(){
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if( ! passwordTextBox.getText().equals(confirmTextBox.getText())){
					ErrorDialogBox.display("Mot de passe différent de sa confirmation");
					return;
				}
				AuthenticationGwtServiceAsync authenticationGwtService = GWT.create(AuthenticationGwtService.class);
				authenticationGwtService.register(usernameTextBox.getText(), passwordTextBox.getText(), eMail, token, new AbstractAsyncCallback<UserGwt>() {
					@Override
					public void onSuccess(UserGwt result) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		};
	}

}
