package com.mrprez.roborally.client.home;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mrprez.roborally.client.ErrorDialogBox;
import com.mrprez.roborally.client.service.AbstractAsyncCallback;
import com.mrprez.roborally.client.service.BoardGwtService;
import com.mrprez.roborally.client.service.BoardGwtServiceAsync;
import com.mrprez.roborally.client.service.GameGwtService;
import com.mrprez.roborally.client.service.GameGwtServiceAsync;
import com.mrprez.roborally.shared.BuildingBoardGwt;

public class NewGameDialogBox extends DialogBox implements SubmitHandler, ValueChangeHandler<Integer> {
	public static final int MAX_ROBOT_INDEX = 7;

	private GameGwtServiceAsync gameGwtService = GWT.create(GameGwtService.class);
	private BoardGwtServiceAsync boardGwtService = GWT.create(BoardGwtService.class);

	private TextBox nameField;
	private ListBox boardList;
	private IntegerBox aiNbField;
	private List<String> invitedPlayerEMails = new ArrayList<String>();
	private int aiRobotNb = 0;


	public NewGameDialogBox() {
		super(false, true);
		setGlassEnabled(true);
		addStyleName("newGameDialogBox");
		setText("Nouvelle partie");
		add(buildNewGameFormPanel());
	}

	private FormPanel buildNewGameFormPanel() {
		final FormPanel formPanel = new FormPanel();
		final VerticalPanel verticalPanel = new VerticalPanel();
		formPanel.add(verticalPanel);

		FlowPanel nameFlowPanel = new FlowPanel();
		verticalPanel.add(nameFlowPanel);
		Label nameLabel = new Label("Nom");
		nameLabel.addStyleName("newGameFormLabel");
		nameFlowPanel.add(nameLabel);
		nameField = new TextBox();
		nameField.addStyleName("gameNameField");
		nameFlowPanel.add(nameField);

		FlowPanel boardFlowPanel = new FlowPanel();
		verticalPanel.add(boardFlowPanel);
		Label boardLabel = new Label("Plateau");
		boardLabel.addStyleName("newGameFormLabel");
		boardFlowPanel.add(boardLabel);
		boardList = new ListBox();
		boardFlowPanel.add(boardList);
		boardGwtService.listUserValidBuildingBoard(new AbstractAsyncCallback<List<BuildingBoardGwt>>() {
			@Override
			public void onSuccess(List<BuildingBoardGwt> result) {
				for (BuildingBoardGwt buildingBoard : result) {
					boardList.addItem(buildingBoard.getName() + " " + buildingBoard.getSizeX() + "x" + buildingBoard.getSizeY(), buildingBoard.getId().toString());
				}
			}
		});

		FlowPanel aiNbFlowPanel = new FlowPanel();
		verticalPanel.add(aiNbFlowPanel);
		Label aiNbLabel = new Label("Nombre d'IA");
		aiNbLabel.addStyleName("newGameFormLabel");
		aiNbFlowPanel.add(aiNbLabel);
		aiNbField = new IntegerBox();
		aiNbField.setValue(aiRobotNb);
		aiNbField.getElement().setAttribute("type", "number");
		aiNbField.getElement().setAttribute("min", "0");
		aiNbField.getElement().setAttribute("max", String.valueOf(MAX_ROBOT_INDEX));
		aiNbField.addStyleName("sizeField");
		aiNbField.addValueChangeHandler(this);
		aiNbFlowPanel.add(aiNbField);

		verticalPanel.add(new HTML("<hr/>"));

		verticalPanel.add(new Label("Inviter des joueurs (e-mails):"));

		final FlowPanel newPlayerPanel = new FlowPanel();
		verticalPanel.add(newPlayerPanel);
		final TextBox newPlayerTextBox = new TextBox();
		newPlayerTextBox.addStyleName("newPlayerField");
		Button addPlayerButton = new Button("Inviter");
		addPlayerButton.addStyleName("addPlayerButton");
		newPlayerPanel.add(newPlayerTextBox);
		newPlayerPanel.add(addPlayerButton);

		addPlayerButton.addClickHandler(buildAddPlayerClickHandler(newPlayerTextBox, verticalPanel));

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

		formPanel.addSubmitHandler(this);

		return formPanel;
	}


	@Override
	public void onValueChange(ValueChangeEvent<Integer> event) {
		try {
			if (aiNbField.getValueOrThrow() == null) {
				aiNbField.setValue(aiRobotNb);
			} else if (aiNbField.getValueOrThrow() < 0) {
				ErrorDialogBox.display("Nombre d'IA: valeur négative");
				aiNbField.setValue(aiRobotNb);
			} else if (invitedPlayerEMails.size() + aiNbField.getValueOrThrow() > MAX_ROBOT_INDEX) {
				ErrorDialogBox.display("Maximum " + (MAX_ROBOT_INDEX + 1) + " robots sur le plateau");
				aiNbField.setValue(aiRobotNb);
			} else {
				aiRobotNb = aiNbField.getValue();
			}
		} catch (ParseException e) {
			ErrorDialogBox.display("Nombre d'IA: valeur indiquée invalide");
		}
	}

	private ClickHandler buildAddPlayerClickHandler(final TextBox newPlayerTextBox, final VerticalPanel verticalPanel) {
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!newPlayerTextBox.getText().matches("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+[.][A-Za-z]{2,6}")) {
					ErrorDialogBox.display("Vous devez renseigner une adresse e-mail valide");
					return;
				}

				if (invitedPlayerEMails.size() + 1 + aiRobotNb > MAX_ROBOT_INDEX) {
					ErrorDialogBox.display("Maximum " + (MAX_ROBOT_INDEX + 1) + " robots sur le plateau");
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

	@Override
	public void onSubmit(SubmitEvent event) {
		if (nameField.getText().isEmpty()) {
			ErrorDialogBox.display("Vous devez renseigner un nom");
			return;
		}
		gameGwtService.createNewGame(nameField.getText(), Integer.valueOf(boardList.getSelectedValue()), aiRobotNb, invitedPlayerEMails,
				new AbstractAsyncCallback<Integer>() {
					@Override
					public void onSuccess(Integer result) {
						UrlBuilder urlBuilder = Window.Location.createUrlBuilder();
						urlBuilder.setPath("roboRally/Game.html");
						urlBuilder.setParameter("gameId", String.valueOf(result));
						Window.Location.assign(urlBuilder.buildString());
					}
				});
	}

}
