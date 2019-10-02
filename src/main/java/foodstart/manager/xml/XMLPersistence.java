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

import foodstart.manager.Managers;
import foodstart.manager.Persistence;
import foodstart.manager.exceptions.ExportFailureException;
import foodstart.manager.exceptions.ImportFailureException;
import foodstart.model.Constants;
import foodstart.model.DataType;
import foodstart.ui.FXExceptionDisplay;

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

	/**
	 * Constructs an instance of an XML persistence manager
	 */
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
			dBuilder.setErrorHandler(new SAXErrorHandler());
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
			throw new ExportFailureException("DocumentBuilder class not initialized");
		} else if (transformer == null) {
			throw new ExportFailureException("Transformer class not initialized");
		}

		Document doc = dBuilder.newDocument();
		parsers.get(dataType).export(doc, transformer);

		DOMSource source = new DOMSource(doc);

		try {
			FileWriter writer = new FileWriter(file);
			StreamResult result = new StreamResult(writer);
			transformer.transform(source, result);
			
			writer.close();
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
	 * @param directory Directory that the DTD files should be copied into
	 * @throws IOException if an exception occurs during file stream actions
	 */
	public void copyDTDFiles(File directory) throws IOException {
		for (DataType type : DataType.values()) {
			File file = new File(directory.getAbsolutePath() + File.separator + type.name().toLowerCase() + ".dtd");
			InputStream dtdFile = getClass().getResourceAsStream("/foodstart/dtd/" + type.name().toLowerCase() + ".dtd");
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

	@Override
	public boolean saveAllFiles() {
		boolean succeeded = true;
		for (File file : Constants.importOrder) {
			try {
				switch (file.getName().toLowerCase()) {
				case "ingredients.xml":
					exportFile(file, DataType.INGREDIENT);
					break;
				case "menu.xml":
					exportFile(file, DataType.MENU);
					break;
				case "recipes.xml":
					exportFile(file, DataType.RECIPE);
					break;
				case "sales_log.xml":
					exportFile(file, DataType.SALES_LOG);
					break;
				case "suppliers.xml":
					exportFile(file, DataType.SUPPLIER);
					break;
				}
			} catch (ExportFailureException e) {
				succeeded = false;
				FXExceptionDisplay.showException(e, false);
			}
		}
		return succeeded;
	}
	
	@Override
	public void loadAllFiles() {
		File directory = new File(Constants.persistencePath);
		if (!directory.isDirectory()) {
			if (directory.exists()) {
				throw new ImportFailureException("File " + directory.getAbsolutePath() + " is not a directory");
			} else {
				if (!directory.mkdir()) {
					throw new ImportFailureException("Permissions error creating directory " + directory.getAbsolutePath());
				}
			}
		}
		XMLPersistence persistence = (XMLPersistence) Managers.getDefaultPersistence();
		try {
			persistence.copyDTDFiles(directory);
		} catch (IOException e) {
			throw new ImportFailureException("Could not copy DTD files into target directory");
		}
		
		for (File file : Constants.importOrder) {
			if (file.isFile()) {
				switch (file.getName().toLowerCase()) {
					case "ingredients.xml":
						persistence.importFile(file, DataType.INGREDIENT);
						break;
					case "menu.xml":
						persistence.importFile(file, DataType.MENU);
						break;
					case "recipes.xml":
						persistence.importFile(file, DataType.RECIPE);
						break;
					case "sales_log.xml":
						persistence.importFile(file, DataType.SALES_LOG);
						break;
					case "suppliers.xml":
						persistence.importFile(file, DataType.SUPPLIER);
						break;
				}
			}
		}
	}
}
