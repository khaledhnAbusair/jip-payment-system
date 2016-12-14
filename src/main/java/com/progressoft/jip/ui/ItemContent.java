package com.progressoft.jip.ui;

import java.io.PrintStream;

import com.progressoft.jip.model.actions.MenuItem;
import com.progressoft.jip.model.actions.ItemAction;

public class ItemContent implements MenuItem {

    private String value;
    private ItemAction action;

    public ItemContent(String value, ItemAction action) {
	this.value = value;
	this.action = action;
    }

    public static ItemContent instance(String value, ItemAction action) {
	return new ItemContent(value, action);
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
