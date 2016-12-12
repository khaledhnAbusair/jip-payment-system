package com.progressoft.jip.iban.impl;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.progressoft.jip.iban.IBANCountryFormatsReader;
import com.progressoft.jip.iban.IBANValidator;
import com.progressoft.jip.iban.IBANVersion;
import com.progressoft.jip.iban.exception.EmptyIBANException;
import com.progressoft.jip.iban.exception.NullIBANException;
import com.progressoft.jip.iban.exception.TooShortIBANException;

@IBANVersion("ISO13616")
public class IBANFormatValidator implements IBANValidator {
    private static final String FORMAT_REGEX = "([0-9]{1,2})!([nac])";
    private static final int NUMBER_GROUP = 1;
    private static final int TYPE_GROUP = 2;
    private IBANCountryFormatsReader countryFormats = new IBANCountryFormatsReaderImp();

    @Override
    public boolean isValid(String iban) {
	if (Objects.isNull(iban))
	    throw new NullIBANException();
	if (iban.trim().isEmpty())
	    throw new EmptyIBANException();
	if (iban.length() < 2)
	    throw new TooShortIBANException();
	String format = countryFormats.getIBANFormat(iban.substring(0, 2));
	if (Pattern.matches(generateRegEx(format), iban))
	    return true;
	return false;
    }

    private String generateRegEx(String format) {
	StringBuffer regex = new StringBuffer();
	compileFormatPattern(format, regex);
	return regex.toString();
    }

    private void compileFormatPattern(String format, StringBuffer regex) {
	Matcher numberMatcher = Pattern.compile(FORMAT_REGEX).matcher(format);
	while (numberMatcher.find())
	    getAndReplaceMatch(regex, numberMatcher);
    }

    private void getAndReplaceMatch(StringBuffer regex, Matcher numberMatcher) {
	String number = numberMatcher.group(NUMBER_GROUP);
	String type = numberMatcher.group(TYPE_GROUP);
	numberMatcher.appendReplacement(regex, getReplacement(number, type));
    }

    private String getReplacement(String number, String type) {
	if (type.equals("n"))
	    return "[0-9]{" + number + "}";
	if (type.equals("a"))
	    return "[a-zA-Z]{" + number + "}";
	if (type.equals("c"))
	    return "[0-9a-zA-Z]{" + number + "}";
	return null;
    }
}
