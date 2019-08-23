package foodstart.model.menu;
import java.util.Map;

import foodstart.model.DietaryRequirement;
import foodstart.model.stock.Ingredient;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public abstract class Recipe
{

	/**
         * The price of that the item costs
	 */
	
	private float price;

	/**
         * A map of all ingrediants/amounts that are part of the recipe
	 */
	
	private Map<Ingredient, Integer> ingredients;

	/**
         * Constructor class for recipe
	 */
	public Recipe(float price, Map<Ingredient, Integer> ingredients) {
		this.price = price;
		this.ingredients = ingredients;
	}

	/**
	 * Checks if all ingredients in recipe are safe for a specific dietary requirement which is given as a parameter
	 * @param requirement
	 * @return boolean 
	 */
	
	public boolean isSafeFor(DietaryRequirement requirement) {
		// TODO implement me
		return false;
	}


	/**
	 * Returns true if the menu item is available
	 * Checks if all the menu items ingredients have a kitchen stock greater than 0
	 */
	
	public boolean isAvailable() {
		return false;
	}

	/**
	 * Gets the price of the recipe
         * @return price
	 */

	public float getPrice() {
		return price;
	}

        /**
	 * Sets the price of the recipe
         */

	public void setPrice(float price) {
		this.price = price;
	}

        /**
	 * Gets all ingrediants that are part of the recipe
         * @return ingrediants
         */

	public Map<Ingredient, Integer> getIngredients() {
		return ingredients;
	}

        /**
	 * Sets all ingrediants that are part of the recipe
         */

	public void setIngredients(Map<Ingredient, Integer> ingredients) {
		this.ingredients = ingredients;
	}

}

