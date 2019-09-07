package foodstart.ui.recipebuilder;

import java.io.IOException;

import foodstart.model.menu.MenuItem;
import foodstart.ui.FXExceptionDisplay;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Everything to do with the new window that lets you customise a recipe before
 * adding it to the order
 * 
 * @author Alex Hobson
 * @date 07/09/2019
 */
public class RecipeBuilder {

	/**
	 * The menu item that this recipe builder is based on
	 */
	private final MenuItem baseItem;
	
	/**
	 * The callback that should be called on completion
	 */
	private final RecipeBuilderRunnable callback;
	
	/**
	 * The JavaFX controller of the window this class spawns
	 */
	private final RecipeBuilderController controller;
	
	/**
	 * Constructor for RecipeBuilder. Calling the constructor will cause the window
	 * to be opened to customise the recipe
	 * @param baseItem Menu item that the user clicked on to customise
	 * @param callback What should be called when the user is finished
	 */
	public RecipeBuilder(MenuItem baseItem, RecipeBuilderRunnable callback) {
		this.baseItem = baseItem;
		this.callback = callback;
		
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../customiseitem.fxml"));
		try {
			loader.load();
		} catch (IOException e) {
			FXExceptionDisplay.showException(e, false);
		}
		
		this.controller = ((RecipeBuilderController)loader.getController());
		this.controller.setRecipeBuilder(this);
		this.controller.populateFields(baseItem);
		
		Scene scene = new Scene(loader.getRoot());
		Stage stage = new Stage();
		stage.setTitle("Customise Item");
		stage.setScene(scene);
		stage.show();
	}
}
