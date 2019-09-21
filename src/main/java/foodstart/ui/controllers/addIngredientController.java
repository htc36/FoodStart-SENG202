package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.stock.IngredientManager;
import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashMap;

public class addIngredientController {

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
	private ComboBox<String> unitComboBox;
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
		unitComboBox.getItems().removeAll(unitComboBox.getItems());
		//will need to change this to use the enum rather than hardcode
		unitComboBox.getItems().addAll("ml", "g", "count");
	}

	/**
	 * Called when a new code must be generated and set in the code text box
	 */
	public void setNewID() {
		id = Managers.getIngredientManager().generateNewID();
		idDisplay.setText(Integer.toString(id));
	}

	//used to check if kitchen stock and truck stock fields are ints
	private boolean isInt(TextField input) {
		try {
			int value = Integer.parseInt(input.getText());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
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
	private boolean comboBoxValidate(ComboBox<String> field, Label label) {
		boolean isValid = true;
		String message = null;
		if (field.getValue() == null) {
			isValid = false;
			message = "Combobox must have selection";
		}
		label.setText(message);
		return isValid;
	}

	public void submit() {
		boolean isNameValid = textFieldValidate(nameInput, nameError);
		boolean isKitchenStockValid = integerFieldValidate(kitchenStockInput, kitchenStockError);
		boolean isTruckStockValid = integerFieldValidate(truckStockInput, truckStockError);
		boolean isUnitComboBoxValid = comboBoxValidate(unitComboBox, unitBoxError);
		String unitString = unitComboBox.getValue();
		/*
		if (isInt(truckStockInput) && isInt(kitchenStockInput) && ! isInt(nameInput) && unitString != "") {
		 */
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
