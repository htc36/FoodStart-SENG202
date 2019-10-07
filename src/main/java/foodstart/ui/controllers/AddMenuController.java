package foodstart.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class AddMenuController {

    @FXML
    private Text menuNameText;

    @FXML
    private TableView<?> menuTable;

    @FXML
    private TableColumn<?, ?> tableIDColumn;

    @FXML
    private TableColumn<?, ?> tableNameColumn;

    @FXML
    private TableColumn<?, ?> tableDescriptionColumn;

    @FXML
    private TableView<?> availableMenuItemsTable;

    @FXML
    private TableColumn<?, ?> availableIDColumn;

    @FXML
    private TableColumn<?, ?> availableNameColumn;

    @FXML
    private TableColumn<?, ?> availableDescriptionColumn;

    void onAddMenuItem() {

    }

    void onApplyChanges() {

    }

    void onCancel() {

    }

    void onRemoveMenuItem() {

    }

    void onResetMenuItems() {

    }

}
