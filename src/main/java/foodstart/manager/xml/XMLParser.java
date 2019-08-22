package foodstart.manager.xml;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import foodstart.manager.Managers;
import foodstart.manager.Persistence;
import foodstart.manager.exceptions.ImportFailureException;
import foodstart.model.DataType;
import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;
import foodstart.model.stock.Ingredient;

/**
 * Parses an XML file with a given DataType
 * 
 * @author Alex Hobson
 * @date 22/08/2019
 */
public class XMLParser extends Persistence {

	/**
	 * Imports a XML file of a given data type
	 */
	@Override
	public void importFile(File file, DataType dataType) {

		Document doc = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(file);
		} catch (ParserConfigurationException e) {
			throw new ImportFailureException("Parser had a configuration exception: " + e.getMessage());
		} catch (SAXException e) {
			throw new ImportFailureException("Parser threw a SAX Exception: " + e.getMessage());
		} catch (IOException e) {
			throw new ImportFailureException("Parser threw an IO Exception: " + e.getMessage());
		}

		switch (dataType) {
		case INGREDIENT:
			importIngredients(doc);
		}
	}

	@Override
	public void exportFile(File file, DataType dataType) {
		// TODO
	}

	/**
	 * Imports an ingredient file
	 * @param doc The XML document to parse
	 */
	private void importIngredients(Document doc) {
		NodeList ingredientNodes = doc.getDocumentElement().getChildNodes();
		for (int i = 0; i < ingredientNodes.getLength(); i++) {
			Node node = ingredientNodes.item(i);
			if (!node.getNodeName().equals("#text")) {
				Node unitNode = node.getAttributes().getNamedItem("unit");
				NodeList ingredientNode = node.getChildNodes();
				Unit unit = Unit.matchUnit(unitNode.getNodeValue());
				
				int id = -1;
				String name = "";
				int truckStock = 0;
				int kitchenStock = 0;
				Map<DietaryRequirement, Boolean> dietaryRequirements = new HashMap<DietaryRequirement, Boolean>();
				for (DietaryRequirement requirement : DietaryRequirement.values()) {
					dietaryRequirements.put(requirement, false);
				}
				
				for (int j = 0; j < ingredientNode.getLength(); j++) {
					Node ingredientElementNode = ingredientNode.item(j);
					String ingredientNodeName = ingredientElementNode.getNodeName();
					if (ingredientNodeName.equals("id")) {
						id = Integer.parseInt(ingredientElementNode.getTextContent());
					} else if (ingredientNodeName.equals("name")) {
						name = ingredientElementNode.getTextContent();
					} else if (ingredientNodeName.equals("truck_stock")) {
						truckStock = Integer.parseInt(ingredientElementNode.getTextContent());
					} else if (ingredientNodeName.equals("kitchen_stock")) {
						kitchenStock = Integer.parseInt(ingredientElementNode.getTextContent());
					} else if (ingredientNodeName.equals("dietary")) {
						NodeList dietaryNodes = ingredientElementNode.getChildNodes();
						for (int k = 0; k < dietaryNodes.getLength(); k++) {
							Node dietaryNode = dietaryNodes.item(k);
							boolean value = Boolean.valueOf(dietaryNode.getNodeValue());
							String dietaryName = dietaryNode.getNodeName();
							dietaryRequirements.put(DietaryRequirement.matchDietaryRequirement(dietaryName), value);
						}
					}
				}
				
				Ingredient ingredient = new Ingredient(unit, name, id, dietaryRequirements, kitchenStock, truckStock);
				Managers.getIngredientManager().addIngredient(ingredient);
			}
		}
	}

}
