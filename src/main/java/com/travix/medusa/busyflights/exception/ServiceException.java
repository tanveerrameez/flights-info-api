package com.travix.medusa.busyflights.exception;

/**
 * @author tanveerrameez, 30 Oct 2021
 */

public class ServiceException extends Exception {

	/**
	 * Exception thrown by Service layer
	 */
	private static final long serialVersionUID = -6794470734163352472L;

	public ServiceException() {

	}

	public ServiceException(String message) {
		super(message);

	}

	public ServiceException(Throwable cause) {
		super(cause);

	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);

	}

	public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
