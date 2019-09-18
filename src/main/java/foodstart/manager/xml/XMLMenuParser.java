package foodstart.manager.xml;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import foodstart.manager.Managers;
import foodstart.manager.exceptions.IDLeadsNowhereException;
import foodstart.manager.menu.MenuManager;
import foodstart.model.DataType;
import foodstart.model.menu.Menu;
import foodstart.model.menu.MenuItem;
import foodstart.model.menu.PermanentRecipe;

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
	
	/**
	 * Exports a menu file
	 * 
	 * @param doc
	 *            The XML document to write everything to
	 */
	@Override
	public void export(Document doc, Transformer transformer) {
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "menu.dtd");
		exportWithManager(doc, Managers.getMenuManager());
	}

	/**
	 * Export a menu file by writing it to the document. By specifying the
	 * menu manager this makes it easier to test
	 * 
	 * @param doc
	 *            Document to export to
	 * @param menuItemManager
	 *            The ingredient manager to export ingredients from
	 */
	public void exportWithManager(Document doc, MenuManager manager) {
		Element menuRoot = doc.createElement("menus");
		for (Menu menu : manager.getMenuSet()) {
			Element root = doc.createElement("menu");
			
			Element menuId = doc.createElement("menu_id");
			menuId.appendChild(doc.createTextNode(String.valueOf(menu.getId())));
			root.appendChild(menuId);
			
			Element menuName = doc.createElement("title");
			menuName.appendChild(doc.createTextNode(menu.getTitle()));
			root.appendChild(menuName);
			
			Element menuDesc = doc.createElement("description");
			menuDesc.appendChild(doc.createTextNode(menu.getDescription()));
			root.appendChild(menuDesc);
			
			Element items = doc.createElement("items");
			
			for (MenuItem item : menu.getMenuItems()) {
				Element menuItemElement = exportMenuItem(doc, item);
				items.appendChild(menuItemElement);
			}
			
			root.appendChild(items);
			menuRoot.appendChild(root);
		}
		doc.appendChild(menuRoot);
	}

	/**
	 * Turn an individual menu item into an element with respect to the
	 * specified document instance
	 * @param doc Document to create elements with
	 * @param item Menu item to export
	 * @return The XML element to export with
	 */
	private Element exportMenuItem(Document doc, MenuItem item) {
		Element itemElement = doc.createElement("item");
		
		Element itemId = doc.createElement("item_id");
		itemId.appendChild(doc.createTextNode(String.valueOf(item.getId())));
		itemElement.appendChild(itemId);
		
		Element itemName = doc.createElement("name");
		itemName.appendChild(doc.createTextNode(item.getName()));
		itemElement.appendChild(itemName);
		
		Element itemDesc = doc.createElement("item_description");
		itemDesc.appendChild(doc.createTextNode(item.getDescription()));
		itemElement.appendChild(itemDesc);
		
		Element itemRecipes = doc.createElement("recipes");
		
		for (PermanentRecipe recipe : item.getVariants()) {
			Element recipeId = doc.createElement("recipe_id");
			recipeId.appendChild(doc.createTextNode(String.valueOf(recipe.getId())));
			itemRecipes.appendChild(recipeId);
		}
		
		itemElement.appendChild(itemRecipes);
		
		return itemElement;
	}
}
