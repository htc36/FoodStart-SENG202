package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.menu.RecipeManager;
import foodstart.model.menu.Recipe;
import foodstart.model.stock.Ingredient;
import foodstart.ui.Refreshable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Controls UI for recipe editor
 */
public class RecipeEditorController implements Refreshable {
	/**
	 * Converts an ingredient to a string
	 */
	static class IngredientStringConverter extends StringConverter<Ingredient> {
		@Override
		public String toString(Ingredient ingredient) {
			if (ingredient == null) {
				return "Please select an ingredient";
			} else {
				return ingredient.getName();
			}
		}

		@Override
		public Ingredient fromString(String s) {
			return Managers.getIngredientManager().getIngredientByName(s);
		}
	}

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
	Button addIngredientButton;
	/**
	 * Button for removing ingredient from recipe
	 */
	@FXML
	Button removeIngredientButton;
	/**
	 * Combo box of all ingredients
	 */
	@FXML
	ComboBox<Ingredient> ingredientsCB;
	/**
	 * Input field for ingredient quantity
	 */
	@FXML
	TextField ingredientQuantityInput;
	/**
	 * Table of ingredients in recipe
	 */
	@FXML
	TableView<Ingredient> ingredientsTable;
	/**
	 * Table column for ingredient name
	 */
	@FXML
	TableColumn<Ingredient, String> nameCol;
	/**
	 * Table column for ingredient quantity
	 */
	@FXML
	TableColumn<Ingredient, String> quantityCol;

	/**
	 * An observable list of ingredients for the table
	 */
	private ObservableList<Ingredient> observableIngredients;
	/**
	 * The recipe being edited
	 */
	private Recipe recipe;
	/**
	 * A map of ingredients to ingredient quantities to set the recipe to have
	 */
	private Map<Ingredient, Integer> ingredients;

	/**
	 * Initialises the RecipeEditorController
	 */
	@FXML
	public void initialize() {
		populateTable();
		this.ingredientQuantityInput.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d{0,7}")) {
				ingredientQuantityInput.setText(oldValue);
			}
		});
		this.ingredientsCB.valueProperty().addListener(((observableValue, ingredientSingleSelectionModel, t1) -> ingredientQuantityInput.textProperty().setValue(Integer.toString(ingredients.get((t1))))));
	}

	/**
	 * Populates the table with data
	 */
	private void populateTable() {
		RecipeManager recipeManager = Managers.getRecipeManager();
		Set<Ingredient> ingredients = new HashSet<Ingredient>();
		observableIngredients = FXCollections.observableArrayList(ingredients);
		ingredientsTable.setItems(observableIngredients);
		nameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
		quantityCol.setCellValueFactory(cell -> new SimpleStringProperty(Integer.toString(recipe.getIngredients().get(cell.getValue()))));
	}

	/**
	 * Populates the ingredients combo box with data
	 */
	private void populateCB() {
		Set<Ingredient> ingredientsSet = Managers.getIngredientManager().getIngredientSet();
		this.ingredientsCB.setItems(FXCollections.observableArrayList(ingredientsSet));
		this.ingredientsCB.setCellFactory(ComboBoxListCell.forListView(new IngredientStringConverter()));
		this.ingredientsCB.setConverter(new IngredientStringConverter());
	}

	/**
	 * Sets the recipe to ne edited
	 * @param recipe the recipe to be edited
	 */
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
		ingredients = recipe.getIngredients();
		refreshTable();
	}

	/**
	 * Sets the price of the recipe
	 * @param price price of the recipe
	 */
	public void setPrice(float price) {
		//TODO
	}

	/**
	 * Refreshes the table data
	 */
	@Override
	public void refreshTable() {
		this.observableIngredients.setAll(ingredients.keySet());
		populateCB();
	}

	/**
	 * Confirms the edit and closes the stage
	 */
	@FXML
	private void confirm() {
		closeSelf();
	}

	/**
	 * Cancels the edit and closes the stage
	 */
	@FXML
	private void cancel() {
		this.ingredients = null;
		closeSelf();
	}

	/**
	 * Adds an ingredient to the new ingredients map
	 */
	@FXML
	private void addIngredient() {
		Ingredient ingredient = ingredientsCB.getSelectionModel().getSelectedItem();
		String amount = ingredientQuantityInput.getText();
		if (ingredient == null || amount.equals("")) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Could not add ingredient as there ", ButtonType.OK);
			alert.setHeaderText("No recipe selected");
			alert.showAndWait();
		} else {
			Integer amountInt = Integer.parseInt(amount);
			ingredients.put(ingredient, amountInt);
		}
		refreshTable();
	}

	/**
	 * Removes an ingredient from the new ingredients map
	 */
	@FXML
	private void removeIngredient() {
		Ingredient ingredient = ingredientsTable.getSelectionModel().getSelectedItem();
		if (ingredient == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Could not remove ingredient as none was selected", ButtonType.OK);
			alert.setHeaderText("No ingredient selected");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to remove this ingredient?", ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> selection = alert.showAndWait();
			if (selection.isPresent() && selection.get() == ButtonType.YES) {
				ingredients.remove(ingredient);
			}
		}
		refreshTable();
	}

	/**
	 * Closes the stage
	 */
	private void closeSelf() {
		Stage stage = (Stage) this.ingredientsTable.getScene().getWindow();
		stage.close();
	}

	/**
	 * Returns the new ingredients map
	 * @return the new ingredients map
	 */
	public Map<Ingredient, Integer> getIngredients() {
		return this.ingredients;
	}

	/**
	 * Returns the new price of the recipe
	 * @return the new price of the recipe
	 */
	public float getPrice() {
		//TODO
		return 0;
	}
}
