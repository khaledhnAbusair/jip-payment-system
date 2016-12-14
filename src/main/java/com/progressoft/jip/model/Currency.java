package com.progressoft.jip.model;

import com.progressoft.jip.datastructures.CurrencyDataStructure;
import com.progressoft.jip.datastructures.builder.CurrencyViewBuilder;
import com.progressoft.jip.view.CurrencyView;

public class Currency {

    private CurrencyDataStructure currency;

    public Currency(CurrencyDataStructure currency) {
        this.currency = currency;
    }

    public CurrencyViewBuilder buildCurrencyView(CurrencyViewBuilder builder) {
        return builder.setName(currency.getCurrencyDescription())
                .setCode(currency.getCurrencyCode());
    }
}
