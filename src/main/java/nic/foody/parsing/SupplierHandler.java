package nic.foody.parsing;

// and some of the usual stuff
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A custom class to demonstrate the basics of using SAX 2.0 as
 * implemented in Sun's JAXP to process the content of an XML file.
 *
 * @author (C) Neville Churcher 2001 
 * @see SAXdemoMain
 * @see SaxDemoCustomErrorHandler
 */

// We'll need some JAXP packages
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.LocatorImpl;

import nic.foody.model.Supplier;
import nic.foody.util.PhoneType;

public class SupplierHandler extends DefaultHandler {
    /**
     * Where we'll put the stuff we read.
     */
    private static Map<String, Supplier> suppliers = new HashMap<String, Supplier>();
    private static Supplier s;
    private String sid;
    private String name;
    private String address;
    private String phone;
    private PhoneType type = PhoneType.UNKNOWN;
    private String email = Supplier.UNKNOWN_EMAIL;
    private String url = Supplier.UNKNOWN_URL;
    private String attName;

    private StringBuffer content = new StringBuffer();

    /**
     * We need an XMLReader to parse the file and generate the events which get
     * handled by our handler methods
     */
    private XMLReader xmlReader = null;

    /**
     * Let's also shout ourselves an org.xml.sax.helpers.Locator in case we ever get
     * curious about where in the xml source file the events we are processing
     * actually occurred.
     */
    private Locator movingFinger = new LocatorImpl();

    /**
     * org.xml.sax.helpers.DefaultHandlersa "do-nothing" implementation of this. The
     * parser will call us exactly once at the beginning of the document. This is a
     * good place to put a timestamp in the log file etc.
     */
    public void startDocument() throws SAXException {

    }

    /**
     * This is the partner of startDocument() and also comes from DefaultHandler.
     * The parser will call us exactly once at the end of the document so this is a
     * good place to close log files etc
     */
    public void endDocument() throws SAXException {
        // System.out.println("All finished...");
    }

    /**
     * Every time the parser encounters a start tag for an element it will call this
     * method. We get passed information about the element itself together with
     * information about the attributes and their values. Attribute information
     * comes as an object of the org.xml.sax.helpers.AttributesImpl class which
     * implements the org.xml.sax.Attributes interface.
     */

    public void startElement(String namespaceURI, String localName, String rawName, Attributes atts)
            throws SAXException {
        // System.out.println("Yet another element starts... " + rawName);
        switch (rawName) {
        case "suppliers":
            // empty
            break;
        case "supplier":
            // empty
            break;
        case "phone":
            // we're expecting a type attribute here
            for (int i = 0; i < atts.getLength(); i++) {
                String attName = atts.getQName(i);
                String attVal = atts.getValue(i);
                if (attName.equals("type")) {
                    // It's for us
                    switch (attVal) {
                    case "mobile":
                        type = PhoneType.MOBILE;
                        break;
                    case "work":
                        type = PhoneType.WORK;
                        break;
                    case "home":
                        type = PhoneType.HOME;
                        break;
                    default:
                        break;
                    }
                } else {
                    // An attribue we don't know/care about
                }
            }
            break;
        default:
            break;
        }

    }

    /**
     * Partner of startElement and called when the end element tag is parsed. Now we
     * know for sure that all the characters from th element content have been seen.
     * A good time to create application objects, update collections etc.
     */
    public void endElement(String namespaceURI, String localName, String rawName) throws SAXException {
        // System.out.println("Yet another element ends...");
        switch (rawName) {
        case "suppliers":
            break;
        case "description":
            break;
        case "supplier":
            /*
             * Now we have a complete supplier record from file. All required
             * elements wil lbe present or the validating parser would have barfed.
             */
            s = new Supplier(sid, name, address, phone, type, email, url);
            suppliers.put(sid, s);
            reset();
            break;
        case "sid":
            sid = content.toString();
            break;
        case "name":
            name = content.toString();
            break;
        case "address":
            address = content.toString();
            break;
        case "phone":
            phone = content.toString();
            break;
        case "email":
            email = content.toString();
            break;
        case "url":
            url = content.toString();
            break;

        default:
            // Shouldn't be able to get here unless validating parser used.
            System.err.println("Unknown element <" + rawName + ">");
            ;
        }

        // reset content for next element
        content.delete(0, content.length());
    }

    /**
     * When the parser has some characters for us it will call this method to let us
     * know. This is another method from DefaultHandler. See also the
     * ignorableWhitespace method. Be careful as the character content for an
     * element may come back to us via several calls so we probably need to be
     * prepared to concatenate it and process it when the end of the element is
     * notified via endElement(). This might be done using a StringBuffer property
     * of the class.
     */
    public void characters(char[] ch, int start, int length) throws SAXException {
        // stash content for later reference
        content.append(ch, start, length);
    }

    /**
     * If we are going to use our own locator then we'll need to implement this
     * interface method in order to hook it up.
     */
    public void setDocumentLocator(Locator aLocator) {
        movingFinger = aLocator;
    }

    /**
     * Constructor for class. 
     */
    public SupplierHandler(String pathName, boolean validating) {
        // First let's trundle off to the parser factory...
        SAXParserFactory acmeParsersInc = SAXParserFactory.newInstance();

        /*
         * Configure our factory's production line to produce validating or
         * non-validating parsers as desired. There are other features that 
         * could be set here too.
         */
        acmeParsersInc.setValidating(validating);

        try {
            /*
             * Do this in a try-catch block as stuff could go wrong. First, use our factory
             * to Create a JAXP SAXParser.
             */
            SAXParser mySaxParser = acmeParsersInc.newSAXParser();
            /*
             * A SAXParser is a wrapper for an XML readers and we really only need to talk
             * to the reader. Extract the reader for future reference and stash it in our
             * property.
             */
            xmlReader = mySaxParser.getXMLReader();
        } catch (Exception ex) {
            System.err.println(ex);
            System.exit(1);
        }

        /*
         * Set the ContentHandler of the XMLReader. This will be an object of some class
         * such as DefaultHandler which implements the ContentHandler interface. Come to
         * think of it, _we_ implement this interface so we can do it ourselves...
         */
        xmlReader.setContentHandler(this);

        /*
         * Set an ErrorHandler before parsing. This could also be a DefaultHandler since
         * that implements org.xml.sax.ErrorHandler. We will roll our own error handler
         * just for fun....
         */

        xmlReader.setErrorHandler(new MyErrorHandler(System.err));
        // xmlReader.setErrorHandler(new DefaultHandler());
        try {
            // Tell the XMLReader to parse the XML document
            xmlReader.parse(pathName);
        } catch (SAXException se) {
            System.err.println(se.getMessage());
            System.exit(1);
        } catch (IOException ioe) {
            System.err.println(ioe);
            System.exit(1);
        }
    }

    public static Map<String, Supplier> getSuppliers() {
        return suppliers;
    }

    private void reset() {
        sid = name = address = phone = "";
        attName = "";
        type = PhoneType.UNKNOWN;
        email = Supplier.UNKNOWN_EMAIL;
        url = Supplier.UNKNOWN_URL;
    }
}
