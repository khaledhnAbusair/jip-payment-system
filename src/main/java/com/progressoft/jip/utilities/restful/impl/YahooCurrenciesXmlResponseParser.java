package com.progressoft.jip.utilities.restful.impl;

import com.progressoft.jip.utilities.restful.RestfulDataParser;
import com.thoughtworks.xstream.XStream;

public class YahooCurrenciesXmlResponseParser implements RestfulDataParser<YahooXmlResponse> {

	private XStream xstream = new XStream();

	@Override
	public YahooXmlResponse parse(String data) {
		xstream.autodetectAnnotations(true);
		xstream.processAnnotations(YahooXmlResponse.class);
		return (YahooXmlResponse) xstream.fromXML(data);
	}

}
