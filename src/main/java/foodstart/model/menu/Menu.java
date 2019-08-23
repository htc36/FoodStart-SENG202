package foodstart.model.menu;
import java.util.Set;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Menu
{
	/**
	 * A set to keep all the menu items in
	 */
	
	private Set<MenuItem> menuItems;

	/**
	 * The name of the menu
	 */
	
	private String name;

	/**
	 * Constructor for menu class
	 */
	public Menu(Set<MenuItem> menuItems, String name) {
		this.menuItems = menuItems;
		this.name = name;
	}
	/**
	 * Returns a set of all menu items
         * @return menuItems
	 */
	public Set<MenuItem> getMenuItem() {
		return menuItems;
	}
	/**
	 * Returns name of menu
         * @return name
	 */

	public String getName() {
		return name;
	}



}

