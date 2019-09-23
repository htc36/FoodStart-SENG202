package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.Persistence;
import foodstart.manager.exceptions.ExportFailureException;
import foodstart.manager.order.OrderManager;
import foodstart.model.DataFileType;
import foodstart.model.DataType;
import foodstart.model.order.Order;
import foodstart.ui.FXExceptionDisplay;
import foodstart.ui.Main;
import foodstart.ui.Refreshable;
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

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

/**
 * Controls the UI of the sales log management screen
 */
public class SalesController implements Refreshable {
	/**
	 * Table view of orders in the sales log
	 */
	@FXML
	private TableView<Order> salesTableView;
	/**
	 * Table column for order IDs
	 */
	@FXML
	private TableColumn<Order, String> tranIDCol;
	/**
	 * Table column for order names
	 */
	@FXML
	private TableColumn<Order, String> nameCol;
	/**
	 * Table column for order recipes
	 */
	@FXML
	private TableColumn<Order, String> itemsCol;
	/**
	 * Table column for total order prices
	 */
	@FXML
	private TableColumn<Order, String> priceCol;
	/**
	 * Table column for order time
	 */
	@FXML
	private TableColumn<Order, String> timeCol;
	/**
	 * Table column for order date
	 */
	@FXML
	private TableColumn<Order, String> dateCol;
	/**
	 * Table column for order payment method
	 */
	@FXML
	private TableColumn<Order, String> paymentMethodCol;

	/**
	 * FXML for the order editor popup
	 */
	private Parent orderEditorFXML;
	/**
	 * Stage for order editor popup
	 */
	private Stage popupStage;
	/**
	 * FXML loader for order editor popup
	 */
	private FXMLLoader editorLoader;
	/**
	 * Observable list of orders for table
	 */
	private ObservableList<Order> observableOrders;

	/**
	 * Initialises the SalesController
	 */
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

	/**
	 * Populates the sales table with data
	 */
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

	/**
	 * Import sales log from a persistence files
	 */
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

	/**
	 * Export sales log to a persistence files
	 */
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

	/**
	 * Remove a sale from the sales log
	 */
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
		refreshTable();
	}

	/**
	 * Calls the edit order popup
	 */
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

	/**
	 * Refreshes the order table
	 */
	@Override
	public void refreshTable() {
		this.observableOrders.setAll(Managers.getOrderManager().getOrderSet());
	}
}
