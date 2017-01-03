package com.progressoft.jip.gateway.exception;

public class NoAccountInsertedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NoAccountInsertedException() {
		super();
	}

	public NoAccountInsertedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoAccountInsertedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoAccountInsertedException(String message) {
		super(message);
	}

	public NoAccountInsertedException(Throwable cause) {
		super(cause);
	}

}
