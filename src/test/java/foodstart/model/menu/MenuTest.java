package foodstart.model.menu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MenuTest {

	private Menu testMenu, nullMenu;
	private Set<MenuItem> menuItems, dummyItems;
	private MenuItem testitem;
	private List<PermanentRecipe> recipes;

    @Before
    public void setUp() throws Exception {
    	menuItems = new HashSet<MenuItem>();
    	dummyItems = new HashSet<MenuItem>();
    	recipes = new ArrayList<PermanentRecipe>();
        testitem = new MenuItem(0, "TestItem", "MenuItem test item", recipes);
    	testMenu = new Menu(menuItems, 0, "Summer Menu", "Base menu for the summer months");
    	nullMenu = new Menu(null, 0, null, null);
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
    
    @Test
    public void testCloneBasic() {
        Menu copy = testMenu.clone();
        Assert.assertEquals(testMenu, copy);
    }
    
    @Test
    public void testCloneIsDeep() {
        Menu copy = testMenu.clone();
        testMenu.getMenuItems().add(testitem);
        Assert.assertNotEquals(testMenu, copy); 
    }
    
    @Test 
    public void testEqualsIfSame() {
        Object test2 = testMenu;
        assertSame(testMenu, test2);
        assertEquals(testMenu, test2);
    }
    
    @Test 
    public void testNotEqualsIfNull() {
        assertNotEquals(testMenu, null);
    }
    
    @Test 
    public void testNotEqualsIfDifferentClass() {
        Object notMenu = new String("not a menu");
        assertNotEquals(testMenu, notMenu);
    }
    
    @Test 
    public void testNotEqualIfOnlySelfNullDescription() {
        Menu other = new Menu(null, 0, null, "");
        assertNotEquals(nullMenu, other);
    }
    
    @Test 
    public void testNotEqualIfDifferentDescription() {
        Menu other = new Menu(menuItems, 0, "Summer Menu", "WAAAA");
        assertNotEquals(testMenu, other);
    }
    
    @Test 
    public void testNotEqualIfOnlySelfNullTitle() {
        Menu other = new Menu(null, 0, "", null);
        assertNotEquals(nullMenu, other);
    }
    
    @Test 
    public void testNotEqualIfDifferentTitle() {
        Menu other = new Menu(menuItems, 0, "Whipple", "Base menu for the summer months");
        assertNotEquals(testMenu, other);
    }
    
    @Test 
    public void testNotEqualIfDifferentID() {
        Menu other = new Menu(menuItems, 2, "Summer Menu", "Base menu for the summer months");
        assertNotEquals(testMenu, other);
    }
    
    @Test 
    public void testNotEqualIfOnlySelfNullItems() {
        Menu other = new Menu(dummyItems, 0, null, null);
        assertNotEquals(nullMenu, other);
    }
    
    @Test 
    public void testNotEqualIfDifferentItems() {
        dummyItems.add(testitem);
        Menu other = new Menu(dummyItems, 0, "Summer Menu", "Base menu for the summer months");
        assertNotEquals(testMenu, other);
    }
    
    @Test 
    public void testEqualsIfAllFieldsNull() {
        Menu other = new Menu(null, 0, null, null);
        assertEquals(nullMenu, other);
    }
    
    @Test 
    public void testEqualsIfAllFieldsMatch() {
        Menu other = new Menu(menuItems, 0, "Summer Menu", "Base menu for the summer months");
        assertEquals(testMenu, other);
    }
}