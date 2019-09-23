package foodstart.model.menu;

import foodstart.model.DietaryRequirement;
import foodstart.model.stock.Ingredient;

import java.util.Map;
import java.util.Set;


/**
 * Models a recipe that makes up a menu item in the system
 */
public abstract class Recipe {

	/**
	 * The price of that the item costs
	 */
	private float price;

	/**
	 * A map of all ingredients/amounts that are part of the recipe
	 */
	private Map<Ingredient, Integer> ingredients;

	/**
	 * Constructs an instance of the recipe class
	 *
	 * @param price       the price of the recipe
	 * @param ingredients a map of the ingredients that make up the recipe to the amount of that ingredient
	 */
	public Recipe(float price, Map<Ingredient, Integer> ingredients) {
		this.price = price;
		this.ingredients = ingredients;
	}

	/**
	 * Checks if all ingredients in recipe are safe for a specific dietary requirement
	 *
	 * @param requirement the dietary requirement to check
	 * @return boolean true if the recipe is allowed for the requirement; false otherwise
	 */
	public boolean isSafeFor(DietaryRequirement requirement) {
		Set<Ingredient> ingredients = this.ingredients.keySet();
		for (Ingredient ingredient : ingredients) {
			if (!ingredient.isSafeFor(requirement)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns true if the menu item is available
	 * Checks if all the menu items ingredients have a kitchen stock greater than 0
	 */
	public boolean isAvailable() {
		Set<Ingredient> ingredients = this.ingredients.keySet();
		for (Ingredient ingredient : ingredients) {
			if (ingredient.getKitchenStock() <= 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Gets the price of the recipe
	 *
	 * @return the price of the recipe
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * Sets the price of the recipe
     * @param price the price of the recipe
     */
	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 * Gets all ingredients that are part of the recipe
	 *
	 * @return the map of ingredients in the recipe to their quantities
	 */
	public Map<Ingredient, Integer> getIngredients() {
		return ingredients;
	}

	/**
	 * Sets all ingredients that are part of the recipe
     * @param ingredients the map of ingredients in the recipe to their quantities
     */
	public void setIngredients(Map<Ingredient, Integer> ingredients) {
		this.ingredients = ingredients;
	}

	/**
	 * Adds an ingredient to the map of ingredients in the recipe.
	 * If the ingredient is already a part of the map, the previous entry is overwritten
	 *
	 * @param ingredient the ingredient to add to the recipe
	 * @param amount     the amount of the ingredient to add
	 * @return the previous amount of the ingredient, or null if the ingredient did not exist
	 */
	public Integer addIngredient(Ingredient ingredient, Integer amount) {
		return this.ingredients.put(ingredient, amount);
	}

	/**
	 * Removes an ingredient from the map of ingredients in the recipe
	 *
	 * @param ingredient the ingredient to remove from the recipe
	 * @return the amount of the ingredient, or null if the ingredient did not exist
	 */
	public Integer removeIngredient(Ingredient ingredient) {
		return this.ingredients.remove(ingredient);
	}

	public abstract String getDisplayName();

	/**
	 * Returns the id of the recipe that this is based on
	 * @return the id of the recipe that this is based on
	 */
	public abstract int getId();
}

