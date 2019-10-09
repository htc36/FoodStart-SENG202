package foodstart.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


/**
 * Controls ui for add ingredients screen
 */
public class EditRecipeInstructionsController {

	@FXML
	private Button submit;

	@FXML
	private Button cancel;

	@FXML TextArea instructionsTextBox;

	private String oldInstructions;
	private  String newInstructions;
	/**
	 * Initialises the AddIngredientController
	 */
	@FXML
	public void initialize() {
	}

	/**
	 * Closes the current stage
	 */
	public void closeSelf() {
		Stage stage = (Stage) this.instructionsTextBox.getScene().getWindow();
		stage.close();
	}
	public void cancel() {
		newInstructions = oldInstructions;
		closeSelf();
	}

	public void setInstructionsText(String text) {
		oldInstructions = text;
		instructionsTextBox.setText(text);
	}

	public void submit() {
	    newInstructions = instructionsTextBox.getText();
	    closeSelf();
	}

	public String getNewText() {
		return newInstructions;
	}
}
