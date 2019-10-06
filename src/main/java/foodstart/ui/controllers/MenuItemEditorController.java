package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.menu.RecipeManager;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.stock.Ingredient;
import foodstart.ui.Refreshable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.*;

/**
 * Controls UI for recipe editor
 */
public class MenuItemEditorController implements Refreshable {
	/**
	 * Button for confirm edit
	 */
	@FXML
	Button confirmButton;
	/**
	 * Button for cancel edit
	 */
	@FXML
	Button cancelButton;
	/**
	 * Button for adding ingredient to recipe
	 */
	@FXML
	Button addRecipeButton;
	/**
	 * Button for removing ingredient from recipe
	 */
	@FXML
	Button removeIngredientButton;

	@FXML
	TableView<Ingredient> ingredientsTable;
	/**
	 * Table column for ingredient name
	 */
	@FXML
	TableColumn<Ingredient, String> nameCol;

	/**
	 * FXML loader for the edit recipe popup
	 */
	private FXMLLoader editLoader;
	/**
	 * The FXML for the edit recipe popup screen
	 */
	private Parent editFXML;
	/**
	 * The stage of the edit recipe popup screen
	 */
	private Stage editPopup;


	/**
	 * An observable list of ingredients for the table
	 */
	private ObservableList<Ingredient> observableIngredients;

	/**
	 * A map of ingredients to ingredient quantities to set the recipe to have
	 */
	private Map<Ingredient, Integer> ingredients;


	/**
	 * Initialises the RecipeEditorController
	 */
	@FXML
	public void initialize() {
//		try {
//			editLoader = new FXMLLoader(getClass().getResource("allRecipesDisplay.fxml"));
//			editFXML = editLoader.load();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		Screen screen = Screen.getPrimary();
//		editPopup = new Stage();
//		editPopup.initModality(Modality.WINDOW_MODAL);
//		editPopup.setScene(new Scene(editFXML, screen.getVisualBounds().getWidth() / 4, screen.getVisualBounds().getHeight() / 2));
//		editPopup.setScene(new Scene(editFXML));
		populateTable();
	}

	/**
	 * Populates the table with data
	 */
	private void populateTable() {

	}

	/**
	 * Refreshes the table data
	 */
	@Override
	public void refreshTable() {
		this.observableIngredients.setAll(ingredients.keySet());
	}


	/**
	 * Adds an ingredient to the new ingredients map
	 */
	@FXML
	private void addRecipe() {
		if (editPopup.getOwner() == null) {
			editPopup.initOwner(this.addRecipeButton.getScene().getWindow());
		}
		editPopup.showAndWait();
	}


	/**
	 * Closes the stage
	 */
	private void closeSelf() {
		Stage stage = (Stage) this.ingredientsTable.getScene().getWindow();
		stage.close();
	}

}
