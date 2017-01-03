package com.progressoft.jip.usecase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.progressoft.jip.repository.CurrencyRepository;
import com.progressoft.jip.view.CurrencyView;

public class LoadCurrenciesUseCase {

    private CurrencyRepository currencyRepository;

    public LoadCurrenciesUseCase(CurrencyRepository repository) {
        this.currencyRepository = repository;
    }

    public Collection<CurrencyView> loadCurrencies() {
        List<CurrencyView> currencies = new ArrayList<>();
        CurrencyView currencyView = new CurrencyView();
        currencyRepository.loadCurrencies().stream().forEach((currency) -> {
            currency.buildCurrencyView(currencyView);
            currencies.add(currencyView.build());
        });
        return currencies;
    }

}
