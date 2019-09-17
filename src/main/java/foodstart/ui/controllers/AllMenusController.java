package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.model.menu.Menu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AllMenusController {
	@FXML
	private FlowPane flowPane;


    private FXMLLoader loader;
    private Stage popupStage;
    private Scene scene;


    /**
	 * Box background for menu items in the grid
	 */
	private Background boxBackground;


	public void initialize() {
		boxBackground = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
		populateAllMenus(flowPane);


	}

	/**
	 * Populate the FlowPane with all menu items
	 *
	 * @param flowPane The flowpane to populate
	 */
	public void populateAllMenus(FlowPane flowPane) {
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

}
