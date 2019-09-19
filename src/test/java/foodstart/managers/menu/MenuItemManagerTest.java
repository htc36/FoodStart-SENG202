package foodstart.managers.menu;

import foodstart.manager.menu.MenuItemManager;
import foodstart.model.Unit;
import foodstart.model.menu.MenuItem;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.stock.Ingredient;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

public class MenuItemManagerTest {

	private MenuItemManager testManager;
	private PermanentRecipe recipe1, recipeEmpty;
	private List<PermanentRecipe> recipeList;

	@Before
	public void setUp() throws Exception {
		testManager = new MenuItemManager();
		HashMap<Ingredient, Integer> ingredients1 = new HashMap<Ingredient, Integer>();
		HashMap<Ingredient, Integer> ingredients2 = new HashMap<Ingredient, Integer>();
		Ingredient ingredient1 = new Ingredient(Unit.UNITS, "ingredient1", 0, null, 10, 20);
		ingredients1.put(ingredient1, 20);
		recipe1 = new PermanentRecipe(1, "recipe1", "Create recipe one", 5, ingredients1);
		recipeEmpty = new PermanentRecipe(0, "", "", (float) 0.0, null);
		recipeList = new ArrayList<PermanentRecipe>();
		recipeList.add(recipe1);
		testManager.addMenuItem(0, "test menu item", "a menu item test", recipeList);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddMenuItem() {
		assertFalse(testManager.getMenuItems().isEmpty());
		testManager.addMenuItem(1, "test menu item", "a menu item test", recipeList);
		assertEquals(2, testManager.getMenuItems().size());
		testManager.addMenuItem(1, "Overriding item", "Overrides another item", null);
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
		testManager.addMenuItem(1, "fetchedItem", "a menu item test", recipeList);
		testManager.addMenuItem(3, "test menu item", "a menu item test", recipeList);
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
		testManager.addMenuItem(5, "TestItem5", "A test item", null);
		testManager.addMenuItem(4, "TestItem4", "A test item", null);
		testManager.addMenuItem(3, "TestItem3", "A test item", null);
		Collection<Integer> ids = new ArrayList<Integer>();
		ids.add(0);
		ids.add(3);
		Set<MenuItem> items = testManager.getMenuItems(ids);
		assertEquals(2, items.size());
	}

	@Test
	public void testGetMenuItemsInvalidId() {
		testManager.addMenuItem(5, "TestItem5", "A test item", null);
		testManager.addMenuItem(4, "TestItem4", "A test item", null);
		testManager.addMenuItem(3, "TestItem3", "A test item", null);
		Collection<Integer> ids = new ArrayList<Integer>();
		ids.add(0);
		ids.add(10);
		Set<MenuItem> items = testManager.getMenuItems(ids);
		assertEquals(1, items.size());
	}

	@Test
	public void testGetMenuItemsNoIds() {
		testManager.addMenuItem(5, "TestItem5", "A test item", null);
		testManager.addMenuItem(4, "TestItem4", "A test item", null);
		testManager.addMenuItem(3, "TestItem3", "A test item", null);
		Collection<Integer> ids = new ArrayList<Integer>();
		Set<MenuItem> items = testManager.getMenuItems(ids);
		assertEquals(0, items.size());
	}

	@Test
	public void testGetMenuItemSet() {
		assertEquals(1, testManager.getMenuItemSet().size());
		testManager.addMenuItem(1, "AnotherTestItem", "Another Testing Item", null);
		testManager.addMenuItem(3, "AnotherTestItem", "Another Testing Item", null);
		assertEquals(3, testManager.getMenuItemSet().size());
	}

	@Test
	public void testGetMenuItemSetAbuse() {
		Map<Integer, MenuItem> items = testManager.getMenuItems();
		MenuItem item = new MenuItem(1, "AbusiveMenuItem", "This should never occur in the system", null);
		items.put(1, item);
		items.put(2, item);
		assertEquals(3, items.size());
		assertEquals(2, testManager.getMenuItemSet().size());
	}

	@Ignore
	@Test
	public void testGetApproxPrice() {
		fail("Not yet implemented");
	}

}
