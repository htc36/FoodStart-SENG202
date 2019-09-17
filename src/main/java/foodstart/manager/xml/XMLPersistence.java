package foodstart.manager.xml;

import foodstart.manager.Persistence;
import foodstart.manager.exceptions.ImportFailureException;
import foodstart.model.DataType;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Parses an XML file with a given DataType
 *
 * @author Alex Hobson on 22/08/2019
 */
public class XMLPersistence extends Persistence {

	/**
	 * Map of datatypes to XML parsers
	 */
	Map<DataType, XMLParser> parsers;

	public XMLPersistence() {
		parsers = new HashMap<DataType, XMLParser>();
		parsers.put(DataType.INGREDIENT, new XMLIngredientParser());
		parsers.put(DataType.MENU, new XMLMenuParser());
		parsers.put(DataType.RECIPE, new XMLRecipeParser());
		parsers.put(DataType.SUPPLIER, new XMLSupplierParser());
		parsers.put(DataType.SALES_LOG, new XMLSalesLogParser());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Imports a XML file of a given data type
	 */
	@Override
	public void importFile(File file, DataType dataType) {

		Document doc;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(file);
		} catch (ParserConfigurationException e) {
			throw new ImportFailureException("Parser had a configuration exception: " + e.getMessage());
		} catch (SAXException e) {
			throw new ImportFailureException("Parser threw a SAX Exception: " + e.getMessage());
		} catch (IOException e) {
			throw new ImportFailureException("Parser threw an IO Exception: " + e.getMessage());
		}

		parsers.get(dataType).parse(doc);
	}

	@Override
	public void exportFile(File file, DataType dataType) {
		// TODO
	}

	/**
	 * This will copy DTD files into the target directory, overwriting files if necessary
	 *
	 * @param directory Directory that the DTD files should be copied into
	 */
	public void copyDTDFiles(File directory) throws IOException {
		for (DataType type : DataType.values()) {
			File file = new File(directory.getAbsolutePath() + File.separator + type.name().toLowerCase() + ".dtd");
			InputStream dtdFile = getClass().getResourceAsStream("../../dtd/" + type.name().toLowerCase() + ".dtd");
			if (dtdFile != null) {
				FileOutputStream output = new FileOutputStream(file);
				byte[] fileContents = new byte[16384]; //dtd file shouldn't be bigger than 16kB
				int length = dtdFile.read(fileContents);
				dtdFile.close();

				output.write(fileContents, 0, length);
				output.close();
			}
		}
	}
}
