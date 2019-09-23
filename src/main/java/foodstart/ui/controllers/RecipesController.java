package foodstart.ui.controllers;

import java.util.Set;

import foodstart.manager.Managers;
import foodstart.manager.menu.RecipeManager;
import foodstart.model.menu.PermanentRecipe;
import foodstart.ui.Refreshable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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

}
