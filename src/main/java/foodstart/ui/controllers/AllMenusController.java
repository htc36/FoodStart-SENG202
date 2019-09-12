package foodstart.ui.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public class AllMenusController {
	@FXML
	private FlowPane flowPane;

	@FXML
	private MenuButton menuButton;

	/**
	 * Box background for menu items in the grid
	 */
	private Background boxBackground;


	public void initialize() {
		boxBackground = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
		//populateAllMenus(flowPane);

	}

	/**
	 * Populate the FlowPane with all menu items
	 *
	 * @param flowPane The flowpane to populate
	 */
	public void populateAllMenus(FlowPane flowPane) {
		flowPane.getChildren().clear();
//        for (Set<Menu> menu : Managers.getMenuManager().getMenus()) {
//            flowPane.getChildren().add(createMenuItemBox(menu));
//        }
	}
}
