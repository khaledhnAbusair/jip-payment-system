package com.progressoft.jip.utilities.restful.impl;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("rate")
public class Rate {

	@XStreamAsAttribute
	public String id;

	@XStreamAlias("Name")
	public String name;

	@XStreamAlias("Rate")
	public String rate;

	@XStreamAlias("Date")
	public String date;

	@XStreamAlias("Time")
	public String time;

	@XStreamAlias("Ask")
	public String ask;

	@XStreamAlias("Bid")
	public String bid;

}