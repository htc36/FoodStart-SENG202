package foodstart.manager.menu;

import foodstart.model.menu.PermanentRecipe;
import foodstart.model.stock.Ingredient;

import java.util.HashMap;
import java.util.Map;

public class RecipeManager {

	/**
	 * The set of all permanent recipes modeled
	 */
	private Map<Integer, PermanentRecipe> recipes;

	/**
	 * Constructs an instance of a recipe manager
	 */
	public RecipeManager() {
		this.recipes = new HashMap<Integer, PermanentRecipe>();
	}

	/**
	 * Returns the set of all permanent recipes modeled
	 * @return the set of all permanent recipes modeled
	 */
	public Map<Integer, PermanentRecipe> getRecipes() {
		return this.recipes;
	}

	/**
	 * Adds a pre-constructed permanent recipe to the set of recipes
	 * @param recipe the permanent recipe to add to the set
	 */
	public void addRecipe(PermanentRecipe recipe) {
		this.recipes.put(recipe.getId(), recipe);
	}

	/**
	 * Constructs and adds a permanent recipe to the set of recipes
	 * @param id the UID of the recipe
	 * @param name the display name of the recipe
	 * @param instructions the instructions to make the recipe
	 * @param price the price of the recipe
	 * @param ingredients the ingredients that make up the recipe
	 * @return true if the recipe was added to the set; false otherwise
	 */
	public void addRecipe(int id, String name, String instructions, float price, Map<Ingredient, Integer> ingredients) {
		PermanentRecipe recipe = new PermanentRecipe(id, name, instructions, price, ingredients);
		this.recipes.put(id, recipe);
	}

	/**
	 * Gets a recipe by its id, or null if the recipe is not defined
	 * @param id The unique recipe ID
	 * @return The recipe that the UID refers to, or null
	 */
	public PermanentRecipe getRecipe(int id) {
		return this.recipes.get(id);
	}
}
