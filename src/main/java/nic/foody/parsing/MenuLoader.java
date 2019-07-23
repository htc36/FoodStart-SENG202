package nic.foody.parsing;

/**
 * A custom class to demonstrate the basics of using the DOM as
 * implemented in JAXP to process the content of an XML file.
 */

// We'll need some JAXP packages
import javax.xml.parsers.*;
import org.xml.sax.*;

import nic.foody.model.MenuItem;

import org.w3c.dom.*;
// and some of the usual stuff
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuLoader {
    /**
     * The menu items we find
     */
    private Map<String, MenuItem> menuItems;

    /**
     * Somewhere to keep track of the names of the ingredients of each item for now
     */
    List<String> ingredientNames;
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

    private String id;
    private String name;

    public MenuLoader(String pathName, boolean validating, OutputStream dest) {
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

    /**
     * Now that the DOM has been built, select each menu item element and construct
     * the corresponding objects. Eventually, we need to check whether we already
     * know about each ingredient but for now we assume they are all stored
     * elsewhere.
     */
    public Map<String, MenuItem> getMenuItems() {
        menuItems = new HashMap<String, MenuItem>();
        NodeList itemNodes = parsedDoc.getElementsByTagName("item");
        int numItems = itemNodes.getLength();
        int numItemIngredients = 0;

        Node anItemNode;
        Node anIngredientNode;
        NodeList kids;
        NamedNodeMap atts;

        for (int n = 0; n < numItems; n++) {
            ingredientNames = new ArrayList<String>();
            /*
             * This is an element node so we know from the DTD the expected child nodes and
             * their order, as well as the attributes that must/might be present. If we
             * didn't have order specified then we would use getNodeName() to check.
             */

            anItemNode = itemNodes.item(n);

            kids = anItemNode.getChildNodes();
            atts = anItemNode.getAttributes();
            id = kids.item(1).getTextContent();
            name = kids.item(3).getTextContent();

            // How many ingredients are in this menu ietm?
            numItemIngredients = kids.getLength();
            for (int k = 0; k < numItemIngredients; k++) {
                anIngredientNode = kids.item(k);

                if (kids.item(k).getNodeName().equals("ingredient")) {
                    // Dodge the #text of parent
                    anIngredientNode = kids.item(k);
                    anIngredientNode.getFirstChild().getNextSibling();
                    ingredientNames.add(anIngredientNode.getFirstChild().getNextSibling().getTextContent());
                }
            }
            /*
             * DIY: handle the various attributes and other elements
             */
            menuItems.put(id, new MenuItem(id, name, ingredientNames));
            //System.out.println(id + menuItems.get(id).ingredients());
        }
        return menuItems;
    }

    public Document parsedDoc() {
        return parsedDoc;
    }
}
