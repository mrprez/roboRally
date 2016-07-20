package com.mrprez.roborally.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class ErrorDialogBox extends DialogBox {
	
	
	public ErrorDialogBox(String errorMessage){
		super(true, true);
		setText("Erreur");
		FlowPanel flowPanel = new FlowPanel();
		flowPanel.add(new Label(errorMessage));
		Button okButton = new Button("OK");
		flowPanel.add(okButton);
		okButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setVisible(false);
			}
		});
		add(flowPanel);
	}
	
	
	public static void display(String errorMessage){
		new ErrorDialogBox(errorMessage).center();
	}

}
