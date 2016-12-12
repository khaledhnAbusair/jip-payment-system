package com.progressoft.jip.iban.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.iban.exception.EmptyIBANException;
import com.progressoft.jip.iban.exception.NullIBANException;

public class IBANModValidatorTest {

    private IBANModValidator ibanModValidator;

    @Before
    public void createIBANModValidator() {
	ibanModValidator = new IBANModValidator();
    }

    @Test
    public void givenIBANModValidator_CallnigIsValid_PassingNoneValidIBAN_ShouldReturnFalse() {
	assertFalse(ibanModValidator.isValid("IBAN"));
    }

    @Test(expected = NullIBANException.class)
    public void givenIBANModValidator_CallnigIsValid_PassingNullIBAN_ShouldThrowNullIBAN() {
	assertFalse(ibanModValidator.isValid(null));
    }

    @Test(expected = EmptyIBANException.class)
    public void givenIBANModValidator_CallnigIsValid_PassingEmptyIBAN_ShouldThrowNullIBAN() {
	assertFalse(ibanModValidator.isValid(""));
    }

    @Test
    public void givenIBANModValidator_CallnigIsValid_PassingValidIBAN_ShouldReturnTrue() {
	assertTrue(ibanModValidator.isValid("CR0515202001026284066"));
    }
}
