package foodstart.ui.controllers;

import foodstart.model.menu.Menu;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ViewMenuController {

    @FXML
    private Text menuNameText;

    @FXML
    private Text menuDescriptionText;

    @FXML
    private TableView menuTable;

    @FXML
    private TableColumn tableIDColumn;

    @FXML
    private TableColumn tableNameColumn;

    @FXML
    private TableColumn tableDescriptionColumn;

    @FXML
    private TableColumn tableVariantsColumn;

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

    public void setStage(Stage popupStage) {
        stage = popupStage;
    }

    public void setMenuInfo(Menu menu) {
        menuNameText.setText(menu.getTitle());
        menuDescriptionText.setText(menu.getDescription());
    }

    public void onCancel() {
        stage.close();
    }





}
