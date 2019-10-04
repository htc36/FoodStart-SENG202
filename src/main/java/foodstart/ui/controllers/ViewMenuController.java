package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.model.menu.Menu;
import foodstart.model.menu.MenuItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    private TableColumn<MenuItem, String> availableVariantsColumn;
    
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


    Stage stage;
    
    /**
     * The displayed menu
     */
    int menuId;
    

    

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
        setCurrentMenuIems(menu);
        setAvailableMenuItems(menu);
        observableCurrentItems = FXCollections.observableArrayList(currentMenuItems);
        observableAvailableItems = FXCollections.observableArrayList(currentAvailableMenuItems);
        populateCurrentMenuTable(menu);
        populateAllMenuItemsTable(menu);
    }
    
    /**
     * Called to set the current menu items in the menu
     */
    private void setCurrentMenuIems(Menu menu) {
        currentMenuItems = menu.getMenuItems();
    }
    
    /**
     * Called to set the available menu items to be added to the menu
     */
    private void setAvailableMenuItems(Menu menu) {


    	//currentAvailableMenuItems.removeAll(menu.getMenuItems());
    	currentAvailableMenuItems = Managers.getMenuItemManager().getMenuItemSet();
    	
    	for (MenuItem inMenu : menu.getMenuItems()) {
	    	currentAvailableMenuItems.remove(Managers.getMenuItemManager().getMenuItem(inMenu.getId()));
    		
    	}
    	//currentAvailableMenuItems.remove(Managers.getMenuItemManager().getMenuItem(1));

        

    }
    
    /**
     * Called to populate the table view with the menu information
     * @param menu the menu to populate the table with data of
     */
    private void populateCurrentMenuTable(Menu menu) {
    	/*
    	if (currentMenuItems == null) {
    		setCurrentMenuIems(menu);
    		observableCurrentItems = FXCollections.observableArrayList(currentMenuItems);
    	}*/
    	
        tableIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableVariantsColumn.setCellValueFactory(cell -> {
            String output = cell.getValue().getVariantsAsString();
            return new SimpleStringProperty(output);
        });

        menuTable.setItems(FXCollections.observableArrayList(observableCurrentItems));

    }

    private void populateAllMenuItemsTable(Menu menu) {

        availableIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        availableNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        availableVariantsColumn.setCellValueFactory(cell -> {
            String output = cell.getValue().getVariantsAsString();
            return new SimpleStringProperty(output);
        });
        availableMenuItemsTable.setItems(FXCollections.observableArrayList(observableAvailableItems));

    }

    /**
     * Closes stage on cancel
     */
    public void onCancel() {
        stage.close();
        currentAvailableMenuItems = null;
        currentMenuItems = null;
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
        if (selectedMenuItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No menu item selected from the available menu items table");
            alert.setHeaderText("No Menu Item Selected");
            alert.showAndWait();
        } else {
            observableCurrentItems.add(selectedMenuItem);
            observableAvailableItems.remove(selectedMenuItem);
            refreshTables();
        }
    }

    public void onRemoveMenuItem() {
        MenuItem selectedMenuItem = menuTable.getSelectionModel().getSelectedItem();
        if (selectedMenuItem == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "No menu item selected from the current menu table");
			alert.setHeaderText("No Menu Item Selected");
			alert.showAndWait();
        } else {
            observableCurrentItems.remove(selectedMenuItem);
            observableAvailableItems.add(selectedMenuItem);
            refreshTables();
        }

    }

    public void onResetMenuItems() {
    }

    public void onApplyChanges() {
    }

    private void refreshTables() {
        Menu menu = Managers.getMenuManager().getMenu(menuId);
        populateCurrentMenuTable(menu);
        populateAllMenuItemsTable(menu);
    }

}
