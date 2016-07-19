package com.mrprez.roborally.client;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.HTML;
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
	public static final int MAX_ROBOT_INDEX = 7;
	
	private GameGwtServiceAsync gameGwtService = GWT.create(GameGwtService.class);
	private Tree gameList = new Tree();
	private UrlBuilder urlBuilder = Window.Location.createUrlBuilder();
	private List<String> invitedPlayerEMails = new ArrayList<String>();
	private int aiRobotNb = 0;
	
	
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
			}
		});
		
		final DialogBox newGameDialogBox = buildNewGameDialogBox();
		
		Button newGameButton = new Button("Nouvelle partie");
		newGameButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				newGameDialogBox.center();
			}
		});
		verticalPanel.add(newGameButton);
		
	}
	
	
	
	private DialogBox buildNewGameDialogBox(){
		DialogBox newGameDialogBox = new DialogBox(false, true);
		newGameDialogBox.setGlassEnabled(true);
		newGameDialogBox.addStyleName("newGameDialogBox");
		newGameDialogBox.setText("Nouvelle partie");
		newGameDialogBox.add(buildNewGameFormPanel());
		return newGameDialogBox;
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
		sizeXField.getElement().setAttribute("type", "number");
		sizeXField.addStyleName("sizeField");
		IntegerBox sizeYField = new IntegerBox();
		sizeYField.getElement().setAttribute("type", "number");
		sizeYField.addStyleName("sizeField");
		sizeFlowPanel.add(sizeXField);
		sizeFlowPanel.add(new InlineLabel("x"));
		sizeFlowPanel.add(sizeYField);
		
		FlowPanel aiNbFlowPanel = new FlowPanel();
		verticalPanel.add(aiNbFlowPanel);
		Label aiNbLabel = new Label("Nombre d'IA");
		aiNbLabel.addStyleName("newGameFormLabel");
		aiNbFlowPanel.add(aiNbLabel);
		final IntegerBox aiNbField = new IntegerBox();
		aiNbField.setValue(aiRobotNb);
		aiNbField.getElement().setAttribute("type", "number");
		aiNbField.getElement().setAttribute("min", "0");
		aiNbField.getElement().setAttribute("max", String.valueOf(MAX_ROBOT_INDEX));
		aiNbField.addStyleName("sizeField");
		aiNbField.addValueChangeHandler(buildOnAiNbChangeHandler(aiNbField));
		aiNbFlowPanel.add(aiNbField);
		
		verticalPanel.add(new HTML("<hr/>"));
		
		verticalPanel.add(new Label("Inviter des joueurs (e-mails):"));
		
		final FlowPanel newPlayerPanel = new FlowPanel();
		verticalPanel.add(newPlayerPanel);
		final TextBox newPlayerTextBox = new TextBox();
		newPlayerTextBox.addStyleName("newPlayerField");
		Button addPlayerButton = new Button("+");
		addPlayerButton.addStyleName("addPlayerButton");
		newPlayerPanel.add(newPlayerTextBox);
		newPlayerPanel.add(addPlayerButton);
		
		addPlayerButton.addClickHandler(buildAddPlayerClickHandler(newPlayerTextBox, verticalPanel));
				
		Button submitButton = new Button("Créer");
		submitButton.addStyleName("createGameButton");
		verticalPanel.add(submitButton);
		submitButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				formPanel.submit();
			}
		});
		
		formPanel.addSubmitHandler(buildSubmitHandler(nameField, sizeXField, sizeYField));
		
		return formPanel;
	}
	
	
	private ValueChangeHandler<Integer> buildOnAiNbChangeHandler(final IntegerBox aiNbField){
		return new ValueChangeHandler<Integer>() {
			@Override
			public void onValueChange(ValueChangeEvent<Integer> event) {
				try {
					if(aiNbField.getValueOrThrow()==null){
						aiNbField.setValue(aiRobotNb);
					}else if(aiNbField.getValueOrThrow()<0){
						displayErrorDialogBox("Nombre d'IA: valeur négative");
						aiNbField.setValue(aiRobotNb);
					}else if(invitedPlayerEMails.size() + aiNbField.getValueOrThrow() > MAX_ROBOT_INDEX){
						displayErrorDialogBox("Maximum "+(MAX_ROBOT_INDEX+1)+" robots sur le plateau");
						aiNbField.setValue(aiRobotNb);
					} else {
						aiRobotNb = aiNbField.getValue();
					}
				} catch (ParseException e) {
					displayErrorDialogBox("Nombre d'IA: valeur indiquée invalide");
				}
			}
		};
	}
	
	
	private ClickHandler buildAddPlayerClickHandler(final TextBox newPlayerTextBox, final VerticalPanel verticalPanel){
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if( ! newPlayerTextBox.getText().matches("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+[.][A-Za-z]{2,6}")){
					displayErrorDialogBox("Vous devez renseigner une adresse e-mail valide");
					return;
				}
				
				if(invitedPlayerEMails.size() + 1 + aiRobotNb > MAX_ROBOT_INDEX){
					displayErrorDialogBox("Maximum "+(MAX_ROBOT_INDEX+1)+" robots sur le plateau");
					return;
				}
				
				invitedPlayerEMails.add(newPlayerTextBox.getText());
				
				final FlowPanel flowPanel = new FlowPanel();
				verticalPanel.insert(flowPanel, 6);
				final Label invitedPlayerMailLabel = new Label(newPlayerTextBox.getText());
				flowPanel.add(invitedPlayerMailLabel);
				invitedPlayerMailLabel.addStyleName("invitedPlayerMailLabel");
				Button removeInvitedPlayerButton = new Button("✖");
				removeInvitedPlayerButton.addStyleName("removeInvitedPlayerButton");
				flowPanel.add(removeInvitedPlayerButton);
				removeInvitedPlayerButton.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						invitedPlayerEMails.remove(invitedPlayerMailLabel.getText());
						flowPanel.removeFromParent();
					}
				});
				
				newPlayerTextBox.setText("");
			}
		};
	}
	
	
	private DialogBox displayErrorDialogBox(String errorMessage){
		final DialogBox errorDialogBox = new DialogBox(true, true);
		errorDialogBox.setText("Erreur");
		FlowPanel flowPanel = new FlowPanel();
		flowPanel.add(new Label(errorMessage));
		Button okButton = new Button("OK");
		flowPanel.add(okButton);
		okButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				errorDialogBox.setVisible(false);
			}
		});
		errorDialogBox.add(flowPanel);
		errorDialogBox.center();
		return errorDialogBox;
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
