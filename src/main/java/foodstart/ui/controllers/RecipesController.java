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
import foodstart.ui.util.FileImporter;
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
public class RecipesController implements Refreshable {
	/**
	 * Table of permanent recipes
	 */
	@FXML
	private TableView<PermanentRecipe> recipesTableView;
	/**
	 * Table column for recipe id
	 */
	@FXML
	private TableColumn<PermanentRecipe, String> recIDCol;
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

	/**
	 * The edit loader of the popup screen
	 */

	private FXMLLoader editLoader;

	/**
	 * The FXML for the edit recipe popup screen
	 */
	private Parent editFXML;

	/**
	 * The stage of the edit popup screen
	 */
	private Stage editPopup;
	
	/**
	 * List of currently shown recipes
	 */
	ObservableList<PermanentRecipe> observableRecipes;


	/**
	 * Initialises the RecipesController
	 */
	@FXML
	public void initialize() {
		try {
			editLoader = new FXMLLoader(getClass().getResource("PermanentRecipeEditor.fxml"));
			editFXML = editLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Screen screen = Screen.getPrimary();
		editPopup = new Stage();
		editPopup.initModality(Modality.WINDOW_MODAL);
		editPopup.setScene(new Scene(editFXML, screen.getVisualBounds().getWidth() /4, screen.getVisualBounds().getHeight() / 2));
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
		recIDCol.setCellValueFactory(cell -> new SimpleStringProperty(Integer.toString(cell.getValue().getId())));
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

	/**
	 * Exports ingredients then updates table view
	 */
	public void exportRecipe() {
		Stage stage = (Stage) this.recipesTableView.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Recipes File");
		fileChooser.getExtensionFilters().addAll(Main.generateFilters());
		File selectedFile = fileChooser.showSaveDialog(stage);
		if (selectedFile != null) {
			Persistence persist = Managers.getPersistence(DataFileType.getFromExtensions(fileChooser.getSelectedExtensionFilter().getExtensions()));
			try {
				persist.exportFile(selectedFile, DataType.RECIPE);
			} catch (ExportFailureException e) {
				FXExceptionDisplay.showException(e, false);
			}
		}
	}

	/**
	 * Imports ingredients then updates table view
	 */
	public void importRecipe() {
		Stage stage = (Stage) this.recipesTableView.getScene().getWindow();
		FileImporter importer = new FileImporter(stage, "Open Recipe File", DataType.RECIPE);
		importer.execute();
		refreshTable();
	}

	/**
	 * Asks for conformation then removes selected item, if none selected display warning message
	 */
	public void removeRecipe() {
		Recipe recipe = recipesTableView.getSelectionModel().getSelectedItem();
		if (recipe == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Could not remove recipe as nothing is selected", ButtonType.OK);
			alert.setHeaderText("No recipe selected");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to remove this recipe?", ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> selection = alert.showAndWait();
			if (selection.isPresent() && selection.get() == ButtonType.YES) {
				Managers.getRecipeManager().removeRecipe(recipe.getId());
			}
		}
		populateTable();
	}

	/**
	 * Calls the edit recipe popup
	 */
	@FXML
	private void modifyRecipe() {
		PermanentRecipe recipe = recipesTableView.getSelectionModel().getSelectedItem();
		if (recipe == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Could not modify recipe as none was selected", ButtonType.OK);
			alert.setHeaderText("No has been selected");
			alert.showAndWait();
		} else {
			Map<Ingredient, Integer> ingredients = recipe.getIngredients();
			if (editPopup.getOwner() == null) {
				editPopup.initOwner(this.recipesTableView.getScene().getWindow());
			}
			((RecipeEditorController) editLoader.getController()).setRecipeAndFields(recipe);
			((RecipeEditorController) editLoader.getController()).instructionsDoubleClickListener();
			editPopup.showAndWait();
			refreshTable();
		}
	}
	/**
	 * Calls add recipe popup
	 */
	@FXML void addRecipe() {
        if (editPopup.getOwner() == null) {
            editPopup.initOwner(this.recipesTableView.getScene().getWindow());
        }
		((RecipeEditorController) editLoader.getController()).clearFields();
        ((RecipeEditorController) editLoader.getController()).instructionsDoubleClickListener();
        editPopup.showAndWait();
        refreshTable();


	}

}
