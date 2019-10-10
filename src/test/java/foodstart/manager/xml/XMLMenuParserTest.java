package foodstart.manager.xml;

import foodstart.manager.Managers;
import foodstart.manager.Persistence;
import foodstart.model.DataType;
import foodstart.model.menu.MenuItem;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.stock.Ingredient;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class XMLMenuParserTest {

	Persistence persistence;
	
	@BeforeClass
	public static void setupRecipes() {
		Managers.getRecipeManager().addRecipe(1000, "Burger", "Test instructions", 5.00F, new HashMap<Ingredient, Integer>());
		Managers.getRecipeManager().addRecipe(1001, "Grass burger", "Test instructions", 8.00F, new HashMap<Ingredient, Integer>());
	}
	
	@Before
	public void setUp() throws Exception {
		persistence = new XMLPersistence();
		persistence.importFile(new File("resources/data/TestMenu1.xml"), DataType.MENU);
		Managers.writeBuffer();
	}

	@Test
	public void testMenuExists() {
		assertTrue("Menu with ID 0 should exist", Managers.getMenuManager().getMenu(0) != null);
	}
	
	@Test
	public void testMenuTitle() {
		assertEquals("Menu with ID 0 should have a name", "Test title", Managers.getMenuManager().getMenu(0).getTitle());
	}
	
	@Test
	public void testMenuDescription() {
		assertEquals("Menu with ID 0 should have a description", "Test description", Managers.getMenuManager().getMenu(0).getDescription());
	}
	
	@Test
	public void testMenuItems() {
		assertEquals("Menu with ID 0 should have an item", 1, Managers.getMenuManager().getMenu(0).getMenuItems().size());
		MenuItem item = Managers.getMenuManager().getMenu(0).getMenuItems().iterator().next();
		assertEquals("Should have an item with ID 0", 0, item.getId());
		assertEquals("Should have an item with name 'Boring Burger'", "Boring Burger", item.getName());
		assertEquals("Should have an item with description 'Test burger'", "Test burger", item.getDescription());

		Set<PermanentRecipe> variants = item.getVariants();
		assertEquals("Item should have 2 recipes", 2, variants.size());
		assertTrue("Item should have a recipe with ID 1000", variants.stream().anyMatch(variant -> variant.getId() == 1000));
		assertTrue("Item should have a recipe with ID 1001", variants.stream().anyMatch(variant -> variant.getId() == 1000));
	}
	
	@Test
	public void testImportMultipleMenus() {
		persistence.importFile(new File("resources/data/TestMenu2.xml"), DataType.MENU);
		Managers.writeBuffer();
		assertTrue("Menu with ID 1 should exist", Managers.getMenuManager().getMenu(1) != null);
		assertTrue("Menu with ID 2 should exist", Managers.getMenuManager().getMenu(2) != null);
	}
}
