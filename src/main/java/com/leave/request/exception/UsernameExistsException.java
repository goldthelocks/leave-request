/**
 * 
 */
package com.leave.request.exception;

/**
 * @author Eraine
 *
 */
public class UsernameExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UsernameExistsException() {
		super();
	}

	public UsernameExistsException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public UsernameExistsException(final String message) {
		super(message);
	}

	public UsernameExistsException(final Throwable cause) {
		super(cause);
	}
}
