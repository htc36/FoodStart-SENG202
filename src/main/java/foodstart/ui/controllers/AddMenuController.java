package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.menu.MenuManager;
import foodstart.model.menu.MenuItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


/**
 * Controls the UI for the add menu screen
 */
public class AddMenuController {
    /**
     * Table for the available menu items that are not already in the current menu
     */
    @FXML
    private TableView<MenuItem> menuItemTable;
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
    /**
     * Button to close the pop up
     */
    @FXML
    private Button closeButton;
    /**
     * The list of menu items in the current menu that will be displayed in the table
     */
    private ObservableList<MenuItem> observableCurrentItems;

    /**
     * The list of menu items available to be added to the menu that will be displayed in the table
     */
    private ObservableList<MenuItem> observableAvailableItems;
    /**
     * The initial menu items in the menu
     */
    private Set<MenuItem> currentMenuItems;
    /**
     * The initial available menu items
     */
    private Set<MenuItem> currentAvailableMenuItems;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField descriptionTextField;
    /**
     * Error label to display any invalid entries
     */
    @FXML
    private Label errorLabel;
    /**
     * Stage of the current screen
     */
    private Stage stage;
    /**
     * Menu id
     */
    private int newMenuId;
    /**
     * A boolean to keep track of whether changes have been made to the tables or not
     */
    public Boolean changed = false;


    public void initialize() {

    }

    /**
     * Generates a new id for the new menu
     */
    public void setNewCode() {
        newMenuId = Managers.getMenuManager().generateNewID();
    }

    /**
     * Sets up the lists and tables related to adding menu items for the new menu
     */
    public void setUpMenuInfo() {
        setNewCode();
        setAvailableMenuItems();
        setCurrentMenuItems();
        observableAvailableItems = FXCollections.observableArrayList(currentAvailableMenuItems);
        populateAllMenuItemsTable();
        observableCurrentItems = FXCollections.observableArrayList(currentMenuItems);
        populateCurrentMenuTable();
    }

    /**
     * Checks if the text field for the menu title or the text area for the menu description has changed
     * Or if any other changes have been made to the menu items
     * @return whether or not there have been changes
     */
    private boolean hasChanged() {
        if (!observableCurrentItems.isEmpty()) {
            changed = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Called to set the available menu items to be added to the menu
     */
    private void setAvailableMenuItems() {
        currentAvailableMenuItems = Managers.getMenuItemManager().getMenuItemSet();
    }

    /**
     * Called to set the current menu items to be empty so that menu items can be added to it
     */
    private void setCurrentMenuItems() {
        currentMenuItems = new HashSet<MenuItem>();
    }

    /**
     * Called to populate the current menu's menu items table view with the menu information
     */
    private void populateCurrentMenuTable() {
        tableIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        menuItemTable.setItems(FXCollections.observableArrayList(observableCurrentItems));

    }

    /**
     * Called to populate the available menu items table view with the menu information
     */
    private void populateAllMenuItemsTable() {
        availableIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        availableNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        availableDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        availableMenuItemsTable.setItems(FXCollections.observableArrayList(observableAvailableItems));
    }

    /**
     * Called when the add menu item button is pressed
     * Checks that a menu item from the available menu items table is selected and moves it from this table
     * to the current menu's menu items table then refreshes both tables
     */
    public void onAddMenuItem() {
        MenuItem selectedMenuItem = availableMenuItemsTable.getSelectionModel().getSelectedItem();
        if (selectedMenuItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No menu item selected from the available menu items table");
            alert.setHeaderText("No menu item selected");
            alert.showAndWait();


        } else {
            changed = true;
            observableCurrentItems.add(selectedMenuItem);
            observableAvailableItems.remove(selectedMenuItem);
            refreshTables();
        }
    }

    /**
     * Removes a menu item in the current menu table
     */
    public void onRemoveMenuItem() {
        MenuItem selectedMenuItem = menuItemTable.getSelectionModel().getSelectedItem();
        if (selectedMenuItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No menu item selected from the current menu table");
            alert.setHeaderText("No menu item selected");
            alert.showAndWait();
        } else {
            changed = true;
            observableCurrentItems.remove(selectedMenuItem);
            observableAvailableItems.add(selectedMenuItem);
            refreshTables();
        }
    }

    /**
     * Resets the current menu items table and the available menu items table back to their initial state
     */
    public void onResetMenuItems() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to reset both tables?");
        Optional<ButtonType> selection = alert.showAndWait();
        if (selection.isPresent() && selection.get() == ButtonType.OK) {
            currentMenuItems.clear();
            setUpMenuInfo();
            changed = false;
        }
    }

    /**
     * Closes the menu popup on cancel
     */
    public void onCancel() {
        if (hasChanged()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to close with unapplied changes?");
            Optional<ButtonType> selection = alert.showAndWait();
            if (selection.isPresent() && selection.get() == ButtonType.OK) {
                closeSelf();
            }
        } else {
            closeSelf();
        }
    }

    /**
     * Closes the stage
     */
    public void closeSelf() {
        stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Checks if the menu name text field is valid
     * @return Boolean if the menu name is valid or not
     */
    private Boolean isValidMenuName() {
        if (nameTextField.getText().isEmpty()) {
            errorLabel.setText("Menu name field cannot be empty");
            errorLabel.setVisible(true);
            return false;
        } else {
            errorLabel.setVisible(false);
            return true;
        }
    }

    /**
     * Checks if the current menu items contains menu items and is not empty
     * @return Boolean if the current menu items are not empty
     */
    private Boolean isValidMenuItems() {
        if (currentMenuItems.isEmpty()) {
            errorLabel.setText("There must be menu items in your new menu");
            errorLabel.setVisible(true);
            return false;
        } else {
            errorLabel.setVisible(false);
            return true;
        }
    }

    /**
     * Adding the new menu to be stored into menu manager with all the other menus
     */
    public void onAddToMenus() {
        if (observableCurrentItems.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Cannot add to menus because there are no menu items");
            alert.setHeaderText("No menu items");
            alert.showAndWait();
        } else if (isValidMenuItems() && isValidMenuName()) {
            MenuManager menuManager = Managers.getMenuManager();
            menuManager.addMenu(currentMenuItems, newMenuId, nameTextField.getText(), descriptionTextField.getText());
            closeSelf();
            clearFields();
        }
    }

    /**
     * Clears the contents in the text fields
     */
    public void clearFields() {
        nameTextField.clear();
        descriptionTextField.clear();
    }

    /**
     * Called to refresh both table views
     */
    private void refreshTables() {
        populateCurrentMenuTable();
        populateAllMenuItemsTable();
    }

}
