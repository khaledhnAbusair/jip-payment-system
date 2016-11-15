package com.progressoft.jip.iban.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "index")
@XmlAccessorType(XmlAccessType.FIELD)
public class IBANCountryFormatsSettings {
	@XmlElement(name = "countryNameIndex")
	private int countryNameIndex;
	@XmlElement(name = "countryCodeIndex")
	private int countryCodeIndex;
	@XmlElement(name = "ibanFormatIndex")
	private int ibanFormatIndex;
	@XmlElement(name = "ibanLengthIndex")
	private int ibanLengthIndex;

	public void setCountryNameIndex(int countryNameIndex) {
		this.countryNameIndex = countryNameIndex;
	}

	public void setCountryCodeIndex(int countryCodeIndex) {
		this.countryCodeIndex = countryCodeIndex;
	}

	public void setIbanFormatIndex(int ibanFormatIndex) {
		this.ibanFormatIndex = ibanFormatIndex;
	}

	public void setIbanLength(int ibanLengthIndex) {
		this.ibanLengthIndex = ibanLengthIndex;
	}

	public int getCountryNameIndex() {
		return countryNameIndex;
	}

	public int getCountryCodeIndex() {
		return countryCodeIndex;
	}

	public int getIbanFormatIndex() {
		return ibanFormatIndex;
	}

	public int getIbanLengthIndex() {
		return ibanLengthIndex;
	}

	public int getIndexByName(String columnName) {

		if (columnName.equals("countryCodeIndex"))
			return countryCodeIndex;
		if (columnName.equals("countryNameIndex"))
			return countryNameIndex;
		if (columnName.equals("ibanFormatIndex"))
			return ibanFormatIndex;
		if (columnName.equals("ibanLengthIndex"))
			return ibanLengthIndex;
		return -1;
	}
}
