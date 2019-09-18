package foodstart.manager.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import foodstart.manager.Persistence;
import foodstart.manager.exceptions.ExportFailureException;
import foodstart.manager.exceptions.ImportFailureException;
import foodstart.model.DataType;

/**
 * Parses an XML file with a given DataType
 *
 * @author Alex Hobson on 22/08/2019
 */
public class XMLPersistence extends Persistence {

	/**
	 * Map of datatypes to XML parsers
	 */
	private Map<DataType, XMLParser> parsers;

	/**
	 * Document builder
	 */
	private DocumentBuilder dBuilder;

	/**
	 * Transformer (for exporting)
	 */
	private Transformer transformer;

	public XMLPersistence() {
		parsers = new HashMap<DataType, XMLParser>();
		parsers.put(DataType.INGREDIENT, new XMLIngredientParser());
		parsers.put(DataType.MENU, new XMLMenuParser());
		parsers.put(DataType.RECIPE, new XMLRecipeParser());
		parsers.put(DataType.SUPPLIER, new XMLSupplierParser());
		parsers.put(DataType.SALES_LOG, new XMLSalesLogParser());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(true);
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		try {
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Imports a XML file of a given data type
	 */
	@Override
	public void importFile(File file, DataType dataType) {

		if (dBuilder == null) {
			throw new ImportFailureException("DocumentBuilder class not initialized");
		}

		try {
			Document doc = dBuilder.parse(file);
			parsers.get(dataType).parse(doc);
		} catch (IOException e) {
			throw new ImportFailureException("Parser threw an IO Exception: " + e.getMessage());
		} catch (SAXException e) {
			throw new ImportFailureException("Parser threw a SAX Exception: " + e.getMessage());
		}
	}

	@Override
	public void exportFile(File file, DataType dataType) {
		if (dBuilder == null) {
			throw new ImportFailureException("DocumentBuilder class not initialized");
		} else if (transformer == null) {
			throw new ImportFailureException("Transformer class not initialized");
		}
		
		Document doc = dBuilder.newDocument();
		parsers.get(dataType).export(doc, transformer);

		DOMSource source = new DOMSource(doc);

		try {
			FileWriter writer = new FileWriter(file);
			StreamResult result = new StreamResult(writer);
			transformer.transform(source, result);
		} catch (IOException e) {
			throw new ExportFailureException("Exporter threw an IO Exception: " + e.getMessage());
		} catch (TransformerException e) {
			throw new ExportFailureException("Exporter threw a TransformerException: " + e.getMessage());
		}
	}

	/**
	 * This will copy DTD files into the target directory, overwriting files if
	 * necessary
	 *
	 * @param directory
	 *            Directory that the DTD files should be copied into
	 */
	public void copyDTDFiles(File directory) throws IOException {
		for (DataType type : DataType.values()) {
			File file = new File(directory.getAbsolutePath() + File.separator + type.name().toLowerCase() + ".dtd");
			InputStream dtdFile = getClass().getResourceAsStream("../../dtd/" + type.name().toLowerCase() + ".dtd");
			if (dtdFile != null) {
				FileOutputStream output = new FileOutputStream(file);
				byte[] fileContents = new byte[16384]; // dtd file shouldn't be bigger than 16kB
				int length = dtdFile.read(fileContents);
				dtdFile.close();

				output.write(fileContents, 0, length);
				output.close();
			}
		}
	}
}
