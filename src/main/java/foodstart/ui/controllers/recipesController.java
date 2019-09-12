package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.menu.RecipeManager;
import foodstart.model.menu.PermanentRecipe;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Set;

public class recipesController {

	@FXML
	private TableView<PermanentRecipe> recipesTableView;
	@FXML
	private TableColumn<PermanentRecipe, String> recIDCol;
	@FXML
	private TableColumn<PermanentRecipe, String> nameCol;
	@FXML
	private TableColumn<PermanentRecipe, String> priceCol;
	@FXML
	private TableColumn<PermanentRecipe, String> ingredientsCol;

	@FXML
	public void initialize() {
		populateTable();
	}

	public void populateTable() {
		RecipeManager manager = Managers.getRecipeManager();
		Set<PermanentRecipe> recipesSet = manager.getRecipeSet();
		ObservableList<PermanentRecipe> observableRecipes = FXCollections.observableArrayList(recipesSet);
		recipesTableView.setItems(observableRecipes);
		recIDCol.setCellValueFactory(cell -> new SimpleStringProperty(Integer.toString(cell.getValue().getId())));
		nameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDisplayName()));
		priceCol.setCellValueFactory(cell -> new SimpleStringProperty(String.format("%.2f", cell.getValue().getPrice())));
		ingredientsCol.setCellValueFactory(cell -> new SimpleStringProperty(manager.getIngredientsAsString(cell.getValue().getId())));

	}

}
