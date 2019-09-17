package foodstart.manager.exceptions;

/**
 * General exception where exporting something fails
 *
 * @author Alex Hobson on 17/09/2019
 */
public class ExportFailureException extends RuntimeException {

	/**
	 * Constructor for exception
	 *
	 * @param message The reason why the import failed
	 */
	public ExportFailureException(String message) {
		super(message);
	}
}
