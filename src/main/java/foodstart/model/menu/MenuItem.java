package foodstart.model.menu;

import java.util.List;


/**
 * Models a menu item in the system
 */
public class MenuItem {
	/**
	 * The unique identifier for each menu item
	 */
	private int id;

	/**
	 * The name of the menu item
	 */
	private String name;

	/**
	 * The description of the menu item
	 */
	private String description;

	/**
	 * A set containing all the recipe variants of a menu item
	 */
	private List<PermanentRecipe> variants;

	/**
	 * Constructs an instance of a menu item
	 *
	 * @param databaseId  the UID of the menu item
	 * @param name        the name of the menu item
	 * @param description a description of the menu item
	 * @param variants    a set of all recipes that make up the menu item
	 */
	public MenuItem(int databaseId, String name, String description, List<PermanentRecipe> variants) {
		this.id = databaseId;
		this.name = name;
		this.description = description;
		this.variants = variants;
	}

	/**
	 * Gets the data base ID
	 *
	 * @return databaseId
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the data base ID
	 */
	public void setDatabaseId(int id) {
		this.id = id;
	}

	/**
	 * Gets the name of the menu item
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the menu item
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the description of the menu item
	 *
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the menu item
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the different variants of the menu item
	 *
	 * @return variants
	 */
	public List<PermanentRecipe> getVariants() {
		return variants;
	}

	/**
	 * Sets the variants of the menu item
	 */
	public void setVariants(List<PermanentRecipe> variants) {
		this.variants = variants;
	}


}

