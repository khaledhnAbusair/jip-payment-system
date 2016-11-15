package com.progressoft.jip.iban.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.progressoft.jip.iban.IBANCountryFormatsReader;
import com.progressoft.jip.iban.IBANVersion;

@IBANVersion("ISO13616")
public class IBANCountryFormatsReaderImp implements IBANCountryFormatsReader {
    private static final String IBAN_COUNTRY_FORMATS_FILE = "IBANCountryFormats.csv";
    private static final String IBAN_COUNTRY_FORMATS_SETTINGS = "IBANCountryFormatsSettings.xml";
    private static final String COMMA_REGEX = ",";

    private IBANCountryFormatsSettings ibanCountryFormatsSettings;

    public IBANCountryFormatsReaderImp() {
	initializeSettings();
    }

    private void initializeSettings() {
	try {
	    JAXBContext jaxbContext = JAXBContext.newInstance(IBANCountryFormatsSettings.class);
	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	    ibanCountryFormatsSettings = (IBANCountryFormatsSettings) jaxbUnmarshaller.unmarshal(new File(
		    IBAN_COUNTRY_FORMATS_SETTINGS));
	} catch (JAXBException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public String getCountryName(String countryCode) {
	return getLineContainingCountryCode(countryCode).get().split(COMMA_REGEX)[ibanCountryFormatsSettings
		.getIndexByName("countryNameIndex")];
    }

    @Override
    public String getIBANFormat(String countryCode) {
	return getLineContainingCountryCode(countryCode).get().split(COMMA_REGEX)[ibanCountryFormatsSettings
		.getIndexByName("ibanFormatIndex")];
    }

    @Override
    public int getIBANLength(String countryCode) {
	return Integer
		.parseInt(getLineContainingCountryCode(countryCode).get().split(COMMA_REGEX)[ibanCountryFormatsSettings
			.getIndexByName("ibanLengthIndex")]);
    }

    @Override
    public boolean lookupCountryCode(String countryCode) {
	return getLineContainingCountryCode(countryCode).isPresent();
    }

    private Optional<String> getLineContainingCountryCode(String countryCode) {
	try (BufferedReader br = new BufferedReader(new FileReader(new File(IBAN_COUNTRY_FORMATS_FILE)))) {
	    return br.lines().filter(containsCountryCode(countryCode)).findAny();
	} catch (IOException e) {
	    throw new IllegalArgumentException();
	}
    }

    private Predicate<String> containsCountryCode(String countryCode) {
	return l -> l.split(COMMA_REGEX)[ibanCountryFormatsSettings.getIndexByName("countryCodeIndex")]
		.equals(countryCode);
    }
}
