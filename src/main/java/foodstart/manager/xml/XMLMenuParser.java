package foodstart.manager.xml;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import foodstart.manager.Managers;
import foodstart.manager.exceptions.IDLeadsNowhereException;
import foodstart.model.DataType;
import foodstart.model.menu.MenuItem;
import foodstart.model.menu.PermanentRecipe;

/**
 * Parses menu XML files
 * 
 * @author Alex Hobson
 * @date 28/08/2019
 */
public class XMLMenuParser extends XMLParser {

	public XMLMenuParser() {
		super(DataType.MENU);
	}

	/**
	 * Imports a menu file
	 * 
	 * @param doc The XML document to parse
	 */
	public void parse(Document doc) {
		NodeList ingredientNodes = doc.getChildNodes();
		for (int i = 0; i < ingredientNodes.getLength(); i++) {
			Node node = ingredientNodes.item(i);
			if (node instanceof Element) {
				Set<Integer> menuItems = new HashSet<Integer>();
				Element element = (Element) node;
				int menuId = Integer.parseInt(element.getElementsByTagName("menu_id").item(0).getTextContent());
				String title = element.getElementsByTagName("title").item(0).getTextContent();
				String description = element.getElementsByTagName("description").item(0).getTextContent();
				NodeList nodeList = element.getElementsByTagName("items").item(0).getChildNodes();
				for (int j = 0; j < nodeList.getLength(); j++) {
					Node menuItemNode = nodeList.item(j);
					if (menuItemNode instanceof Element) {
						menuItems.add(parseOneMenuItem((Element) menuItemNode));
					}
				}
				Set<MenuItem> items = Managers.getMenuItemManager().getMenuItems(menuItems);
				Managers.getMenuManager().addMenu(items, menuId, title, description);
			}
		}
	}

	/**
	 * Parses one menu item from the given element
	 * 
	 * @param element XML Element to parse
	 */
	private int parseOneMenuItem(Element element) {
		int itemId = Integer.parseInt(element.getElementsByTagName("item_id").item(0).getTextContent());
		String name = element.getElementsByTagName("name").item(0).getTextContent();
		String description = element.getElementsByTagName("item_description").item(0).getTextContent();

		NodeList recipeIds = element.getElementsByTagName("recipes").item(0).getChildNodes();
		List<PermanentRecipe> recipes = parseRecipeList(recipeIds);

		Managers.getMenuItemManager().addMenuItem(itemId, name, description, recipes);
		return itemId;
	}

	/**
	 * Parses and validates the recipe list from the given NodeList
	 * 
	 * @throws IDLeadsNowhereException if a recipe from a given ID is not defined
	 * 
	 * @return Set of recipes
	 */
	private List<PermanentRecipe> parseRecipeList(NodeList recipeIds) {
		List<PermanentRecipe> recipeList = new ArrayList<PermanentRecipe>();
		for (int i = 0; i < recipeIds.getLength(); i++) {
			Node node = recipeIds.item(i);
			if (node.getNodeName().equalsIgnoreCase("recipe_id")) {
				int recipeId = Integer.parseInt(node.getTextContent());
				PermanentRecipe recipe = Managers.getRecipeManager().getRecipe(recipeId);
				if (recipe == null) {
					throw new IDLeadsNowhereException(DataType.RECIPE, recipeId);
				}
				recipeList.add(recipe);
			}
		}
		return recipeList;
	}
}
