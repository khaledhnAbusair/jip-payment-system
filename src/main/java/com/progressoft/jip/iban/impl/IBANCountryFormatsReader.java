package com.progressoft.jip.iban.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import com.progressoft.jip.iban.IBANVersion;

@IBANVersion("ISO13616")
public class IBANCountryFormatsReader {
    private static final String IBAN_COUNTRY_FORMATS_ISO13616_FILE = "IBANCountryFormats_ISO13616.csv";

    private int numOfLines;
    private String[] countryNames;
    private String[] countryCodes;
    private String[] ibanFormats;
    private int[] ibanLengths;

    private File file;
    private BufferedReader bufferedReader;

    public IBANCountryFormatsReader() {
	file = new File(IBAN_COUNTRY_FORMATS_ISO13616_FILE);
	try {
	    bufferedReader = new BufferedReader(new FileReader(file));
	    loadInformation();
	    bufferedReader.close();
	} catch (IOException ex) {
	    System.out.println(ex.getMessage());
	}
    }

    private void loadInformation() throws IOException {
	String line = bufferedReader.readLine();
	numOfLines = Integer.parseInt(line);
	parseLines();
    }

    private void parseLines() throws IOException {
	initializeArrays();
	for (int i = 0; i < numOfLines; i++) {
	    String info[] = bufferedReader.readLine().split(",");
	    addInfoToArrays(i, info);
	}
    }

    private void initializeArrays() {
	countryNames = new String[numOfLines];
	countryCodes = new String[numOfLines];
	ibanFormats = new String[numOfLines];
	ibanLengths = new int[numOfLines];
    }

    private void addInfoToArrays(int index, String[] info) {
	countryNames[index] = info[0];
	countryCodes[index] = info[1];
	ibanFormats[index] = info[2];
	ibanLengths[index] = Integer.parseInt(info[3]);
    }

    public String getCountryName(String countryCode) {
	int i = getCountryCodeIndex(countryCode);
	return countryNames[i];
    }

    public String getIBANFormat(String countryCode) {
	int i = getCountryCodeIndex(countryCode);
	return ibanFormats[i];
    }

    public int getIBANLength(String countryCode) {
	int i = getCountryCodeIndex(countryCode);

	return ibanLengths[i];
    }

    private int getCountryCodeIndex(String countryCode) {
	int i = Arrays.asList(countryCodes).indexOf(countryCode);
	return i;
    }
}
