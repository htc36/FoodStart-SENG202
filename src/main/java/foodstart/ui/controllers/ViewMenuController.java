package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.model.menu.Menu;
import foodstart.model.menu.MenuItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Set;

/**
 * Controls the UI for the view menu screen
 */
public class ViewMenuController {
    /**
     * Table for menu items in menu
     */
    @FXML
    private TableView<MenuItem> menuTable;
    /**
     * Text area for menu item names
     */
    @FXML
    private Text menuNameText;
    /**
     * Text area for menu item descriptions
     */
    @FXML
    private Text menuDescriptionText;
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
     * Table column for menu item descriptions
     */
    @FXML
    private TableColumn<MenuItem, String> tableDescriptionColumn;
    /**
     * Table column for menu item recipe variants
     */
    @FXML
    private TableColumn<MenuItem, String> tableVariantsColumn;
    /**
     * Button to set menu as current menu
     */
    @FXML
    private Button btnSetCurrentMenu;
    /**
     * Button to cancel action
     */
    @FXML
    private Button btnCancel;
    /**
     * The stage of the current screen
     */
    @FXML
    private TableView<MenuItem> availableMenuItemsTable;

    @FXML
    private TableColumn<MenuItem, String> availableIDColumn;

    @FXML
    private TableColumn<MenuItem, String> availableNameColumn;

    @FXML
    private TableColumn<MenuItem, String> availableDescriptionColumn;

    @FXML
    private TableColumn<MenuItem, String> availableVariantsColumn;

    @FXML
    private Button btnAddMenuItem;

    @FXML
    private Button btnRemoveMenuItem;




    Stage stage;
    
    /**
     * The displayed menu
     */
    int menuId;

    private Set<MenuItem> allAvailableMenuItems;

    /**
     * Called when a menu box is clicked to be viewed
     */
    @FXML
    public void initialize() {
    }

    /**
     * Called when a popup stage is made.
     * Gives the controller class the popup stage
     * @param popupStage the stage to give the controller
     */
    public void setStage(Stage popupStage) {
        stage = popupStage;
    }

    /**
     * Called to set up the view menu popup with the correct menu information
     * @param menu the menu to give the view menu popup
     */
    public void setMenuInfo(Menu menu) {
        menuNameText.setText(menu.getTitle());
        menuDescriptionText.setText(menu.getDescription());
        menuId = menu.getId();
        populateCurrentMenuTable(menu);
        populateAllMenuItemsTable(menu);
    }

    /**
     * Called to populate the table view with the menu information
     * @param menu the menu to populate the table with data of
     */
    private void populateCurrentMenuTable(Menu menu) {
        Set<MenuItem> menuItems = menu.getMenuItems();
        tableIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        //tableDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableVariantsColumn.setCellValueFactory(cell -> {
            String output = cell.getValue().getVariantsAsString();
            return new SimpleStringProperty(output);
        });

        menuTable.setItems(FXCollections.observableArrayList(menuItems));

    }

    private void populateAllMenuItemsTable(Menu menu) {
        allAvailableMenuItems = Managers.getMenuItemManager().getMenuItemSet();
        for (MenuItem menuItem : menu.getMenuItems()) {
            allAvailableMenuItems.remove(menuItem);
        }

        availableIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        availableNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        //availableDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        availableVariantsColumn.setCellValueFactory(cell -> {
            String output = cell.getValue().getVariantsAsString();
            return new SimpleStringProperty(output);
        });
        availableMenuItemsTable.setItems(FXCollections.observableArrayList(allAvailableMenuItems));

    }

    /**
     * Closes stage on cancel
     */
    public void onCancel() {
        stage.close();
    }


    /**
     * Sets the open menu as the current one in create order panel
     */
    public void setCurrentMenu() {
    	Managers.getMenuManager().setCurrentMenu(menuId);
    	stage.close();
    }

    public void onAddMenuItem() {
        MenuItem selectedMenuItem = availableMenuItemsTable.getSelectionModel().getSelectedItem();
        Menu currentMenu = Managers.getMenuManager().getMenu(menuId);
        currentMenu.addMenuItem(selectedMenuItem);
        allAvailableMenuItems.remove(selectedMenuItem);
        refreshTables();
    }

    public void onRemoveMenuItem() {
        MenuItem selectedMenuItem = menuTable.getSelectionModel().getSelectedItem();
        Menu currentMenu = Managers.getMenuManager().getMenu(menuId);
        currentMenu.removeMenuItem(selectedMenuItem);
        allAvailableMenuItems.add(selectedMenuItem);

        refreshTables();

    }

    public void onResetMenuItems() {
    }

    public void onApplyChanges() {
    }

    private void refreshTables() {
        Menu menu = Managers.getMenuManager().getMenu(menuId);
        populateAllMenuItemsTable(menu);
        populateCurrentMenuTable(menu);
    }

}
