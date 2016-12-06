package com.progressoft.jip.factory;

import java.util.Collection;

import com.progressoft.jip.datastructures.CurrencyDataStructure;

public interface CurrencyGatewayDBBehaviorsFactory {
    
    Behavior<Collection<CurrencyDataStructure>> loadCurrencies();

    Behavior<CurrencyDataStructure> loadCurrencyByCode();

}
