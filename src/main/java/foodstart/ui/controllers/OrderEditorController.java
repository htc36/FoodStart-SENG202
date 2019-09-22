package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.order.OrderManager;
import foodstart.model.PaymentMethod;
import foodstart.model.menu.Recipe;
import foodstart.model.order.Order;
import foodstart.model.stock.Ingredient;
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
	@FXML
	private ComboBox<PaymentMethod> paymentMethodCB;

	private Order order;
	private FXMLLoader editorLoader;
	private Stage popupStage;
	private Parent orderEditorFXML;
	private Map<Recipe, Integer> newRecipes;

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
		popupStage.initModality(Modality.WINDOW_MODAL);
		popupStage.setScene(new Scene(orderEditorFXML, screen.getVisualBounds().getWidth() / 2, screen.getVisualBounds().getHeight() / 2));
	}

	public void setOrder(Order order) {
		this.order = order;
		if (order != null) {
			this.nameField.setText(order.getCustomerName());
			this.priceField.setText(Float.toString(order.getTotalCost()));
			this.dateTimePicker.setDateTimeValue(order.getTimePlaced());
			this.paymentMethodCB.getSelectionModel().select(order.getPaymentMethod());
		}
	}

	public void editItems() {
		if (popupStage.getOwner() == null) {
			popupStage.initOwner(this.nameField.getScene().getWindow());
		}
		((EditOrderItemsController) editorLoader.getController()).setOrder(order);
		((EditOrderItemsController) editorLoader.getController()).pushRecipes(newRecipes);
		popupStage.showAndWait();
		this.newRecipes = ((EditOrderItemsController) editorLoader.getController()).getNewRecipes();

	}

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

	public void cancelEdit() {
		closeSelf();
	}

	private void closeSelf() {
		Stage stage = (Stage) this.cancelButton.getScene().getWindow();
		stage.close();
	}
}
