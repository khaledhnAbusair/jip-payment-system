package com.progressoft.jip.gateway.exception;

public class NoAccountUpdatedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoAccountUpdatedException() {
		super();
	}

	public NoAccountUpdatedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoAccountUpdatedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoAccountUpdatedException(String message) {
		super(message);
	}

	public NoAccountUpdatedException(Throwable cause) {
		super(cause);
	}

}
