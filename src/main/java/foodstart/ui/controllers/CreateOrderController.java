package foodstart.ui.controllers;

import java.util.HashSet;
import java.util.Set;

import foodstart.manager.Managers;
import foodstart.model.PaymentMethod;
import foodstart.model.menu.Menu;
import foodstart.model.menu.MenuItem;
import foodstart.model.menu.OnTheFlyRecipe;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.order.OrderBuilder;
import foodstart.ui.Refreshable;
import foodstart.ui.recipebuilder.RecipeBuilder;
import foodstart.ui.recipebuilder.RecipeBuilderRunnable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
 * @author Alex Hobson on 04/09/2019
 */
public class CreateOrderController implements Refreshable {
	/**
	 * Flow pane for menu items
	 */
	@FXML
	private FlowPane flowPane;
	/**
	 * Table view for current order items
	 */
	@FXML
	private TableView<Recipe> orderTable;
	/**
	 * Table column for current order item quantity
	 */
	@FXML
	private TableColumn<Recipe, Integer> columnQty;
	/**
	 * Table column for current order items
	 */
	@FXML
	private TableColumn<Recipe, String> columnItem;
	/**
	 * Table column for current order items price
	 */
	@FXML
	private TableColumn<Recipe, String> columnPrice;
	/**
	 * Input field for customer name
	 */
	@FXML
	private TextField orderCustomerName;
	/**
	 * Combo box for order payment method
	 */
	@FXML
	private ComboBox<String> orderPaymentMethod;
	/**
	 * Text area  for the order price
	 */
	@FXML
	private Text orderPrice;

	/**
	 * Box background for menu items in the grid
	 */
	private Background boxBackground;

	/**
	 * There should only be 1 order in progress at a time
	 */
	private OrderBuilder orderBuilder;

	/**
	 * The number of items being edited at a given point in time
	 */
	private int editSessions;

	/**
	 * Called when the controller is initialized
	 */
	@FXML
	public void initialize() {
		boxBackground = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
		refreshTable();
		orderBuilder = new OrderBuilder();

		columnQty.setCellValueFactory(
				cell -> new SimpleIntegerProperty(orderBuilder.getQuantity(cell.getValue())).asObject());
		columnItem.setCellValueFactory(cell -> new SimpleStringProperty(
				cell.getValue() instanceof PermanentRecipe ? cell.getValue().getDisplayName()
						: ("(Custom) " + ((OnTheFlyRecipe) cell.getValue()).getBasedOn().getDisplayName())));
		columnPrice.setCellValueFactory(cell -> new SimpleStringProperty(
				String.format("%.02f", cell.getValue().getPrice() * orderBuilder.getQuantity(cell.getValue()))));

		orderPaymentMethod.getItems().clear();
		for (

				PaymentMethod method : PaymentMethod.values()) {
			orderPaymentMethod.getItems().add(method.getNiceName());
		}
		orderPaymentMethod.setValue(PaymentMethod.values()[0].getNiceName());
		
		orderTable.setPlaceholder(new Text("No items in this order, add one on the left"));
	}

