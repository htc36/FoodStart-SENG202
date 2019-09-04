package foodstart.ui;

import java.io.File;
import java.io.IOException;

import foodstart.manager.Managers;
import foodstart.manager.exceptions.ImportFailureException;
import foodstart.manager.xml.XMLPersistence;
import foodstart.model.Constants;
import foodstart.model.DataType;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Main/Bootstrap class that launches the application
 * 
 * @author Alex Hobson
 * @date 02/09/2019
 */
public class Main extends Application {

	private static Stage primaryStage;
	private static Stage splashStage;

	private Parent rootFXML;
	private Parent createOrderFXML;
	private Parent manageCurrentMenuFXML;
	private Parent manageAllMenus;
	private Parent manageMenuItems;
	private Parent manageRecipes;
	private Parent manageIngredients;
	private Parent stockInventory;
	private Parent stockSuppliers;
	private Parent salesLog;

	/**
	 * Launches the JavaFX Application
	 * 
	 * @param args
	 *            List of commandline args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	/**
	 * Start the JavaFX application
	 * 
	 * @param primaryStage
	 *            Main stage for the application
	 */
	public void start(Stage primaryStage) throws IOException {
		Main.primaryStage = primaryStage;

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

	private void loadFXMLFiles() throws IOException {
		rootFXML = FXMLLoader.load(getClass().getResource("main.fxml"));
		createOrderFXML = FXMLLoader.load(getClass().getResource("createorder.fxml"));
	}
	
	private void loadUserData() {
		File directory = new File(Constants.persistencePath);
		if (!directory.isDirectory()) {
			if (directory.exists()) {
				throw new ImportFailureException("File "+directory.getAbsolutePath()+" is not a directory");
			} else {
				if (!directory.mkdir()) {
					throw new ImportFailureException("Permissions error creating directory "+directory.getAbsolutePath());
				}
			}
		}
		XMLPersistence persistence = (XMLPersistence) Managers.getDefaultPersistence();
		try {
			persistence.copyDTDFilesIfNotExists(directory);
		} catch (IOException e) {
			throw new ImportFailureException("Could not copy DTD files into target directory");
		}
		File[] importOrder = new File[] {
			new File(directory, "ingredients.xml"),
			new File(directory, "recipes.xml"),
			new File(directory, "menu.xml"),
			new File(directory, "sales.xml"),
			new File(directory, "suppliers.xml")
		};
		for (File file : importOrder) {
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
				case "sales.xml":
					persistence.importFile(file, DataType.SALES_LOG);
					break;
				case "suppliers.xml":
					persistence.importFile(file, DataType.SUPPLIER);
					break;
				}
			}
		}
	}

	private void prepareMainScreen() {
		primaryStage.setTitle("FoodStart");
		Screen screen = Screen.getPrimary();
		primaryStage.setScene(
				new Scene(rootFXML, screen.getVisualBounds().getWidth(), screen.getVisualBounds().getHeight()));
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
		primaryStage.setMaximized(true);
		
		BorderPane node = (BorderPane)rootFXML.getChildrenUnmodifiable().get(1);
		node.getChildren().clear();
		node.setCenter(createOrderFXML);
	}

	protected static Stage getPrimaryStage() {
		return primaryStage;
	}

}
