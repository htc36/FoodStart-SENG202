package foodstart.manager.xml;

import foodstart.manager.Managers;
import foodstart.manager.exceptions.DuplicateDataException;
import foodstart.manager.exceptions.IDLeadsNowhereException;
import foodstart.manager.menu.RecipeManager;
import foodstart.model.DataType;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.stock.Ingredient;
import org.w3c.dom.*;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import java.util.HashMap;
import java.util.Map;

/**
 * Parses recipe XML files
 *
 * @author Alex Hobson on 06/09/2019
 * @author Alan Wang
 */
public class XMLRecipeParser extends XMLParser {

	/**
	 * Constructor for recipe parser
	 */
	public XMLRecipeParser() {
		super(DataType.RECIPE);
	}

	/**
	 * Imports a recipe file
	 *
	 * @param doc The XML document to parse
	 */
	@Override
	public void parse(Document doc) {
		NodeList recipeNodes = doc.getChildNodes();
		for (int j = 0; j < recipeNodes.getLength(); j++) {
			if (recipeNodes.item(j) instanceof Element && recipeNodes.item(j).getNodeName().equalsIgnoreCase("recipes")) {
				if (recipeNodes.item(0).getNodeName().equalsIgnoreCase("recipes")) {
					Element element = (Element) recipeNodes.item(j);
					NodeList nodes = element.getElementsByTagName("recipe");
					for (int i = 0; i < nodes.getLength(); i++) {
						Node recipeNode = nodes.item(i);
						if (recipeNode instanceof Element) {
							parseOneRecipe((Element) recipeNode);
						}
					}
				}
			}
		}
	}

	/**
	 * Parses a single recipe from the given Element.
	 *
	 * @param element The Element object representing a single recipe in a XML file to parse
	 * @return Recipe that was parsed (also added to the registry)
	 */
	private int parseOneRecipe(Element element) {
		int recipeId = Integer.parseInt(element.getElementsByTagName("recipe_id").item(0).getTextContent());
		String name = element.getElementsByTagName("name").item(0).getTextContent();
		String method = element.getElementsByTagName("method").item(0).getTextContent();
		float price = Float.parseFloat(element.getElementsByTagName("price").item(0).getTextContent());
		NodeList ingredientsNodes = ((Element) element.getElementsByTagName("ingredients").item(0)).getElementsByTagName("ingredient");
		Map<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>();

		for (int i = 0; i < ingredientsNodes.getLength(); i++) {
			Node ingredientNode = ingredientsNodes.item(i);
			if (ingredientNode instanceof Element) {
				Element recipeElement = (Element) ingredientNode;
				int ingredientId = Integer.parseInt(recipeElement.getElementsByTagName("ingredient_id").item(0).getTextContent());
				int quantity = Integer.parseInt(recipeElement.getElementsByTagName("quantity").item(0).getTextContent());
				Ingredient ingredient = Managers.getIngredientManager().getIngredientBuffered(ingredientId);
				if (ingredient == null) {
					throw new IDLeadsNowhereException(DataType.INGREDIENT, ingredientId);
				}
				ingredients.put(ingredient, quantity);
			}
		}

		for (PermanentRecipe recipe : Managers.getRecipeManager().getRecipes().values()) {
			if (recipe.getDisplayName().equals(name)) {
				throw new DuplicateDataException(DataType.RECIPE, name);
			}
		}
		Managers.getRecipeManager().pushToBuffer(recipeId, name, method, price, ingredients);
		return recipeId;
	}
	
	/**
     * Exports an ingredient file
     * 
     * @param doc The XML document to write everything to
     */
    @Override
    public void export(Document doc, Transformer transformer) {
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "recipe.dtd");
        exportWithManager(doc, Managers.getRecipeManager());
    }

    /**
     * Export a recipe file by writing it to the document. By specifying the
     * ingredient manager this makes it easier to test
     * 
     * @param doc Document to export to
     * @param manager The ingredient manager to export ingredients from
     */
    public void exportWithManager(Document doc, RecipeManager manager) {
        Element root = doc.createElement("recipes");
        Map<Integer, PermanentRecipe> recipeMap = manager.getRecipes();
        for (Integer id : recipeMap.keySet()) {
            PermanentRecipe recipe = recipeMap.get(id);
            Element recipeElement = createRecipeElement(doc, recipe);
            root.appendChild(recipeElement);
        }
        doc.appendChild(root);
    }
    
    /**
     * Converts a Recipe object into a subtree of Element objects for exporting to an XML file.
     * @param doc The target Document object to create tags from
     * @param recipe The recipe to be exported
     * @return The Element object at the root of the subtree 
     */
    private Element createRecipeElement(Document doc, PermanentRecipe recipe) {
        Element recipeElement = doc.createElement("recipe");
        
        Element recipeId = doc.createElement("recipe_id");
        recipeId.appendChild(doc.createTextNode(Integer.toString(recipe.getId())));
        recipeElement.appendChild(recipeId);       
        
        Element recipeName = doc.createElement("name");
        recipeName.appendChild(doc.createTextNode(recipe.getDisplayName()));
        recipeElement.appendChild(recipeName);
        
        Element recipeIngredients = doc.createElement("ingredients");
        Map<Ingredient, Integer> ingredientMap = recipe.getIngredients();
        for (Ingredient ing : ingredientMap.keySet()) {
            Element ingredient = doc.createElement("ingredient");
            
            Element ingredient_id = doc.createElement("ingredient_id");
            Text idString = doc.createTextNode(Integer.toString(ing.getId()));
            ingredient_id.appendChild(idString);
            Element quantity = doc.createElement("quantity");
            Text qtyString = doc.createTextNode(Integer.toString(ingredientMap.get(ing)));
            quantity.appendChild(qtyString);
            ingredient.appendChild(ingredient_id);
            ingredient.appendChild(quantity);
            
            recipeIngredients.appendChild(ingredient);                    
        }
        recipeElement.appendChild(recipeIngredients);
        
        Element recipeMethod = doc.createElement("method");
        recipeMethod.appendChild(doc.createTextNode(recipe.getInstructions()));
        recipeElement.appendChild(recipeMethod);
        
        Element recipePrice = doc.createElement("price");
        recipePrice.appendChild(doc.createTextNode(String.valueOf(recipe.getPrice())));
        recipeElement.appendChild(recipePrice);
        
        return recipeElement;
    }
}
