package foodstart.ui.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import foodstart.manager.Managers;
import foodstart.manager.stock.IngredientManager;
import foodstart.model.stock.Ingredient;

public class InventoryController {
	@FXML
	private TableView<Ingredient> inventoryView;

	@FXML
	private TableColumn<Ingredient, String> id;

	@FXML
	private TableColumn<Ingredient, String> name;
	
	@FXML
	private TableColumn<Ingredient, String> truckStock;

	@FXML
	private TableColumn<Ingredient, String> kitchenStock;

	@FXML
	private TableColumn<Ingredient, String> dietary;
	
	private FXMLLoader loader;
	Stage stage;

	@FXML
	public void initialize() {
		populateTable();
	}
	
	private void populateTable() {
		IngredientManager ingredientManager = Managers.getIngredientManager();
		Set<Ingredient> ingredientSet = ingredientManager.getIngredientSet();
		ObservableList<Ingredient> ingredientObserver = FXCollections.observableArrayList(ingredientSet);
		inventoryView.setItems(ingredientObserver);
		id.setCellValueFactory(cell -> new SimpleStringProperty(Integer.toString(cell.getValue().getId())));
		name.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
		truckStock.setCellValueFactory(cell -> new SimpleStringProperty(Integer.toString(cell.getValue().getTruckStock())));
		kitchenStock.setCellValueFactory(cell -> new SimpleStringProperty(Integer.toString(cell.getValue().getKitchenStock())));
		dietary.setCellValueFactory(cell -> new SimpleStringProperty(ingredientManager.safeForString(cell.getValue().getId())));
	}
	public void addIngredient() {
		loader = new FXMLLoader(getClass().getResource("../addIngredientPopUp.fxml"));
		try {
			loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(loader.getRoot());
		stage = new Stage();
		stage.setTitle("Add Item");
		stage.setScene(scene);
		stage.show();

		
	}
		

}