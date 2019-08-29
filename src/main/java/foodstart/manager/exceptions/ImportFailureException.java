package foodstart.manager.exceptions;

/**
 * General exception where importing something fails
 * @author Alex Hobson
 * @date 28/08/2019
 */
public class ImportFailureException extends RuntimeException {
	
	/**
	 * Constructor for exception 
	 * 
	 * @param message The reason why the import failed
	 */
	public ImportFailureException(String message) {
		super(message);
	}
}
