package foodstart.manager.xml;

import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import foodstart.manager.Managers;
import foodstart.manager.exceptions.ImportFailureException;
import foodstart.manager.stock.IngredientManager;
import foodstart.model.DataType;
import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;
import foodstart.model.stock.Ingredient;

/**
 * Parses ingredient XML files
 *
 * @author Alex Hobson on 28/08/2019
 */
public class XMLIngredientParser extends XMLParser {

	/**
	 * Constructs an instance of an XML ingredient parser
	 */
	public XMLIngredientParser() {
		super(DataType.INGREDIENT);
	}

	/**
	 * Imports an ingredient file
	 *
	 * @param doc
	 *            The XML document to parse
	 */
	@Override
	public void parse(Document doc) {
		NodeList ingredientNodes = doc.getDocumentElement().getChildNodes();
		for (int i = 0; i < ingredientNodes.getLength(); i++) {
			Node node = ingredientNodes.item(i);
			if (node instanceof Element) {
				Element element = (Element) node;
				parseOneIngredient(element);
			}
		}
	}

	/**
	 * Exports an ingredient file
	 * 
	 * @param doc
	 *            The XML document to write everything to
	 */
	@Override
	public void export(Document doc, Transformer transformer) {
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "ingredient.dtd");
		exportWithManager(doc, Managers.getIngredientManager());
	}

	/**
	 * Export an ingredient file by writing it to the document. By specifying the
	 * ingredient manager this makes it easier to test
	 * 
	 * @param doc
	 *            Document to export to
	 * @param manager
	 *            The ingredient manager to export ingredients from
	 */
	public void exportWithManager(Document doc, IngredientManager manager) {
		Element root = doc.createElement("ingredients");
		for (Ingredient ingredient : manager.getIngredientSet()) {
			Element ingredientElement = exportIngredient(doc, ingredient);
			root.appendChild(ingredientElement);
		}
		doc.appendChild(root);
	}
	
	/**
	 * Export an individual ingredient
	 * @param doc Document to create tags from
	 * @param ingredient Ingredient to export
	 * @return Element referring to the ingredient
	 */
	private Element exportIngredient(Document doc, Ingredient ingredient) {
		Element ingredientElement = doc.createElement("ingredient");
		
		ingredientElement.setAttribute("unit", ingredient.getUnit().getDBName());
		
		Element ingredientId = doc.createElement("id");
		ingredientId.appendChild(doc.createTextNode(String.valueOf(ingredient.getId())));
		ingredientElement.appendChild(ingredientId);
		
		Element ingredientName = doc.createElement("name");
		ingredientName.appendChild(doc.createTextNode(ingredient.getName()));
		ingredientElement.appendChild(ingredientName);
		
		Element ingredientTruckStock = doc.createElement("truck_stock");
		ingredientTruckStock.appendChild(doc.createTextNode(String.valueOf(ingredient.getTruckStock())));
		ingredientElement.appendChild(ingredientTruckStock);
		
		Element ingredientKitchenStock = doc.createElement("kitchen_stock");
		ingredientKitchenStock.appendChild(doc.createTextNode(String.valueOf(ingredient.getKitchenStock())));
		ingredientElement.appendChild(ingredientKitchenStock);
		
		Element ingredientDietaryRequirements = doc.createElement("dietary");
		for (DietaryRequirement requirement : DietaryRequirement.values()) {
			boolean safe = ingredient.isSafeFor(requirement);
			Element isSafeForElement = doc.createElement(requirement.getDBName());
			isSafeForElement.appendChild(doc.createTextNode(String.valueOf(safe)));
			ingredientDietaryRequirements.appendChild(isSafeForElement);
		}
		ingredientElement.appendChild(ingredientDietaryRequirements);
		
		return ingredientElement;
	}

	/**
	 * Parses one ingredient from the given element
	 *
	 * @param element
	 *            XML Element to parse
	 */
	private void parseOneIngredient(Element element) {
		Unit unit = Unit.matchUnit(element.getAttribute("unit"));
		// unit cannot be null because DTD would validate this
		int id = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
		if (id < 0) {
			throw new ImportFailureException("Item ID '" + id + "' is negative");
		}
		String name = element.getElementsByTagName("name").item(0).getTextContent();
		if (name.length() == 0) {
			throw new ImportFailureException("Name of item ID '" + id + "' is blank");
		}
		int truckStock = Integer.parseInt(element.getElementsByTagName("truck_stock").item(0).getTextContent());
		int kitchenStock = Integer.parseInt(element.getElementsByTagName("kitchen_stock").item(0).getTextContent());
		if (truckStock < 0 || kitchenStock < 0) {
			throw new ImportFailureException("Stock of item ID '" + id + "' goes into the negatives");
		}
		Map<DietaryRequirement, Boolean> dietaryRequirements = new HashMap<DietaryRequirement, Boolean>();
		for (DietaryRequirement requirement : DietaryRequirement.values()) {
			dietaryRequirements.put(requirement, false);
		}

		NodeList dietaryNodes = element.getElementsByTagName("dietary").item(0).getChildNodes();
		for (int k = 0; k < dietaryNodes.getLength(); k++) {
			Node dietaryNode = dietaryNodes.item(k);
			boolean value = Boolean.valueOf(dietaryNode.getTextContent());
			String dietaryName = dietaryNode.getNodeName();
			DietaryRequirement requirement = DietaryRequirement.matchDietaryRequirement(dietaryName);
			if (requirement != null) {
				dietaryRequirements.put(requirement, value);
			}
		}

		Managers.getIngredientManager().addIngredient(unit, name, id, dietaryRequirements, kitchenStock, truckStock);
	}
}
