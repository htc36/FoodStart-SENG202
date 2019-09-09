package foodstart.manager.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import foodstart.model.DataType;
import foodstart.model.PhoneType;

public class XMLSupplierParser extends XMLParser {

	public XMLSupplierParser(DataType dataType) {
		super(DataType.SUPPLIER);
	}

	public void parse(Document doc) {
		NodeList supplierNodes = doc.getChildNodes();
		for (int i = 0; i < supplierNodes.getLength(); i++) {
			if (supplierNodes instanceof Element && supplierNodes.item(i).getNodeName().equalsIgnoreCase("suppliers")) {
				
				if (supplierNodes.item(0).getNodeName().equalsIgnoreCase("suppliers")) {
					Element element = (Element) supplierNodes.item(i);
					NodeList nodes = element.getElementsByTagName("supplier");
					for (int j = 0; j < nodes.getLength(); j++) {
						Node supplierNode = nodes.item(j);
						if (supplierNode instanceof Element) {
							parseOneSupplier((Element) supplierNode);
					
						}
					}
				}
			}
		}
	}

	private int parseOneSupplier(Element element) {
		int sid = Integer.parseInt(element.getElementsByTagName("sid").item(0).getTextContent());
		String name = element.getElementsByTagName("name").item(0).getTextContent();
		String address = element.getElementsByTagName("address").item(0).getTextContent();
		String phone = element.getElementsByTagName("phone").item(0).getTextContent();
		PhoneType phoneType = PhoneType.matchType(element.getAttribute("type"));
		String email = element.getElementsByTagName("email").item(0).getTextContent();
		String url = element.getElementsByTagName("url").item(0).getTextContent();
		return sid;
	}
	
}
