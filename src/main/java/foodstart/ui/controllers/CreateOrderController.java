package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.model.menu.MenuItem;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.ui.recipebuilder.RecipeBuilder;
import foodstart.ui.recipebuilder.RecipeBuilderRunnable;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Controller for Create Order panel
 * 
 * @author Alex Hobson
 * @date 04/09/2019
 */
public class CreateOrderController {

	@FXML
	private FlowPane flowPane;

	private static Background boxBackground;

	@FXML
	/**
	 * Called when the controller is initialized
	 */
	public void initialize() {
		boxBackground = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
		populateMenu(flowPane);
	}

	/**
	 * Populate the FlowPane with all menu items
	 * 
	 * @param flowPane
	 *            The flowpane to populate
	 */
	public static void populateMenu(FlowPane flowPane) {
		flowPane.getChildren().clear();
		for (MenuItem item : Managers.getMenuItemManager().getMenuItemSet()) {
			flowPane.getChildren().add(createMenuItemBox(item));
		}
	}

	private static Node createMenuItemBox(MenuItem item) {
		VBox box = new VBox();
		box.setPrefSize(150, 150);
		box.setPadding(new Insets(5));
		box.setBackground(boxBackground);
		box.setAlignment(Pos.CENTER);
		box.setCursor(Cursor.HAND);
		box.setOnMouseClicked((event) -> {
			new RecipeBuilder(item, new RecipeBuilderRunnable() {
				@Override
				public boolean onRecipeComplete(Recipe recipe, int quantity) {
					return true;
					 //TODO make a callback to an OrderBuilder
				}
			});
		});
		FlowPane.setMargin(box, new Insets(5));
		box.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(4), BorderWidths.DEFAULT)));

		Text itemName = new Text(item.getName());
		itemName.setTextAlignment(TextAlignment.CENTER);

		String priceString = item.getVariants().size() > 0
				? String.format("$%.02f", Managers.getMenuItemManager().getApproxPrice(item.getId()))
				: "No Recipes";
		Text itemPrice = new Text(priceString);

		box.getChildren().add(itemName);
		box.getChildren().add(itemPrice);

		return box;
	}
}
