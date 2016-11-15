package com.progressoft.jip.datastructures;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Currency")
public class Currency {

    public static final Currency JOD = new Currency(1.31);

    private Currency(double base) {
	this.base = base;
    }

    private double base;
    
    public double base(){
	return base;
    }
}
