package com.progressoft.jip.datastructures.builder;

import com.progressoft.jip.datastructures.AccountDatastructure;

public interface AccountDataStructureBuilder {

	AccountDataStructureBuilder setIBAN(String IBAN);

	AccountDataStructureBuilder setType(String type);

	AccountDataStructureBuilder setBalance(double balance);

	AccountDataStructureBuilder setStatus(String status);

	AccountDataStructureBuilder setCurrencyCode(String currencyCode);

	AccountDataStructureBuilder setRule(String rule);

	AccountDatastructure build();
}
