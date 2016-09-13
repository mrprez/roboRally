package com.mrprez.roborally.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mrprez.roborally.shared.AuthenticationException;

public abstract class AbstractAsyncCallback<T> implements AsyncCallback<T> {

	@Override
	public void onFailure(Throwable caught) {
		DialogBox dialogBox;
		if(caught instanceof AuthenticationException){
			dialogBox = buildDisconnectionDialogBox();
		} else {
			dialogBox = buildExceptionDialogBox(caught);
		}
		
		dialogBox.center();
		
	}
	
	
	private DialogBox buildDisconnectionDialogBox(){
		DialogBox dialogBox = new DialogBox(false, true);
		dialogBox.setTitle("Déconnexion");
		dialogBox.setText("Déconnexion");
		VerticalPanel verticalPanel = new VerticalPanel();
		dialogBox.add(verticalPanel);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(new Label("Vous avez était déconnecté. Veuillez recharger la page."));
		Button reloadButton = new Button("Recharger");
		verticalPanel.add(reloadButton);
		reloadButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				UrlBuilder urlBuilder = Window.Location.createUrlBuilder();
				urlBuilder.setPath("roboRally");
				Window.Location.assign(urlBuilder.buildString());
			}
		});
		return dialogBox;
	}
	
	
	private DialogBox buildExceptionDialogBox(Throwable caught){
		DialogBox dialogBox = new DialogBox(false, true);
		dialogBox.setTitle(caught.getMessage());
		dialogBox.setText(caught.getMessage());
		VerticalPanel verticalPanel = new VerticalPanel();
		dialogBox.add(verticalPanel);
		for(StackTraceElement stackEl : caught.getStackTrace()){
			String stackTraceElText = "at "+stackEl.getClassName()+"."+stackEl.getMethodName()
					+"("+stackEl.getFileName()+":"+stackEl.getLineNumber()+")";
			verticalPanel.add(new Label(stackTraceElText));
		}
		return dialogBox;
	}
	
}
