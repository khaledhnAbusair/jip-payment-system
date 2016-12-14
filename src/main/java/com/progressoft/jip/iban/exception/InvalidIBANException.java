package com.progressoft.jip.iban.exception;

public class InvalidIBANException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidIBANException(String e) {
	super(e);
    }

}
