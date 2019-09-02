package foodstart.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class MainController {

	@FXML
	private Pane rootPane;
	
	public void showOrder() {
		rootPane.getChildren().clear();
		rootPane.getChildren().add(new Text("Hello"));
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
	
	public void showSales() {
		
	}
}
