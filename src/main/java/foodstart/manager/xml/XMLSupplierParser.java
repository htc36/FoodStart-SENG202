package foodstart.manager.xml;

import foodstart.manager.Managers;
import foodstart.manager.stock.SupplierManager;
import foodstart.model.DataType;
import foodstart.model.PhoneType;
import foodstart.model.stock.Supplier;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;

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
	
	
	/**
	 * Exports a supplier file
	 * 
	 * @param doc The XML document to write the data to
	 */
	@Override
	public void export(Document doc, Transformer transformer) {
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "supplier.dtd");
		exportWithManager(doc, Managers.getSupplierManager());
	}
	
	public void exportWithManager(Document doc, SupplierManager manager) {
		Element supplierRoot = doc.createElement("suppliers");
		for (Supplier supplier : manager.getSupplierSet()) {
			Element root = doc.createElement("supplier");
			
			Element sid = doc.createElement("sid");
			sid.appendChild(doc.createTextNode(String.valueOf(supplier.getId())));
			root.appendChild(sid);
			
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(String.valueOf(supplier.getSupplierName())));
			root.appendChild(name);
			
			Element address = doc.createElement("address");
			address.appendChild(doc.createTextNode(String.valueOf(supplier.getAddress())));
			root.appendChild(address);
			
			Element phone = doc.createElement("phone");
			phone.setAttribute("type", String.valueOf(supplier.getPhoneType()));
			root.appendChild(doc.createTextNode(String.valueOf(supplier.getPhoneNumber())));
			
			Element email = doc.createElement("email");
			email.appendChild(doc.createTextNode(String.valueOf(supplier.getEmail())));
			root.appendChild(email);
			
			Element url = doc.createElement("url");
			url.appendChild(doc.createTextNode(String.valueOf(supplier.getUrl())));
			root.appendChild(url);
			supplierRoot.appendChild(root);

		}
		doc.appendChild(supplierRoot);
	}
	

}
