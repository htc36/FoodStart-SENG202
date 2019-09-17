package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.menu.MenuManager;
import foodstart.model.menu.Menu;
import foodstart.model.menu.MenuItem;
import foodstart.model.menu.PermanentRecipe;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.Set;

public class ViewMenuController {


    @FXML
    private TableView<MenuItem> menuTable;

    @FXML
    private Text menuNameText;

    @FXML
    private Text menuDescriptionText;

    @FXML
    private TableColumn<MenuItem, String> tableIDColumn;

    @FXML
    private TableColumn<MenuItem, String> tableNameColumn;

    @FXML
    private TableColumn<MenuItem, String> tableDescriptionColumn;

    @FXML
    private TableColumn<MenuItem, String> tableVariantsColumn;

    @FXML
    private Button btnSetCurrentMenu;

    @FXML
    private Button btnCancel;

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
     */
    public void setStage(Stage popupStage) {
        stage = popupStage;
    }

    /**
     * Called to set up the view menu popup with the correct menu information
     */
    public void setMenuInfo(Menu menu) {
        menuNameText.setText(menu.getTitle());
        menuDescriptionText.setText(menu.getDescription());
        populateTable(menu);
    }

    /**
     * Called to populate the table view with the menu information
     */
    private int test = 0;
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

    public void onCancel() {
        stage.close();
    }





}
