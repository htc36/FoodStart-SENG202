package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.order.OrderManager;
import foodstart.model.order.Order;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tornadofx.control.DateTimePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderEditorController {
	@FXML
	private Button confirmButton;
	@FXML
	private Button cancelButton;
	@FXML
	private Button editItemsButton;
	@FXML
	private TextField nameField;
	@FXML
	private TextField priceField;
	@FXML
	private DateTimePicker dateTimePicker;

	private Order order;

	public void initialize() {
		this.priceField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d{0,7}([.]\\d{0,2})?")) {
				priceField.setText(oldValue);
			}
		});
	}

	public void setOrder(Order order) {
		this.order = order;
		if (order != null) {
			this.nameField.setText(order.getCustomerName());
			this.priceField.setText(Float.toString(order.getTotalCost()));
			this.dateTimePicker.setDateTimeValue(order.getTimePlaced());
		}
	}

	public void editItems() {

	}

	public void confirmEdit() {
		//TODO: Add payment method editor and price to orders
		OrderManager manager = Managers.getOrderManager();

		int id = order.getId();
		String name = this.nameField.getText();
		float price = Float.parseFloat(this.priceField.getText());
		LocalDateTime timePlaced = dateTimePicker.getDateTimeValue();

		manager.mutateOrder(id, name, timePlaced, order.getPaymentMethod());
		System.out.println(timePlaced);
		closeSelf();
	}

	public void cancelEdit() {
		closeSelf();
	}

	private void closeSelf() {
		Stage stage = (Stage) this.cancelButton.getScene().getWindow();
		stage.close();
	}
}
