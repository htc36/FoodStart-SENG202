package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.order.OrderManager;
import foodstart.model.order.Order;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Set;

public class SalesController {

	@FXML
	private TableView<Order> salesTableView;
	@FXML
	private TableColumn<Order, String> tranIDCol;
	@FXML
	private TableColumn<Order, String> nameCol;
	@FXML
	private TableColumn<Order, String> itemsCol;
	@FXML
	private TableColumn<Order, String> priceCol;
	@FXML
	private TableColumn<Order, String> timeCol;
	@FXML
	private TableColumn<Order, String> dateCol;

	@FXML
	public void initialize() {
		populateTable();
	}

	private void populateTable() {
		OrderManager manager = Managers.getOrderManager();
		Set<Order> orders = manager.getOrderSet();
		ObservableList<Order> observableOrders = FXCollections.observableArrayList(orders);
		salesTableView.setItems(observableOrders);
		tranIDCol.setCellValueFactory(cell -> new SimpleStringProperty(Integer.toString(cell.getValue().getId())));
		nameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCustomerName()));
		priceCol.setCellValueFactory(cell -> new SimpleStringProperty(String.format("%.2f", Float.toString(cell.getValue().getTotalCost()))));
		itemsCol.setCellValueFactory(cell -> new SimpleStringProperty(manager.getItemsAsString(cell.getValue().getId())));
		timeCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTimePlaced().toLocalTime().toString()));
		dateCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTimePlaced().toLocalDate().toString()));

	}

	public void importSales() {

	}

	public void exportSales() {

	}

	public void removeSale() {

	}

	public void editSale() {

	}

}
