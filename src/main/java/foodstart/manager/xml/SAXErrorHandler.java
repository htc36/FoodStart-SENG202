package foodstart.manager.xml;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import foodstart.manager.exceptions.ImportFailureException;

public class SAXErrorHandler implements ErrorHandler {
	
	public void warning(SAXParseException e) throws SAXException {
		throw new ImportFailureException("Warning: "+e.getMessage()+" Line "+e.getLineNumber()+" of "+e.getSystemId());
	}

	public void error(SAXParseException e) throws SAXException {
		throw new ImportFailureException("Error: "+e.getMessage()+" Line "+e.getLineNumber()+" of "+e.getSystemId());
	}

	public void fatalError(SAXParseException e) throws SAXException {
		throw new ImportFailureException("Fatal error: "+e.getMessage()+" Line "+e.getLineNumber()+" of "+e.getSystemId());
	}
}