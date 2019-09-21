package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.stock.SupplierManager;
import foodstart.model.PhoneType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddSupplierController {
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
    private Text codeText;

    @FXML
    private ComboBox<PhoneType> phoneTypeComboBox;

    @FXML
    private Label nameErrorLabel;

    @FXML
    private Label addressErrorLabel;

    @FXML
    private Label phoneErrorLabel;


    private int code;

    /**
     * Called when the controller is initialized
     */
    @FXML
    public void initialize() {
        setNewCode();
        this.phoneTypeComboBox.setItems(FXCollections.observableArrayList(PhoneType.values()));
        phoneTypeComboBox.getSelectionModel().select(PhoneType.WORK); // default phone type
    }


    /**
     * Called when a new code must be generated and set in the code text box
     */
    public void setNewCode() {
        code = Managers.getSupplierManager().generateNewCode();
        codeText.setText(Integer.toString(code));
    }

    /**
     * Checks if the name text field is valid
     * @return if the name is valid or not
     */
    private Boolean isValidSupplierName() {
        if (nameTextField.getText().isEmpty()) {
            nameErrorLabel.setText("Name field cannot be empty");
            nameErrorLabel.setVisible(true);
            return false;
        } else {
            nameErrorLabel.setVisible(false);
            return true;
        }
    }

    /**
     * Checks if the address text field is valid
     * @return if the address is valid or not
     */
    private Boolean isValidAddressName() {
        if (addressTextField.getText().isEmpty()) {
            addressErrorLabel.setText("Address field cannot be empty");
            addressErrorLabel.setVisible(true);
            return false;
        } else {
            addressErrorLabel.setVisible(false);
            return true;
        }

    }

    /**
     * Checks if the phone number text field is valid
     * @return if the phone number is valid or not
     */
    private Boolean isValidPhoneNumber() {
        if (phoneTextField.getText().isEmpty()) {
            phoneErrorLabel.setText("Phone number field cannot be empty");
            phoneErrorLabel.setVisible(true);
            return false;
        } else {
            phoneErrorLabel.setVisible(false);
            return true;
        }
    }


    /**
     * Called when the popup is to be closed
     */
    public void onCancel() {
        Stage stage = (Stage) this.nameTextField.getScene().getWindow();
        stage.close();

    }

    /**
     * Called when the submit button is pressed
     */
    public void onSubmit() {
        boolean validAddress = isValidAddressName();
        boolean validPhone = isValidPhoneNumber();
        boolean validName = isValidSupplierName();
        if (validAddress && validPhone && validName) {
            SupplierManager supplierManager = Managers.getSupplierManager();
            int code = Integer.parseInt(codeText.getText());
            supplierManager.removeSupplier(code);
            supplierManager.addSupplier(code, nameTextField.getText(), phoneTextField.getText(), phoneTypeComboBox.getValue(),
                    emailTextField.getText(), websiteTextField.getText(), addressTextField.getText());
            this.onCancel();
            clearFields();
        }
    }

    /**
     * Called when the text fields are to be cleared
     */
    private void clearFields() {
        nameTextField.clear();
        addressTextField.clear();
        websiteTextField.clear();
        emailTextField.clear();
        phoneTextField.clear();
        phoneTypeComboBox.getSelectionModel().select(PhoneType.WORK);
    }


}
