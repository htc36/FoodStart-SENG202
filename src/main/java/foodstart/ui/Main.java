package foodstart.ui;

import foodstart.manager.Managers;
import foodstart.model.DataFileType;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Main/Bootstrap class that launches the application
 *
 * @author Alex Hobson on 02/09/2019
 */
public class Main extends Application {

	/**
	 * The primary stage of the application
	 */
	private Stage primaryStage;

	/**
	 * The stage for the splash screen
	 */
	private Stage splashStage;

	/**
	 * The root FXML file
	 */
	private Parent rootFXML;

	/**
	 * Launches the JavaFX Application
	 *
	 * @param args List of commandline args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Start the JavaFX application
	 *
	 * @param primaryStage
	 *            Main stage for the application
	 */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setMinWidth(950);
		primaryStage.setMinHeight(700);

		splashStage = new Stage();
		VBox splashLayout = createSplash(splashStage);
		FadeTransition fadeIn = new FadeTransition(Duration.millis(300), splashLayout);
		fadeIn.setFromValue(0);
		fadeIn.setToValue(1);
		fadeIn.setCycleCount(1);

		FadeTransition fadeOut = new FadeTransition(Duration.millis(600), splashLayout);
		fadeOut.setFromValue(1);
		fadeOut.setToValue(0);
		fadeOut.setCycleCount(1);

		fadeIn.setOnFinished((e) -> {
			try {
				loadEverything();

				fadeOut.play();
				primaryStage.show();
			} catch (Exception ex) {
				splashStage.setAlwaysOnTop(false);
				FXExceptionDisplay.showException(ex, true);
			}

		});

		fadeOut.setOnFinished((e) -> splashStage.hide());

		fadeIn.play();
		splashStage.show();
	}

	/**
	 * Create the splash screen for the application and display it on the given stage
	 *
	 * @param stage Stage to display the splash screen on
	 * @return the layout for the splash screen
	 */
	private VBox createSplash(Stage stage) {
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setAlwaysOnTop(true);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));

		ImageView splashImage = new ImageView(new Image(getClass().getResourceAsStream("splash.png")));
		splashImage.setFitWidth(700);
		splashImage.setFitHeight(450);
		VBox splashLayout = new VBox();
		splashLayout.getChildren().add(splashImage);
		Scene splashScene = new Scene(splashLayout, 700, 450);
		splashScene.setFill(Color.TRANSPARENT);

		stage.setScene(splashScene);
		return splashLayout;
	}

	/**
	 * Blocks until everything required to start the application is loaded
	 * (at least 1 second)
	 *
	 * @throws Exception if an exception is encountered while loading the FXML files or sleeping the thread
	 */
	private void loadEverything() throws Exception {
		long startTime = System.currentTimeMillis();
		Managers.getDefaultPersistence().loadAllFiles();
		Managers.writeBuffer();
		loadFXMLFiles(); //this is done 2nd so initialize methods can access user data
		prepareMainScreen();
		
		primaryStage.setOnCloseRequest(event -> {
			Alert closeConfirmation = new Alert(
	                Alert.AlertType.CONFIRMATION,
	                "Any unsaved changed will be lost."
	        );
	        closeConfirmation.setHeaderText("Are you sure you want to exit?");
	        closeConfirmation.initModality(Modality.APPLICATION_MODAL);
	        closeConfirmation.initOwner(primaryStage);
	        
	        closeConfirmation.getButtonTypes().clear();
	        
	        closeConfirmation.getButtonTypes().add(ButtonType.OK);
	        Button saveButton = (Button) closeConfirmation.getDialogPane().lookupButton(ButtonType.OK);
            saveButton.textProperty().set("Save");
	        
	        closeConfirmation.getButtonTypes().add(ButtonType.APPLY);
	        Button closeButton = (Button) closeConfirmation.getDialogPane().lookupButton(ButtonType.APPLY);
            closeButton.textProperty().set("Don't Save");
            
            
            closeConfirmation.getButtonTypes().add(ButtonType.CANCEL);
	        Button cancelButton = (Button) closeConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
            cancelButton.textProperty().set("Cancel");
	        

	        Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
	        if (ButtonType.OK.equals(closeResponse.get())) {
	        	if (!Managers.getDefaultPersistence().saveAllFiles()) {
	        		event.consume();
	        	}
	        } else if (!closeResponse.isPresent() || ButtonType.CANCEL.equals(closeResponse.get())) {
	            event.consume();
	        }
		});
		
		long duration = System.currentTimeMillis() - startTime;
		if (duration < 1000) {
			Thread.sleep(1000 - duration);
		}
	}

	/**
	 * Load all the FXML files and initialize them
	 *
	 * @throws IOException If the FXML file could not be read
	 */
	private void loadFXMLFiles() throws IOException {
		//rootFXML = FXMLLoader.load(getClass().getResource("main.fxml"));
		FXMLLoader rootFXMLLoader = new FXMLLoader(getClass().getResource("main.fxml"));
		rootFXML = rootFXMLLoader.load();
		//Loaded by calling initialize on MainController
	}

	/**
	 * Prepare's the main screen so it can be displayed
	 * (rootFXML should already have the create order controller in it
	 * from initializing it in loadFXMLFiles)
	 */
	private void prepareMainScreen() {
		primaryStage.setTitle("FoodStart");
		Screen screen = Screen.getPrimary();
		primaryStage.setScene(
				new Scene(rootFXML, screen.getVisualBounds().getWidth(), screen.getVisualBounds().getHeight()));
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
		primaryStage.setMaximized(true);
	}

	/**
	 * Returns an array of all possible file filters for a file chooser
	 * @return the array of all possible file filters for a file chooser
	 */
	public static FileChooser.ExtensionFilter[] generateFilters() {
		ArrayList<FileChooser.ExtensionFilter> filters = new ArrayList<FileChooser.ExtensionFilter>();
		for (DataFileType type : DataFileType.values()) {
			filters.add(new FileChooser.ExtensionFilter(type.getDescription(), type.getExtensions()));
		}
		return filters.toArray(new FileChooser.ExtensionFilter[0]);
	}

}
