package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.stock.SupplierManager;
import foodstart.model.PhoneType;
import foodstart.model.stock.Supplier;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    private Text codeText;

    @FXML
    private ComboBox<PhoneType> phoneTypeComboBox;

    /**
     * Called when the controller is initialized
     */
    @FXML
    public void initialize() {
        this.phoneTypeComboBox.setItems(FXCollections.observableArrayList(PhoneType.values()));
    }

    /**
     * Called to set the supplier so the correct information can be displayed
     * @param supplier
     */
    public void setSupplier(Supplier supplier) {
        codeText.setText(Integer.toString(supplier.getId()));
        nameTextField.setText(supplier.getSupplierName());
        addressTextField.setText(supplier.getAddress());
        websiteTextField.setText(supplier.getUrl());
        emailTextField.setText(supplier.getEmail());
        phoneTextField.setText(supplier.getPhoneNumber());
        phoneTypeComboBox.getSelectionModel().select(supplier.getPhoneType());
    }

    public void onCancel() {
        Stage stage = (Stage) this.nameTextField.getScene().getWindow();
        stage.close();

    }

    public void onConfirm() {
        //TODO: Validations of the text fields
        SupplierManager supplierManager = Managers.getSupplierManager();
        int code = Integer.parseInt(codeText.getText());
        supplierManager.removeSupplier(code);
        supplierManager.addSupplier(code, nameTextField.getText(), phoneTextField.getText(), phoneTypeComboBox.getValue(),
                emailTextField.getText(), websiteTextField.getText(), addressTextField.getText());
        this.onCancel();
    }


}
