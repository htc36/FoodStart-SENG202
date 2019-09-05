package foodstart.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class MainController {

	@FXML
	private BorderPane rootPane;
	
	public void showOrder() {
		rootPane.getChildren().clear();
		rootPane.setCenter(new Text("Hello"));
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
		rootPane.getChildren().clear();
		rootPane.setCenter(FXMLLoader.load(getClass().getResource("createorder.fxml")));
	}
}
