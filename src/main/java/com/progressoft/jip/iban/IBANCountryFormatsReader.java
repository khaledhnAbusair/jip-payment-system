package com.progressoft.jip.iban;

public interface IBANCountryFormatsReader {
    String getCountryName(String countryCode);

    String getIBANFormat(String countryCode);

    int getIBANLength(String countryCode);

    boolean lookupCountryCode(String countryCode);
}
