package com.progressoft.jip.iban.impl;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.progressoft.jip.iban.IBANValidator;
import com.progressoft.jip.iban.IBANVersion;
import com.progressoft.jip.iban.exception.EmptyIBANException;
import com.progressoft.jip.iban.exception.NullIBANException;

@IBANVersion("EBS204v3.2")
public class IBANModValidator implements IBANValidator {
    private final int SEVEN_DIGITS = 7;
    private final int CHARS_TO_MOVE = 4;
    private final long TEN_POW_SEVEN_MOD_97 = 10_000_000 % 97;

    @Override
    public boolean isValid(String iban) {
	if (Objects.isNull(iban))
	    throw new NullIBANException();
	if (iban.trim().isEmpty())
	    throw new EmptyIBANException();
	String result = iban.substring(CHARS_TO_MOVE) + iban.substring(0, CHARS_TO_MOVE);
	result = convertLettersToNumbers(result);
	return mod97(result) == 1;
    }

    private long mod97(String number) {
	int start = number.length();
	int end = start;
	long base = 1;
	long result = 0;

	while (start > 0) {
	    start -= SEVEN_DIGITS;
	    if (start < 0)
		start = 0;
	    String digits = number.substring(start, end);
	    result = AddDigitsMod97ToNumber(result, base, digits);
	    base = (base * TEN_POW_SEVEN_MOD_97) % 97;
	    end -= SEVEN_DIGITS;
	}

	return result;
    }

    private long AddDigitsMod97ToNumber(long number, long base, String digits) {
	long digitsMod = Long.parseLong(digits) % 97;
	digitsMod = (digitsMod * base) % 97;
	return (number + digitsMod) % 97;
    }

    private String convertLettersToNumbers(String IBANCode) {
	return Stream.of(IBANCode.split("")).map(c -> isAlphabet(c) ? getNumberFromAlphabet(c) : c)
		.collect(Collectors.joining());
    }

    private String getNumberFromAlphabet(String alpha) {
	return Integer.toString((int) (alpha.charAt(0) - 'A' + 10));
    }

    private boolean isAlphabet(String character) {
	char chr = character.charAt(0);
	if (chr >= 'A' && chr <= 'Z')
	    return true;
	return false;
    }
}
