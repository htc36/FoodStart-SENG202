package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.menu.MenuItemManager;
import foodstart.manager.menu.RecipeManager;
import foodstart.model.menu.MenuItem;
import foodstart.model.menu.PermanentRecipe;
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
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
	TableColumn<PermanentRecipe, String> priceCol;

	@FXML
	TableColumn<PermanentRecipe, String> ingredientsCol;

	@FXML
	ComboBox<PermanentRecipe> defaultVariantCB;
	@FXML
	TextArea descriptionInput;

	@FXML
	TextField nameInput;

	@FXML
	Label title;

	@FXML
	Button removeButton;
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
		editPopup.setResizable(false);
		editPopup.initModality(Modality.WINDOW_MODAL);
		editPopup.setScene(new Scene(editFXML, screen.getVisualBounds().getWidth() / 4, screen.getVisualBounds().getHeight() / 1.75));
		populateTable();
	}

	/**
	 * Populates the table with data
	 */
	private void populateTable() {
		RecipeManager manager = Managers.getRecipeManager();
		recipesSet = new HashSet<>();
		observableRecipes = FXCollections.observableArrayList(recipesSet);
		recipesTable.setItems(observableRecipes);
		nameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDisplayName()));
		priceCol.setCellValueFactory(cell -> new SimpleStringProperty(String.format("%.2f", cell.getValue().getPrice())));
		ingredientsCol.setCellValueFactory(cell -> new SimpleStringProperty(manager.getIngredientsAsString(cell.getValue().getId())));
		defaultVariantCB.setItems(observableRecipes);
		defaultVariantCB.setConverter(new RecipeStringConverter());
		defaultVariantCB.setCellFactory(ComboBoxListCell.forListView(new RecipeStringConverter()));
		defaultVariantCB.setPlaceholder(new Label("Please add a recipe"));
		recipesTable.setPlaceholder(new Label("No recipes in this menu item, please add some using 'Add Recipe'"));
	}

	/**
	 * Refreshes the table data
	 */
	@Override
	public void refreshTable() {
		this.observableRecipes.setAll(recipesSet);
	}

	public void clearFields() {
		title.setText("Add Menu Item");
		removeButton.setVisible(false);
		id = Managers.getMenuItemManager().generateNewId();
		nameInput.clear();
		descriptionInput.clear();
		recipesSet.clear();
		defaultVariantCB.getSelectionModel().clearSelection();
		refreshTable();
	}

	public void setFields(MenuItem menuItem) {
		title.setText("Edit Menu Item");
		removeButton.setVisible(true);
		id = menuItem.getId();
		nameInput.setText(menuItem.getName());
		descriptionInput.setText(menuItem.getDescription());
		recipesSet.clear();
		recipesSet.addAll(menuItem.getVariants());
		refreshTable();
		defaultVariantCB.getSelectionModel().select(menuItem.getDefault());
	}



	/**
	 * Adds a recipe to the new recipe map
	 */
	@FXML
	private void addRecipe() {
		if (editPopup.getOwner() == null) {
			editPopup.initOwner(this.addRecipeButton.getScene().getWindow());
		}
		((AllRecipesDisplayController) editLoader.getController()).refreshTable();
		editPopup.setHeight(700);
        editPopup.setWidth(650);
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
		} else if (defaultVariantCB.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "A default variant must be selected", ButtonType.OK);
			alert.setHeaderText("No default variant");
			alert.showAndWait();
		} else {
			MenuItemManager manager = Managers.getMenuItemManager();
			Set<PermanentRecipe> recipesList = new HashSet<PermanentRecipe>();
			recipesList.addAll(recipesSet);
			PermanentRecipe defaultVariant = defaultVariantCB.getValue();
			if (manager.getMenuItems().containsKey(id)) {
				manager.mutateMenuItem(id, nameInput.getText(), descriptionInput.getText(), recipesList, defaultVariant);
			}else {
				manager.addMenuItem(id, nameInput.getText(), descriptionInput.getText(), recipesList, defaultVariant);
			}
			closeSelf();
		}
	}

	@FXML private void removeMenuItem() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to remove this Menu Item?", ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> selection = alert.showAndWait();
		if (selection.isPresent() && selection.get() == ButtonType.YES) {
			Managers.getMenuItemManager().removeMenuItem(id);
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
