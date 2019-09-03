package foodstart.ui;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * Static class for displaying an exception in JavaFX
 * @author Alex Hobson
 * @date 02/09/2019
 */
public class FXExceptionDisplay {

	/**
	 * Code adapted from https://code.makery.ch/blog/javafx-dialogs-official/
	 * Shows a dialog with an exception, and if exiting is true the application will
	 * exit after the dialog is closed with System.exit(1)
	 * @param t The Exception that needs to be shown
	 * @param exiting Whether the application should exit after the dialog is closed
	 */
	public static void showException(Throwable t, boolean exiting) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("FoodStart Crash");
		alert.setHeaderText("FoodStart encountered " + (exiting ? "a critical error and must close" : "an error"));
		alert.setContentText(t.getMessage());

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		String exceptionText = sw.toString();

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(textArea, 0, 0);

		alert.getDialogPane().setExpandableContent(expContent);
		alert.show();
		
		if (exiting) {
			alert.setOnHidden((e) -> {
				System.exit(1);
			});
		}
	}
}
