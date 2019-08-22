package foodstart.model.menu;

/**
 * A PermanentRecipe object represents a recipe 
 */

public class PermanentRecipe extends Recipe
{
	/**
     * 
	 */
	//private String databaseId;

	/**
	 * The instructions for the recipe given as a single string.
	 */
	private String instructions;

	/**
	 * The name of the recipe as displayed in the GUI.
	 */
	private String displayName;

	/**
	 * Creates a new PermanentRecipe object
	 */
	public PermanentRecipe(){
		super();
	}

}

