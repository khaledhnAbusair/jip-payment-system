package com.progressoft.jip.utilities;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.progressoft.jip.exception.DataExceedingCodeColumnLimitException;
import com.progressoft.jip.exception.DataExceedingNameColumnLimitException;
import com.progressoft.jip.exception.EmptyPaymentPurposeCodeException;
import com.progressoft.jip.exception.NullPaymentPurposeCodeException;
import com.progressoft.jip.exception.NullPaymentPurposeNameException;

public class Validators {
    
    public interface Validator<T> {
	void validate(T str);
    }

    public static final Validator<String> NULL_CODE_VALIDATOR = (code) -> {
	if (Objects.isNull(code))
	    throw new NullPaymentPurposeCodeException();
    };
    public static final Validator<String> NULL_NAME_VALIDATOR = (name) -> {
	if (Objects.isNull(name))
	    throw new NullPaymentPurposeNameException();
    };
    public static final Validator<String> EMPTY_CODE_VALIDATOR = (code) -> {
	if (code.isEmpty())
	    throw new EmptyPaymentPurposeCodeException();
    };

    public static final Validator<String> CODE_LENGTH_VALIDATOR = (code) -> {
	if (code.length() > Constants.PAYMENT_PURPOSE_CODE_COLUMN_MAX_LENGTH)
	    throw new DataExceedingCodeColumnLimitException();
    };

    public static final Validator<String> NAME_LENGTH_VALIDATOR = (name) -> {
	if (name.length() > Constants.PAYMENT_PURPOSE_NAME_COLUMN_MAX_LENGTH)
	    throw new DataExceedingNameColumnLimitException();
    };

    public final static List<Validator<String>> CODE_VALIDATORS = Arrays.asList(NULL_CODE_VALIDATOR,
	    EMPTY_CODE_VALIDATOR);
}
