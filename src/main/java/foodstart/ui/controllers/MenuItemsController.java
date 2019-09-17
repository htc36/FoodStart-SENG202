package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.model.menu.MenuItem;
import foodstart.model.menu.Recipe;
import foodstart.ui.recipebuilder.RecipeBuilder;
import foodstart.ui.recipebuilder.RecipeBuilderRunnable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MenuItemsController {
    @FXML
    private FlowPane flowPane;


    private FXMLLoader loader;
    private Stage popupStage;
    private Scene scene;
    /**
     * Box background for menu items in the grid
     */
    private Background boxBackground;


    public void initialize() {
        boxBackground = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
        populateMenuItems(flowPane);
    }

    public void populateMenuItems(FlowPane flowPane) {
        flowPane.getChildren().clear();
        for (MenuItem item : Managers.getMenuItemManager().getMenuItemSet()) {
            flowPane.getChildren().add(createMenuItemBox(item));
        }
    }

    private Node createMenuItemBox(MenuItem item) {
        VBox box = new VBox();
        box.setPrefSize(150, 150);
        box.setPadding(new Insets(5));
        box.setBackground(boxBackground);
        box.setAlignment(Pos.CENTER);
        box.setCursor(Cursor.HAND);
        box.setOnMouseClicked((event) -> {

        });
        FlowPane.setMargin(box, new Insets(5));
        box.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(4), BorderWidths.DEFAULT)));

        Text menuItemName = new Text(item.getName());
        menuItemName.setTextAlignment(TextAlignment.CENTER);

        String priceString = item.getVariants().size() > 0
                ? String.format("$%.02f", Managers.getMenuItemManager().getApproxPrice(item.getId()))
                : "No Recipes";
        Text itemPrice = new Text(priceString);

        box.getChildren().add(menuItemName);
        box.getChildren().add(itemPrice);

        return box;
    }


}
