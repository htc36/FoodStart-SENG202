package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.model.menu.MenuItem;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Controller for Create Order panel
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
	 * @param flowPane The flowpane to populate
	 */
	public static void populateMenu(FlowPane flowPane) {
		flowPane.getChildren().clear();
		for (MenuItem item : Managers.getMenuItemManager().getMenuItemSet()) {
			VBox box = new VBox();
			box.setPrefSize(150, 150);
			box.setPadding(new Insets(20));
			box.setStyle("-fx-background-color: #FFFFFF;");
			FlowPane.setMargin(box, new Insets(5));
			box.setBorder(new Border(new BorderStroke(Color.BLACK, 
		            BorderStrokeStyle.SOLID, new CornerRadii(4), BorderWidths.DEFAULT)));
			box.getChildren().add(new Text(item.getName()));
			flowPane.getChildren().add(box);
		}
	}
}
