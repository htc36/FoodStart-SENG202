package foodstart.ui;

import java.io.IOException;

import foodstart.manager.exceptions.IDLeadsNowhereException;
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
		loadFXMLFiles();
		prepareMainScreen();
		// TODO import XML files if they exist
		long duration = System.currentTimeMillis() - startTime;
		if (duration < 1000) {
			Thread.sleep(1000 - duration);
		}
	}

	private void loadFXMLFiles() throws IOException {
		rootFXML = FXMLLoader.load(getClass().getResource("main.fxml"));
	}

	private void prepareMainScreen() {
		primaryStage.setTitle("FoodStart");
		Screen screen = Screen.getPrimary();
		primaryStage.setScene(
				new Scene(rootFXML, screen.getVisualBounds().getWidth(), screen.getVisualBounds().getHeight()));
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
		primaryStage.setMaximized(true);
	}

	protected static Stage getPrimaryStage() {
		return primaryStage;
	}

}
