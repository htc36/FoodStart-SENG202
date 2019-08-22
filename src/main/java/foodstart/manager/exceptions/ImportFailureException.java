package foodstart.manager.exceptions;

public class ImportFailureException extends RuntimeException {
	
	/**
	 * 
	 * @param message The reason why the import failed
	 */
	public ImportFailureException(String message) {
		super(message);
	}
}
