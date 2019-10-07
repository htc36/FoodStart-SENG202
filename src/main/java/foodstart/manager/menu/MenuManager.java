package foodstart.manager.menu;

import foodstart.model.menu.Menu;
import foodstart.model.menu.MenuItem;

import java.util.*;

/**
 * Acts as a controller, storing and managing the menus in the model
 */
public class MenuManager {

	/**
	 * The map of all menus modeled in the system
	 */
	private Map<Integer, Menu> menus;

	/**
	 * A buffer for putting menus in before writing them to the system
	 */
	private Map<Integer, Menu> buffer;
	
	/**
	 * Current menu to display. 
	 */
	int currentMenu;

	/**
	 * Constructs an instance of a menu manager
	 */
	public MenuManager() {
		this.menus = new HashMap<Integer, Menu>();
		this.buffer = new HashMap<Integer, Menu>();
	}

	/**
	 * Constructs and adds a menu to the set of menus
	 *
	 * @param menuItems   the menu items that are in the menu
	 * @param id          the UID of the menu
	 * @param title       the name of the menu
	 * @param description a description of the menu
	 */
	public void addMenu(Set<MenuItem> menuItems, int id, String title, String description) {
		Menu menu = new Menu(menuItems, id, title, description);
		this.menus.put(id, menu);
	}

	/**
	 * Gets a menu from the map of menus by its UID
	 *
	 * @param id the UID of the menu
	 * @return The menu that the UID refers to, or null
	 */
	public Menu getMenu(int id) {
		return this.menus.get(id);
	}

	/**
	 * Returns the map of all menus modeled
	 *
	 * @return the map of all menus modeled
	 */
	public Map<Integer, Menu> getMenus() {
		return this.menus;
	}

	/**
	 * Returns the set of menu from the ids specified
	 *
	 * @param ids the ids of the menu to fetch
	 * @return the set of menu requested
	 */
	public Set<Menu> getMenus(Collection<Integer> ids) {
		Set<Menu> items = new HashSet<Menu>();
		for (int id : ids) {
			Menu item = this.menus.get(id);
			if (item != null) {
				items.add(item);
			}
		}
		return items;
	}

	/**
	 * Returns the set of all menu stored in the map
	 *
	 * @return the set of all menu stored in the map
	 */
	public Set<Menu> getMenuSet() {
		return new HashSet<Menu>(this.menus.values());
	}

	/**
	 * Gets the current menu to display
	 * @return menu id on display, default 0
	 */
	public int getCurrentMenu() {
		return currentMenu;
	}

	/**
	 * Sets the current menu to display
	 * @param currentMenu Menu id to display
	 */
	public void setCurrentMenu(int currentMenu) {
		if (this.getMenu(currentMenu) == null) {
			throw new RuntimeException("Set displayed menu to "+currentMenu+" but such a menu does not exist");
		}
		this.currentMenu = currentMenu;
	}

	/**
	 * Removes a menu from the model
	 *
	 * @param id the id of the menu to delete
	 */
	public void removeMenu(int id) {
		this.menus.remove(id);
	}

	/**
	 * Pushes a new menu to the buffer
	 *
	 * @param menuItems   the menu items that are in the menu
	 * @param id          the UID of the menu
	 * @param title       the name of the menu
	 * @param description a description of the menu
	 */
	public void pushToBuffer(Set<MenuItem> menuItems, int id, String title, String description) {
		Menu menu = new Menu(menuItems, id, title, description);
		this.buffer.put(id, menu);
	}

	/**
	 * Writes the data in the buffer into the system
	 */
	public void writeBuffer() {
		this.menus.putAll(this.buffer);
		this.buffer.clear();
	}

	/**
	 * Generates an ID for a menu
	 * @return an ID for a menu
	 */
	public int generateNewID() {
		return menus.keySet().size() == 0 ? 0 : Collections.max(menus.keySet()) + 1;
	}

	/**
	 * Drops all data from the menu buffer
	 */
	public void dropBuffer() {
		this.buffer.clear();
	}
}
