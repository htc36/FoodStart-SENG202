package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.Persistence;
import foodstart.manager.exceptions.ExportFailureException;
import foodstart.manager.menu.RecipeManager;
import foodstart.model.DataFileType;
import foodstart.model.DataType;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.stock.Ingredient;
import foodstart.ui.FXExceptionDisplay;
import foodstart.ui.Main;
import foodstart.ui.Refreshable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Controls the UI of the recipe management screen
 */
public class AllRecipesDisplayController implements Refreshable {
	/**
	 * Table of permanent recipes
	 */
	@FXML
	private TableView<PermanentRecipe> recipesTableView;
	/**
	 * Table column for recipe name
	 */
	@FXML
	private TableColumn<PermanentRecipe, String> nameCol;
	/**
	 * Table column for recipe price
	 */
	@FXML
	private TableColumn<PermanentRecipe, String> priceCol;
	/**
	 * Table column for recipe ingredients
	 */
	@FXML
	private TableColumn<PermanentRecipe, String> ingredientsCol;

	private PermanentRecipe selectedRecipe;


	/**
	 * List of currently shown recipes
	 */
	ObservableList<PermanentRecipe> observableRecipes;


	/**
	 * Initialises the RecipesController
	 */
	@FXML
	public void initialize() {
		populateTable();
	}

	/**
	 * Populates the recipe table with data
	 */
	public void populateTable() {
		RecipeManager manager = Managers.getRecipeManager();
		Set<PermanentRecipe> recipesSet = manager.getRecipeSet();
		observableRecipes = FXCollections.observableArrayList(recipesSet);

		recipesTableView.setItems(observableRecipes);
		nameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDisplayName()));
		priceCol.setCellValueFactory(cell -> new SimpleStringProperty(String.format("%.2f", cell.getValue().getPrice())));
		ingredientsCol.setCellValueFactory(cell -> new SimpleStringProperty(manager.getIngredientsAsString(cell.getValue().getId())));

	}

	/**
	 * Refreshes the recipe table
	 */
	@Override
	public void refreshTable() {
		observableRecipes.setAll(Managers.getRecipeManager().getRecipeSet());
	}

	public void addRecipe() {
		selectedRecipe = recipesTableView.getSelectionModel().getSelectedItem();
		if (selectedRecipe == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a recipe to add", ButtonType.OK);
			alert.setHeaderText("No recipe selected");
			alert.showAndWait();
		} else {
			closeSelf();
		}
	}

	public void cancel() {
		selectedRecipe = null;
		closeSelf();
	}
	public PermanentRecipe getSelectedRecipe() {
		return selectedRecipe;
	}

	/**
	 * Closes the stage
	 */
	private void closeSelf() {
		Stage stage = (Stage) this.recipesTableView.getScene().getWindow();
		stage.close();
	}
}
