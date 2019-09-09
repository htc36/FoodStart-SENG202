package foodstart.ui.controllers;

import java.util.HashMap;

import foodstart.manager.Managers;
import foodstart.manager.stock.IngredientManager;
import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
	private ComboBox UnitComboBox;
	

	@FXML
	public void initialize(){
	    UnitComboBox.getItems().removeAll(UnitComboBox.getItems());
	    //will need to change this to use the enum rather than hardcode
	    UnitComboBox.getItems().addAll("ml", "g", "count");
	}
	
	//used to check if kitchen stock and truck stock feilds are ints
	private boolean isInt(TextField input) {
		try {
			int value = Integer.parseInt(input.getText());
			return true;
		}catch(NumberFormatException e) {
			return false;
		}
	}
	private void submit() {
		String unitString = (String) UnitComboBox.getValue();
		if (isInt(truckStockInput) && isInt(kitchenStockInput) && ! isInt(nameInput) && unitString != "") {
			HashMap<DietaryRequirement, Boolean> safeFor = new HashMap<DietaryRequirement, Boolean>(); 
//			having trouble making enum this is throwing errors
//          safeFor.put(new DietaryRequirement("VEGAN", "vegan"), vegan.isSelected());
//          safeFor.put(new DietaryRequirement("VEGETARIAN", "vegetarian"), vegetarian.isSelected());
//          safeFor.put(new DietaryRequirement("GLUTEN_FREE", "gluten_free"), glutenFree.isSelected());
//          safeFor.put(new DietaryRequirement("NUT_FREE", "nut_free"), nutFree.isSelected());
//          safeFor.put(new DietaryRequirement("DAIRY_FREE", "dairy_free"), dairyFree.isSelected());
			IngredientManager manager = Managers.getIngredientManager();
			Unit unit = new Unit(unitString);
			manager.addIngredient(unit, nameInput.getText(), idInput.getText(), safeFor, kitchenStockInput.getText(), truckStockInput.getText());
		}






		
	}

	
	

}
