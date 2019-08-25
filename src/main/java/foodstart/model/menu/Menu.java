package foodstart.model.menu;
import java.util.Set;


/**
 * Models a menu in the system
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
	public Set<MenuItem> getMenuItems() {
		return menuItems;
	}
	/**
	 * Returns name of menu
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the menu items that make up the menu
	 * @param menuItems the menu items that make up the menu
	 */
	public void setMenuItems(Set<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	/**
	 * Sets the name of the menu
	 * @param name the name of the menu
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Adds a menu item to the set of menu items in the menu
	 * @param item the menu item to add
	 * @return true if the item was added to the menu; false otherwise
	 */
	public boolean addMenuItem(MenuItem item) {
		boolean added;
		added = this.menuItems.add(item);
		return added;
	}

	/**
	 * Removes a menu item from the set of menu items in the menu
	 * @param item the menu item to remove from the menu
	 * @return true if the item was removed; false otherwise
	 */
	public boolean removeMenuItem(MenuItem item) {
		boolean removed;
		removed = this.menuItems.remove(item);
		return removed;
	}
}

