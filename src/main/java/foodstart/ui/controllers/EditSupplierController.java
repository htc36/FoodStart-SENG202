package foodstart.ui.controllers;

import foodstart.model.PhoneType;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class EditSupplierController {
    @FXML
    private TextField nameTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField websiteTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private ComboBox<PhoneType> phoneTypeComboBox;

    /**
     * Called when the controller is initialized
     */
    @FXML
    public void initialize() {
        //TODO: Display the popup with the correct info of selected supplier
    }

    public void onCancel() {
        //TODO: Close this popup

    }

    public void onConfirm() {
        //TODO: Get the changes from the textfields and modify selected supplier
    }

}