	/**
	 * Create a VBox that represents a menuitem to be added to the list
	 *
	 * @param item Menu item to represent
	 * @return Node representing the menuitem
	 */
	private Node createMenuItemBox(MenuItem item) {
		VBox box = new VBox();
		box.setPrefSize(150, 150);
		box.setPadding(new Insets(5));
		box.setBackground(boxBackground);
		box.setAlignment(Pos.CENTER);
		box.setCursor(Cursor.HAND);
		box.setOnMouseClicked((event) -> new RecipeBuilder(item, new RecipeBuilderRunnable() {
			@Override
			public boolean onRecipeComplete(Recipe recipe, int quantity) {
				if (quantity == 0) return false;
				if (orderBuilder.canAddItem(recipe, quantity)) {
					orderBuilder.addItem(recipe, quantity);
					updateOrderItems();
					return true;
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Insufficient Stock");
					alert.setHeaderText("Cannot add this item to the order as there is insufficient stock");
					alert.setContentText("Modify the order and try again");
					alert.show();
					return false;
				}
			}
		}, orderBuilder));
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

	/**
	 * Updates the display whenever the items in the order change
	 */
	public void updateOrderItems() {
		orderTable.getItems().clear(); // otherwise quantities don't get updated
		orderTable.setItems(FXCollections.observableArrayList(orderBuilder.getCurrentOrder().keySet()));
		float total = orderBuilder.getCurrentTotalPrice();
		orderPrice.setText(String.format("Total $%.02f", total));
	}

	/**
	 * JavaFX calls this when the remove button is clicked to remove an item from
	 * the order
	 */
	public void onRemoveFromOrder() {
		for (Recipe recipe : orderTable.getSelectionModel().getSelectedItems()) {
			orderBuilder.removeItem(recipe);
		}
		updateOrderItems();
	}

	/**
	 * JavaFX calls this when the place order button is clicked
	 */
	public void onPlaceOrder() {
		if (editSessions > 0) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Item is being edited");
			alert.setHeaderText("Cannot place the order as one or more of the items is being edited");
			alert.setContentText("Finish editing the item(s) and try again");
			alert.show();
			return;
		}
		if (orderBuilder.getCurrentOrder().size() == 0) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Cannot place order with 0 items");
			alert.setHeaderText("Cannot place the order as it has no items");
			alert.setContentText("Add an item and try again");
			alert.show();
			return;
		}
		String customerName = orderCustomerName.getText();
		if (customerName.length() == 0) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Missing customer name");
			alert.setHeaderText("Cannot place the order as the customer's name is missing");
			alert.setContentText("Insert the customer's name and try again");
			alert.show();
			return;
		}
		PaymentMethod paymentMethod = PaymentMethod.matchNiceName(orderPaymentMethod.getValue());
		orderBuilder.build(customerName, paymentMethod);

		//Reset the order panel on the side
		orderCustomerName.setText("");
		orderPaymentMethod.setValue(PaymentMethod.values()[0].getNiceName());
		orderBuilder = new OrderBuilder();
		updateOrderItems();
	}

	/**
	 * JavaFX calls this when the Modify button is clicked
	 */
	public void onModifyItem() {
		for (Recipe recipe : orderTable.getSelectionModel().getSelectedItems()) {
			editSessions++;
			new RecipeBuilder(recipe, orderBuilder.getQuantity(recipe), new RecipeBuilderRunnable() {
				@Override
				public boolean onRecipeComplete(Recipe recipe, int quantity) {
					if (quantity == 0) {
						orderBuilder.setEditing(recipe, false);
						editSessions--;
						return true;
					} else {
						if (orderBuilder.canAddItem(recipe, quantity)) {
							orderBuilder.setEditing(recipe, false);
							orderBuilder.removeItem(recipe);
							orderBuilder.addItem(recipe, quantity);
							updateOrderItems();
							editSessions--;
							return true;
						} else {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Insufficient Stock");
							alert.setHeaderText("Cannot modify this item as there is insufficient stock");
							alert.setContentText("Modify the order and try again");
							alert.show();
							return false;
						}
					}
				}
			}, orderBuilder);
		}
	}

	@Override
	public void refreshTable() {
		flowPane.getChildren().clear();
		int menuId = Managers.getMenuManager().getCurrentMenu();
		Menu menu = Managers.getMenuManager().getMenu(menuId);
		Set<MenuItem> items = new HashSet<MenuItem>();
		if (menu == null) {
			items.addAll(Managers.getMenuItemManager().getMenuItemSet());
		} else {
			items.addAll(menu.getMenuItems());
		}
		
		for (MenuItem item : items) {
			flowPane.getChildren().add(createMenuItemBox(item));
		}
	}
}
