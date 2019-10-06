package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.menu.MenuItemManager;
import foodstart.manager.menu.RecipeManager;
import foodstart.model.menu.MenuItem;
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
	TableView<PermanentRecipe> recipesTable;
	/**
	 * Table column for ingredient name
	 */
	@FXML
	TableColumn<PermanentRecipe, String> nameCol;

	@FXML
	TextArea descriptionInput;

	@FXML
	TextField nameInput;

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
	private ObservableList<PermanentRecipe> observableRecipes;

	/**
	 * A map of ingredients to ingredient quantities to set the recipe to have
	 */
	private Set<PermanentRecipe> recipesSet;

	private int id;


	/**
	 * Initialises the RecipeEditorController
	 */
	@FXML
	public void initialize() {
		try {
			editLoader = new FXMLLoader(getClass().getResource("allRecipesDisplay.fxml"));
			editFXML = editLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Screen screen = Screen.getPrimary();
		editPopup = new Stage();
		editPopup.initModality(Modality.WINDOW_MODAL);
		editPopup.setScene(new Scene(editFXML, screen.getVisualBounds().getWidth() / 4, screen.getVisualBounds().getHeight() / 2));
		populateTable();
	}

	/**
	 * Populates the table with data
	 */
	private void populateTable() {
		recipesSet = new HashSet<>();
		observableRecipes = FXCollections.observableArrayList(recipesSet);
		recipesTable.setItems(observableRecipes);
		nameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDisplayName()));

	}

	/**
	 * Refreshes the table data
	 */
	@Override
	public void refreshTable() {
		this.observableRecipes.setAll(recipesSet);
	}
	public void clearFields() {
		id = Managers.getMenuItemManager().generateNewId();
		nameInput.clear();
		descriptionInput.clear();
		recipesSet.clear();
		refreshTable();
	}
	public void setFields(MenuItem menuItem) {
		id = menuItem.getId();
		nameInput.setText(menuItem.getName());
		descriptionInput.setText(menuItem.getDescription());
		recipesSet.clear();
		recipesSet.addAll(menuItem.getVariants());
		refreshTable();

	}



	/**
	 * Adds a recipe to the new recipe map
	 */
	@FXML
	private void addRecipe() {
		if (editPopup.getOwner() == null) {
			editPopup.initOwner(this.addRecipeButton.getScene().getWindow());
		}
		editPopup.showAndWait();
		PermanentRecipe selectedRecipe = ((AllRecipesDisplayController) editLoader.getController()).getSelectedRecipe();
		if (selectedRecipe != null) {
			recipesSet.add(selectedRecipe);
			refreshTable();
		}
	}
	@FXML
	private void cancel() {
		closeSelf();
	}

	@FXML
	private void confirm() {
		if (recipesSet.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "There must be at least one recipe in the menu item", ButtonType.OK);
			alert.setHeaderText("No recipes selected");
			alert.showAndWait();
		}else if (nameInput.getText() == null || nameInput.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "The name field must have an input", ButtonType.OK);
			alert.setHeaderText("Name field empty");
			alert.showAndWait();
		}else if (descriptionInput.getText() == null || descriptionInput.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "The description field must have an input", ButtonType.OK);
			alert.setHeaderText("Description field empty");
			alert.showAndWait();
		} else{
			MenuItemManager manager = Managers.getMenuItemManager();
			List<PermanentRecipe> recipesList = new ArrayList<>();
			recipesList.addAll(recipesSet);
			if (manager.getMenuItems().containsKey(id)) {
				manager.mutateMenuItem(id, nameInput.getText(), descriptionInput.getText(), recipesList);
			}else {
				manager.addMenuItem(id, nameInput.getText(), descriptionInput.getText(), recipesList);
			}
			closeSelf();
		}
	}


	/**
	 * Closes the stage
	 */
	private void closeSelf() {
		Stage stage = (Stage) this.recipesTable.getScene().getWindow();
		stage.close();
	}

}
