package foodstart.manager.menu;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import foodstart.model.menu.MenuItem;
import foodstart.model.menu.PermanentRecipe;

/**
 * Acts as a controller, storing and managing the menu items in the model
 */
public class MenuItemManager {

	/**
	 * The map of all menu items modeled in the system
	 */
	private Map<Integer, MenuItem> menuItems;

	/**
	 * Constructs an instance of a menu item manager
	 */
	public MenuItemManager() {
		this.menuItems = new HashMap<Integer, MenuItem>();
	}

	/**
	 * Constructs and adds a menu item to the map of menu items
	 * @param id the UID of the menu item
	 * @param name the name of the menu item
	 * @param description a description of the menu item
	 * @param variants a set of all recipes that make up the menu item
	 */
	public void addMenuItem(int id, String name, String description, Set<PermanentRecipe> variants) {
		MenuItem menuItem = new MenuItem(id, name, description, variants);
		this.menuItems.put(id, menuItem);
	}

	/**
	 * Gets a menu item from the map of menu items by its UID
	 * @param id the UID of the menu item
	 * @return The menu item that the UID refers to, or null
	 */
	public MenuItem getMenuItem(int id) {
		return this.menuItems.get(id);
	}

	/**
	 * Returns the map of all menu items modeled
	 * @return the map of all menu items modeled
	 */
	public Map<Integer, MenuItem> getMenuItems() {
		return this.menuItems;
	}

	/**
	 * Returns the set of menu items from the ids specified
	 * @param ids the ids of the menu items to fetch
	 * @return the set of menu items requested
	 */
	public Set<MenuItem> getMenuItems(Collection<Integer> ids) {
		Set<MenuItem> items = new HashSet<MenuItem>();
		for (int id : ids) {
			MenuItem item = this.menuItems.get(id);
			if (item != null) {
				items.add(item);
			}
		}
		return items;
	}

	/**
	 * Returns the set of all menu items stored in the map
	 * @return the set of all menu items stored in the map
	 */
	public Set<MenuItem> getMenuItemSet() {
		Set<MenuItem> menuItemsSet = new HashSet<MenuItem>(this.menuItems.values());
		return menuItemsSet;
	}
}
