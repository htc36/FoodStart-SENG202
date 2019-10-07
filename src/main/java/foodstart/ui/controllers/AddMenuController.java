package foodstart.ui.controllers;

import foodstart.model.menu.MenuItem;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Controls the UI for the add menu screen
 */
public class AddMenuController {
    /**
     * Table for menu items in menu
     */
    @FXML
    private Text menuNameText;
    /**
     * Table for the available menu items that are not already in the current menu
     */
    @FXML
    private TableView<MenuItem> menuItemTableView;
    /**
     * Table column for menu item IDs
     */
    @FXML
    private TableColumn<MenuItem, String> tableIDColumn;
    /**
     * Table column for menu item names
     */
    @FXML
    private TableColumn<MenuItem, String> tableNameColumn;
    /**
     * Table column for menu item recipe descriptions
     */
    @FXML
    private TableColumn<MenuItem, String> tableDescriptionColumn;
    /**
     * Table for the available menu items that are not already in the current menu
     */
    @FXML
    private TableView<MenuItem> availableMenuItemsTable;
    /**
     * Table column for the available menu item IDs
     */
    @FXML
    private TableColumn<MenuItem, String> availableIDColumn;
    /**
     * Table column for the available menu item names
     */
    @FXML
    private TableColumn<MenuItem, String> availableNameColumn;
    /**
     * Table column for the available menu item recipe descriptions
     */
    @FXML
    private TableColumn<MenuItem, String> availableDescriptionColumn;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField descriptionTextField;
    /**
     * Stage of the current screen
     */
    private Stage stage;

    /**
     * Called when a popup stage is made.
     * Gives the controller class the popup stage
     * @param popupStage the stage to give the controller
     */
    public void setStage(Stage popupStage) {
        stage = popupStage;
        stage.setMinWidth(900);
        stage.setMinHeight(650);
        stage.setOnCloseRequest(event -> {
            onCancel();
            event.consume();
        });
    }


    public void onAddMenuItem() {

    }

    public void onApplyChanges() {

    }

    public void onCancel() {

    }

    public void onRemoveMenuItem() {

    }

    public void onResetMenuItems() {

    }

}
