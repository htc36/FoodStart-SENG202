package foodstart.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

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
		} catch (IOException e) {
			e.printStackTrace();
		}
		showOrder();
	}

	public void showOrder() {
		((CreateOrderController) createOrderFXML.getController()).initialize();
		rootPane.setCenter(createOrderFXML.getRoot());
	}

	public void showManageCurrentMenu() {

	}

	public void showManageAllMenus() {

	}

	public void showManageMenuItems() {

	}

	public void showManageRecipes() {
		((recipesController) manageRecipes.getController()).populateTable();
		rootPane.setCenter(manageRecipes.getRoot());
	}

	public void showManageIngredients() {

	}

	public void showStockInventory() {
		rootPane.setCenter(stockInventory.getRoot());
	}


	public void showStockSuppliers() {
		((SupplierController) stockSuppliers.getController()).populateTable();
		rootPane.setCenter(stockSuppliers.getRoot());

	}

	public void showSales() {
		((SalesController) salesLog.getController()).populateTable();
		rootPane.setCenter(salesLog.getRoot());
	}
}
