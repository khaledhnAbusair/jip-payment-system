package com.progressoft.jip.iban.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.iban.exception.CountryCodeNotFoundException;
import com.progressoft.jip.iban.exception.EmptyIBANException;
import com.progressoft.jip.iban.exception.NullIBANException;
import com.progressoft.jip.iban.exception.TooShortIBANException;

public class IBANFormatValidatorTest {

    private IBANFormatValidator ibanFormatValidator;

    @Before
    public void createIBANFormatValidator() {
	ibanFormatValidator = new IBANFormatValidator();
    }

    @Test(expected = CountryCodeNotFoundException.class)
    public void givenIBANFormatValidator_CallingIsValid_PassingNoneValidFormatIBAN_WithNoneExistingCountryCode_ShoudThrowCountryCodeNotFound() {
	Assert.assertFalse(ibanFormatValidator.isValid("IBRO"));
    }

    @Test
    public void givenIBANFormatValidator_CallingIsValid_PassingNoneValidFormatIBAN_ShoudReturnFalse() {
	Assert.assertFalse(ibanFormatValidator.isValid("CRCR"));
    }
    
    @Test(expected = TooShortIBANException.class)
    public void givenIBANFormatValidator_CallingIsValid_PassingNoneValidFormatIBAN_ShoudThrowTooShortIBAN() {
	Assert.assertFalse(ibanFormatValidator.isValid("T"));
    }

    @Test
    public void givenIBANFormatValidator_CallingIsValid_PassingValidFormatIBAN_ShoudReturnTrueTrue() {
	Assert.assertTrue(ibanFormatValidator.isValid("CR0515202001026284066"));
    }

    @Test(expected = NullIBANException.class)
    public void givenIBANFormatValidator_CallingIsValid_PassingNullIBAN_ShoudThrowNullIBAN() {
	ibanFormatValidator.isValid(null);
    }

    @Test(expected = EmptyIBANException.class)
    public void givenIBANFormatValidator_CallingIsValid_PassingEmptyIBAN_ShoudThrowNullIBAN() {
	ibanFormatValidator.isValid("");
    }

}
