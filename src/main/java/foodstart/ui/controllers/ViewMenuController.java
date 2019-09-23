package foodstart.ui.controllers;

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
    Stage stage;

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
        populateTable(menu);
    }

    /**
     * Called to populate the table view with the menu information
     * @param menu the menu to populate the table with data of
     */
    private void populateTable(Menu menu) {
        Set<MenuItem> menuItems = menu.getMenuItems();
        tableIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableVariantsColumn.setCellValueFactory(cell -> {
            String output = cell.getValue().getVariantsAsString();
            return new SimpleStringProperty(output);
        });

        menuTable.setItems(FXCollections.observableArrayList(menuItems));



        /*
        ObservableList<MenuItem> observableMenuItems = FXCollections.observableArrayList(menuItems);
        menuTable.setItems(observableMenuItems);

        tableIDColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue()));
        */

    }

    /**
     * Closes stage on cancel
     */
    public void onCancel() {
        stage.close();
    }





}
