package foodstart.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OrderEditorController {
	@FXML
	private Button confirmButton;
	@FXML
	private Button cancelButton;
	@FXML
	private Button editItemsButton;
	@FXML
	private TextField nameField;
	@FXML
	private TextField priceField;
	@FXML
	private TextField timeField;
	@FXML
	private DatePicker dateField;

	public void initialize() {}

	public void editItems() {

	}

	public void confirmEdit() {

	}

	public void cancelEdit() {
		Stage stage = (Stage) this.cancelButton.getScene().getWindow();
		stage.close();
	}
}
