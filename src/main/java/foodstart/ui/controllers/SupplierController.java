package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.model.stock.Supplier;
import foodstart.ui.Refreshable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SupplierController implements Refreshable {

	@FXML
	private TableView<Supplier> supplierTable;
	@FXML
	private TableColumn<Supplier, String> codeColumn;
	@FXML
	private TableColumn<Supplier, String> supplierNameColumn;
	@FXML
	private TableColumn<Supplier, String> addressColumn;
	@FXML
	private TableColumn<Supplier, String> websiteColumn;
	@FXML
	private TableColumn<Supplier, String> emailColumn;
	@FXML
	private TableColumn<Supplier, String> phoneColumn;
	@FXML
	private TableColumn<Supplier, String> phoneTypeColumn;
	
	/**
	 * List of suppliers currently shown on the table
	 */
	private ObservableList<Supplier> observableSuppliers;

	/**
	 * Called when the controller is initialized
	 */
	@FXML
	public void initialize() {
		populateTable();
	}


	/**
	 * Populate the supplierTable with all suppliers and their details
	 */
	public void populateTable() {
		observableSuppliers = FXCollections.observableArrayList(Managers.getSupplierManager().getSupplierSet());
		refreshTable();
		supplierTable.setItems(observableSuppliers);

		codeColumn.setCellValueFactory(cell -> new SimpleStringProperty(Integer.toString(cell.getValue().getId())));
		supplierNameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSupplierName()));
		addressColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getAddress()));
		websiteColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getUrl()));
		emailColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEmail()));
		phoneColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPhoneNumber()));
		phoneTypeColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPhoneType().name()));
	}


	@Override
	public void refreshTable() {
		observableSuppliers.setAll(Managers.getSupplierManager().getSupplierSet());
	}
}
	

