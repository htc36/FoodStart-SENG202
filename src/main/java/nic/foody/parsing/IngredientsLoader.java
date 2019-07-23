package nic.foody.parsing;

/**
 * A custom class to demonstrate the basics of using the DOM as
 * implemented in JAXP to process the content of an XML file.
 */

// We'll need some JAXP packages
import javax.xml.parsers.*;
import org.xml.sax.*;
import nic.foody.model.Ingredient;
import nic.foody.util.ThreeValueLogic;
import nic.foody.util.UnitType;

import org.w3c.dom.*;
// and some of the usual stuff
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class IngredientsLoader {
    /*
     * We'll use one of these to parse the input and build the tree.
     */
    private DocumentBuilder db = null;

    /*
     * This is where our tree will be once it is built.
     */
    private Document parsedDoc = null;

    private String source;

    /** Output goes here */
    private PrintStream dest;


    private Map<String, Ingredient> ingredients;
    private String description = "";
    private String code;
    private String name;
    private UnitType unit;
    private ThreeValueLogic isVeg, isVegan, isGF;

    /**
     * Constructor for class.
     * 
     * @param pathName
     * @param validating
     * @param dest
     */
    public IngredientsLoader(String pathName, boolean validating, OutputStream dest) {
        source = pathName;

        this.dest = new PrintStream(dest);

        /*
         * Hi ho, hi ho it's off to the DocumentBuilderFactory we go...
         */
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        /*
         * Various configuration options are possible here. See the API documentation
         * for details.
         */
        dbf.setValidating(validating);

        /*
         * Now the factory can be used to create as many DocumentBuilders as we need
         * with the options we have set.
         */

        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException pce) {
            System.err.println(pce);
            System.exit(1);
        }

        /*
         * We can upgrade to a custom erorhandler if we wish. Could use the
         * SAXdemoCustomErrorHandler we saw earlier. Otherwise just stick with the
         * deault.
         */
        db.setErrorHandler(new MyErrorHandler(System.err));
    }




    /*
     * The DocumentBuilder can be used to parse the input file and generate a tree
     * ready for later processing.
     */
    public void parseInput() {
        try {
            parsedDoc = db.parse(source);
        } catch (SAXException se) {
            System.err.println(se.getMessage());
            System.exit(1);
        } catch (IOException ioe) {
            System.err.println(ioe);
            System.exit(1);
        }
    }

   

    public Document parsedDoc() {
        return parsedDoc;
    }

    /**
     * Now that the DOM has been built, select each ingredient element and construct
     * the corresponding Ingredient object. Eventually, we need to check whether we
     * already know about each ingredient but for now we assume they are all new to
     * us.
     * 
     * @see Ingredient
     */
    public Map<String, Ingredient> getIngredients() {
        ingredients = new HashMap<String, Ingredient>();
        NodeList nodes = parsedDoc.getElementsByTagName("ingredient");
        int numNodes = nodes.getLength();

        Node aNode;
        NodeList kids;
        NamedNodeMap atts;

        for (int n = 0; n < numNodes; n++) {
            /*
             * If you feel the urge to debug... 
             * System.out.println("Ingredient " + n +
             * " of " + numNodes + ": = " + nodes.item(n).getNodeValue());
             */

            /*
             * This is an element node so we know from the DTD the expected child nodes and
             * their order, as well as the attributes that must/might be present. If we
             * didn't have order specified then we would use getNodeName() to check.
             */
            reset();
            aNode = nodes.item(n);
            kids = aNode.getChildNodes();
            atts = aNode.getAttributes();
            code = kids.item(1).getTextContent();
            name = kids.item(3).getTextContent();

            switch (atts.getNamedItem("unit").getNodeValue()) {
            case "g":
                unit = UnitType.GRAM;
                break;
            case "ml":
                unit = UnitType.ML;
                break;
            case "count":
                unit = UnitType.COUNT;
                break;
            default:
                // error
            }
            isVeg = yesNoMaybe(atts.getNamedItem("isveg").getNodeValue());
            isVegan = yesNoMaybe(atts.getNamedItem("isvegan").getNodeValue());
            isGF = yesNoMaybe(atts.getNamedItem("isgf").getNodeValue());

            /*
             * If you feel the need, then this sort of thing can be handy for debugging.
             * 
             * System.out.println("Code is: " + kids.item(1).getTextContent());
             * System.out.println("Name is: " + name); System.out.println("Unit is: " +
             * unit); System.out.println("Vegetarian: " + isVeg);
             * System.out.println("Vegan: " + isVegan); System.out.println("GF: " + isGF);
             */

            ingredients.put(code, new Ingredient(code, name, unit, isVeg, isVegan, isGF));
        }
        return ingredients;
    }

    /**
     * We're going to be doing this a lot, so make a method to do it...
     * 
     * @param s
     * @return
     */
    private ThreeValueLogic yesNoMaybe(String s) {
        ThreeValueLogic tvl;
        switch (s) {
        case "yes":
            tvl = ThreeValueLogic.YES;
            break;
        case "no":
            tvl = ThreeValueLogic.NO;
            break;
        case "unknown":
            tvl = ThreeValueLogic.UNKNOWN;
            break;
        default:
            tvl = ThreeValueLogic.UNKNOWN;
        }
        return tvl;
    }

    /**
     * Reset the fields before assembling next Ingredient.
     */
    private void reset() {
        code = "";
        name = "";
        unit = UnitType.UNKNOWN;
        isVeg = isVegan = isGF = ThreeValueLogic.UNKNOWN;
    }
}
