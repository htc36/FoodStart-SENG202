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


	

}

