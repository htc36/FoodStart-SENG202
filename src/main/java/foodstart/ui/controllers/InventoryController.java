package foodstart.ui.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import foodstart.manager.Managers;
import foodstart.manager.Persistence;
import foodstart.manager.exceptions.ExportFailureException;
import foodstart.manager.stock.IngredientManager;
import foodstart.model.DataFileType;
import foodstart.model.DataType;
import foodstart.model.stock.Ingredient;
import foodstart.ui.FXExceptionDisplay;
import foodstart.ui.Main;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controls the UI for the inventory screen
 */
public class InventoryController implements Refreshable {

	/**
	 * Table view for the inventory ingredients
	 */
	@FXML
	private TableView<Ingredient> inventoryView;
	/**
	 * Table column for the ingredients id
	 */
	@FXML
	private TableColumn<Ingredient, String> id;
	/**
	 * Table column for the ingredient names
	 */
	@FXML
	private TableColumn<Ingredient, String> name;
	/**
	 * Table column for the ingredient truck stock
	 */
	@FXML
	private TableColumn<Ingredient, String> truckStock;
	/**
	 * Table column for the ingredient kitchen stock
	 */
	@FXML
	private TableColumn<Ingredient, String> kitchenStock;
	/**
	 * Table column for the ingredient dietary requirements
	 */
	@FXML
	private TableColumn<Ingredient, String> dietary;
	/**
	 * FXML loader for add ingredient popup
	 */
	private FXMLLoader addLoader;
	/**
	 * FXML loader for edit ingredient popup
	 */
	private FXMLLoader editLoader;
	/**
	 * Stage for the edit ingredients popup
	 */
	private Stage editPopup;
	/**
	 * Stage for the add ingredients popup
	 */
	private Stage addPopup;

	/**
	 * Observable list of ingredients
	 */
	private ObservableList<Ingredient> observableList;

	/**
	 * Initialises the InventoryController
	 */
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

	/**
	 * Adds all ingredients to the table
	 */
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

	/**
	 * Displays popup screen for adding an ingredient
	 */
	public void addIngredient() {
		if (addPopup.getOwner() == null) {
			addPopup.initOwner(this.inventoryView.getScene().getWindow());
		}
        ((AddIngredientController) addLoader.getController()).setNewID();
		addPopup.showAndWait();
		refreshTable();
	}


	/**
	 * Asks for confimation then removes selected item, if none selected display warning message
	 */
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

	/**
	 * Imports ingredients then updates table view
	 */
	public void importIngredients() {
		Stage stage = (Stage) this.inventoryView.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Inventory File");
		fileChooser.getExtensionFilters().addAll(Main.generateFilters());
		File selectedFile = fileChooser.showOpenDialog(stage);
		if (selectedFile != null) {
			Persistence persist = Managers.getPersistence(DataFileType.getFromExtensions(fileChooser.getSelectedExtensionFilter().getExtensions()));
			persist.importFile(selectedFile, DataType.INGREDIENT);
		}
		refreshTable();
	}


	/**
	 * Exports ingredients then updates table view
	 */
    public void exportIngredients() {
        Stage stage = (Stage) this.inventoryView.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Inventory File");
        fileChooser.getExtensionFilters().addAll(Main.generateFilters());
        File selectedFile = fileChooser.showSaveDialog(stage);
        if (selectedFile != null) {
            Persistence persist = Managers.getPersistence(DataFileType.getFromExtensions(fileChooser.getSelectedExtensionFilter().getExtensions()));
            try {
                persist.exportFile(selectedFile, DataType.INGREDIENT);
            } catch (ExportFailureException e) {
                FXExceptionDisplay.showException(e, false);
            }
        }
    }

	/**
	 * Displays edit ingredient popup then refreshes the table
	 */
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

}