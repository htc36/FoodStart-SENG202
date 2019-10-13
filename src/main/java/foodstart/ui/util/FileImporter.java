package foodstart.ui.util;

import foodstart.manager.Managers;
import foodstart.manager.Persistence;
import foodstart.manager.xml.XMLPersistence;
import foodstart.model.DataFileType;
import foodstart.model.DataType;
import foodstart.ui.FXExceptionDisplay;
import foodstart.ui.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

/**
 * Allows for the importing and exporting of files via a gui
 */
public class FileImporter {
	/**
	 * The file chooser used to select a file
	 */
	private FileChooser fileChooser;
	/**
	 * The parent stage of the FileImporter
	 */
	private Stage parent;
	/**
	 * The DataType that is being imported
	 */
	private DataType dataType;

	/**
	 * Constructs a new instance of a file importer
	 * @param parent the parent window of the importer
	 * @param title the title of the importer window
	 * @param dataType the data type that is being imported
	 */
	public FileImporter(Stage parent, String title, DataType dataType) {
		this.fileChooser = new FileChooser();
		this.parent = parent;
		this.dataType = dataType;
		fileChooser.setTitle(title);
		fileChooser.getExtensionFilters().addAll(Main.generateFilters());
	}

	/**
	 * Opens a new window for the user to select a file and then imports the file
	 */
	public void execute() {
		File selectedFile = fileChooser.showOpenDialog(parent);
		if (selectedFile == null) {
			return;
		}
		Persistence persist = Managers.getPersistence(DataFileType.getFromExtensions(fileChooser.getSelectedExtensionFilter().getExtensions()));
		try {
			if (persist instanceof XMLPersistence) {
				((XMLPersistence) persist).copyDTDFiles(selectedFile.getParentFile(), false);
			}
			persist.importFile(selectedFile, dataType);
			Managers.writeBuffer();
		} catch (Exception e) {
			ButtonType trace = new ButtonType("Trace", ButtonBar.ButtonData.HELP);
			Alert alert = new Alert(Alert.AlertType.ERROR, "", new ButtonType("Drop"), ButtonType.CLOSE, trace);
			alert.setTitle("Error importing file");
			alert.setHeaderText("Could not import file");
			Label label = new Label("Do you wish to drop the imported data or close the program?");
			alert.getDialogPane().setContent(label);
			Optional<ButtonType> selection = alert.showAndWait();
			if (selection.isPresent() && selection.get().getButtonData() == ButtonBar.ButtonData.OTHER) {
				Managers.dropBuffer();
			} else if (selection.isPresent() && selection.get().getButtonData() == ButtonBar.ButtonData.HELP) {
				FXExceptionDisplay.showException(e, true);
			} else {
				System.exit(1);
			}
		}
	}
}
