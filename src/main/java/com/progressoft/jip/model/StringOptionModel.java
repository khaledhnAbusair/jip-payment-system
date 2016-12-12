package com.progressoft.jip.model;

import java.io.PrintStream;

import com.progressoft.jip.model.actions.MenuItem;
import com.progressoft.jip.model.actions.OptionAction;

public class StringOptionModel implements MenuItem {

    private String value;
    private OptionAction action;

    public StringOptionModel(String value, OptionAction action) {
	this.value = value;
	this.action = action;
    }

    public static StringOptionModel instance(String value, OptionAction action) {
	return new StringOptionModel(value, action);
    }

    @Override
    public void print(PrintStream stream) {
	stream.println(value);
    }

    @Override
    public void execute() {
	action.execute();
    }
}
