package foodstart.manager.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import foodstart.manager.stock.IngredientManager;
import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;

public class XMLIngredientExportTest {
	
	IngredientManager manager;
	XMLIngredientParser exporter;
	Document doc = null;

	@Before
	public void setup() throws Exception {
		manager = new IngredientManager();
		exporter = new XMLIngredientParser();

		DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		dBuilder.setErrorHandler(new SAXJUnitTestErrorHandler());
		doc = dBuilder.newDocument();
	}
	
	@Test
	public void testExportOneIngredientIdCorrect() {
		HashMap<DietaryRequirement, Boolean> safeForMap = new HashMap<DietaryRequirement, Boolean>();
		manager.addIngredient(Unit.GRAMS, "Pickle", 0, safeForMap, 1000, 1000);
		
		exporter.exportWithManager(doc, manager);
		
		Element rootNode = (Element) doc.getElementsByTagName("ingredients").item(0);
		Element ingredientNode = (Element) rootNode.getElementsByTagName("ingredient").item(0);
		
		assertEquals("0", ingredientNode.getElementsByTagName("id").item(0).getTextContent());
	}
	
	@Test
	public void testExportOneIngredientNameCorrect() {
		HashMap<DietaryRequirement, Boolean> safeForMap = new HashMap<DietaryRequirement, Boolean>();
		manager.addIngredient(Unit.GRAMS, "Pickle", 0, safeForMap, 1000, 1000);
		
		exporter.exportWithManager(doc, manager);
		
		Element rootNode = (Element) doc.getElementsByTagName("ingredients").item(0);
		Element ingredientNode = (Element) rootNode.getElementsByTagName("ingredient").item(0);
		
		assertEquals("Pickle", ingredientNode.getElementsByTagName("name").item(0).getTextContent());
	}
	
	@Test
	public void testExportOneIngredientTruckStockCorrect() {
		HashMap<DietaryRequirement, Boolean> safeForMap = new HashMap<DietaryRequirement, Boolean>();
		manager.addIngredient(Unit.GRAMS, "Pickle", 0, safeForMap, 1000, 10000);
		
		exporter.exportWithManager(doc, manager);
		
		Element rootNode = (Element) doc.getElementsByTagName("ingredients").item(0);
		Element ingredientNode = (Element) rootNode.getElementsByTagName("ingredient").item(0);
		
		assertEquals("10000", ingredientNode.getElementsByTagName("truck_stock").item(0).getTextContent());
	}
	
	@Test
	public void testExportOneIngredientKitchenStockCorrect() {
		HashMap<DietaryRequirement, Boolean> safeForMap = new HashMap<DietaryRequirement, Boolean>();
		manager.addIngredient(Unit.GRAMS, "Pickle", 0, safeForMap, 1000, 10000);
		
		exporter.exportWithManager(doc, manager);
		
		Element rootNode = (Element) doc.getElementsByTagName("ingredients").item(0);
		Element ingredientNode = (Element) rootNode.getElementsByTagName("ingredient").item(0);
		
		assertEquals("1000", ingredientNode.getElementsByTagName("kitchen_stock").item(0).getTextContent());
	}
	
	@Test
	public void testExportOneIngredientDietaryCorrect() {
		HashMap<DietaryRequirement, Boolean> safeForMap = new HashMap<DietaryRequirement, Boolean>();
		
		safeForMap.put(DietaryRequirement.VEGAN, true);
		
		manager.addIngredient(Unit.GRAMS, "Pickle", 0, safeForMap, 1000, 10000);
		
		exporter.exportWithManager(doc, manager);
		
		Element rootNode = (Element) doc.getElementsByTagName("ingredients").item(0);
		Element ingredientNode = (Element) rootNode.getElementsByTagName("ingredient").item(0);
		
		Element dietaryNode = (Element) ingredientNode.getElementsByTagName("dietary").item(0);
		
		assertEquals("true", dietaryNode.getElementsByTagName("vegan").item(0).getTextContent());
		NodeList vegetarian = dietaryNode.getElementsByTagName("vegetarian"); 
		assertTrue(vegetarian.getLength() == 0 || vegetarian.item(0).getTextContent().equals("false"));
	}
}
