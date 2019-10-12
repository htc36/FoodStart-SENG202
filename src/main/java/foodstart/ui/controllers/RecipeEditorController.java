package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.menu.RecipeManager;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.stock.Ingredient;
import foodstart.ui.Refreshable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
	 * Input field for Name of recipe
	 */
	@FXML
	TextField nameInput;

	/**
	 * Input field for price of recipe
	 */
	@FXML
	TextField priceInput;

	/**
	 * Input field for instructions
	 */
	@FXML
	TextArea instructionsInput;

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
	 * ID of recipe to be edited
	 */
	private int id;

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
	 * The price of the recipe
	 */
	private float price;


    /**
	 * Initialises the RecipeEditorController
	 */
	@FXML
	public void initialize() {
		populateTable();
		if (this.priceInput != null) {
			this.priceInput.textProperty().addListener((observable, oldValue, newValue) -> {
				if (!newValue.matches("\\d{0,7}([.]\\d{0,2})?")) {
					priceInput.setText(oldValue);
				}
			});
		}
		this.ingredientQuantityInput.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d{0,7}")) {
				ingredientQuantityInput.setText(oldValue);
			}
		});
		this.ingredientsCB.valueProperty().addListener(((observableValue, ingredientSingleSelectionModel, t1) ->
				ingredientQuantityInput.textProperty().setValue(Integer.toString(ingredients.getOrDefault(t1, 1)))));
		try {
			editLoader = new FXMLLoader(getClass().getResource("editRecipeInstructions.fxml"));
            editFXML = editLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Screen screen = Screen.getPrimary();
        editPopup = new Stage();
        editPopup.setResizable(false);
        editPopup.initModality(Modality.WINDOW_MODAL);
        editPopup.setScene(new Scene(editFXML, screen.getVisualBounds().getWidth() / 4, screen.getVisualBounds().getHeight() / 2));

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
		quantityCol.setCellValueFactory(cell -> new SimpleStringProperty(Integer.toString(this.ingredients.get(cell.getValue()))));
		ingredientsTable.setPlaceholder(new Label("No ingredients in this recipe, please add them below"));
	}

	/**
	 * Populates the ingredients combo box with data
	 */
	private void populateCB() {
		Set<Ingredient> ingredientsSet = Managers.getIngredientManager().getIngredientSet();
		List<Ingredient> sortedIngredients = ingredientsSet.stream().collect(Collectors.toList());
		Collections.sort(sortedIngredients, Comparator.comparing(Ingredient::getName));
		this.ingredientsCB.setItems(FXCollections.observableArrayList(sortedIngredients));
		this.ingredientsCB.setCellFactory(ComboBoxListCell.forListView(new IngredientStringConverter()));
		this.ingredientsCB.setConverter(new IngredientStringConverter());
	}

	/**
	 * Sets the recipe to ne edited
	 * @param recipe the recipe to be edited
	 */
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
		this.ingredients = new HashMap<>(recipe.getIngredients());
		this.priceInput.setText(Float.toString(recipe.getPrice()));
		refreshTable();
	}

	/**
	 * Sets the recipe and text fields
	 */
	public void setRecipeAndFields(PermanentRecipe recipe) {
		this.id = recipe.getId();
		this.recipe = recipe;
		this.ingredients = new HashMap<>(recipe.getIngredients());
		this.nameInput.setText(recipe.getDisplayName());
		this.priceInput.setText(Float.toString(recipe.getPrice()));
		this.instructionsInput.setText(recipe.getInstructions());
		ingredientsCB.getSelectionModel().clearSelection();

		refreshTable();
	}
	public void instructionsDoubleClickListener() {
		instructionsInput.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
					if(mouseEvent.getClickCount() == 2){
						if (editPopup.getOwner() == null) {
							editPopup.initOwner(ingredientsTable.getScene().getWindow());
						}
						((EditRecipeInstructionsController) editLoader.getController()).setInstructionsText(instructionsInput.getText());
						editPopup.showAndWait();
						String newText = ((EditRecipeInstructionsController) editLoader.getController()).getNewText();
						instructionsInput.setText(newText);
					}
				}
			}
		});
	}
	/**
	 * Clears fields, used when wanting to add new item
	 */
	public void clearFields() {
		this.id = Managers.getRecipeManager().generateNewId();
		this.ingredients = new HashMap<>();
		this.nameInput.clear();
		this.priceInput.clear();
		this.instructionsInput.clear();
		ingredientsCB.getSelectionModel().clearSelection();
		refreshTable();

	}

	/**
	 * Returns the new price of the recipe
	 * @return the new price of the recipe
	 */
	public float getPrice() {
		this.price = Float.parseFloat(priceInput.getText());
		return this.price;
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
		} else if (Integer.parseInt(amount) == 0) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "You can not add an ingredient with amount 0", ButtonType.OK);
			alert.setHeaderText("Amount error");
			alert.showAndWait();
		}
		else {
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
	@FXML
	private void confirmFromRecipePage() {
	    if (ingredients.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "There must be at least one ingredient in the recipe", ButtonType.OK);
			alert.setHeaderText("No ingredients selected");
			alert.showAndWait();
		} else if (nameInput.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "The recipe must have a name", ButtonType.OK);
			alert.setHeaderText("No name selected");
			alert.showAndWait();
		} else if (instructionsInput.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The recipe must have instructions", ButtonType.OK);
            alert.setHeaderText("Instructions field empty");
            alert.showAndWait();
		} else if (priceInput.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "There recipe must have a price", ButtonType.OK);
			alert.setHeaderText("Price field empty");
			alert.showAndWait();
		}
		else {
			RecipeManager manager = Managers.getRecipeManager();
			if (manager.idExists(id)) {
				manager.mutateRecipe(id, nameInput.getText(), instructionsInput.getText(), Float.parseFloat(priceInput.getText()), ingredients);
			} else {
				manager.addRecipe(id, nameInput.getText(), instructionsInput.getText(), Float.parseFloat(priceInput.getText()), ingredients);
			}

			closeSelf();
		}
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
	 * Sets the price of the recipe
	 * @param price price of the recipe
	 */
	public void setPrice(float price) {
		this.price = price;
		this.priceInput.setText(Float.toString(this.price));
	}
}
