package foodstart.model.menu;
import java.util.Map;

import foodstart.model.stock.Ingredient;

/**
 * A PermanentRecipe object represents a recipe 
 */
public class PermanentRecipe extends Recipe
{
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
	 * @param name the display name of the recipe
	 * @param instructions the instructions to make the recipe
	 * @param price the price of the recipe
	 * @param ingredients the ingredients that make up the recipe
	 */
	public PermanentRecipe(int id, String name, String instructions, float price, Map<Ingredient, Integer> ingredients) {
		super(price, ingredients);
		this.id = id;
		this.displayName = name;
		this.instructions = instructions;
	}

	/**
	 * Returns the instructions to make the recipe
	 * @return the instructions to make the recipe
	 */
	public String getInstructions() {
		return instructions;
	}

	/**
	 * Sets the instructions to make the recipe
	 * @param instructions the instructions to make the recipe
	 */
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	/**
	 * Returns the display name of the recipe
	 * @return the display name of the recipe
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the display name of the recipe
	 * @param displayName the display name of the recipe
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Gets the ID that this recipe is given in data files
	 * @return Unique ID of this recipe
	 */
	public int getId() {
		return id;
	}
	
}

