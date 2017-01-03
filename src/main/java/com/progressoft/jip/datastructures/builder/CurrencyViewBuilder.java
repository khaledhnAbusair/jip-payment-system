package com.progressoft.jip.datastructures.builder;

import com.progressoft.jip.view.CurrencyView;

public interface CurrencyViewBuilder {

	CurrencyViewBuilder setCode(String code);

	CurrencyViewBuilder setName(String name);

	CurrencyViewBuilder setCoinsName(String coinsName);

	CurrencyView build();
}
