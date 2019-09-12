package foodstart.manager.xml;

import foodstart.model.DataType;
import org.w3c.dom.Document;

/**
 * Abstract class for XML parsing
 *
 * @author Alex Hobson on 28/08/2019
 */
public abstract class XMLParser {

	private final DataType dataType;

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

}
