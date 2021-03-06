/**
 * InvalidDirectionException is thrown when the Mirror direction is invalid.
 * 
 * @author Bankim Aghera
 *
 */
public class InvalidDirectionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6108139048916656780L;

	/**
	 * Constructs a new exception with null as its detail message. The cause is not
	 * initialized, and may subsequently be initialized by a call to
	 * Throwable.initCause(java.lang.Throwable).
	 */
	public InvalidDirectionException() {
		super();
	}

	/**
	 * Constructs a new exception with the specified detail message. The cause is
	 * not initialized, and may subsequently be initialized by a call to
	 * Throwable.initCause(java.lang.Throwable).
	 * 
	 * @param message
	 *            - the detail message. The detail message is saved for later
	 *            retrieval by the Throwable.getMessage() method.
	 */
	public InvalidDirectionException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified cause and a detail message of
	 * (cause==null ? null : cause.toString()) (which typically contains the class
	 * and detail message of cause). This constructor is useful for exceptions that
	 * are little more than wrappers for other throwables (for example,
	 * PrivilegedActionException).
	 * 
	 * @param cause
	 *            - the cause (which is saved for later retrieval by the
	 *            Throwable.getCause() method). (A null value is permitted, and
	 *            indicates that the cause is nonexistent or unknown.)
	 */
	public InvalidDirectionException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * 
	 * Note that the detail message associated with cause is not automatically
	 * incorporated in this exception's detail message.
	 * 
	 * @param message
	 *            - the detail message (which is saved for later retrieval by the
	 *            Throwable.getMessage() method).
	 * @param cause
	 *            - the cause (which is saved for later retrieval by the
	 *            Throwable.getCause() method). (A null value is permitted, and
	 *            indicates that the cause is nonexistent or unknown.)
	 */
	public InvalidDirectionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new exception with the specified detail message, cause,
	 * suppression enabled or disabled, and writable stack trace enabled or
	 * disabled.
	 * 
	 * @param message
	 *            - the detail message.
	 * @param cause
	 *            - the cause. (A null value is permitted, and indicates that the
	 *            cause is nonexistent or unknown.)
	 * @param enableSuppression
	 *            - whether or not suppression is enabled or disabled
	 * @param writableStackTrace
	 *            - whether or not the stack trace should be writable
	 */
	protected InvalidDirectionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
