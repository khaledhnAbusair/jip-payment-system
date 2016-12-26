package com.progressoft.jip.utilities.restful.impl;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("query")
public class YahooXmlResponse {

	private List<Rate> results;

	public String fromCode() {
		return results.get(0).name.split("/")[1];
	}

	public String toCode() {
		return results.get(0).name.split("/")[0];
	}

	public String rate() {
		return results.get(0).rate;
	}

}