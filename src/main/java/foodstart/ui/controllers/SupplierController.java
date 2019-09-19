package foodstart.ui.controllers;
import java.io.File;
import java.io.IOException;
import java.util.Optional;


import foodstart.manager.Managers;
import foodstart.manager.Persistence;
import foodstart.manager.exceptions.ExportFailureException;
import foodstart.model.DataFileType;
import foodstart.model.DataType;
import foodstart.model.stock.Supplier;
import foodstart.ui.FXExceptionDisplay;
import foodstart.ui.Main;
import foodstart.ui.Refreshable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
	@FXML
	private MenuItem importButton;
	@FXML
	private MenuItem exportButton;
	@FXML
	private MenuItem addButton;
	@FXML
	private MenuItem removeButton;
	@FXML
	private MenuItem editButton;

	private FXMLLoader editLoader;
	private Stage editPopup;
	
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

		try {
			editLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		editPopup = new Stage();
		editPopup.initModality(Modality.WINDOW_MODAL);
		editPopup.setTitle("Edit Supplier");
		Scene editScene = new Scene(editLoader.getRoot());
		editPopup.setScene(editScene);

		//TODO: Add popup

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
	

