package foodstart.manager.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import foodstart.manager.Managers;
import foodstart.model.DataType;
import foodstart.model.PhoneType;

public class XMLSupplierParser extends XMLParser {

	public XMLSupplierParser() {
		super(DataType.SUPPLIER);
	}

	public void parse(Document doc) {
		System.out.println("PARSING");

		NodeList supplierNodes = doc.getChildNodes();
		for (int i = 0; i < supplierNodes.getLength(); i++) {
			System.out.println("node");
			Node node = supplierNodes.item(i);
			if (node instanceof Element) {
				Element element = (Element) node;
				parseOneSupplier(element);
			}
		}
	}

	private void parseOneSupplier(Element element) {
		int sid = Integer.parseInt(element.getElementsByTagName("sid").item(0).getTextContent());
		String name = element.getElementsByTagName("name").item(0).getTextContent();
		String address = element.getElementsByTagName("address").item(0).getTextContent();
		String phone = element.getElementsByTagName("phone").item(0).getTextContent();
		PhoneType phoneType = PhoneType.matchType(element.getAttribute("type"));
		String email = element.getElementsByTagName("email").item(0).getTextContent();
		String url = element.getElementsByTagName("url").item(0).getTextContent();
		System.out.println(name + " " + address + " " + url);
		Managers.getSupplierManager().addSupplier(sid, name, phone, phoneType, email, url, address);
	}
	
}
