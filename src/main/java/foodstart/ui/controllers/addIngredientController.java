package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.stock.IngredientManager;
import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
	private ComboBox<String> UnitComboBox;


	@FXML
	public void initialize() {
		UnitComboBox.getItems().removeAll(UnitComboBox.getItems());
		//will need to change this to use the enum rather than hardcode
		UnitComboBox.getItems().addAll("ml", "g", "count");
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

	public void submit() {
		String unitString = UnitComboBox.getValue();
		/*
		if (isInt(truckStockInput) && isInt(kitchenStockInput) && ! isInt(nameInput) && unitString != "") {
		 */
		HashMap<DietaryRequirement, Boolean> safeFor = new HashMap<DietaryRequirement, Boolean>();
//			having trouble making enum this is throwing errors
		safeFor.put(DietaryRequirement.VEGAN, vegan.isSelected());
		safeFor.put(DietaryRequirement.VEGETARIAN, vegetarian.isSelected());
		safeFor.put(DietaryRequirement.GLUTEN_FREE, glutenFree.isSelected());
		safeFor.put(DietaryRequirement.NUT_ALLERGY, nutFree.isSelected());
		safeFor.put(DietaryRequirement.LACTOSE_INTOLERANT, dairyFree.isSelected());
		IngredientManager manager = Managers.getIngredientManager();
		Unit unit = Unit.matchUnit(unitString);
		manager.addIngredient(unit, nameInput.getText(), Integer.parseInt(idInput.getText()), safeFor,
				Integer.parseInt(kitchenStockInput.getText()), Integer.parseInt(truckStockInput.getText()));
	}
}
