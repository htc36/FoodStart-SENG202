package foodstart.ui.recipebuilder;

import foodstart.model.menu.Recipe;

/**
 * Callback for when the recipe builder finishes
 *
 * @author Alex Hobson on 07/09/2019
 */
public abstract class RecipeBuilderRunnable {

	/**
	 * Called when the recipe builder finishes (the user adds to order) Recipe
	 * contains the per-item price Recipe will be null and quantity 0 if the user
	 * cancels the action
	 *
	 * @param recipe   The recipe that was selected. Can be a OnTheFlyRecipe
	 * @param quantity The quantity to add to the order
	 * @return Boolean on whether the item was able to be added to the order (should
	 * the window be closed?)
	 */
	public abstract boolean onRecipeComplete(Recipe recipe, int quantity);
}
