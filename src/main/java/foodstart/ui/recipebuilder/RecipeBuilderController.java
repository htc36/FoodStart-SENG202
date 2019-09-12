package foodstart.ui.recipebuilder;

import foodstart.manager.Managers;
import foodstart.model.menu.MenuItem;
import foodstart.model.menu.OnTheFlyRecipe;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.stock.Ingredient;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for RecipeBuilder
 *
 * @author Alex Hobson on 07/09/2019
 */
public class RecipeBuilderController {

	@FXML
	private Text itemNameText;

	@FXML
	private Text itemDescriptionText;

	@FXML
	private ComboBox<String> variantsDropdown;

	@FXML
	private Spinner<Integer> masterQuantity;

	@FXML
	private TableView<Ingredient> ingredientTable;

	@FXML
	private TextField pricePerUnit;

	@FXML
	private Text totalPriceText;

	@FXML
	private Button btnAddToOrder;

	@FXML
	private TableColumn<Ingredient, String> tableIngredientColumn;

	@FXML
	private TableColumn<Ingredient, Integer> tableQuantityColumn;

	@FXML
	private TableColumn<Ingredient, Integer> tableActionColumn;

	@FXML
	private ComboBox<String> allIngredientsDropdown;

	/**
	 * Price per unit of the item
	 */
	private float price;

	/**
	 * Quantity of the item to be added to the order
	 */
	private int quantity;

	/**
	 * Root class that 'owns' this Recipe builder controller
	 */
	private RecipeBuilder builder;

	/**
	 * True if the recipe was edited and should be considered an On-The-Fly recipe
	 */
	private boolean isEdited;

	/**
	 * Bitwise flags which should stop the user from being able to add the item to
	 * the order. If this number is 0 then the order is valid and the 'Add to Order'
	 * button should be clickable. - 0b00001 -> Price per unit is a valid, positive
	 * number
	 */
	private byte canProceed = 0;

	/**
	 * The variant currently being edited
	 */
	private PermanentRecipe variant;

	/**
	 * Map of ingredients to their spinners
	 */
	private Map<Ingredient, Spinner<Integer>> spinnerMap;

	/**
	 * Map of all ingredients in the current recipe, to their quantity
	 */
	private Map<Ingredient, Integer> ingredientMap;

