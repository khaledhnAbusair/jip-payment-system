package com.progressoft.jip.model.exception;

public class AccountRuleViolationException extends Exception {

	private static final long serialVersionUID = 1L;

	public AccountRuleViolationException() {
		super();
	}

	public AccountRuleViolationException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public AccountRuleViolationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public AccountRuleViolationException(String arg0) {
		super(arg0);
	}

	public AccountRuleViolationException(Throwable arg0) {
		super(arg0);
	}

}
