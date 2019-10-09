package foodstart.ui.controllers;

import foodstart.analysis.SalesReporter;
import foodstart.manager.Managers;
import foodstart.ui.Refreshable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
	 * FXML loader for analysis view
	 */
	private FXMLLoader analysisView;

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
			
			analysisView = new FXMLLoader(getClass().getResource("analysisView.fxml"));
			analysisView.load();

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
		changeView(manageAllMenus);
	}

	/**
	 * Shows the manage menu items screen
	 */
	public void showManageMenuItems() {
		rootPane.setCenter(manageMenuItems.getRoot());
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
	 * Shows the analysis view
	 */
	public void showAnalysis() {
		changeView(analysisView);
	}

	/**
	 * Writes a sales report
	 */
	public void writeSalesReport() {
		SalesReporter report = new SalesReporter();
		report.collectData();
		boolean success = report.writeData();
		if (success) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully wrote sales report to foodstart directory", ButtonType.OK);
			alert.setHeaderText("Action successful");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Could not write sales report", ButtonType.OK);
			alert.setHeaderText("Error writing sales report");
			alert.showAndWait();
		}
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
