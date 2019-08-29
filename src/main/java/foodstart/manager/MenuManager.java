package foodstart.manager;

import java.util.Set;

import foodstart.model.menu.Menu;

public class MenuManager {
	
	private Set<Menu> menus;
	
	public void addMenu(Menu menu) {
		menus.add(menu);
	}
}
