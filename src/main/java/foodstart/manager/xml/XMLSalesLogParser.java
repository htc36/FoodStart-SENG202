package foodstart.manager.xml;

import foodstart.model.DataType;
import foodstart.manager.Managers;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.time.Month;
import java.time.Year;
import java.util.HashSet;
import java.util.Set;


public class XMLSalesLogParser extends XMLParser {
    /**
     * Constructor for recipe parser
     */
    public XMLSalesLogParser() {
        super(DataType.SALES_LOG);
    }

    @Override
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
     * Parses one sale from the given element
     *
     * @param element XML Element to parse
     */
    private void parseOneSale(Element element) {
        Set<Integer> ingredientIds = new HashSet<Integer>();
        int id = Integer.parseInt(element.getElementsByTagName("sale_id").item(0).getTextContent());
        String name = element.getElementsByTagName("name").item(0).getTextContent();
        long date =  Long.parseLong(element.getElementsByTagName("date").item(0).getTextContent());
        float cost = Float.parseFloat(element.getElementsByTagName("cost").item(0).getTextContent());

        NodeList recipesNodes = ((Element) element.getElementsByTagName("recipes").item(0)).getElementsByTagName("recipe");
        for (int i = 0; i < recipesNodes.getLength(); i++) {
            Node recipeNode = recipesNodes.item(i);
            if (recipeNode instanceof Element) {
                parseOneRecipe((Element) recipeNode);
            }
        }
    }

    private void parseOneRecipe(Element element) {
        int recipeId = Integer.parseInt(element.getElementsByTagName("recipe_id").item(0).getTextContent());
        NodeList ingredientsNodes = element.getElementsByTagName("ingredients");

//        for (int j = 0; j < ingredientsNodes.getLength(); j++) {
//            Node ingredientNode = ingredientsNodes.item(j);
//            if (ingredientNode instanceof Element) {
//                int ingredientId = Integer.parseInt(element.getElementsByTagName("ingredient_id").item(0).getTextContent());
//                System.out.println(ingredientId);
//            }
//        }
//
//        float price = Float.parseFloat(element.getElementsByTagName("price").item(0).getTextContent());
    }

}
