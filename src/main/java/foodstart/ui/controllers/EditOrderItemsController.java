package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.menu.RecipeManager;
import foodstart.manager.order.OrderManager;
import foodstart.model.menu.Recipe;
import foodstart.model.order.Order;
import foodstart.model.stock.Ingredient;
import foodstart.ui.Refreshable;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.util.*;

import static javafx.scene.control.cell.TextFieldTableCell.forTableColumn;

public class EditOrderItemsController implements Refreshable {
	@FXML
	Button confirmButton;
	@FXML
	Button cancelButton;
	@FXML
	Button addRecipeButton;
	@FXML
	Button removeRecipeButton;
	@FXML
	Button modifyRecipeButton;
	@FXML
	Button viewRecipeButton;
	@FXML
	TableView<Recipe> recipesTableView;
	@FXML
	TableColumn<Recipe, String> nameCol;
	@FXML
	TableColumn<Recipe, String> ingredientCol;
	@FXML
	TableColumn<Recipe, Integer> quantityCol;

	private ObservableList<Recipe> observableRecipes;
	private Order order;
	private Map<Recipe, Integer> items;

	private FXMLLoader editLoader;
	private Parent editFXML;
	private Stage editPopup;

	private FXMLLoader addLoader;
	private Parent addFXML;
	private Stage addPopup;

	@FXML
	public void initialize() {
		try {
			editLoader = new FXMLLoader(getClass().getResource("recipeEditor.fxml"));
			editFXML = editLoader.load();
			//addLoader = new FXMLLoader(getClass().getResource("addRecipe.fxml"));
			//addFXML = addLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Screen screen = Screen.getPrimary();
		editPopup = new Stage();
		editPopup.initModality(Modality.WINDOW_MODAL);
		editPopup.setScene(new Scene(editFXML, screen.getVisualBounds().getWidth() / 2, screen.getVisualBounds().getHeight() / 2));

		//addPopup = new Stage();
		//addPopup.initModality(Modality.WINDOW_MODAL);
		//addPopup.setScene(new Scene(addFXML, screen.getVisualBounds().getWidth() / 2, screen.getVisualBounds().getHeight() / 2));

		populateTable();
	}

	public void populateTable() {
		recipesTableView.setEditable(true);
		RecipeManager recipeManager = Managers.getRecipeManager();
		Set<Recipe> recipes = new HashSet<Recipe>();
		observableRecipes = FXCollections.observableArrayList(recipes);
		recipesTableView.setItems(observableRecipes);
		nameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDisplayName()));
		ingredientCol.setCellValueFactory(cell -> new SimpleStringProperty(recipeManager.getIngredientsAsString(cell.getValue())));
		quantityCol.setCellValueFactory(cell -> new SimpleIntegerProperty(items.get(cell.getValue())).asObject());
		quantityCol.setCellFactory(TextFieldTableCell.<Recipe, Integer>forTableColumn(new IntegerStringConverter()));
		quantityCol.setOnEditCommit(e -> items.put(e.getRowValue(), e.getNewValue()));
	}

	public void setOrder(Order order) {
		this.order = order;
		items = new HashMap<Recipe, Integer>(order.getItems());
		refreshTable();
	}

	@Override
	public void refreshTable() {
		this.observableRecipes.setAll(items.keySet());
	}

	@FXML
	private void confirm() {
		closeSelf();
	}

	@FXML
	private void cancel() {
		items = null;
		closeSelf();
	}

	@FXML
	private void addRecipe() {

	}

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

	@FXML
	private void modifyRecipe() {
		Recipe recipe = recipesTableView.getSelectionModel().getSelectedItem();
		if (recipe == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Could not modify recipe as none was selected", ButtonType.OK);
			alert.setHeaderText("No recipe selected");
			alert.showAndWait();
		} else {
			Map<Ingredient, Integer> ingredients = recipe.getIngredients();
			if (editPopup.getOwner() == null) {
				editPopup.initOwner(this.recipesTableView.getScene().getWindow());
			}
			((RecipeEditorController) editLoader.getController()).setRecipe(recipe);
			editPopup.showAndWait();
			Map<Ingredient, Integer> moddedIngredients = ((RecipeEditorController) editLoader.getController()).getIngredients();
			float price = ((RecipeEditorController) editLoader.getController()).getPrice();
			if (checkMaps(ingredients, moddedIngredients)) {
				int otfID = Managers.getRecipeManager().otfManager.addRecipe(recipe.getId(), moddedIngredients, price);
				items.put(Managers.getRecipeManager().otfManager.getRecipe(otfID), order.getItems().get(recipe));
				items.remove(recipe);
			}
			refreshTable();
		}
	}

	private boolean checkMaps(Map<Ingredient, Integer> m1, Map<Ingredient, Integer> m2) {
		if (!m1.keySet().equals(m2.keySet())) {
			return false;
		}
		for (Ingredient ingredient : m1.keySet()) {
			if (m1.get(ingredient) != m2.get(ingredient)) {
				return false;
			}
		}
		return true;
	}

	@FXML
	private void viewRecipe() {

	}

	private void closeSelf() {
		Stage stage = (Stage) this.recipesTableView.getScene().getWindow();
		stage.close();
	}

	public Map<Recipe, Integer> getNewRecipes() {
		return items;
	}

	public void pushRecipes(Map<Recipe, Integer> pushedRecipes) {
		if (pushedRecipes != null) {
			this.items = pushedRecipes;
			refreshTable();
		}
	}
}
