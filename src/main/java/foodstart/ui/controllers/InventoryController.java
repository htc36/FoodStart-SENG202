package foodstart.ui.controllers;

import java.io.IOException;
import java.util.Set;

import foodstart.manager.Managers;
import foodstart.manager.stock.IngredientManager;
import foodstart.model.order.Order;
import foodstart.model.stock.Ingredient;
import foodstart.ui.Refreshable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class InventoryController implements Refreshable {
	private Stage stage;
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
	private Stage popupStage;

	/**
	 * Observable list of ingredients
	 */
	private ObservableList<Ingredient> observableList;

	@FXML
	public void initialize() {
		populateTable();
	}

	public void populateTable() {
		IngredientManager ingredientManager = Managers.getIngredientManager();
		Set<Ingredient> ingredientSet = ingredientManager.getIngredientSet();
		observableList = FXCollections.observableArrayList(ingredientSet);
		inventoryView.setItems(observableList);
		id.setCellValueFactory(cell -> new SimpleStringProperty(Integer.toString(cell.getValue().getId())));
		name.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
		truckStock.setCellValueFactory(
				cell -> new SimpleStringProperty(Integer.toString(cell.getValue().getTruckStock())));
		kitchenStock.setCellValueFactory(
				cell -> new SimpleStringProperty(Integer.toString(cell.getValue().getKitchenStock())));
		dietary.setCellValueFactory(
				cell -> new SimpleStringProperty(ingredientManager.safeForString(cell.getValue().getId())));
	}

	/**
	 * Refreshes the table of displayed ingredients, updating its quantities among
	 * other things
	 */
	@Override
	public void refreshTable() {
		this.observableList.setAll(Managers.getIngredientManager().getIngredientSet());
	}

	public void addIngredient() {
		loader = new FXMLLoader(getClass().getResource("addIngredientPopUp.fxml"));
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
	public void editIngredient2() {
		loader = new FXMLLoader(getClass().getResource("editIngredient.fxml"));
		try {
			loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(loader.getRoot());
		stage = new Stage();
		stage.setTitle("Edit Item");
		stage.setScene(scene);
		stage.show();
		
	}
	public void editIngredient() {
		Ingredient ingredient = inventoryView.getSelectionModel().getSelectedItem();
		if (ingredient == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Could not edit ingredient as nothing is selected", ButtonType.OK);
			alert.setHeaderText("No order selected");
			alert.showAndWait();
		} else {
//			if (popupStage.getOwner() == null) {
//				popupStage.initOwner(this.inventoryView.getScene().getWindow());
//			}
			loader = new FXMLLoader(getClass().getResource("editIngredient.fxml"));
			try {
				loader.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			((EditIngredientController) loader.getController()).setIngredient(ingredient);
			Scene scene = new Scene(loader.getRoot());
			stage = new Stage();
			stage.setTitle("Edit Item");
			stage.setScene(scene);
			stage.show();
			refreshTable();
		}
	}

	public void closeAddIngredient() {
		stage.close();
	}

}