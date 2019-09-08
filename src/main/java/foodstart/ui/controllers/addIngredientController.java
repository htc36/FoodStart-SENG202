package foodstart.ui.controllers;

import java.util.HashMap;

import foodstart.model.DietaryRequirement;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
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
	public void initialize(){

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
	private void addIngredient() {
        HashMap<DietaryRequirement, Boolean> safeFor = new HashMap<DietaryRequirement, Boolean>(); 
        //having trouble making enum this is throwing errors
//        safeFor.put(new DietaryRequirement("VEGAN", "vegan"), vegan.isSelected());
//        safeFor.put(new DietaryRequirement("VEGETARIAN", "vegetarian"), vegetarian.isSelected());
//        safeFor.put(new DietaryRequirement("GLUTEN_FREE", "gluten_free"), glutenFree.isSelected());
//        safeFor.put(new DietaryRequirement("NUT_FREE", "nut_free"), nutFree.isSelected());
//        safeFor.put(new DietaryRequirement("DAIRY_FREE", "dairy_free"), dairyFree.isSelected());





		
	}

	
	

}
