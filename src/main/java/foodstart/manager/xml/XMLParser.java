package foodstart.manager.xml;

import org.w3c.dom.Document;

import foodstart.model.DataType;

/**
 * Abstract class for XML parsing
 * 
 * @author Alex Hobson
 * @date 28/08/2019
 */
public abstract class XMLParser {

	private final DataType dataType;
	
	public XMLParser(DataType dataType) {
		this.dataType = dataType;
	}
	
	/**
	 * Gets the data type that this XML parser parses
	 * @return Data Type that this XML parser parses
	 */
	public DataType getDataType() {
		return this.dataType;
	}
	
	/**
	 * Parse a given XML document
	 * @param doc Document to parse
	 */
	public abstract void parse(Document doc);

}
