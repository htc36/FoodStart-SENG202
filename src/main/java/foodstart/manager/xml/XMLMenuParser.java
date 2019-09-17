package foodstart.manager.xml;

import foodstart.manager.Managers;
import foodstart.manager.exceptions.IDLeadsNowhereException;
import foodstart.model.DataType;
import foodstart.model.menu.MenuItem;
import foodstart.model.menu.PermanentRecipe;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Parses menu XML files
 *
 * @author Alex Hobson on 28/08/2019
 */
public class XMLMenuParser extends XMLParser {

	/**
	 * Constructor for menu parser
	 */
	public XMLMenuParser() {
		super(DataType.MENU);
	}

	/**
	 * Imports a menu file
	 *
	 * @param doc The XML document to parse
	 */
	@Override
	public void parse(Document doc) {
		NodeList menusNodes = doc.getChildNodes();
		for (int i = 0; i < menusNodes.getLength(); i++) {
			if (menusNodes.item(i) instanceof Element && menusNodes.item(i).getNodeName().equalsIgnoreCase("menus")) {
				Element element = (Element) menusNodes.item(i);
				NodeList nodes = element.getElementsByTagName("menu");

				for (int j = 0; j < nodes.getLength(); j++) {

					Node node = nodes.item(j);
					if (node instanceof Element && node.getNodeName().equalsIgnoreCase("menu")) {
						Set<Integer> menuItems = new HashSet<Integer>();
						Element menuElement = (Element) node;
						int menuId = Integer.parseInt(menuElement.getElementsByTagName("menu_id").item(0).getTextContent());
						String title = menuElement.getElementsByTagName("title").item(0).getTextContent();
						String description = menuElement.getElementsByTagName("description").item(0).getTextContent();
						NodeList nodeList = menuElement.getElementsByTagName("items").item(0).getChildNodes();
						for (int k = 0; k < nodeList.getLength(); k++) {
							Node menuItemNode = nodeList.item(k);
							if (menuItemNode instanceof Element && menuItemNode.getNodeName().equalsIgnoreCase("item")) {
								menuItems.add(parseOneMenuItem((Element) menuItemNode));
							}
						}
						Set<MenuItem> items = Managers.getMenuItemManager().getMenuItems(menuItems);
						Managers.getMenuManager().addMenu(items, menuId, title, description);
					}
				}
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
	 * @return Set of recipes
	 * @throws IDLeadsNowhereException if a recipe from a given ID is not defined
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
