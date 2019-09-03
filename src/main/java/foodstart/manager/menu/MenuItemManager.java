package foodstart.manager.menu;

import foodstart.model.menu.MenuItem;
import foodstart.model.menu.Recipe;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Acts as a controller, storing and managing the menu items in the model
 */
public class MenuItemManager {

	/**
	 * The set of all menu items modeled in the system
	 */
	private Map<Integer, MenuItem> menuItems;

	/**
	 * Constructs an instance of a menu item manager
	 */
	public MenuItemManager() {
		this.menuItems = new HashMap<Integer, MenuItem>();
	}

	/**
	 * Constructs and adds a menu item to the set of menu items
	 * @param id the UID of the menu item
	 * @param name the name of the menu item
	 * @param description a description of the menu item
	 * @param variants a set of all recipes that make up the menu item
	 */
	public void addMenu(int id, String name, String description, Set<Recipe> variants) {
		MenuItem menuItem = new MenuItem(id, name, description, variants);
		this.menuItems.put(id, menuItem);
	}

	/**
	 * Gets a menu item from the set of menu items by its UID
	 * @param id the UID of the menu item
	 * @return The menu item that the UID refers to, or null
	 */
	public MenuItem getMenu(int id) {
		return this.menuItems.get(id);
	}

	/**
	 * Returns the set of all menu items modeled
	 * @return the set of all menu items modeled
	 */
	public Map<Integer, MenuItem> getMenus() {
		return this.menuItems;
	}
}
