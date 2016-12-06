package com.progressoft.jip.gateway;

import java.util.Collection;

import com.progressoft.jip.datastructures.CurrencyDataStructure;

public interface CurrencyGateway {

    Collection<CurrencyDataStructure> loadCurrencies();

    CurrencyDataStructure loadCurrencyByCode(String currencyCode);

}
