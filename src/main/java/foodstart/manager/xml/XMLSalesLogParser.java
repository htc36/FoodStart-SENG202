package foodstart.manager.xml;

import foodstart.manager.Managers;
import foodstart.manager.exceptions.IDLeadsNowhereException;
import foodstart.model.DataType;
import foodstart.model.PaymentMethod;
import foodstart.model.menu.Recipe;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;


public class XMLSalesLogParser extends XMLParser {
    /**
     * Constructor for recipe parser
     */
    public XMLSalesLogParser() {
        super(DataType.SALES_LOG);
    }

    @Override
    public void parse(Document doc) {
        //TODO: Finish implementing parser
        NodeList salesNodes = doc.getChildNodes();
        for (int j = 0; j < salesNodes.getLength(); j++) {
            if (salesNodes.item(j) instanceof Element && salesNodes.item(j).getNodeName().equalsIgnoreCase("sales")) {
                if (salesNodes.item(0).getNodeName().equalsIgnoreCase("sales")) {
                    Element element = (Element) salesNodes.item(j);
                    NodeList nodes = element.getElementsByTagName("sale");
                    for (int i = 0; i < nodes.getLength(); i++) {
                        Node saleNode = nodes.item(i);
                        if (saleNode instanceof Element) {
                            parseOneSale((Element) saleNode);
                        }
                    }
                }
            }
        }
    }

    private Map<Recipe, Integer> getSaleItems(Element element) {
    	//TODO: Add parsing for OTF recipes
	    Map<Recipe, Integer> recipes = new HashMap<Recipe, Integer>();
	    NodeList recipeNodes = element.getElementsByTagName("recipes").item(0).getChildNodes();
	    for (int i = 0; i < recipeNodes.getLength(); i++) {
		    Node node = recipeNodes.item(i);
		    if (node.getNodeName().equalsIgnoreCase("recipe")) {
		    	Element recipeEl = (Element) node;
			    int recipeId = Integer.parseInt(recipeEl.getElementsByTagName("recipe_id").item(0).getTextContent());
			    int quantity = Integer.parseInt(recipeEl.getElementsByTagName("quantity").item(0).getTextContent());
			    Recipe recipe = Managers.getRecipeManager().getRecipe(recipeId);
			    if (recipe == null) {
				    throw new IDLeadsNowhereException(DataType.RECIPE, recipeId);
			    }
			    recipes.put(recipe, quantity);
		    }
	    }
	    return recipes;
    }
    /**
     * Parses one sale from the given element
     *
     * @param element XML Element to parse
     */
    private void parseOneSale(Element element) {
        int id = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
        String name = element.getElementsByTagName("name").item(0).getTextContent();
        long date = Long.parseLong(element.getElementsByTagName("date").item(0).getTextContent());
        float cost = Float.parseFloat(element.getElementsByTagName("cost").item(0).getTextContent());
	    PaymentMethod payment = PaymentMethod.matchNiceName(element.getElementsByTagName("payment").item(0).getTextContent());
	    Map<Recipe, Integer> recipes = getSaleItems(element);
		Managers.getOrderManager().addOrder(id, recipes, name, date, payment, cost);

    }
}
