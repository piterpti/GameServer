package pl.elukasik.exception;

/**
 * Exception that is thrown when connection to plater is lost
 * 
 * @author plukasik
 *
 */
public class ConnectionLostException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param description
	 */
	public ConnectionLostException(String description) {
		super(description);
	}
	
	/**
	 * 
	 * @param description
	 * @param cause
	 */
	public ConnectionLostException(String description, Throwable cause) {
		super(description, cause);
	}
	
}
