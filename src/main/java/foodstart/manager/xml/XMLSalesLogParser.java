package foodstart.manager.xml;

import foodstart.manager.Managers;
import foodstart.manager.exceptions.IDLeadsNowhereException;
import foodstart.manager.order.OrderManager;
import foodstart.model.DataType;
import foodstart.model.PaymentMethod;
import foodstart.model.menu.OnTheFlyRecipe;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.order.Order;
import foodstart.model.stock.Ingredient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
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
    	//TODO: Ensure OTF Parsing works
	    Map<Recipe, Integer> recipes = new HashMap<Recipe, Integer>();
	    NodeList recipeNodes = element.getElementsByTagName("recipes").item(0).getChildNodes();
	    for (int i = 0; i < recipeNodes.getLength(); i++) {
		    Node node = recipeNodes.item(i);
		    if (node.getNodeName().equalsIgnoreCase("recipe")) {
		    	Element recipeEl = (Element) node;
			    int recipeId = Integer.parseInt(recipeEl.getElementsByTagName("recipe_id").item(0).getTextContent());
			    int quantity = Integer.parseInt(recipeEl.getElementsByTagName("quantity").item(0).getTextContent());
			    PermanentRecipe recipe = Managers.getRecipeManager().getRecipe(recipeId);
			    if (recipe == null) {
				    throw new IDLeadsNowhereException(DataType.RECIPE, recipeId);
			    }
			    if (((Element) node).hasAttribute("ingredients")) {
			        //Then it's an OTF Recipe
                    Map<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>();
                    NodeList ingredientNodes = element.getElementsByTagName("ingredients").item(0).getChildNodes();
                    float price = 0;
                    for (int j = 0; j < ingredientNodes.getLength(); j++) {
                        Node ingredientNode = ingredientNodes.item(j);
                        if (ingredientNode.getNodeName().equalsIgnoreCase("ingredient")) {
                            Element ingredientElement = (Element) ingredientNode;
                            int ingredientId = Integer.parseInt(ingredientElement.getElementsByTagName("ingredient_id").item(0).getTextContent());
                            int ingredientQuantity = Integer.parseInt(ingredientElement.getElementsByTagName("ingredient_quantity").item(0).getTextContent());
                            Ingredient ingredient = Managers.getIngredientManager().getIngredient(ingredientId);
                            if (ingredient == null) {
                                throw new IDLeadsNowhereException(DataType.INGREDIENT, ingredientId);
                            }
                            ingredients.put(ingredient, ingredientQuantity);
                        } else if (ingredientNode.getNodeName().equalsIgnoreCase("otf_price")) {
                            Element priceElement = (Element) ingredientNode;
                            price = Float.parseFloat(priceElement.getElementsByTagName("otf_price").item(0).getTextContent());
                        }
                    }
                    OnTheFlyRecipe onTheFlyRecipe = new OnTheFlyRecipe(recipe, ingredients, price);
                    recipes.put(onTheFlyRecipe, quantity);
                } else {
                    recipes.put(recipe, quantity);
                }
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

    /**
     * Exports a sales log file
     *
     * @param doc
     *             The XML document to write everything to
     */
    @Override
    public void export(Document doc, Transformer transformer) {
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "sales_log.dtd");
        exportWithManager(doc, Managers.getOrderManager());
    }

    /**
     * Export a sales log file by writing it to the document. By specifying the
     * order manager this makes it easier to test
     *
     * @param doc
     *            Document to export to
     * @param manager
     *            The order manager to export sales from
     */
    public void exportWithManager(Document doc, OrderManager manager) {
        Element saleRoot = doc.createElement("sales");
        for (Order sale : manager.getOrderSet()) {
            Element root = doc.createElement("sale");

            Element saleId = doc.createElement("sale_id");
            saleId.appendChild(doc.createTextNode(String.valueOf(sale.getId())));
            root.appendChild(saleId);

            Element saleName = doc.createElement("name");
            saleName.appendChild(doc.createTextNode(sale.getCustomerName()));
            root.appendChild(saleName);

            Element saleDate = doc.createElement("date");
            saleDate.appendChild(doc.createTextNode(String.valueOf(sale.getTimePlaced())));
            root.appendChild(saleDate);

            Element saleCost = doc.createElement("cost");
            saleCost.appendChild(doc.createTextNode(String.valueOf(sale.getTotalCost())));
            root.appendChild(saleCost);

            Element salePayment = doc.createElement("payment");
            salePayment.appendChild(doc.createTextNode(sale.getPaymentMethod().getNiceName()));
            root.appendChild(salePayment);

            Element saleRecipes = doc.createElement("recipes");

            for (Recipe recipe : sale.getItems().keySet()) {
                int quantity = sale.getItems().get(recipe);
                Element recipeElement = exportPermanentRecipe(doc,(PermanentRecipe) recipe, quantity);
                saleRecipes.appendChild(recipeElement);
            }

            root.appendChild(saleRecipes);
            saleRoot.appendChild(root);
        }
        doc.appendChild(saleRoot);
    }

    /**
     * Turn an individual recipe into an element with respect to the
     * specified document instance
     * @param doc Document to create elements with
     * @param recipe Recipe to export
     * @return The XML element to export with
     */

    private Element exportPermanentRecipe(Document doc, PermanentRecipe recipe, int quantity) {
        Element recipeElement = doc.createElement("recipe");

        Element recipeId = doc.createElement("recipe_id");
        recipeId.appendChild(doc.createTextNode(String.valueOf(recipe.getId())));
        recipeElement.appendChild(recipeId);

        Element recipeQuantity = doc.createElement("quantity");
        recipeQuantity.appendChild(doc.createTextNode(String.valueOf(quantity)));
        recipeElement.appendChild(recipeQuantity);

        return recipeElement;
    }


}
