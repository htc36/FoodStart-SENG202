package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.ui.Refreshable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * Main controller for application UI
 */
public class MainController {
	/**
	 * The root pane of the application in which all other FXML are added to
	 */
	@FXML
	private BorderPane rootPane;

	/**
	 * FXML loader for create order screen
	 */
	private FXMLLoader createOrderFXML;
	/**
	 * FXML loader for manage current menu screen
	 */
	private FXMLLoader manageCurrentMenuFXML;
	/**
	 * FXML loader for manage menus screen
	 */
	private FXMLLoader manageAllMenus;
	/**
	 * FXML loader for manage menu items screen
	 */
	private FXMLLoader manageMenuItems;
	/**
	 * FXML loader for manage recipes screen
	 */
	private FXMLLoader manageRecipes;
	/**
	 * FXML loader for manage ingredients screen
	 */
	private FXMLLoader manageIngredients;
	/**
	 * FXML loader for manage inventory screen
	 */
	private FXMLLoader stockInventory;
	/**
	 * FXML loader for manage suppliers screen
	 */
	private FXMLLoader stockSuppliers;
	/**
	 * FXML loader for manage sales log screen
	 */
	private FXMLLoader salesLog;

	/**
	 * Initialises the main controller
	 */
	@FXML
	public void initialize() {
		try {
			createOrderFXML = new FXMLLoader(getClass().getResource("createOrder.fxml"));
			createOrderFXML.load();

			salesLog = new FXMLLoader(getClass().getResource("sales.fxml"));
			salesLog.load();

			stockSuppliers = new FXMLLoader(getClass().getResource("suppliers.fxml"));
			stockSuppliers.load();

			stockInventory = new FXMLLoader(getClass().getResource("inventory.fxml"));
			stockInventory.load();

			manageRecipes = new FXMLLoader(getClass().getResource("recipes.fxml"));
			manageRecipes.load();

			manageAllMenus = new FXMLLoader(getClass().getResource("allMenus.fxml"));
			manageAllMenus.load();

			manageMenuItems = new FXMLLoader(getClass().getResource("menuItems.fxml"));
			manageMenuItems.load();

		} catch (IOException e) {
			e.printStackTrace();
		}
		showOrder();
	}

	/**
	 * Shows the create order screen
	 */
	public void showOrder() {
		changeView(createOrderFXML);
	}

	/**
	 * Shows the manage current menu screen
	 */
	public void showManageCurrentMenu() {

	}

	/**
	 * Shows the manage menus screen
	 */
	public void showManageAllMenus() {
		rootPane.setCenter(manageAllMenus.getRoot());
	}

	/**
	 * Shows the manage menu items screen
	 */
	public void showManageMenuItems() {
		changeView(manageMenuItems);
	}

	/**
	 * Shows the manage recipes screen
	 */
	public void showManageRecipes() {
		changeView(manageRecipes);
	}

	/**
	 * Shows the manage inventory screen
	 */
	public void showStockInventory() {
		changeView(stockInventory);
	}

	/**
	 * Shows the manage suppliers screen
	 */
	public void showStockSuppliers() {
		changeView(stockSuppliers);
	}

	/**
	 * Shows the manage sales log screen
	 */
	public void showSales() {
		changeView(salesLog);
	}
	
	/**
	 * Change the currently shown view to the one specified
	 * @param loader FXML view to set as the current view
	 */
	private void changeView(FXMLLoader loader) {
		if (loader.getController() instanceof Refreshable) {
			((Refreshable)loader.getController()).refreshTable();
		}
		rootPane.setCenter(loader.getRoot());
	}

	/**
	 * Saves all data from managers to disk
	 */
	public void saveEverything() {
		Managers.getDefaultPersistence().saveAllFiles();
	}
}
