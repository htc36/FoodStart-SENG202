package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.stock.IngredientManager;
import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;
import foodstart.model.stock.Ingredient;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * Controls ui for the edit ingredient screen
 */
public class EditIngredientController {
	/**
	 * Input field for ingredient name
	 */
	@FXML
	private TextField nameInput;
	/**
	 * Input field for ingredient truck stock
	 */
	@FXML
	private TextField truckStockInput;
	/**
	 * Input field for ingredient kitchen stock
	 */
	@FXML
	private TextField kitchenStockInput;
	/**
	 * Checkbox for if ingredient is vegan
	 */
	@FXML
	private CheckBox vegan;
	/**
	 * Checkbox for if ingredient is vegetarian
	 */
	@FXML
	private CheckBox vegetarian;
	/**
	 * Checkbox for if ingredient is gluten free
	 */
	@FXML
	private CheckBox glutenFree;
	/**
	 * Checkbox for if ingredient is nut free
	 */
	@FXML
	private CheckBox nutFree;
	/**
	 * Checkbox for if ingredient is dairy free
	 */
	@FXML
	private CheckBox dairyFree;
	/**
	 * Combo box for ingredient unit
	 */
	@FXML
	private ComboBox<Unit> unitComboBox;
	/**
	 * Label for if there is an error with the name input
	 */
	@FXML
	private Label nameError;
	/**
	 * Label for if there is an error with the kitchen stock input
	 */
	@FXML 
	private Label kitchenStockError;
	/**
	 * Label for if there is an error with the truck stock input
	 */
	@FXML 
	private Label truckStockError;
	/**
	 * Label for if there is an error with the unit input
	 */
	@FXML 
	private Label unitBoxError;
	/**
	 * Label for ingredient id display
	 */
	@FXML
	private Label idDisplay;
	/**
	 * ID of the ingredient
	 */
	private int id;
	/**
	 * Model of the ingredient
	 */
	private Ingredient ingredient;

	/**
	 * Initialises the EditIngredientController
	 */
	@FXML
	public void initialize() {
		this.unitComboBox.setItems(FXCollections.observableArrayList(Unit.values()));
	}


    /**
     * Checks if the text feild given is an integer
     * @param input the specific entry to test
     * @return boolean if it is an integer or not
     */
	private boolean isInt(TextField input) {
		try {
			int value = Integer.parseInt(input.getText());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

    /**
     * Validates if text field is not empty and if it is not an integer
     * @param field specific entry to test
     * @param label Displays message
     * @return true if field passes all tests, false if not
     */
	private boolean textFieldValidate(TextField field, Label label) {
		String message = null;
		boolean isValid = true;
		if (field.getText() == null || field.getText().isEmpty()) {
			isValid = false;
			message = "Input can not be empty";
		}
		else if (isInt(field)) {
			isValid = false;
			message = "Input must be a word";
		}
		label.setText(message);
		return isValid;
		}

	/**
	 * Validates weather an integer field is valid
 	 * @param field specific entry to test
	 * @param label message to be displayed
	 * @return true if field passes all the tests, false if not
	 */
	private boolean integerFieldValidate(TextField field, Label label) {
		String message = null;
		boolean isValid = true;
		if (field.getText() == null || field.getText().isEmpty()){
			isValid = false;
			message = "Input can not be empty";
		}
		else if (!isInt(field)) {
			isValid = false;
			message = "Input must be an integer";
		}
		else if (Integer.parseInt(field.getText()) < 0){
			isValid = false;
			message = "Input must be greater than 0";
		}
		label.setText(message);
		return isValid;
	}
	/**
	 * Validates weather an combobox field is valid
	 * @param field specific entry to test
	 * @param label message to be displayed
	 * @return true if field passes all the tests, false if not
	 */
	private boolean comboBoxValidate(ComboBox<Unit> field, Label label) {
		boolean isValid = true;
		String message = null;
		if (field.getValue() == null) {
			isValid = false;
			message = "Combobox must have selection";
		}
		label.setText(message);
		return isValid;
	}

	/**
	 * Sets up popup screen with all the current inputs for a specific ingredient
	 * @param ingredient the ingredient to be edited
	 */
	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
		if (ingredient != null) {
			this.id = ingredient.getId();
			idDisplay.setText(Integer.toString(id));
			this.nameInput.setText(ingredient.getName());
			this.truckStockInput.setText(Integer.toString(ingredient.getTruckStock()));
			this.kitchenStockInput.setText(Integer.toString(ingredient.getKitchenStock()));
			this.unitComboBox.getSelectionModel().select(ingredient.getUnit());
			this.vegan.setSelected(ingredient.isSafeFor(DietaryRequirement.VEGAN));
			this.vegetarian.setSelected(ingredient.isSafeFor(DietaryRequirement.VEGETARIAN));
			this.glutenFree.setSelected(ingredient.isSafeFor(DietaryRequirement.GLUTEN_FREE));
			this.nutFree.setSelected(ingredient.isSafeFor(DietaryRequirement.NUT_ALLERGY));
			this.dairyFree.setSelected(ingredient.isSafeFor(DietaryRequirement.LACTOSE_INTOLERANT));
		}
	}

	/**
	 * Checks if all fields are valid then adds the ingredient to the system and closes popup
	 */
	public void submit() {
		boolean isNameValid = textFieldValidate(nameInput, nameError);
		boolean isKitchenStockValid = integerFieldValidate(kitchenStockInput, kitchenStockError);
		boolean isTruckStockValid = integerFieldValidate(truckStockInput, truckStockError);
		boolean isUnitComboBoxValid = comboBoxValidate(unitComboBox, unitBoxError);
		String unitString = unitComboBox.getValue().getDBName();
		HashMap<DietaryRequirement, Boolean> safeFor = new HashMap<DietaryRequirement, Boolean>();
		safeFor.put(DietaryRequirement.VEGAN, vegan.isSelected());
		safeFor.put(DietaryRequirement.VEGETARIAN, vegetarian.isSelected());
		safeFor.put(DietaryRequirement.GLUTEN_FREE, glutenFree.isSelected());
		safeFor.put(DietaryRequirement.NUT_ALLERGY, nutFree.isSelected());
		safeFor.put(DietaryRequirement.LACTOSE_INTOLERANT, dairyFree.isSelected());
		IngredientManager manager = Managers.getIngredientManager();
		Unit unit = Unit.matchUnit(unitString);
		if (isNameValid && isKitchenStockValid && isTruckStockValid && isUnitComboBoxValid) {
			manager.mutateIngredient(unit, nameInput.getText(), id, safeFor,
					Integer.parseInt(kitchenStockInput.getText()), Integer.parseInt(truckStockInput.getText()));
			this.closeSelf();
		}
		
	}

	/**
	 * Closes the current popup stage
	 */

	private void closeSelf() {
		Stage stage = (Stage) this.nameInput.getScene().getWindow();
		stage.close();
	}
}
