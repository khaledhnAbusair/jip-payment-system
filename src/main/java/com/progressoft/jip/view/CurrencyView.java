package com.progressoft.jip.view;

import com.progressoft.jip.datastructures.builder.CurrencyViewBuilder;

public class CurrencyView implements CurrencyViewBuilder {
	private String name;
	private String code;
	private String coinsName;

	public String getName() {
		return name;
	}

	public CurrencyView setName(String name) {
		this.name = name;
		return this;
	}

	public String getCode() {
		return code;
	}

	public CurrencyView setCode(String code) {
		this.code = code;
		return this;
	}

	public String getCoinsName() {
		return coinsName;
	}

	public CurrencyView setCoinsName(String coinsName) {
		this.coinsName = coinsName;
		return this;
	}

	public CurrencyView build() {
		return new CurrencyView().setCode(code).setName(name).setCoinsName(coinsName);
	}
}
