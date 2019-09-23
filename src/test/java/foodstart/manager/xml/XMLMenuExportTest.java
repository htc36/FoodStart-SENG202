package foodstart.manager.xml;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import foodstart.manager.menu.MenuManager;
import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;
import foodstart.model.menu.MenuItem;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.stock.Ingredient;

public class XMLMenuExportTest {
	
	MenuManager manager;
	XMLMenuParser exporter;
	Document doc = null;

	@Before
	public void setup() throws Exception {
		manager = new MenuManager();
		exporter = new XMLMenuParser();

		DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		dBuilder.setErrorHandler(new SAXJUnitTestErrorHandler());
		doc = dBuilder.newDocument();
		
		Ingredient pickle = new Ingredient(Unit.UNITS, "Pickle", 1234, new HashMap<DietaryRequirement, Boolean>(), 1, 1);
		HashMap<Ingredient, Integer> pickleMap = new HashMap<Ingredient, Integer>();
		pickleMap.put(pickle, 2);
		PermanentRecipe recipe = new PermanentRecipe(5678, "Pickled Pickle", "Pickle the pickle", 1.5f, pickleMap);
		ArrayList<PermanentRecipe> recipeList = new ArrayList<PermanentRecipe>();
		recipeList.add(recipe);
		MenuItem menuItem = new MenuItem(9012, "A Pickled pickle", "TestDescription", recipeList);
		HashSet<MenuItem> items = new HashSet<MenuItem>();
		items.add(menuItem);
		
		manager.addMenu(items, 3456, "Pickle Menu", "TestDescription2");
		
		exporter.exportWithManager(doc, manager);
	}
	
	@Test
	public void testExportOneMenuIdCorrect() {
		Element rootNode = (Element) doc.getElementsByTagName("menus").item(0);
		Element menuNode = (Element) rootNode.getElementsByTagName("menu").item(0);
		
		assertEquals("3456", menuNode.getElementsByTagName("menu_id").item(0).getTextContent());
	}
	
	@Test
	public void testExportOneMenuTitleCorrect() {
		Element rootNode = (Element) doc.getElementsByTagName("menus").item(0);
		Element menuNode = (Element) rootNode.getElementsByTagName("menu").item(0);
		
		assertEquals("Pickle Menu", menuNode.getElementsByTagName("title").item(0).getTextContent());
	}
	
	@Test
	public void testExportOneMenuDescriptionCorrect() {
		Element rootNode = (Element) doc.getElementsByTagName("menus").item(0);
		Element menuNode = (Element) rootNode.getElementsByTagName("menu").item(0);
		
		assertEquals("TestDescription2", menuNode.getElementsByTagName("description").item(0).getTextContent());
	}
	
	@Test
	public void testExportOneMenuItemsCorrect() {
		Element rootNode = (Element) doc.getElementsByTagName("menus").item(0);
		Element menuNode = (Element) rootNode.getElementsByTagName("menu").item(0);
		Element itemsNode = (Element) menuNode.getElementsByTagName("items").item(0);
		Element itemNode = (Element) itemsNode.getElementsByTagName("item").item(0);
		Element recipesNode = (Element) itemNode.getElementsByTagName("recipes").item(0);
		
		assertEquals("9012", itemNode.getElementsByTagName("item_id").item(0).getTextContent());
		assertEquals("A Pickled pickle", itemNode.getElementsByTagName("name").item(0).getTextContent());
		assertEquals("TestDescription", itemNode.getElementsByTagName("item_description").item(0).getTextContent());
		
		assertEquals("5678", recipesNode.getElementsByTagName("recipe_id").item(0).getTextContent());
	}
}
