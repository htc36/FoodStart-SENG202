package foodstart.manager.menu;

import foodstart.model.Unit;
import foodstart.model.menu.Menu;
import foodstart.model.menu.MenuItem;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.stock.Ingredient;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class MenuItemManagerTest {

    private MenuItemManager testManager;
    private PermanentRecipe recipe1;
	private Set<PermanentRecipe> recipeList;

    @Before
    public void setUp() {
        testManager = new MenuItemManager();
        HashMap<Ingredient, Integer> ingredients1 = new HashMap<Ingredient, Integer>();
        Ingredient ingredient1 = new Ingredient(Unit.UNITS, "ingredient1", 0, null, 10, 20);
        ingredients1.put(ingredient1, 20);
        recipe1 = new PermanentRecipe(1, "recipe1", "Create recipe one", 5, ingredients1);
		recipeList = new HashSet<PermanentRecipe>();
        recipeList.add(recipe1);
		testManager.addMenuItem(0, "test menu item", "a menu item test", recipeList, recipe1);
    }

    @Test
    public void testAddMenuItem() {
        assertFalse(testManager.getMenuItems().isEmpty());
		testManager.addMenuItem(1, "test menu item", "a menu item test", recipeList, recipe1);
        assertEquals(2, testManager.getMenuItems().size());
		testManager.addMenuItem(1, "Overriding item", "Overrides another item", null, null);
        assertEquals(2, testManager.getMenuItems().size());
    }

    @Test
    public void testGetMenuItemValidId() {
        MenuItem item = testManager.getMenuItem(0);
        assertNotNull(item);
        assertEquals(0, item.getId());
    }

    @Test
    public void testGetMenuItemMultipleItems() {
		testManager.addMenuItem(1, "fetchedItem", "a menu item test", recipeList, recipe1);
		testManager.addMenuItem(3, "test menu item", "a menu item test", recipeList, recipe1);
        MenuItem item2 = testManager.getMenuItem(1);
        assertEquals("fetchedItem", item2.getName());
    }

    @Test
    public void testGetMenuItemInvalidId() {
        assertNull(testManager.getMenuItem(10));
    }

    @Test
    public void testGetMenuItemsSingleId() {
        Collection<Integer> ids = new ArrayList<Integer>();
        ids.add(0);
        Set<MenuItem> items = testManager.getMenuItems(ids);
        assertEquals(1, items.size());
    }

    @Test
    public void testGetMenuItemsMultipleIds() {
		testManager.addMenuItem(5, "TestItem5", "A test item", null, null);
		testManager.addMenuItem(4, "TestItem4", "A test item", null, null);
		testManager.addMenuItem(3, "TestItem3", "A test item", null, null);
        Collection<Integer> ids = new ArrayList<Integer>();
        ids.add(0);
        ids.add(3);
        Set<MenuItem> items = testManager.getMenuItems(ids);
        assertEquals(2, items.size());
    }

    @Test
    public void testGetMenuItemsInvalidId() {
		testManager.addMenuItem(5, "TestItem5", "A test item", null, null);
		testManager.addMenuItem(4, "TestItem4", "A test item", null, null);
		testManager.addMenuItem(3, "TestItem3", "A test item", null, null);
        Collection<Integer> ids = new ArrayList<Integer>();
        ids.add(0);
        ids.add(10);
        Set<MenuItem> items = testManager.getMenuItems(ids);
        assertEquals(1, items.size());
    }

    @Test
    public void testGetMenuItemsNoIds() {
		testManager.addMenuItem(5, "TestItem5", "A test item", null, null);
		testManager.addMenuItem(4, "TestItem4", "A test item", null, null);
		testManager.addMenuItem(3, "TestItem3", "A test item", null, null);
        Collection<Integer> ids = new ArrayList<Integer>();
        Set<MenuItem> items = testManager.getMenuItems(ids);
        assertEquals(0, items.size());
    }

    @Test
    public void testGetMenuItemSet() {
        assertEquals(1, testManager.getMenuItemSet().size());
		testManager.addMenuItem(1, "AnotherTestItem", "Another Testing Item", null, null);
		testManager.addMenuItem(3, "AnotherTestItem", "Another Testing Item", null, null);
        assertEquals(3, testManager.getMenuItemSet().size());
    }

    @Test
    public void testGetMenuItemSetAbuse() {
        Map<Integer, MenuItem> items = testManager.getMenuItems();
		MenuItem item = new MenuItem(1, "AbusiveMenuItem", "This should never occur in the system", null, null);
        items.put(1, item);
        items.put(2, item);
        assertEquals(3, items.size());
        assertEquals(2, testManager.getMenuItemSet().size());
    }

    @Test
    public void testGetApproxPriceSingleItem() {
        assertEquals(5, testManager.getApproxPrice(0), 2e-1);
    }

    @Test
    public void testGetApproxPriceMultipleItems() {
		Set<PermanentRecipe> recipes = new HashSet<PermanentRecipe>();
        recipes.add(new PermanentRecipe(0, "recipe0", "blank", 1, null));
        recipes.add(new PermanentRecipe(1, "recipe1", "blank", (float) 2.5, null));
        recipes.add(new PermanentRecipe(2, "recipe2", "blank", 3, null));
        recipes.add(new PermanentRecipe(3, "recipe3", "blank", (float) 4.34, null));
		testManager.addMenuItem(1, "TestItem", "blank", recipes, recipe1);
		assertEquals(recipe1.getPrice(), testManager.getApproxPrice(1), 1e-100);
    }

    @Test
    public void testPushToBuffer() {
        testManager.getMenuItems().clear();
        MenuItem expected = new MenuItem(0, "test menu item", "a menu item test", recipeList, recipe1); 
        testManager.pushToBuffer(0, "test menu item", "a menu item test", recipeList, recipe1);  
        testManager.writeBuffer();
        assertTrue(testManager.getMenuItems().containsValue(expected));
    }
    
    @Test
    public void testDropBuffer() {
        testManager.getMenuItems().clear();
        testManager.pushToBuffer(0, "test menu item", "a menu item test", recipeList, recipe1);  
        testManager.dropBuffer();
        testManager.writeBuffer();
        assertTrue(testManager.getMenuItems().isEmpty());
    }
    
    @Test
    public void testGenerateIDNormal() {
        assertEquals(1, testManager.generateNewId());
    }
    
    @Test
    public void testGenerateIDEmpty() {
        testManager.getMenuItems().clear();
        assertEquals(0, testManager.generateNewId());
    }
}
