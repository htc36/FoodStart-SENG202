package foodstart.manager.exceptions;

import foodstart.model.DataType;

/**
 * Exception where part of the data depends on something else
 * that is not loaded yet
 *
 * @author Alex Hobson on 28/08/2019
 */
public class IDLeadsNowhereException extends ImportFailureException {

	/**
	 * Constructor for exception
	 *
	 * @param type Expected data type
	 * @param id   Expected id
	 */
	public IDLeadsNowhereException(DataType type, int id) {
		super("Could not parse file: " + type.name() + " with id " + id + " does not yet exist in memory");
	}

}
