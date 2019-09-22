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
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class RecipeEditorController implements Refreshable {
	@FXML
	Button confirmButton;
	@FXML
	Button cancelButton;
	@FXML
	Button addIngredientButton;
	@FXML
	Button removeIngredientButton;
	@FXML
	ComboBox<Ingredient> ingredientsCB;
	@FXML
	TextField ingredientQuantityInput;
	@FXML
	TableView<Ingredient> ingredientsTable;
	@FXML
	TableColumn<Ingredient, String> nameCol;
	@FXML
	TableColumn<Ingredient, String> quantityCol;

	private ObservableList<Ingredient> observableIngredients;
	private Recipe recipe;
	private Map<Ingredient, Integer> ingredients;


	@FXML
	public void initialize() {
		populateTable();
	}

	private void populateTable() {
		RecipeManager recipeManager = Managers.getRecipeManager();
		Set<Ingredient> ingredients = new HashSet<Ingredient>();
		observableIngredients = FXCollections.observableArrayList(ingredients);
		ingredientsTable.setItems(observableIngredients);
		nameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
		quantityCol.setCellValueFactory(cell -> new SimpleStringProperty(Integer.toString(recipe.getIngredients().get(cell.getValue()))));
	}

	private void populateCB() {
		Set<Ingredient> ingredientsSet = Managers.getIngredientManager().getIngredientSet();
		this.ingredientsCB.setItems(FXCollections.observableArrayList(ingredientsSet));
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
		ingredients = recipe.getIngredients();
		refreshTable();
	}

	@Override
	public void refreshTable() {
		this.observableIngredients.setAll(ingredients.keySet());
		populateCB();
	}

	@FXML
	private void confirm() {
		Stage stage = (Stage) this.ingredientsTable.getScene().getWindow();
		closeSelf();
	}

	@FXML
	private void cancel() {
		closeSelf();
	}

	@FXML
	private void addIngredient() {

	}

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

	private void closeSelf() {
		Stage stage = (Stage) this.ingredientsTable.getScene().getWindow();
		stage.close();
	}
}
