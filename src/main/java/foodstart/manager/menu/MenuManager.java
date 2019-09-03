package foodstart.manager.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import foodstart.model.menu.Menu;
import foodstart.model.menu.MenuItem;

/**
 * Acts as a controller, storing and managing the menus in the model
 */
public class MenuManager {

	/**
	 * The set of all menus modeled in the system
	 */
	private Map<Integer, Menu> menus;

	/**
	 * Constructs an instance of a menu manager
	 */
	public MenuManager() {
		this.menus = new HashMap<Integer, Menu>();
	}

	/**
	 * Constructs and adds a menu to the set of menus
	 * @param menuItems the menu items that are in the menu
	 * @param id the UID of the menu
	 * @param title the name of the menu
	 * @param description a description of the menu
	 */
	public void addMenu(Set<MenuItem> menuItems, int id, String title, String description) {
		Menu menu = new Menu(menuItems, id, title, description);
		this.menus.put(id, menu);
	}

	/**
	 * Gets a menu from the set of menus by its UID
	 * @param id the UID of the menu
	 * @return The menu that the UID refers to, or null
	 */
	public Menu getMenu(int id) {
		return this.menus.get(id);
	}

	/**
	 * Returns the set of all menus modeled
	 * @return the set of all menus modeled
	 */
	public Map<Integer, Menu> getMenus() {
		return this.menus;
	}
}
