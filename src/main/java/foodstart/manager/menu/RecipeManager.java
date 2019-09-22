package foodstart.manager.menu;

import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.stock.Ingredient;

import java.util.*;

/**
 * Acts as a controller, storing and managing the recipes in the model
 */
public class RecipeManager {

	/**
	 * The map of all permanent recipes modeled
	 */
	private Map<Integer, PermanentRecipe> recipes;

	/**
	 * Constructs an instance of a recipe manager
	 */
	public RecipeManager() {
		this.recipes = new HashMap<Integer, PermanentRecipe>();
	}

	/**
	 * Returns the map of all permanent recipes modeled
	 *
	 * @return the map of all permanent recipes modeled
	 */
	public Map<Integer, PermanentRecipe> getRecipes() {
		return this.recipes;
	}

	/**
	 * Constructs and adds a permanent recipe to the map of recipes
	 *
	 * @param id           the UID of the recipe
	 * @param name         the display name of the recipe
	 * @param instructions the instructions to make the recipe
	 * @param price        the price of the recipe
	 * @param ingredients  the ingredients that make up the recipe
	 */
	public void addRecipe(int id, String name, String instructions, float price, Map<Ingredient, Integer> ingredients) {
		PermanentRecipe recipe = new PermanentRecipe(id, name, instructions, price, ingredients);
		this.recipes.put(id, recipe);
	}

	/**
	 * Gets a recipe by its id, or null if the recipe is not defined
	 *
	 * @param id The unique recipe ID
	 * @return The recipe that the UID refers to, or null
	 */
	public PermanentRecipe getRecipe(int id) {
		return this.recipes.get(id);
	}

	/**
	 * Gets a recipe by its display name, or null if the recipe is not defined
	 *
	 * @param displayName The unique display name of the recipe
	 * @return recipe The permanent recipe that the name refers to, or null
	 */
	public PermanentRecipe getRecipeByDisplayName(String displayName) {
		for (PermanentRecipe recipe : getRecipes().values()) {
			if (recipe.getDisplayName().equals(displayName)) {
				return recipe;
			}
		}
		return null;
	}

	/**
	 * Returns the set of recipes from the ids specified
	 *
	 * @param ids the ids of the recipes to fetch
	 * @return items The set of recipes requested
	 */
	public Set<PermanentRecipe> getRecipes(Collection<Integer> ids) {
		Set<PermanentRecipe> items = new HashSet<PermanentRecipe>();
		for (int id : ids) {
			PermanentRecipe item = this.recipes.get(id);
			if (item != null) {
				items.add(item);
			}
		}
		return items;
	}

	/**
	 * Returns the set of all recipes stored in the map
	 *
	 * @return recipeSet The set of all recipes stored in the map
	 */
	public Set<PermanentRecipe> getRecipeSet() {
		return new HashSet<PermanentRecipe>(this.recipes.values());
	}

	/**
	 * Returns the ingredients and their truck stock quantity as a string
	 *
	 * @param id The id of the recipe
	 * @return out The ingredients and their truck stock quantity as a string
	 */
	public String getIngredientsAsString(int id) {
		PermanentRecipe recipe = this.recipes.get(id);
		if (recipe == null) {
			return "";
		}
		String out = "";
		for (Ingredient ingredient : recipe.getIngredients().keySet()) {
			out = out.concat(String.format("%dx %s ", ingredient.getTruckStock(), ingredient.getName()));
		}
		return out;
	}

	/**
	 * Returns the ingredients and their truck stock quantity as a string.
	 * This version is intended for use when unable to differentiate between permanent and on the fly recipes
	 *
	 * @param recipe The recipe to get the ingredients of
	 * @return out The ingredients and their truck stock quantity as a string
	 */
	public String getIngredientsAsString(Recipe recipe) {
		if (recipe == null) {
			return "";
		}
		String out = "";
		for (Ingredient ingredient : recipe.getIngredients().keySet()) {
			out = out.concat(String.format("%s, ", ingredient.getName()));
		}
		return out;
	}
}
