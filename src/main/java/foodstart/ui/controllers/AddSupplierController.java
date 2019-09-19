package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.stock.SupplierManager;
import foodstart.model.PhoneType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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
        SupplierManager supplierManager = Managers.getSupplierManager();
        int code = Integer.parseInt(codeText.getText());
        supplierManager.removeSupplier(code);
        supplierManager.addSupplier(code, nameTextField.getText(), phoneTextField.getText(), phoneTypeComboBox.getValue(),
                emailTextField.getText(), websiteTextField.getText(), addressTextField.getText());
        this.onCancel();
        clearFields();
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
