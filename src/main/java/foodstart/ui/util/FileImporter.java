package foodstart.ui.util;

import foodstart.manager.Managers;
import foodstart.manager.Persistence;
import foodstart.manager.exceptions.ImportFailureException;
import foodstart.model.DataFileType;
import foodstart.model.DataType;
import foodstart.ui.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

public class FileImporter {

	private FileChooser fileChooser;
	private Stage parent;
	private DataType dataType;

	public FileImporter(Stage parent, String title, DataType dataType) {
		this.fileChooser = new FileChooser();
		this.parent = parent;
		this.dataType = dataType;
		fileChooser.setTitle(title);
		fileChooser.getExtensionFilters().addAll(Main.generateFilters());
	}

	public void execute() {
		File selectedFile = fileChooser.showOpenDialog(parent);
		if (selectedFile == null) {
			return;
		}
		Persistence persist = Managers.getPersistence(DataFileType.getFromExtensions(fileChooser.getSelectedExtensionFilter().getExtensions()));
		try {
			persist.importFile(selectedFile, dataType);
			Managers.writeBuffer();
		} catch (ImportFailureException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "", new ButtonType("Drop"), ButtonType.CLOSE);
			alert.setTitle("Error importing file");
			alert.setHeaderText("Could not import file");
			Label label = new Label("Do you wish to drop the imported data or close the program?");
			alert.getDialogPane().setContent(label);
			Optional<ButtonType> selection = alert.showAndWait();
			if (selection.isPresent() && selection.get().getButtonData() == ButtonBar.ButtonData.OTHER) {
				Managers.dropBuffer();
			} else {
				System.exit(1);
			}
		}
	}
}
