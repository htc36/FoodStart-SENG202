package foodstart.ui.controllers;

import java.io.IOException;

import foodstart.manager.Managers;
import foodstart.ui.Refreshable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;

public class MainController {

	@FXML
	private BorderPane rootPane;

	private FXMLLoader createOrderFXML;
	private FXMLLoader manageCurrentMenuFXML;
	private FXMLLoader manageAllMenus;
	private FXMLLoader manageMenuItems;
	private FXMLLoader manageRecipes;
	private FXMLLoader manageIngredients;
	private FXMLLoader stockInventory;
	private FXMLLoader stockSuppliers;
	private FXMLLoader salesLog;

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

	public void showOrder() {
		changeView(createOrderFXML);
	}

	public void showManageCurrentMenu() {

	}

	public void showManageAllMenus() {
		//((AllMenusController) manageAllMenus.getController()).populateAllMenus();
		rootPane.setCenter(manageAllMenus.getRoot());
	}

	public void showManageMenuItems() {
		rootPane.setCenter(manageMenuItems.getRoot());
	}

	public void showManageRecipes() {
		changeView(manageRecipes);
	}

	public void showStockInventory() {
		changeView(stockInventory);
	}


	public void showStockSuppliers() {
		changeView(stockSuppliers);
	}

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
	
	public void saveEverything() {
		Managers.getDefaultPersistence().saveAllFiles();
	}
}
