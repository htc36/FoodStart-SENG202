package foodstart.manager.xml;

import foodstart.manager.Managers;
import foodstart.model.DataType;
import foodstart.model.PhoneType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses supplier XML files
 */
public class XMLSupplierParser extends XMLParser {

	/**
	 * The constructor class for the supplier parser
	 */
	public XMLSupplierParser() {
		super(DataType.SUPPLIER);
	}

	/**
	 * Imports a supplier file
	 *
	 * @param doc The XML document to parse
	 */
	public void parse(Document doc) {

		NodeList supplierNodes = doc.getDocumentElement().getChildNodes();

		for (int i = 0; i < supplierNodes.getLength(); i++) {
			Node node = supplierNodes.item(i);

			if (node instanceof Element) {
				Element element = (Element) node;
				parseOneSupplier(element);
			}
		}
	}

	/**
	 * Parse a single supplier element
	 * @param element The supplier XML element to parse
	 */
	private void parseOneSupplier(Element element) {
		int sid = Integer.parseInt(element.getElementsByTagName("sid").item(0).getTextContent());
		String name = element.getElementsByTagName("name").item(0).getTextContent();
		String address = element.getElementsByTagName("address").item(0).getTextContent();
		String phone = element.getElementsByTagName("phone").item(0).getTextContent();
		Element phoneTypeElement = (Element) element.getElementsByTagName("phone").item(0);
		PhoneType phoneType = PhoneType.matchType(phoneTypeElement.getAttribute("type"));
		String email = element.getElementsByTagName("email").item(0).getTextContent();
		String url = element.getElementsByTagName("url").item(0).getTextContent();
		Managers.getSupplierManager().addSupplier(sid, name, phone, phoneType, email, url, address);
	}

}
