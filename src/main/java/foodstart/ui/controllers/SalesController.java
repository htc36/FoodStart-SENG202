package foodstart.ui.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import foodstart.manager.Managers;
import foodstart.manager.Persistence;
import foodstart.manager.exceptions.ExportFailureException;
import foodstart.manager.order.OrderManager;
import foodstart.model.DataFileType;
import foodstart.model.DataType;
import foodstart.model.order.Order;
import foodstart.ui.FXExceptionDisplay;
import foodstart.ui.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

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
	private TableColumn<Order, String> paymentMethodCol;

	private Parent orderEditorFXML;
	private Stage popupStage;
	private FXMLLoader editorLoader;
	private ObservableList<Order> observableOrders;

	@FXML
	public void initialize() {
		try {
			editorLoader = new FXMLLoader(getClass().getResource("editOrder.fxml"));
			orderEditorFXML = editorLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Screen screen = Screen.getPrimary();
		popupStage = new Stage();
		popupStage.initModality(Modality.WINDOW_MODAL);
		popupStage.setScene(new Scene(orderEditorFXML, screen.getVisualBounds().getWidth() / 2, screen.getVisualBounds().getHeight() / 2));
		populateTable();
	}

	public void populateTable() {
		OrderManager manager = Managers.getOrderManager();
		Set<Order> orders = manager.getOrderSet();
		observableOrders = FXCollections.observableArrayList(orders);
		salesTableView.setItems(observableOrders);
		tranIDCol.setCellValueFactory(cell -> new SimpleStringProperty(Integer.toString(cell.getValue().getId())));
		nameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCustomerName()));
		priceCol.setCellValueFactory(cell -> new SimpleStringProperty(String.format("%.02f", cell.getValue().getTotalCost())));
		itemsCol.setCellValueFactory(cell -> new SimpleStringProperty(manager.getItemsAsString(cell.getValue().getId())));
		timeCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTimePlaced().toLocalTime().toString()));
		dateCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTimePlaced().toLocalDate().toString()));
		paymentMethodCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPaymentMethod().getNiceName()));
	}

	public void importSales() {
		Stage stage = (Stage) this.salesTableView.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Sales Log File");
		fileChooser.getExtensionFilters().addAll(Main.generateFilters());
		File selectedFile = fileChooser.showOpenDialog(stage);
		if (selectedFile != null) {
			Persistence persist = Managers.getPersistence(DataFileType.getFromExtensions(fileChooser.getSelectedExtensionFilter().getExtensions()));
			persist.importFile(selectedFile, DataType.SALES_LOG);
		}
		refreshTable();
	}

	public void exportSales() {
		Stage stage = (Stage) this.salesTableView.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Sales Log File");
		fileChooser.getExtensionFilters().addAll(Main.generateFilters());
		File selectedFile = fileChooser.showSaveDialog(stage);
		if (selectedFile != null) {
			Persistence persist = Managers.getPersistence(DataFileType.getFromExtensions(fileChooser.getSelectedExtensionFilter().getExtensions()));
			try {
				persist.exportFile(selectedFile, DataType.SALES_LOG);
			} catch (ExportFailureException e) {
				FXExceptionDisplay.showException(e, false);
			}
		}
	}

	public void removeSale() {
		Order order = salesTableView.getSelectionModel().getSelectedItem();
		if (order == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Could not remove order as none was selected", ButtonType.OK);
			alert.setHeaderText("No order selected");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to remove this order?", ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> selection = alert.showAndWait();
			if (selection.isPresent() && selection.get() == ButtonType.YES) {
				Managers.getOrderManager().removeOrder(order.getId());
			}
		}
		populateTable();
	}

	public void editSale() {
		Order order = salesTableView.getSelectionModel().getSelectedItem();
		if (order == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Could not edit order as none was selected", ButtonType.OK);
			alert.setHeaderText("No order selected");
			alert.showAndWait();
		} else {
			if (popupStage.getOwner() == null) {
				popupStage.initOwner(this.salesTableView.getScene().getWindow());
			}
			((OrderEditorController) editorLoader.getController()).setOrder(order);
			popupStage.showAndWait();
			refreshTable();
		}
	}

	private void refreshTable() {
		this.observableOrders.setAll(Managers.getOrderManager().getOrderSet());
	}
}
