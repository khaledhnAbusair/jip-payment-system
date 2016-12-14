package com.progressoft.jip.model;

import java.io.PrintStream;

import com.progressoft.jip.model.actions.MenuItem;

public class MenuItemModel {

    private int id;
    protected MenuItem value;

    public MenuItemModel(int id, MenuItem value) {
	this.id = id;
	this.value = value;
    }

    public void print(PrintStream stream) {
	stream.print(id + " - ");
	value.print(stream);
    }

    public void execute() {
	value.execute();
    }

}
