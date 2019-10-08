package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.menu.MenuManager;
import foodstart.model.menu.Menu;
import foodstart.model.menu.MenuItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
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
     * Text field for menu name
     */
    @FXML
    private TextField menuNameTextField;
    /**
     * Text area for menu description
     */
    @FXML
    private TextArea MenuDescriptionTextArea;
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
     * Table column for the available menu item recipe variants
     */
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
    /**
     * The stage of the current screen
     */
    private Stage stage;
    /**
     * Loader for popup fxml
     */
    private FXMLLoader loader;
    /**
     * Stage for popup
     */
    private Stage popupStage;
    /**
     * The displayed menu
     */
    private int menuId;
    /**
     * A boolean to keep track of whether changes have been made to the tables or not
     */
    private boolean changed = false;
    

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
        stage.setMinWidth(700);
        stage.setMinHeight(400);
        stage.setOnCloseRequest(event -> {
            onCancel();
            event.consume();
        });
    }

    /**
     * Called to set up the view menu popup with the correct menu information
     * @param menu the menu to give the view menu popup
     */
    public void setMenuInfo(Menu menu) {
        menuNameTextField.setText(menu.getTitle());
    	MenuDescriptionTextArea.setText(menu.getDescription());
        menuId = menu.getId();
        setCurrentMenuItems(menu);
        setAvailableMenuItems(menu);
        observableCurrentItems = FXCollections.observableArrayList(currentMenuItems);
        observableAvailableItems = FXCollections.observableArrayList(currentAvailableMenuItems);
        populateCurrentMenuTable();
        populateAllMenuItemsTable();
    }
    
    /**
     * Called to set the current menu items in the menu
     */
    private void setCurrentMenuItems(Menu menu) {
        currentMenuItems = menu.getMenuItems();
    }
    
    /**
     * Called to set the available menu items to be added to the menu
     */
    private void setAvailableMenuItems(Menu menu) {
        currentAvailableMenuItems = Managers.getMenuItemManager().getMenuItemSet();
    	for (MenuItem inMenu : menu.getMenuItems()) {
	    	currentAvailableMenuItems.remove(Managers.getMenuItemManager().getMenuItem(inMenu.getId()));
    	}
    }

    private void showMenuItemDetails(MenuItem menuItem) {
        loader = new FXMLLoader(getClass().getResource("viewVariantsPopUp.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((ViewVariantsPopupController) loader.getController()).setMenuItem(menuItem);

        Scene scene = new Scene(loader.getRoot());

        popupStage = new Stage();
        popupStage.initModality(Modality.WINDOW_MODAL);

        ((ViewVariantsPopupController) loader.getController()).setStage(popupStage);


        if (popupStage.getOwner() == null) {
            popupStage.initOwner(this.menuTable.getScene().getWindow());
        }
        popupStage.setTitle("View Menu Item");
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    /**
     * Called to populate the current menu's menu items table view with the menu information
     */
    private void populateCurrentMenuTable() {
        tableIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        menuTable.setRowFactory( tv -> {
            TableRow<MenuItem> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 & (! row.isEmpty())) {
                    MenuItem menuItem = row.getItem();
                    showMenuItemDetails(menuItem);
                }
            });
            return row;
        });

        menuTable.setItems(FXCollections.observableArrayList(observableCurrentItems));
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
                    showMenuItemDetails(menuItem);
                }
            });
            return row;
        });

        availableMenuItemsTable.setItems(FXCollections.observableArrayList(observableAvailableItems));
    }

    /**
     * Closes the menu popup on cancel
     */
    public void onCancel() {
        if (changed) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to close with unapplied changes?");
            Optional<ButtonType> selection = alert.showAndWait();
            if (selection.isPresent() && selection.get() == ButtonType.OK) {
                stage.close();
            }
        } else {
            stage.close();
        }
    }

    /**
     * Sets the open menu as the current one in create order panel
     */
    public void setCurrentMenu() {
    	if (changed) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Apply your changes before setting as current menu");
            alert.setHeaderText("You have unapplied changes");
            alert.showAndWait();
        } else {
            Managers.getMenuManager().setCurrentMenu(menuId);
            stage.close();
        }
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
     * Called when the remove menu item button is pressed
     * Checks that a menu item from the menu's menu items table is selected and moves it from this table
     * to the available menu items table then refreshes both tables
     */
    public void onRemoveMenuItem() {
        MenuItem selectedMenuItem = menuTable.getSelectionModel().getSelectedItem();
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
     * Called when the reset menu items button is clicked
     * Resets both table views to their original states which is showing the actual current menu items and current
     * available menu items
     */
    public void onResetMenuItems() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to reset both tables?");
        Optional<ButtonType> selection = alert.showAndWait();
        if (selection.isPresent() && selection.get() == ButtonType.OK) {
            setMenuInfo(Managers.getMenuManager().getMenu(menuId));
            changed = false;
        }
    }

    /**
     * Called when the apply changes button is clicked
     * Takes the current state of the current menu's menu items table and sets it as the new set of menu items
     * for the current menu
     */
    public void onApplyChanges() {
        if (observableCurrentItems.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "There are no menu items in the menu!");
                alert.setHeaderText("No menu items");
                alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            if (changed) {
                MenuManager menuManager = Managers.getMenuManager();
                Menu currentMenu = menuManager.getMenu(menuId);
                currentMenuItems.clear();
                currentMenuItems.addAll(observableCurrentItems);
                menuManager.removeMenu(menuId);
                menuManager.addMenu(currentMenuItems, currentMenu.getId(), currentMenu.getTitle(), currentMenu.getDescription());
                alert.setTitle("Changes applied");
                alert.setContentText("Changes made have been applied");
                changed = false;
            } else {
                alert.setTitle("No changes made");
                alert.setContentText("No changes were made to the original menu");
            }
            alert.showAndWait();
        }
    }
    
    /**
     * Called when the delete menu button is pressed
     * Will delete this menu from the set of available menus
     */
    public void onDeleteMenu() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to remove this menu?", ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> selection = alert.showAndWait();
		if (selection.isPresent() && selection.get() == ButtonType.YES) {
			System.out.println(Managers.getMenuManager().getMenuSet().contains(Managers.getMenuManager().getMenu(1)));
			Managers.getMenuManager().removeMenu(1);
			System.out.println(Managers.getMenuManager().getMenuSet().contains(Managers.getMenuManager().getMenu(1)));
			onCancel();
		}
    }

    /**
     * Called to refresh both table views
     */
    private void refreshTables() {
        populateCurrentMenuTable();
        populateAllMenuItemsTable();
    }

}
