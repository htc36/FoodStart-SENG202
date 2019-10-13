package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.menu.RecipeManager;
import foodstart.model.menu.MenuItem;
import foodstart.model.menu.Recipe;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ViewVariantsPopupController {

    @FXML
    private Text menuItemNameText;

    @FXML
    private Text menuItemDescriptionText;

    @FXML
    private TableView<Recipe> variantsTable;

    @FXML
    private TableColumn<Recipe, String> variantNameColumn;

    @FXML
    private TableColumn<Recipe, String> variantPriceColumn;

    @FXML
    private TableColumn<Recipe, String> variantIngredientsColumn;

    private Stage stage;

    @FXML
    public void initialize() {
    }

    public void setStage(Stage popupStage) {
        stage = popupStage;
        stage.setMinWidth(popupStage.getWidth());
        stage.setMinHeight(popupStage.getHeight());
    }

    public void setMenuItem(MenuItem menuItem) {
        menuItemNameText.setText(menuItem.getName());
        menuItemDescriptionText.setText(menuItem.getDescription());
        populateTable(menuItem);
    }

    private void populateTable(MenuItem menuItem) {
        variantNameColumn.setCellValueFactory(new PropertyValueFactory<>("displayName"));
        variantPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        variantIngredientsColumn.setCellValueFactory(cell -> {
            RecipeManager recipeManager = Managers.getRecipeManager();

            String output = recipeManager.getIngredientsAsString(cell.getValue());
            return new SimpleStringProperty(output);
        });

        variantsTable.setItems(FXCollections.observableArrayList(menuItem.getVariants()));
    }

    public void onClose() {
        stage.close();
    }

}
