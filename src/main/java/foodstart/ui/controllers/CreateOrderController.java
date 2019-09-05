package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.model.menu.MenuItem;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
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

	@FXML
	/**
	 * Called when the controller is initialized
	 */
	public void initialize() {
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
		box.setStyle("-fx-background-color: #FFFFFF;");
		box.setAlignment(Pos.CENTER);
		box.setCursor(Cursor.HAND);
		FlowPane.setMargin(box, new Insets(5));
		box.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(4), BorderWidths.DEFAULT)));

		Text itemName = new Text(item.getName());
		itemName.setTextAlignment(TextAlignment.CENTER);

		String priceString = item.getVariants().size() > 0
				? String.format("$%.02f", item.getVariants().get(0).getPrice())
				: "No Recipes";
		Text itemPrice = new Text(priceString);

		box.getChildren().add(itemName);
		box.getChildren().add(itemPrice);
		
		return box;
	}
}
