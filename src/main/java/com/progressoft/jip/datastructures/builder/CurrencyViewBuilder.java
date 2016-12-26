package com.progressoft.jip.datastructures.builder;

import com.progressoft.jip.view.CurrencyView;

public interface CurrencyViewBuilder {

	CurrencyViewBuilder setCode(String code);

	CurrencyView build();
}
