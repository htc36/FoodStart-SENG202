package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.model.menu.Menu;
import foodstart.model.menu.MenuItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.Set;


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
    private boolean changed = false;

    public void initialize() {

    }

    public void setStage(Stage popupStage) {
        stage = popupStage;
        stage.setMinWidth(900);
        stage.setMinHeight(650);
        stage.setOnCloseRequest(event -> {
            onCancel();
            event.consume();
        });
    }


    public void setNewCode() {
        newMenuId = Managers.getMenuManager().generateNewID();
    }

    public void setUpMenuInfo() {
        setNewCode();
        setAvailableMenuItems();
        observableAvailableItems = FXCollections.observableArrayList(currentAvailableMenuItems);
        populateAllMenuItemsTable();
    }

    /**
     * Checks if the text field for the menu title or the text area for the menu description has changed
     * Or if any other changes have been made to the menu items
     * @return whether or not there have been changes
     */
    private boolean hasChanged() {
        if (observableCurrentItems != null) {
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
     * Called to populate the current menu's menu items table view with the menu information
     */
    private void populateCurrentMenuTable() {
        tableIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        menuItemTable.setRowFactory( tv -> {
            TableRow<MenuItem> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 & (! row.isEmpty())) {
                    MenuItem menuItem = row.getItem();
                }
            });
            return row;
        });
        observableCurrentItems = FXCollections.observableArrayList(currentMenuItems);

        menuItemTable.setItems(FXCollections.observableArrayList(observableCurrentItems));
    }

    /**
     * Called to populate the available menu items table view with the menu information
     */
    private void populateAllMenuItemsTable() {
        availableIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        availableNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        availableDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        availableMenuItemsTable.setRowFactory( tv -> {
            TableRow<MenuItem> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 & (! row.isEmpty())) {
                    MenuItem menuItem = row.getItem();
                }
            });
            return row;
        });

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
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void onRemoveMenuItem() {

    }

    public void onResetMenuItems() {

    }

    public void onAddToMenus() {

    }

    /**
     * Called to refresh both table views
     */
    private void refreshTables() {
        populateCurrentMenuTable();
        populateAllMenuItemsTable();
    }

}
