package foodstart.manager.xml;

import foodstart.manager.Managers;
import foodstart.manager.exceptions.ExportFailureException;
import foodstart.manager.exceptions.IDLeadsNowhereException;
import foodstart.manager.menu.RecipeManager;
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
import java.time.ZoneOffset;
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
	/**
	 * Parses a log of sales document and writes to the model
	 * @param doc Document to parse
	 */
	public void parse(Document doc) {
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

	/**
	 * Parses an on the fly recipe if it is detected during parsing
	 *
	 * @param otfDataNodes the NodeList that contains the nodes relevant ot the on the fly recipe
	 * @param basis        the recipe that the on the fly recipe is based on
	 * @return the id of the fly recipe generated from the data
	 */
	private int parseOTFRecipe(NodeList otfDataNodes, int basis) {
		Map<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>();
		float price = 0;
		for (int j = 0; j < otfDataNodes.getLength(); j++) {
			//May be either an ingredients list or the price of the recipe
			Node dataNode = otfDataNodes.item(j);

			if (dataNode.getNodeName().equalsIgnoreCase("ingredients")) {
				NodeList ingredientsNodes = dataNode.getChildNodes();
				for (int k = 0; k < ingredientsNodes.getLength(); k++) {
					Node ingredientNode = ingredientsNodes.item(k);
					if (ingredientNode.getNodeName().equalsIgnoreCase("ingredient")) {
						Element ingredientElement = (Element) ingredientNode;
						int ingredientId = Integer.parseInt(ingredientElement.getElementsByTagName("ingredient_id").item(0).getTextContent());
						int ingredientQuantity = Integer.parseInt(ingredientElement.getElementsByTagName("ingredient_quantity").item(0).getTextContent());
						Ingredient ingredient = Managers.getIngredientManager().getIngredient(ingredientId);
						if (ingredient == null) {
							throw new IDLeadsNowhereException(DataType.INGREDIENT, ingredientId);
						}
						ingredients.put(ingredient, ingredientQuantity);
					}
				}

				if (dataNode.getNodeName().equalsIgnoreCase("otf_price")) {
					Element priceElement = (Element) dataNode;
					price = Float.parseFloat(priceElement.getElementsByTagName("otf_price").item(0).getTextContent());
				}
			}
		}
		int id = Managers.getRecipeManager().otfManager.addRecipe(basis, ingredients, price);
		return id;
	}

	/**
	 * Parses a single sale for the items in it
	 *
	 * @param element the sale element to parse
	 * @return a map of the recipe items in the sale to the quantity ordered
	 */
	private Map<Recipe, Integer> getSaleItems(Element element) {
		RecipeManager manager = Managers.getRecipeManager();
		Map<Recipe, Integer> recipes = new HashMap<Recipe, Integer>();
		NodeList recipeNodes = element.getElementsByTagName("recipes").item(0).getChildNodes();
		for (int i = 0; i < recipeNodes.getLength(); i++) {
			Node node = recipeNodes.item(i);
			if (node.getNodeName().equalsIgnoreCase("recipe")) {
				Element recipeEl = (Element) node;
				int recipeId = Integer.parseInt(recipeEl.getElementsByTagName("recipe_id").item(0).getTextContent());
				int quantity = Integer.parseInt(recipeEl.getElementsByTagName("quantity").item(0).getTextContent());
				PermanentRecipe recipe = manager.getRecipe(recipeId);
				if (recipe == null) {
					throw new IDLeadsNowhereException(DataType.RECIPE, recipeId);
				}
				NodeList ingredientNodes = recipeEl.getElementsByTagName("ingredients");
				if (ingredientNodes.getLength() > 0) {
					//Then it's an OTF Recipe
					int onTheFlyRecipe = parseOTFRecipe(ingredientNodes, recipeId);
					recipes.put(manager.otfManager.getRecipe(onTheFlyRecipe), quantity);
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
	 * @param doc The XML document to write everything to
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
	 * @param doc     Document to export to
	 * @param manager The order manager to export sales from
	 */
	public void exportWithManager(Document doc, OrderManager manager) {
		Element saleRoot = doc.createElement("sales");
		for (Order sale : manager.getOrderSet()) {
			Element root = doc.createElement("sale");

			Element saleId = doc.createElement("id");
			saleId.appendChild(doc.createTextNode(String.valueOf(sale.getId())));
			root.appendChild(saleId);

			Element saleName = doc.createElement("name");
			saleName.appendChild(doc.createTextNode(sale.getCustomerName()));
			root.appendChild(saleName);

			Element saleDate = doc.createElement("date");
			saleDate.appendChild(doc.createTextNode(String.valueOf(sale.getTimePlaced().toEpochSecond(ZoneOffset.UTC) * 1000L)));
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
				Element recipeElement;
				if (recipe.getClass() == PermanentRecipe.class) {
					recipeElement = exportPermanentRecipe(doc, (PermanentRecipe) recipe, quantity);
				} else if (recipe.getClass() == OnTheFlyRecipe.class) {
					recipeElement = exportOnTheFlyRecipe(doc, (OnTheFlyRecipe) recipe, quantity);
				} else {
					throw new ExportFailureException("Unknown onject type as recipe.");
				}
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
	 *
	 * @param doc    Document to create elements with
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

	/**
	 * Turn a OnTheFlyRecipe into an element with respect to the specified document instance
	 *
	 * @param doc    Document to create elements with
	 * @param recipe Recipe to export
	 * @return The recipe node in the XML document to export with
	 */

	private Element exportOnTheFlyRecipe(Document doc, OnTheFlyRecipe recipe, int quantity) {
		Element recipeElement = doc.createElement("recipe");

		Element recipeIdElement = doc.createElement("recipe_id");
		recipeIdElement.appendChild(doc.createTextNode(String.valueOf(recipe.getBasedOn().getId())));
		recipeElement.appendChild(recipeIdElement);

		Element ingredientsElement = doc.createElement("ingredients");
		for (Ingredient ingredient : recipe.getIngredients().keySet()) {
			Element ingredientIDElement = doc.createElement("ingredient_id");
			ingredientIDElement.appendChild(doc.createTextNode(String.valueOf(ingredient.getId())));
			ingredientsElement.appendChild(ingredientIDElement);
		}
		recipeElement.appendChild(ingredientsElement);


		Element quantityElement = doc.createElement("quantity");
		quantityElement.appendChild(doc.createTextNode(String.valueOf(quantity)));
		recipeElement.appendChild(quantityElement);

		return recipeElement;
	}


}