	/**
	 * Called when FXML file is loaded, sets up a bunch of things
	 */
	@FXML
	public void initialize() {
		spinnerMap = new HashMap<Ingredient, Spinner<Integer>>();
		ingredientMap = new HashMap<Ingredient, Integer>();

		masterQuantity.setValueFactory(new IntegerSpinnerValueFactory(1, 1000, 1));
		masterQuantity.valueProperty().addListener((observable, oldValue, newValue) -> {
			quantity = newValue;
			updateTotalPrice();
		});

		tableIngredientColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName() + " ("
				+ Managers.getIngredientManager().safeForString(cell.getValue().getId()) + ")"));

		tableQuantityColumn.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getId()).asObject());

		tableQuantityColumn.setCellFactory(param -> new TableCell<Ingredient, Integer>() {

			@Override
			public void updateItem(Integer item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					Ingredient ingredient = Managers.getIngredientManager().getIngredient(item);
					if (ingredient != null) {
						setGraphic(spinnerMap.get(ingredient));
					}
					setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
				}
			}
		});

		String defaultDropdown = "Select an ingredient";
		allIngredientsDropdown.getItems().add(defaultDropdown);
		for (Ingredient ingredient : Managers.getIngredientManager().getIngredientSet()) {
			allIngredientsDropdown.getItems().add(ingredient.getName());
		}
		allIngredientsDropdown.setValue(defaultDropdown);

		pricePerUnit.textProperty().addListener((observable, oldValue, newValue) -> {
			isEdited = true;
			try {
				float newPrice = Float.parseFloat(newValue);
				if (newPrice >= 0) {
					price = Math.round(newPrice * 100F) / 100F; // make the price max 2dp
					setFinishableStatus(1, true);
					updateTotalPrice();
				} else {
					setFinishableStatus(1, false);
				}
			} catch (NumberFormatException e) {
				setFinishableStatus(1, false);
			}
		});
	}

	/**
	 * Sets the instance of the owning recipe builder
	 *
	 * @param builder The builder that created this controller
	 */
	public void setRecipeBuilder(RecipeBuilder builder) {
		this.builder = builder;
	}

	/**
	 * Populate the GUI with the given menuitem
	 *
	 * @param menuItem The menu item to populate the GUI with
	 */
	public void populateFields(MenuItem menuItem) {
		itemNameText.setText(menuItem.getName());
		itemDescriptionText.setText(menuItem.getDescription());

		for (PermanentRecipe recipe : menuItem.getVariants()) {
			variantsDropdown.getItems().add(recipe.getDisplayName());
		}
		variantsDropdown.setValue(menuItem.getVariants().get(0).getDisplayName());

		quantity = 1;

		setVariant(menuItem.getVariants().get(0));
	}

	/**
	 * Populate the GUI with the given recipe
	 *
	 * @param recipe The recipe to populate it with
	 */
	public void populateFields(Recipe recipe, int quantity) {
		itemNameText.setText("Editing recipe");
		itemDescriptionText.setText("");

		variantsDropdown.setDisable(true);

		masterQuantity.getValueFactory().setValue(quantity);
		this.quantity = quantity;

		setVariant(recipe);
	}

	/**
	 * Update the UI to show a new variant of this menuitem
	 *
	 * @param recipe The recipe to update everything to
	 */
	private void setVariant(Recipe recipe) {
		if (recipe instanceof PermanentRecipe) {
			this.variant = (PermanentRecipe) recipe;
		} else {
			this.variant = ((OnTheFlyRecipe) recipe).getBasedOn();
		}
		isEdited = false;
		price = recipe.getPrice();
		pricePerUnit.setText(String.format("%.02f", recipe.getPrice()));

		ingredientMap.clear();
		ingredientMap.putAll(recipe.getIngredients());
		refreshTable();

		updateTotalPrice();
	}

	/**
	 * Refreshes the table with all the ingredients and their quantities
	 */
	private void refreshTable() {
		for (Ingredient ingredient : ingredientMap.keySet()) {
			int quantity = ingredientMap.get(ingredient);
			if (!spinnerMap.containsKey(ingredient)) {
				Spinner<Integer> spinner = new Spinner<Integer>();
				spinner.setEditable(true);
				int stock = builder.getStockFactoringCurrentOrder(ingredient);
				if (stock == 0) {
					spinner.setDisable(true);
					spinner.setValueFactory(new IntegerSpinnerValueFactory(0, 0, 0));
				} else {
					if (quantity > stock) {
						spinner.setValueFactory(new IntegerSpinnerValueFactory(1, stock, stock));

						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Insufficient Stock");
						alert.setHeaderText("There is not enough '" + ingredient.getName() + "' to make this item");
						alert.setContentText("The quantity was automatically set to " + stock + " which is the amount of "
								+ "this item in the truck");
						alert.show();
					} else {
						spinner.setValueFactory(new IntegerSpinnerValueFactory(1, stock, quantity));
					}
					spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
						isEdited = true;
						ingredientMap.put(ingredient, newValue);
					});
				}
				spinnerMap.put(ingredient, spinner);
			} else {
				spinnerMap.get(ingredient).getValueFactory().setValue(quantity);
			}
		}
		ingredientTable.setItems(FXCollections.observableArrayList(ingredientMap.keySet()));
	}

	/**
	 * Refreshes the total price of the item (which is multiplied by the quantity)
	 */
	private void updateTotalPrice() {
		totalPriceText.setText(String.format("TOTAL $%.02f", price * quantity));
	}

	/**
	 * Sets the 'bit'th bit of canProceed to state. True means this bit will be set
	 * to 0 and the item is valid
	 *
	 * @param bit   Bit to set
	 * @param state Value to give this bit
	 */
	private void setFinishableStatus(int bit, boolean state) {
		if (!state) {
			canProceed |= 1 << bit;
		} else {
			canProceed &= ~(1 << bit);
		}

		btnAddToOrder.setDisable(canProceed != 0);
	}

	/**
	 * Called by JavaFX when the dropdown box for the variant is changed
	 */
	public void onChangeVariant() {
		String value = variantsDropdown.getValue();
		PermanentRecipe recipe = Managers.getRecipeManager().getRecipeByDisplayName(value);
		setVariant(recipe);
	}

	/**
	 * Called by JavaFX when user clicks Add Ingredient
	 */
	public void onAddIngredient() {
		String ingredientName = allIngredientsDropdown.getValue();
		Ingredient ingredient = Managers.getIngredientManager().getIngredientByName(ingredientName);
		if (ingredient != null) {
			if (ingredientMap.containsKey(ingredient)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Ingredient already in order");
				alert.setHeaderText("The ingredient '" + ingredient.getName() + "' is already in the order");
				alert.setContentText("The operation was cancelled, nothing was added to the order");
				alert.show();
			} else {
				isEdited = true;
				this.ingredientMap.put(ingredient, 1);
				refreshTable();
			}
		}
	}

	/**
	 * Called by JavaFX when user clicks cancel to stop customising this item
	 */
	public void onCancelItem() {
		this.builder.cancel();
	}

	/**
	 * Called by JavaFX when user clicks Add To Order
	 */
	public void onAddToOrder() {
		this.builder.addToOrder(isEdited, variant, ingredientMap, quantity, price);
	}
}
