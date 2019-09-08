package foodstart.model.menu;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class MenuTest {

	private Menu testMenu;
	private Set<MenuItem> menuItems;
	private MenuItem testitem;
	private List<PermanentRecipe> recipes;

    @Before
    public void setUp() throws Exception {
    	menuItems = new HashSet<MenuItem>();
    	recipes = new ArrayList<PermanentRecipe>();
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