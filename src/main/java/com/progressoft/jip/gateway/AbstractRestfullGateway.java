package com.progressoft.jip.gateway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.progressoft.jip.utilities.restful.RestfulDataParser;

public abstract class AbstractRestfullGateway<T> {

	private RestfulDataParser<T> parser;

	public AbstractRestfullGateway(RestfulDataParser<T> parser) {
		this.parser = parser;
	}

	protected T response(String url) {
		try {
			URL urlObject = new URL(url);
			String line, data = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlObject.openStream()));
			while ((line = reader.readLine()) != null)
				data += line;
			return parser.parse(data);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

}
