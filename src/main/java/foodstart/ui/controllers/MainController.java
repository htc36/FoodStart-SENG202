package foodstart.ui.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
			createOrderFXML = new FXMLLoader(getClass().getResource("../createorder.fxml"));
			createOrderFXML.load();
			
			salesLog = new FXMLLoader(getClass().getResource("../sales.fxml"));
			salesLog.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		showOrder();
	}
	
	public void showOrder() {
		((CreateOrderController)createOrderFXML.getController()).initialize();
		rootPane.setCenter(createOrderFXML.getRoot());
	}
	
	public void showManageCurrentMenu() {
		
	}
	
	public void showManageAllMenus() {
		
	}
	
	public void showManageMenuItems() {
		
	}
	
	public void showManageRecipes() {
		
	}
	
	public void showManageIngredients() {
		
	}
	
	public void showStockInventory() {
		
	}
	
	public void showStockSuppliers() {
		
	}
	
	public void showSales() throws IOException {
		rootPane.setCenter(salesLog.getRoot());
	}
}
