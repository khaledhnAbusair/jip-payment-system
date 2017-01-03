package com.progressoft.jip.utilities.chequewriting.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.progressoft.jip.utilities.chequewriting.ChequeAmountWriter;

public class AbstractAmountWriter {

	private Map<String, ChequeAmountWriter> amountWriters = new HashMap<>();

	public void addWriter(ChequeAmountWriter chequeAmountWriter, String Key) {
		amountWriters.put(Key, chequeAmountWriter);
	}

	public String writeAmount(BigDecimal bigDecimal, String currencyCode, String key) {
		return amountWriters.get(key).writeAmountInWords(bigDecimal, currencyCode);
	}
}
