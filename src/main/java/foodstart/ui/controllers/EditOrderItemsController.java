package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.menu.RecipeManager;
import foodstart.manager.order.OrderManager;
import foodstart.model.menu.Recipe;
import foodstart.model.order.Order;
import foodstart.ui.Refreshable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
	TableColumn<Recipe, String> quantityCol;

	private ObservableList<Recipe> observableRecipes;
	private Order order;
	private Map<Recipe, Integer> items;

	@FXML
	public void initialize() {
		populateTable();
	}

	public void populateTable() {
		OrderManager orderManager = Managers.getOrderManager();
		RecipeManager recipeManager = Managers.getRecipeManager();
		//Set<Recipe> recipes = orderManager.getOrderRecipes(order.getId());
		Set<Recipe> recipes = new HashSet<Recipe>();
		observableRecipes = FXCollections.observableArrayList(recipes);
		recipesTableView.setItems(observableRecipes);
		nameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDisplayName()));
		ingredientCol.setCellValueFactory(cell -> new SimpleStringProperty(recipeManager.getIngredientsAsString(cell.getValue())));
		quantityCol.setCellValueFactory(cell -> new SimpleStringProperty(Integer.toString(order.getItems().get(cell.getValue()))));
	}

	public void setOrder(Order order) {
		this.order = order;
		items = order.getItems();
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
		closeSelf();
	}

	@FXML
	private void addRecipe() {

	}

	@FXML
	private void removeRecipe() {

	}

	@FXML
	private void modifyRecipe() {

	}

	@FXML
	private void viewRecipe() {

	}

	private void closeSelf() {
		Stage stage = (Stage) this.recipesTableView.getScene().getWindow();
		stage.close();
	}
}
