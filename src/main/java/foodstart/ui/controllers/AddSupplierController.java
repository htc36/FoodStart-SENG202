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

/**
 * Controls ui for add suppliers screen
 */
public class AddSupplierController {
    /**
     * Input field for supplier name
     */
    @FXML
    private TextField nameTextField;
    /**
     * Input field for supplier address
     */
    @FXML
    private TextField addressTextField;
    /**
     * Input field for supplier website
     */
    @FXML
    private TextField websiteTextField;
    /**
     * Input field for supplier email
     */
    @FXML
    private TextField emailTextField;
    /**
     * Input field for supplier phone
     */
    @FXML
    private TextField phoneTextField;
    /**
     * Text area for supplier id
     */
    @FXML
    private Text codeText;
    /**
     * Combo box for supplier phone type
     */
    @FXML
    private ComboBox<PhoneType> phoneTypeComboBox;
    /**
     * Label for if there is an error with the name input
     */
    @FXML
    private Label nameErrorLabel;
    /**
     * Label for if there is an error with the address input
     */
    @FXML
    private Label addressErrorLabel;
    /**
     * Label for if there is an error with the phone input
     */
    @FXML
    private Label phoneErrorLabel;

    /**
     * Supplier id
     */
    private int code;

    /**
     * Called when the controller is initialized
     */
    @FXML
    public void initialize() {
        setNewCode();
        this.phoneTypeComboBox.setItems(FXCollections.observableArrayList(PhoneType.values()));
        this.phoneTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                phoneTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        }));
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
