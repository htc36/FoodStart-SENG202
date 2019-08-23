package foodstart.model.menu;
import java.util.Set;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class MenuItem
{
	/**
	 * The unique identifier for each menu item
	 */
	
	private String databaseId;

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
	
	private Set<Recipe> variants;

	/**
	 * Constructor for MenuItem class
	 */

	public MenuItem(String databaseId, String name, String description, Set<Recipe> variants) {
		this.databaseId = databaseId;
		this.name = name;
		this.description = description;
		this.variants = variants;
	}
	
	/**
	 * Gets the data base ID
	 * @return databaseId
	 */

	public String getDatabaseId() {
		return databaseId;
	}
	
	/**
	 * Sets the data base ID
	 */

	public void setDatabaseId(String databaseId) {
		this.databaseId = databaseId;
	}
	
	/**
	 * Gets the name of the menu item
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
	 * @return variants
	 */

	public Set<Recipe> getVariants() {
		return variants;
	}
	
	/**
	 * Sets the variants of the menu item
	 */

	public void setVariants(Set<Recipe> variants) {
		this.variants = variants;
	}


	

}

