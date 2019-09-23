package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.stock.IngredientManager;
import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLOutput;
import java.util.HashMap;

/**
 * Controls ui for add ingredients screen
 */
public class AddIngredientController {

	@FXML
	private TextField idInput;
	@FXML
	private TextField nameInput;
	@FXML
	private TextField truckStockInput;
	@FXML
	private TextField kitchenStockInput;
	@FXML
	private CheckBox vegan;
	@FXML
	private CheckBox vegetarian;
	@FXML
	private CheckBox glutenFree;
	@FXML
	private CheckBox nutFree;
	@FXML
	private CheckBox dairyFree;
	@FXML
	private ComboBox<Unit> unitComboBox;
	@FXML
	private Label nameError;

	@FXML 
	private Label kitchenStockError;
	@FXML 
	private Label truckStockError;
	@FXML 
	private Label unitBoxError;
	@FXML
	private Label idDisplay;

	private int id;


	@FXML
	public void initialize() {
		setNewID();
		this.unitComboBox.setItems(FXCollections.observableArrayList(Unit.values()));
	}

	/**
	 *Generates a new unique code and input it into the text box
	 */
	public void setNewID() {
		id = Managers.getIngredientManager().generateNewID();
		idDisplay.setText(Integer.toString(id));
	}

	/**
	 * Checks if the text field given is an integer
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
		label.setText(message);
		return isValid;
	}

	/**
	 * Validates weather an combobox feild is valid
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
	 * Checks if all fields are valid then adds the ingredient to the system and closes popup
	 */
	public void submit() {
	    String unitString = "";
		boolean isNameValid = textFieldValidate(nameInput, nameError);
		boolean isKitchenStockValid = integerFieldValidate(kitchenStockInput, kitchenStockError);
		boolean isTruckStockValid = integerFieldValidate(truckStockInput, truckStockError);
		boolean isUnitComboBoxValid = comboBoxValidate(unitComboBox, unitBoxError);
		if (unitComboBox.getValue() != null) {
			unitString = unitComboBox.getValue().getDBName();
		}
		HashMap<DietaryRequirement, Boolean> safeFor = new HashMap<DietaryRequirement, Boolean>();
		safeFor.put(DietaryRequirement.VEGAN, vegan.isSelected());
		safeFor.put(DietaryRequirement.VEGETARIAN, vegetarian.isSelected());
		safeFor.put(DietaryRequirement.GLUTEN_FREE, glutenFree.isSelected());
		safeFor.put(DietaryRequirement.NUT_ALLERGY, nutFree.isSelected());
		safeFor.put(DietaryRequirement.LACTOSE_INTOLERANT, dairyFree.isSelected());
		IngredientManager manager = Managers.getIngredientManager();
		Unit unit = Unit.matchUnit(unitString);
		if (isNameValid && isKitchenStockValid && isTruckStockValid && isUnitComboBoxValid) {
			manager.addIngredient(unit, nameInput.getText(), id, safeFor,
					Integer.parseInt(kitchenStockInput.getText()), Integer.parseInt(truckStockInput.getText()));
			this.closeSelf();
			clearFields();
		}
		
	}

	/**
	 * Closes the current stage
	 */
	private void closeSelf() {
		Stage stage = (Stage) this.nameInput.getScene().getWindow();
		stage.close();
	}

	/**
	 * Called when the text fields are to be cleared
	 */
	private void clearFields() {
		nameInput.clear();
		truckStockInput.clear();
		kitchenStockInput.clear();
		unitComboBox.getSelectionModel().clearSelection();
		vegan.setSelected(false);
		vegetarian.setSelected(false);
		glutenFree.setSelected(false);
		nutFree.setSelected(false);
		dairyFree.setSelected(false);
	}
}
