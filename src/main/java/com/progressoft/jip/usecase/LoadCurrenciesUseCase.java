package com.progressoft.jip.usecase;

import com.progressoft.jip.context.AppContext;
import com.progressoft.jip.repository.CurrencyRepository;
import com.progressoft.jip.view.CurrencyView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LoadCurrenciesUseCase {

    private CurrencyRepository repository;

    public LoadCurrenciesUseCase(CurrencyRepository repository) {
        this.repository = repository;
    }

    public Collection<CurrencyView> loadCurrencies() {
        List<CurrencyView> currencies = new ArrayList<>();
        CurrencyView currencyView = new CurrencyView();
        repository.loadCurrencies().stream().forEach((currency) -> {
            currency.buildCurrencyView(currencyView);
            currencies.add(currencyView.build());
        });
        return currencies;
    }

}
