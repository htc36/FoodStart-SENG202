package foodstart.manager.exceptions;

/**
 * Exception when there is not enough stock to do a particular action
 * 
 * @author Alex Hobson
 * @date 08/09/2019
 */
public class InsufficientStockException extends RuntimeException {

	/**
	 * Constructor for this class
	 * @param message Reason why the exception was thrown
	 */
	public InsufficientStockException(String message) {
		super(message);
	}
}
