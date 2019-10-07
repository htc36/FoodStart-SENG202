package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.Persistence;
import foodstart.model.DataFileType;
import foodstart.model.DataType;
import foodstart.model.menu.Menu;
import foodstart.ui.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Controls ui for all menus screen
 */
public class AllMenusController {
	/**
	 * Flow pane for menu display
	 */
	@FXML
	private FlowPane flowPane;
	/**
	 * Loader for popup fxml
	 */
    private FXMLLoader loader;
	/**
	 * Stage for popup
	 */
	private Stage popupStage;
	/**
	 * FXML loader for add menu popup
	 */
	private FXMLLoader addLoader;
	/**
	 * Stage for the add menu popup
	 */
	private Stage addPopup;


    /**
	 * Box background for menu items in the grid
	 */
	private Background boxBackground;


	/**
	 * Initialises the AllMenusController
	 */
	public void initialize() {
		boxBackground = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));

		addLoader = new FXMLLoader(getClass().getResource("addMenu.fxml"));
		try {
			addLoader.load();

		} catch (IOException e) {
			e.printStackTrace();
		}

		addPopup = new Stage();
		addPopup.initModality(Modality.WINDOW_MODAL);
		addPopup.setTitle("Add New Menu");
		Scene addScene = new Scene(addLoader.getRoot());
		addPopup.setScene(addScene);

		populateAllMenus();

	}

	/**
	 * Populate the FlowPane with all menu items
	 *
	 */
	public void populateAllMenus() {
		flowPane.getChildren().clear();
        for (Menu menu : Managers.getMenuManager().getMenuSet()) {
			flowPane.getChildren().add(createMenuBox(menu));
        }
	}

	/**
	 * Create a VBox that represents a menu to be added to the list
	 *
	 * @param menu Menu item to represent
	 * @return Node representing the menuitem
	 */
	private Node createMenuBox(Menu menu) {
		VBox box = new VBox();
		box.setPrefSize(150, 150);
		box.setPadding(new Insets(5));
		box.setBackground(boxBackground);
		box.setAlignment(Pos.CENTER);
		box.setCursor(Cursor.HAND);
		
		box.setOnMouseClicked((event) -> {
			loader = new FXMLLoader(getClass().getResource("viewMenuPopUp.fxml"));
			try {
				loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
            ((ViewMenuController) loader.getController()).setMenuInfo(menu);

			Scene scene = new Scene(loader.getRoot());

			popupStage = new Stage();
			popupStage.initModality(Modality.WINDOW_MODAL);

            ((ViewMenuController) loader.getController()).setStage(popupStage);


			if (popupStage.getOwner() == null) {
				popupStage.initOwner(this.flowPane.getScene().getWindow());
			}
			popupStage.setTitle("View Menu");
			popupStage.setScene(scene);
			popupStage.showAndWait();
		});
		FlowPane.setMargin(box, new Insets(5));
		box.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(4), BorderWidths.DEFAULT)));

		Text menuName = new Text(menu.getTitle());
		menuName.setTextAlignment(TextAlignment.CENTER);


		box.getChildren().add(menuName);

		return box;
	}

	public void onAdd() {
		addPopup.showAndWait();
	}




}
