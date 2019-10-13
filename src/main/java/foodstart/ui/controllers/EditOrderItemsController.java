package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.menu.RecipeManager;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.order.Order;
import foodstart.model.stock.Ingredient;
import foodstart.ui.Refreshable;
import foodstart.ui.util.RecipeStringConverter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

/**
 * Controls the UI of the edit order items screen
 */
public class EditOrderItemsController implements Refreshable {
	/**
	 * Button for confirming input
	 */
	@FXML
	Button confirmButton;
	/**
	 * Button for cancelling input
	 */
	@FXML
	Button cancelButton;
	/**
	 * Button for adding a recipe
	 */
	@FXML
	Button addRecipeButton;
	/**
	 * Button for removing a recipe
	 */
	@FXML
	Button removeRecipeButton;
	/**
	 * Button for modifying a recipe
	 */
	@FXML
	Button modifyRecipeButton;
	/**
	 * Button for viewing recipe details
	 */
	@FXML
	ComboBox<PermanentRecipe> recipesComboBox;
	/**
	 * Table view of the recipes in the order
	 */
	@FXML
	TableView<Recipe> recipesTableView;
	/**
	 * Table column for the recipe name
	 */
	@FXML
	TableColumn<Recipe, String> nameCol;
	/**
	 * Table column for the recipe ingredients
	 */
	@FXML
	TableColumn<Recipe, String> ingredientCol;
	/**
	 * Table column for the recipe quantity
	 */
	@FXML
	TableColumn<Recipe, String> quantityCol;
	/**
	 * Observable list of the recipes in the order for the table view
	 */
	private ObservableList<Recipe> observableRecipes;
	/**
	 * The order being edited
	 */
	private Order order;
	/**
	 * The map of the new recipes for the order to their quantity
	 */
	private Map<Recipe, Integer> items;
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
	 * Initialises the EditOrderItemsController
	 */
	@FXML
	public void initialize() {
		try {
			editLoader = new FXMLLoader(getClass().getResource("recipeEditor.fxml"));
			editFXML = editLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Screen screen = Screen.getPrimary();
		editPopup = new Stage();
		editPopup.setResizable(false);
		editPopup.initModality(Modality.WINDOW_MODAL);
		editPopup.setScene(new Scene(editFXML, screen.getVisualBounds().getWidth() / 2, screen.getVisualBounds().getHeight() / 2));
		populateTable();
		populateCB();
	}

	/**
	 * Populates the table view with data
	 */
	public void populateTable() {
		recipesTableView.setEditable(true);
		RecipeManager recipeManager = Managers.getRecipeManager();
		Set<Recipe> recipes = new HashSet<Recipe>();
		observableRecipes = FXCollections.observableArrayList(recipes);
		recipesTableView.setItems(observableRecipes);
		nameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDisplayName()));
		ingredientCol.setCellValueFactory(cell -> new SimpleStringProperty(recipeManager.getIngredientsAsString(cell.getValue())));
		quantityCol.setCellValueFactory(cell -> new SimpleStringProperty(items.get(cell.getValue()).toString()));
		quantityCol.setCellFactory(TextFieldTableCell.forTableColumn());
		//quantityCol.get
		quantityCol.setOnEditCommit(e -> {
			if (e.getNewValue().matches("\\d{0,20}")) {
				items.put(e.getRowValue(), Integer.parseInt(e.getNewValue()));
			} else {
				items.put(e.getRowValue(), Integer.parseInt(e.getOldValue()));
			}
			e.getTableColumn().setVisible(false);
			e.getTableColumn().setVisible(true);
		});
	}

	/**
	 * Populates the recipes combo box with data
	 */
	private void populateCB() {
		Set<PermanentRecipe> recipesSet = Managers.getRecipeManager().getRecipeSet();
		this.recipesComboBox.setItems(FXCollections.observableArrayList(recipesSet));
		this.recipesComboBox.setCellFactory(ComboBoxListCell.forListView(new RecipeStringConverter()));
		this.recipesComboBox.setConverter(new RecipeStringConverter());
		this.recipesComboBox.setPlaceholder(new Label("Please add a recipe"));
	}

	/**
	 * Sets the order being edited
	 * @param order the order being edited
	 */
	public void setOrder(Order order) {
		this.order = order;
		items = new HashMap<Recipe, Integer>(order.getItems());
		refreshTable();
	}

	/**
	 * Refreshes the table view
	 */
	@Override
	public void refreshTable() {
		this.observableRecipes.setAll(items.keySet());
	}

	/**
	 * Confirms the order edit and closes
	 */
	@FXML
	private void confirm() {
		closeSelf();
	}

	/**
	 * Cancels the order edit and closes
	 */
	@FXML
	private void cancel() {
		items = null;
		closeSelf();
	}

	/**
	 * Removes a recipe from the order
	 */
	@FXML
	private void removeRecipe() {
		Recipe recipe = recipesTableView.getSelectionModel().getSelectedItem();
		if (recipe == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Could not remove recipe as none was selected", ButtonType.OK);
			alert.setHeaderText("No recipe selected");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to remove this recipe?", ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> selection = alert.showAndWait();
			if (selection.isPresent() && selection.get() == ButtonType.YES) {
				items.remove(recipe);
			}
		}
		refreshTable();
	}

	/**
	 * Calls the edit recipe popup
	 */
	@FXML
	private void modifyRecipe() {
		Recipe recipe = recipesTableView.getSelectionModel().getSelectedItem();
		if (recipe == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Could not modify recipe as none was selected", ButtonType.OK);
			alert.setHeaderText("No recipe selected");
			alert.showAndWait();
		} else {
			Map<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>(recipe.getIngredients());
			float price = recipe.getPrice();
			if (editPopup.getOwner() == null) {
				editPopup.initOwner(this.recipesTableView.getScene().getWindow());
			}
			((RecipeEditorController) editLoader.getController()).setRecipe(recipe);
			((RecipeEditorController) editLoader.getController()).getPrice();
			editPopup.showAndWait();
			Map<Ingredient, Integer> moddedIngredients = ((RecipeEditorController) editLoader.getController()).getIngredients();
			float moddedPrice = ((RecipeEditorController) editLoader.getController()).getPrice();
			if (moddedIngredients != null && (!ingredients.equals(moddedIngredients) || price != moddedPrice)) {
				int otfID = Managers.getRecipeManager().otfManager.addRecipe(recipe.getId(), moddedIngredients, moddedPrice);
				items.put(Managers.getRecipeManager().otfManager.getRecipe(otfID), items.get(recipe));
				items.remove(recipe);
			}
			refreshTable();
		}
	}

	/**
	 * Closes the stage
	 */
	private void closeSelf() {
		Stage stage = (Stage) this.recipesTableView.getScene().getWindow();
		stage.close();
	}

	/**
	 * Returns the map of recipes to recipe quantity for the modified order
	 * @return the map of recipes to recipe quantity for the modified order
	 */
	public Map<Recipe, Integer> getNewRecipes() {
		return items;
	}

	/**
	 * Pushes a map of items in the order back to this controller
	 * @param pushedRecipes the map of recipes to recipe quantities
	 */
	public void pushRecipes(Map<Recipe, Integer> pushedRecipes) {
		if (pushedRecipes != null) {
			this.items = pushedRecipes;
			refreshTable();
		}
	}

	/**
	 * Adds an ingredient to the new ingredients map
	 */
	@FXML
	private void addRecipe() {
		PermanentRecipe recipe = recipesComboBox.getSelectionModel().getSelectedItem();
		if (recipe == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Could not add recipe as there was none selected", ButtonType.OK);
			alert.setHeaderText("No recipe selected");
			alert.showAndWait();
		} else if (!this.items.containsKey(recipe)) {
			this.items.put(recipe, 1);
		}
		refreshTable();
	}
}
