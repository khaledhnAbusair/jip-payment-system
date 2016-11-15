package com.progressoft.jip.iban;

public interface IBANCountryFormatsReader {
    public String getCountryName(String countryCode);

    public String getIBANFormat(String countryCode);

    public int getIBANLength(String countryCode);

    public boolean lookupCountryCode(String countryCode);
}
