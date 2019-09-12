package foodstart.manager.xml;

import foodstart.manager.Managers;
import foodstart.model.DataType;
import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

/**
 * Parses ingredient XML files
 *
 * @author Alex Hobson on 28/08/2019
 */
public class XMLIngredientParser extends XMLParser {

	public XMLIngredientParser() {
		super(DataType.INGREDIENT);
	}

	/**
	 * Imports an ingredient file
	 *
	 * @param doc The XML document to parse
	 */
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
	 * Parses one ingredient from the given element
	 *
	 * @param element XML Element to parse
	 */
	private void parseOneIngredient(Element element) {
		Unit unit = Unit.matchUnit(element.getAttribute("unit"));
		int id = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
		String name = element.getElementsByTagName("name").item(0).getTextContent();
		int truckStock = Integer.parseInt(element.getElementsByTagName("truck_stock").item(0).getTextContent());
		int kitchenStock = Integer.parseInt(element.getElementsByTagName("kitchen_stock").item(0).getTextContent());
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
