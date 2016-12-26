package com.progressoft.jip.gateway.impl;

import java.util.Objects;

import com.progressoft.jip.datastructures.CurrencyExchangeRateDataStructure;
import com.progressoft.jip.gateway.AbstractRestfullGateway;
import com.progressoft.jip.gateway.CurrencyExchangeRateGateway;
import com.progressoft.jip.gateway.exception.NullRestfulParserException;
import com.progressoft.jip.gateway.exception.CurrencyCodeNotFoundException;
import com.progressoft.jip.gateway.exception.InvalidRestfulResponseFormatException;
import com.progressoft.jip.utilities.Utilities;
import com.progressoft.jip.utilities.restful.RestfulDataParser;
import com.progressoft.jip.utilities.restful.RestfulResponseFormat;
import com.progressoft.jip.utilities.restful.impl.YahooXmlResponse;

public class YahooCurrencyExchangeRateGateway extends AbstractRestfullGateway<YahooXmlResponse>
		implements CurrencyExchangeRateGateway {

	private static final String YAHOO_SERVER = "http://query.yahooapis.com/v1/public/yql?q=";
	private static final String YAHOO_QUERY = "select * from yahoo.finance.xchange where pair in";
	private RestfulResponseFormat responseFormat;

	public YahooCurrencyExchangeRateGateway(RestfulResponseFormat format, RestfulDataParser<YahooXmlResponse> parser) {
		super(parser);
		if (Objects.isNull(format))
			throw new InvalidRestfulResponseFormatException();
		if (Objects.isNull(parser))
			throw new NullRestfulParserException();
		this.responseFormat = format;
	}

	@Override
	public CurrencyExchangeRateDataStructure loadCurrencyExchangeRate(String codeFrom, String codeTo) {
		if (!isValidCode(codeFrom) || !isValidCode(codeTo))
			throw new CurrencyCodeNotFoundException();
		YahooXmlResponse response = super.response(url(codeFrom, codeTo));
		if (!isValidResponse(response))
			throw new CurrencyCodeNotFoundException();
		return new CurrencyExchangeRateDataStructure(response.fromCode(), response.toCode(),
				Double.parseDouble(response.rate()));
	}

	private boolean isValidCode(String codeFrom) {
		return !Objects.isNull(codeFrom) && !codeFrom.isEmpty();
	}

	private boolean isValidResponse(YahooXmlResponse response) {
		return !"N".equals(response.toCode()) && !"A".equals(response.fromCode());
	}

	private String url(String codeFrom, String codeTo) {
		return YAHOO_SERVER + Utilities.utf_8_encodded(YAHOO_QUERY + " (\"" + codeTo + codeFrom + "\")")
				+ "&env=store://datatables.org/alltableswithkeys&format=" + responseFormat;
	}

}