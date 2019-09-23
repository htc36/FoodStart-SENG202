package foodstart.manager.xml;

import foodstart.model.DataType;

import javax.xml.transform.Transformer;

import org.w3c.dom.Document;

/**
 * Abstract class for XML parsing
 *
 * @author Alex Hobson on 28/08/2019
 */
public abstract class XMLParser {

	/**
	 * The data type that is parsed
	 */
	private final DataType dataType;

	/**
	 * Constructs an instance of an XML parser for a given data type
	 * @param dataType the data type of the parser
	 */
	public XMLParser(DataType dataType) {
		this.dataType = dataType;
	}

	/**
	 * Gets the data type that this XML parser parses
	 *
	 * @return Data Type that this XML parser parses
	 */
	public DataType getDataType() {
		return this.dataType;
	}

	/**
	 * Parse a given XML document
	 *
	 * @param doc Document to parse
	 */
	public abstract void parse(Document doc);
	
	/**
	 * Export a given XML document
	 * @param doc Document to populate with data
	 * @param transformer transformer for the XML tree
	 */
	public abstract void export(Document doc, Transformer transformer);

}
