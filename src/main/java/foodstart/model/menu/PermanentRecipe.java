package foodstart.model.menu;

import foodstart.model.stock.Ingredient;

import java.util.HashMap;
import java.util.Map;

/**
 * A PermanentRecipe object represents a recipe
 */
public class PermanentRecipe extends Recipe {
	/**
	 * The ID that this recipe is given in data files
	 */
	private int id;

	/**
	 * The instructions for the recipe given as a single string.
	 */
	private String instructions;

	/**
	 * The name of the recipe as displayed in the GUI.
	 */
	private String displayName;

	/**
	 * Constructs an instance of a permanent recipe
	 *
	 * @param id the id of the recipe
     * @param name         the display name of the recipe
	 * @param instructions the instructions to make the recipe
	 * @param price        the price of the recipe
	 * @param ingredients  the ingredients that make up the recipe
	 */
	public PermanentRecipe(int id, String name, String instructions, float price, Map<Ingredient, Integer> ingredients) {
		super(price, ingredients);
		this.id = id;
		this.displayName = name;
		this.instructions = instructions;
	}

	/**
	 * Returns the instructions to make the recipe
	 *
	 * @return the instructions to make the recipe
	 */
	public String getInstructions() {
		return instructions;
	}

	/**
	 * Sets the instructions to make the recipe
	 *
	 * @param instructions the instructions to make the recipe
	 */
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	/**
	 * Returns the display name of the recipe
	 *
	 * @return the display name of the recipe
	 */
	@Override
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the display name of the recipe
	 *
	 * @param displayName the display name of the recipe
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Gets the ID that this recipe is given in data files
	 *
	 * @return Unique ID of this recipe
	 */
	@Override
	public int getId() {
		return id;
	}

	/**
	 * Returns a clone of the recipe
	 *
	 * @return a clone of the recipe
	 */
	public PermanentRecipe clone() {
		Map<Ingredient, Integer> newIngredients = new HashMap<Ingredient, Integer>(getIngredients());
		return new PermanentRecipe(id, displayName, instructions, getPrice(), newIngredients);
	}

	/**
	 * Returns true if the given object has the same parameters
	 * @param obj the object to check against
	 * @return true if the objects have equal parameters; false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PermanentRecipe other = (PermanentRecipe) obj;
		if (displayName == null) {
			if (other.displayName != null) {
				return false;
			}
		} else if (!displayName.equals(other.displayName)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (instructions == null) {
			if (other.instructions != null) {
				return false;
			}
		} else if (!instructions.equals(other.instructions)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the hash code of the recipe
	 * @return the hash code of the recipe
	 */
	@Override
	public int hashCode() {
		return ((Integer) this.id).hashCode() + ((Float) this.price).hashCode() +
				((this.displayName == null) ? 0 : this.displayName.hashCode()) +
				((this.instructions == null) ? 0 : this.instructions.hashCode()) +
				((super.ingredients == null) ? 0 : this.ingredients.hashCode());
	}
	
	
}

