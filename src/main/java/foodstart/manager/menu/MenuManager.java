package foodstart.manager.menu;

import java.util.HashSet;
import java.util.Set;

import foodstart.model.menu.Menu;

public class MenuManager {

	/**
	 * The set of all menus modeled in the system
	 */
	private Set<Menu> menus;

	public MenuManager() {
		this.menus = new HashSet<Menu>();
	}
	
	public void addMenu(Menu menu) {
		this.menus.add(menu);
	}
}
