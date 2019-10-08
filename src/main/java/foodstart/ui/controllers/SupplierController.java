package foodstart.ui.controllers;

import foodstart.manager.Managers;
import foodstart.manager.Persistence;
import foodstart.manager.exceptions.ExportFailureException;
import foodstart.model.DataFileType;
import foodstart.model.DataType;
import foodstart.model.stock.Supplier;
import foodstart.ui.FXExceptionDisplay;
import foodstart.ui.Main;
import foodstart.ui.Refreshable;
import foodstart.ui.util.FileImporter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * Controls the UI for the suppliers management screen
 */
public class SupplierController implements Refreshable {
	/**
	 * Table of suppliers
	 */
	@FXML
	private TableView<Supplier> supplierTable;
	/**
	 * Table column of supplier IDs
	 */
	@FXML
	private TableColumn<Supplier, String> codeColumn;
	/**
	 * Table column of supplier names
	 */
	@FXML
	private TableColumn<Supplier, String> supplierNameColumn;
	/**
	 * Table column of supplier addresses
	 */
	@FXML
	private TableColumn<Supplier, String> addressColumn;
	/**
	 * Table column of supplier websites
	 */
	@FXML
	private TableColumn<Supplier, String> websiteColumn;
	/**
	 * Table column of supplier emails
	 */
	@FXML
	private TableColumn<Supplier, String> emailColumn;
	/**
	 * Table column of supplier phones
	 */
	@FXML
	private TableColumn<Supplier, String> phoneColumn;
	/**
	 * Table column of supplier phone types
	 */
	@FXML
	private TableColumn<Supplier, String> phoneTypeColumn;

	/**
	 * FXML loader for edit suppliers popup
	 */
	private FXMLLoader editLoader;
	/**
	 * Stage for the edit suppliers popup
	 */
	private Stage editPopup;
	/**
	 * FXML loader for add supplier popup
	 */
	private FXMLLoader addLoader;
	/**
	 * Stage for the add supplier popup
	 */
	private Stage addPopup;
	
	/**
	 * List of suppliers currently shown on the table
	 */
	private ObservableList<Supplier> observableSuppliers;

	/**
	 * Called when the controller is initialized
	 */
	@FXML
	public void initialize() {
		editLoader = new FXMLLoader(getClass().getResource("editSupplier.fxml"));
		addLoader = new FXMLLoader(getClass().getResource("addSupplier.fxml"));
		try {
			editLoader.load();
			addLoader.load();

		} catch (IOException e) {
			e.printStackTrace();
		}
		editPopup = new Stage();
		editPopup.initModality(Modality.WINDOW_MODAL);
		editPopup.setTitle("Edit Supplier");
		Scene editScene = new Scene(editLoader.getRoot());
		editPopup.setScene(editScene);


		addPopup = new Stage();
		addPopup.initModality(Modality.WINDOW_MODAL);
		addPopup.setTitle("Add New Supplier");
		Scene addScene = new Scene(addLoader.getRoot());
		addPopup.setScene(addScene);


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


	/**
	 * Updates the supplierTable to show the current supplier data
	 */
	@Override
	public void refreshTable() {
		observableSuppliers.setAll(Managers.getSupplierManager().getSupplierSet());
	}

	/**
	 * Called when the import button in the menu list is clicked
	 */
	public void onImport() {
		Stage stage = (Stage) this.supplierTable.getScene().getWindow();
		FileImporter importer = new FileImporter(stage, "Open Suppliers File", DataType.SUPPLIER);
		importer.execute();
		refreshTable();
	}

	/**
	 * Called when the export button in the menu list is clicked
	 */
	public void onExport() {
		Stage stage = (Stage) this.supplierTable.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Suppliers Log File");
		fileChooser.getExtensionFilters().addAll(Main.generateFilters());
		File selectedFile = fileChooser.showSaveDialog(stage);
		if (selectedFile != null) {
			Persistence persist = Managers.getPersistence(DataFileType.getFromExtensions(fileChooser.getSelectedExtensionFilter().getExtensions()));
			try {
				persist.exportFile(selectedFile, DataType.SUPPLIER);
			} catch (ExportFailureException e) {
				FXExceptionDisplay.showException(e, false);
			}
		}
	}

	/**
	 * Called when the add button in the menu list is clicked
	 */

	public void onAdd() {

		if (addPopup.getOwner() == null) {
			addPopup.initOwner(this.supplierTable.getScene().getWindow());
		}
		((AddSupplierController) addLoader.getController()).setNewCode();
		addPopup.showAndWait();
		refreshTable();

	}


	/**
	 * Called when the remove button in the menu list is clicked
	 */
	public void onRemove() {
		Supplier supplier = supplierTable.getSelectionModel().getSelectedItem();
		if (supplier == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Could not remove supplier as no supplier was selected.");
			alert.setHeaderText("No supplier selected");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to remove this supplier?");
			Optional<ButtonType> selection = alert.showAndWait();
			if (selection.isPresent() && selection.get() == ButtonType.OK) {
				Managers.getSupplierManager().removeSupplier(supplier.getId());
			}
		}
		populateTable();
	}

	/**
	 * Called when the edit button in the menu list is clicked
	 */
	public void onEdit() {
		Supplier supplier = supplierTable.getSelectionModel().getSelectedItem();
		if (supplier == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Could not edit supplier as nothing is selected", ButtonType.OK);
			alert.setHeaderText("No supplier selected");
			alert.showAndWait();
		} else {
			if (editPopup.getOwner() == null) {
				editPopup.initOwner(this.supplierTable.getScene().getWindow());
			}

			((EditSupplierController) editLoader.getController()).setSupplier(supplier);
			editPopup.showAndWait();
			refreshTable();

		}
		
	}
	
}
	

