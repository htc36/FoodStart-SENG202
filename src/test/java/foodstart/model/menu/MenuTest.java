package foodstart.model.menu;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import foodstart.model.menu.MenuItem;

import java.util.HashSet;
import java.util.Set;

public class MenuTest {

	private Menu testMenu;
	private Set<MenuItem> menuItems;
	private MenuItem testitem;
	private Set<Recipe> recipes;

    @Before
    public void setUp() throws Exception {
    	menuItems = new HashSet<MenuItem>();
    	recipes = new HashSet<Recipe>();
        testitem = new MenuItem(0, "TestItem", "MenuItem test item", recipes);
    	testMenu = new Menu(menuItems, 0, "Summer Menu", "Base menu for the summer months");
    }

    @Test
    public void getId() {
    	assertEquals(0 , testMenu.getId());
    }

    @Test
    public void getTitle() {
    	assertEquals("Summer Menu", testMenu.getTitle());
    }

    @Test
    public void getDescription() {
    	assertEquals("Base menu for the summer months" , testMenu.getDescription());
    }

    @Test
    public void getMenuItems() {
    	assertEquals(menuItems, testMenu.getMenuItems());
    }

    @Test
    public void setMenuItems() {
        menuItems.add(testitem);
    	testMenu.setMenuItems(menuItems);
    	assertEquals(menuItems, testMenu.getMenuItems());
    }

    @Test
    public void addMenuItem() {
    	assertEquals(true, testMenu.addMenuItem(testitem));
    }

    @Test
    public void removeMenuItem() {
    	testMenu.addMenuItem(testitem);
    	assertEquals(true, testMenu.removeMenuItem(testitem));
    }
}