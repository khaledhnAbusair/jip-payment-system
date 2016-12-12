package com.progressoft.jip.model;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class MenuModel {

    private String title;
    private List<MenuItemModel> options = new ArrayList<>();

    public MenuModel(String title) {
	this.title = title;
    }

    public void addMenuItem(MenuItemModel option) {
	options.add(option);
    }

    public void print(PrintStream stream) {
	stream.println(title + ":");
	options.stream().forEach(option -> option.print(stream));
    }

    public void execute(int optionId) {
	if (optionId < 1 || optionId >= options.size())
	    return;
	options.get(optionId - 1).execute();
    }

}
