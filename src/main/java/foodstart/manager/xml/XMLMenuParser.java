package foodstart.manager.xml;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import foodstart.manager.Managers;
import foodstart.model.DataType;
import foodstart.model.menu.Menu;
import foodstart.model.menu.MenuItem;

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
		NodeList ingredientNodes = doc.getDocumentElement().getChildNodes();
		for (int i = 0; i < ingredientNodes.getLength(); i++) {
			Node node = ingredientNodes.item(i);
			if (node instanceof Element) {
				Set<MenuItem> menuItems = new HashSet<MenuItem>();
				Element element = (Element) node;
				int menuId = Integer.parseInt(element.getElementsByTagName("menu_id").item(0).getTextContent());
				String title = element.getElementsByTagName("title").item(0).getTextContent();
				String description = element.getElementsByTagName("description").item(0).getTextContent();
				NodeList nodeList = element.getElementsByTagName("items").item(0).getChildNodes();
				for (int j = 0; j < nodeList.getLength(); j++) {
					Node menuItemNode = nodeList.item(j);
					if (menuItemNode instanceof Element) {
						menuItems.add(parseOneMenuItem((Element)menuItemNode));
					}
				}
				Menu menu = new Menu(menuItems, menuId, title, description);
				Managers.getMenuManager().addMenu(menu);
			}
		}
	}

	/**
	 * Parses one menu item from the given element
	 * 
	 * @param element XML Element to parse
	 */
	private MenuItem parseOneMenuItem(Element element) {
		
	}
}
