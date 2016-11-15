package com.progressoft.jip.datastructures;

public class PurposeCodeName {

    public static final PurposeCodeName SALA = new PurposeCodeName("SALA", "SALARY");
    public static final PurposeCodeName BUY = new PurposeCodeName("BUY", "BUY");

    private String code;
    private String name;

    public PurposeCodeName(String code, String name) {
	this.code = code;
	this.name = name;
    }

}
