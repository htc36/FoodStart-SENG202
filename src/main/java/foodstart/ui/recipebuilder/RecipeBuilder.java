package foodstart.ui.recipebuilder;

import java.io.IOException;
import java.util.Map;

import foodstart.model.menu.MenuItem;
import foodstart.model.menu.OnTheFlyRecipe;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.stock.Ingredient;
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
	 * JavaFX stage for this builder
	 */
	Stage stage;

	/**
	 * Constructor for RecipeBuilder. Calling the constructor will cause the window
	 * to be opened to customise the recipe
	 * 
	 * @param baseItem
	 *            Menu item that the user clicked on to customise
	 * @param callback
	 *            What should be called when the user is finished
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

		this.controller = ((RecipeBuilderController) loader.getController());
		this.controller.setRecipeBuilder(this);

		Scene scene = new Scene(loader.getRoot());
		stage = new Stage();
		stage.setTitle("Customise Item");
		stage.setScene(scene);
		stage.show();
		
		stage.setOnCloseRequest((event) -> {
			cancel();
		});

		this.controller.populateFields(baseItem);
	}

	public void cancel() {
		stage.hide();
		this.callback.onRecipeComplete(null, 0);
	}

	/**
	 * When the customisation process is finished, this method should be called to finialise it
	 * @param isEdited Whether the order was changed from the permanentrecipe variant
	 * @param variant The original recipe this is based on
	 * @param ingredients The ingredients (which might be changed)
	 * @param quantity Quantity of this item to add to the order
	 * @param price The (per unit) price of this item
	 */
	public void addToOrder(boolean isEdited, PermanentRecipe variant, Map<Ingredient, Integer> ingredients,
			int quantity, float price) {
		Recipe recipe = variant;
		if (isEdited && (!ingredients.equals(variant.getIngredients()) || price != variant.getPrice())) {
			//the order could be the same if, for example, the price was changed then changed back
			recipe = new OnTheFlyRecipe(variant, ingredients, price);
		}
		if (this.callback.onRecipeComplete(recipe, quantity)) {
			stage.hide();
		}
	}
}