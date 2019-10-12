package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.order.OrderManager;
import foodstart.model.PaymentMethod;
import foodstart.model.menu.Recipe;
import foodstart.model.order.Order;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import tornadofx.control.DateTimePicker;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Controls the UI for the order edit screen
 */
public class OrderEditorController {
	/**
	 * Button for confirming input
	 */
	@FXML
	Button confirmButton;
	/**
	 * Button for cancelling input
	 */
	@FXML
	Button cancelButton;
	/**
	 * Button for calling edit order items screen
	 */
	@FXML
	private Button editItemsButton;
	/**
	 * Input field for customer name
	 */
	@FXML
	private TextField nameField;
	/**
	 * Input field for order price
	 */
	@FXML
	private TextField priceField;
	/**
	 * DateTime picker for order datetime
	 */
	@FXML
	private DateTimePicker dateTimePicker;
	/**
	 * Combo box for payment method
	 */
	@FXML
	private ComboBox<PaymentMethod> paymentMethodCB;

	/**
	 * The order being edited
	 */
	private Order order;
	/**
	 * FXML loader for edit items popup
	 */
	private FXMLLoader editorLoader;
	/**
	 * Stage for edit items popup
	 */
	private Stage popupStage;
	/**
	 * FXML for order editor popup
	 */
	private Parent orderEditorFXML;
	/**
	 * Map of recipes to recipe quantity to mutate order items to on confirmation of edit
	 */
	private Map<Recipe, Integer> newRecipes;

	/**
	 * Initialises the OrderEditorController
	 */
	@FXML
	public void initialize() {
		this.paymentMethodCB.setItems(FXCollections.observableArrayList(PaymentMethod.values()));
		this.priceField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d{0,7}([.]\\d{0,2})?")) {
				priceField.setText(oldValue);
			}
		});

		try {
			editorLoader = new FXMLLoader(getClass().getResource("editOrderItems.fxml"));
			orderEditorFXML = editorLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Screen screen = Screen.getPrimary();
		popupStage = new Stage();
		popupStage.setResizable(false);
		popupStage.initModality(Modality.WINDOW_MODAL);
		popupStage.setScene(new Scene(orderEditorFXML, screen.getVisualBounds().getWidth() / 2, screen.getVisualBounds().getHeight() / 2));
	}

	/**
	 * Sets the order being edited
	 * @param order the order to be edited
	 */
	public void setOrder(Order order) {
		this.order = order;
		if (order != null) {
			this.nameField.setText(order.getCustomerName());
			this.priceField.setText(Float.toString(order.getTotalCost()));
			this.dateTimePicker.setDateTimeValue(order.getTimePlaced());
			this.paymentMethodCB.getSelectionModel().select(order.getPaymentMethod());
		}
		this.newRecipes = null;
	}

	/**
	 * Calls popup for edit order items
	 */
	public void editItems() {
		if (popupStage.getOwner() == null) {
			popupStage.initOwner(this.nameField.getScene().getWindow());
		}
		((EditOrderItemsController) editorLoader.getController()).setOrder(this.order);
		((EditOrderItemsController) editorLoader.getController()).pushRecipes(newRecipes);
		popupStage.showAndWait();
		this.newRecipes = ((EditOrderItemsController) editorLoader.getController()).getNewRecipes();

	}

	/**
	 * Confirms the edited order, writing it to the model and closing the stage
	 */
	public void confirmEdit() {
		OrderManager manager = Managers.getOrderManager();
		int id = order.getId();
		String name = this.nameField.getText();
		float price = Float.parseFloat(this.priceField.getText());
		LocalDateTime timePlaced = dateTimePicker.getDateTimeValue();
		PaymentMethod paymentMethod = this.paymentMethodCB.getValue();
		manager.mutateOrder(id, name, timePlaced, price, paymentMethod);
		if (newRecipes != null) {
			manager.mutateOrderItems(id, newRecipes);
		}
		closeSelf();
	}

	/**
	 * Cancels the edit and closes the popup
	 */
	public void cancelEdit() {
		closeSelf();
	}

	/**
	 * Closes the popup
	 */
	private void closeSelf() {
		Stage stage = (Stage) this.cancelButton.getScene().getWindow();
		stage.close();
	}
}
