package foodstart.manager.xml;

import foodstart.model.DataType;
import foodstart.manager.Managers;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.time.Month;
import java.time.Year;


public class XMLSalesLogParser extends XMLParser {
    /**
     * Constructor for recipe parser
     */
    public XMLSalesLogParser() {
        super(DataType.SALES_LOG);
    }

    @Override
    public void parse(Document doc) {
        System.out.println(doc);
        NodeList salesNodes = doc.getChildNodes();

        for (int j = 0; j < salesNodes.getLength(); j++) {
            if (salesNodes.item(j) instanceof Element && salesNodes.item(j).getNodeName().equalsIgnoreCase("sales")) {
                if (salesNodes.item(0).getNodeName().equalsIgnoreCase("sales")) {
                    Element element = (Element) salesNodes.item(j);
                    NodeList nodes = element.getElementsByTagName("sale");
                    for (int i = 0; i < nodes.getLength(); i++) {
                        Node saleNode = nodes.item(i);
                        if (saleNode instanceof Element) {
//                            parseOneSale((Element) saleNode);
                            System.out.println("123");
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
//    private void parseOneSale(Element element) {
//        int id = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
//        String name = element.getElementsByTagName("name").item(0).getTextContent();
//        System.out.println("ytfluihrfytius");
//        long date =  Long.parseLong(element.getElementsByTagName("year").item(0).getTextContent());
//        float cost = Float.parseFloat(element.getElementsByTagName("cost").item(0).getTextContent());
//    }
}
