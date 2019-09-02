package foodstart.manager.menu;

import java.util.HashSet;
import java.util.Set;

import foodstart.model.menu.Menu;
import foodstart.model.menu.MenuItem;

public class MenuManager {

	/**
	 * The set of all menus modeled in the system
	 */
	private Set<Menu> menus;

	/**
	 * Constructs an instance of a menu manager
	 */
	public MenuManager() {
		this.menus = new HashSet<Menu>();
	}

	/**
	 * Adds a pre-constructed menu to the set of all menus
	 * @param menu the menu to add to the set of menus
	 * @return true if the menu was added; false otherwise
	 */
	public boolean addMenu(Menu menu) {
		return this.menus.add(menu);
	}

	/**
	 * Constructs and adds a menu to the set of menus
	 * @param menuItems the menu items that are in the menu
	 * @param id the UID of the menu
	 * @param title the name of the menu
	 * @param description a description of the menu
	 * @return true if the menu was added; false otherwise
	 */
	public boolean addMenu(Set<MenuItem> menuItems, int id, String title, String description) {
		Menu menu = new Menu(menuItems, id, title, description);
		return this.menus.add(menu);
	}

	/**
	 * Gets a menu from the set of menus by its UID
	 * @param id the UID of the menu
	 * @return  The menu that the UID refers to, or null
	 */
	public Menu getMenu(int id) {
		for (Menu menu : this.menus) {
			if (menu.getId() == id) {
				return menu;
			}
		}
		return null;
	}
}
