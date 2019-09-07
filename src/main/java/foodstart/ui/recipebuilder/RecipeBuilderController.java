package foodstart.ui.recipebuilder;

import foodstart.manager.Managers;
import foodstart.model.menu.MenuItem;
import foodstart.model.menu.PermanentRecipe;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Controller for RecipeBuilder
 * @author Alex Hobson
 * @date 07/09/2019
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
	private TableView ingredientTable;
	
	@FXML
	private TextField pricePerUnit;
	
	@FXML
	private Text totalPriceText;
	
	@FXML
	private Button btnAddToOrder;
	
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
	 * Bitwise flags which should stop the user from being able to add
	 * the item to the order. If this number is 0 then the order is valid
	 * and the 'Add to Order' button should be clickable.
	 * - 0b00001 -> Price per unit is a valid, positive number
	 */
	private byte canProceed = 0;
	
	/**
	 * Sets the instance of the owning recipe builder
	 * @param builder The builder that created this controller
	 */
	public void setRecipeBuilder(RecipeBuilder builder) {
		this.builder = builder;
	}
	/**
	 * Populate the GUI with the given menuitem
	 * @param menuItem The menu item to populate the GUI with
	 */
	public void populateFields(MenuItem menuItem) {
		itemNameText.setText(menuItem.getName());
		itemDescriptionText.setText(menuItem.getDescription());
		
		for (PermanentRecipe recipe : menuItem.getVariants()) {
			variantsDropdown.getItems().add(recipe.getDisplayName());
		}
		variantsDropdown.setValue(menuItem.getVariants().get(0).getDisplayName());
		
		masterQuantity.setValueFactory(new IntegerSpinnerValueFactory(1, 1000, 1));
		masterQuantity.valueProperty().addListener((observable, oldValue, newValue) -> {
			quantity = newValue;
			updateTotalPrice();
		});
		quantity = 1;
		
		setVariant(menuItem.getVariants().get(0));
		
		pricePerUnit.textProperty().addListener((observable, oldValue, newValue) -> {
			isEdited = true;
		    try {
		    	float newPrice = Float.valueOf(newValue);
		    	if (newPrice >= 0) {
		    		price = Math.round(newPrice * 100F) / 100F; //make the price max 2dp
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
	
	private void setVariant(PermanentRecipe recipe) {
		isEdited = false;
		price = recipe.getPrice();
		pricePerUnit.setText(String.format("%.02f", recipe.getPrice()));
		updateTotalPrice();
	}
	
	/**
	 * Refreshes the total price of the item (which is multiplied by the quantity)
	 */
	private void updateTotalPrice() {
		totalPriceText.setText(String.format("TOTAL $%.02f", price * quantity));
	}
	
	/**
	 * Sets the 'bit'th bit of canProceed to state. True means this bit will be
	 * set to 0 and the item is valid
	 * @param bit Bit to set
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
}
