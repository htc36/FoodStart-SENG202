package foodstart.ui;

import foodstart.manager.Managers;
import foodstart.manager.exceptions.ImportFailureException;
import foodstart.manager.xml.XMLPersistence;
import foodstart.model.Constants;
import foodstart.model.DataFileType;
import foodstart.model.DataType;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Main/Bootstrap class that launches the application
 *
 * @author Alex Hobson on 02/09/2019
 */
public class Main extends Application {

	private Stage primaryStage;
	private Stage splashStage;

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

		fadeOut.setOnFinished((e) -> {
			splashStage.hide();
		});

		fadeIn.play();
		splashStage.show();
	}

	/**
	 * Create the splash screen for the application and display it on the given stage
	 *
	 * @param stage Stage to display the splash screen on
	 */
	private VBox createSplash(Stage stage) {
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setAlwaysOnTop(true);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));

		ImageView splashImage = new ImageView(new Image(getClass().getResourceAsStream("splash.png")));
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
	 * @throws Exception
	 */
	private void loadEverything() throws Exception {
		long startTime = System.currentTimeMillis();
		loadUserData();
		loadFXMLFiles(); //this is done 2nd so initialize methods can access user data
		prepareMainScreen();
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
	 * Loads all user data from the Constants.persistencePath path.
	 * The order is important so things are not being loaded that depend
	 * on IDs for things that haven't been loaded yet
	 */
	private void loadUserData() {
		File directory = new File(Constants.persistencePath);
		if (!directory.isDirectory()) {
			if (directory.exists()) {
				throw new ImportFailureException("File " + directory.getAbsolutePath() + " is not a directory");
			} else {
				if (!directory.mkdir()) {
					throw new ImportFailureException("Permissions error creating directory " + directory.getAbsolutePath());
				}
			}
		}
		XMLPersistence persistence = (XMLPersistence) Managers.getDefaultPersistence();
		try {
			persistence.copyDTDFiles(directory);
		} catch (IOException e) {
			throw new ImportFailureException("Could not copy DTD files into target directory");
		}
		
		for (File file : Constants.importOrder) {
			if (file.isFile()) {
				switch (file.getName().toLowerCase()) {
					case "ingredients.xml":
						persistence.importFile(file, DataType.INGREDIENT);
						break;
					case "menu.xml":
						persistence.importFile(file, DataType.MENU);
						break;
					case "recipes.xml":
						persistence.importFile(file, DataType.RECIPE);
						break;
					case "sales_log.xml":
						persistence.importFile(file, DataType.SALES_LOG);
						break;
					case "suppliers.xml":
						persistence.importFile(file, DataType.SUPPLIER);
						break;
				}
			}
		}
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
