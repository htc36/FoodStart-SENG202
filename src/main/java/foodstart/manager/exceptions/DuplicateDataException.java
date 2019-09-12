package foodstart.manager.exceptions;

import foodstart.model.DataType;

/**
 * Exception for importing data when there is a duplicate in memory already
 *
 * @author Alex Hobson on 07/09/2019
 */
public class DuplicateDataException extends ImportFailureException {

	/**
	 * Constructor for this exception
	 *
	 * @param type           The type of data that is duplicate
	 * @param duplicateField The value of the duplicate field
	 */
	public DuplicateDataException(DataType type, String duplicateField) {
		super("Duplicate " + type.name() + " data '" + duplicateField + "'");
	}

}
