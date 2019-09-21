package foodstart.ui.controllers;

import java.io.IOException;
import java.util.Optional;
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
import javafx.stage.Modality;
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
	private FXMLLoader addLoader;
	private FXMLLoader editLoader;
	private Stage editPopup;
	private Stage addPopup;

	/**
	 * Observable list of ingredients
	 */
	private ObservableList<Ingredient> observableList;

	@FXML
	public void initialize() {
		editLoader = new FXMLLoader(getClass().getResource("editIngredient.fxml"));
		addLoader = new FXMLLoader(getClass().getResource("addIngredientPopUp.fxml"));

		try {
			editLoader.load();
			addLoader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		editPopup = new Stage();
		editPopup.initModality(Modality.WINDOW_MODAL);
		Scene editScene = new Scene(editLoader.getRoot());
		editPopup.setTitle("Edit Item");
		editPopup.setScene(editScene);
		addPopup = new Stage();
		addPopup.initModality(Modality.WINDOW_MODAL);
		Scene addScene = new Scene(addLoader.getRoot());
		addPopup.setTitle("Add Item");
		addPopup.setScene(addScene);
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
		if (addPopup.getOwner() == null) {
			addPopup.initOwner(this.inventoryView.getScene().getWindow());
		}
		System.out.println("testing");
		addPopup.showAndWait();
	}

	public void removeIngredient() {
		Ingredient ingredient = inventoryView.getSelectionModel().getSelectedItem();
		if (ingredient == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Could not ingredient as none was selected", ButtonType.OK);
			alert.setHeaderText("No ingredient selected");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to remove this ingredient?", ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> selection = alert.showAndWait();
			if (selection.isPresent() && selection.get() == ButtonType.YES) {
				Managers.getIngredientManager(). removeIngredient(ingredient.getId());
			}
		}
		populateTable();
	}


	public void editIngredient() {
		Ingredient ingredient = inventoryView.getSelectionModel().getSelectedItem();
		if (ingredient == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Could not edit ingredient as nothing is selected", ButtonType.OK);
			alert.setHeaderText("No order selected");
			alert.showAndWait();
		} else {
			if (editPopup.getOwner() == null) {
				editPopup.initOwner(this.inventoryView.getScene().getWindow());
			}
			((EditIngredientController) editLoader.getController()).setIngredient(ingredient);
			editPopup.showAndWait();
			refreshTable();
		}
	}

	public void closeAddIngredient() {
		stage.close();
	}

}