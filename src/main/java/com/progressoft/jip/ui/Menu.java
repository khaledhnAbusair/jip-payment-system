package com.progressoft.jip.ui;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.progressoft.jip.model.MenuItemModel;
import com.progressoft.jip.model.actions.ItemAction;

public class Menu {

    public enum BackItem {
        ENABLE, DISABLE;
    }

    private String title;
    private List<MenuItemModel> options = new ArrayList<>();
    private BackItem backItem;
    private Menu prev;
    private boolean addedDefaultOptions = false;

    private Menu(String title, BackItem backItem) {
        this.title = title;
        this.backItem = backItem;
    }

    private Menu(String title, Menu prev, BackItem backItem) {
        this.title = title;
        this.prev = prev;
        this.backItem = backItem;
    }

    public static Menu subMenu(String title, Menu menu) {
        return new Menu(title, menu, BackItem.ENABLE);
    }

    public static Menu menu(String title) {
        return new Menu(title, BackItem.DISABLE);
    }

    public Menu addMenuItem(ItemContent content) {
        options.add(new MenuItemModel(options.size() + 1, content));
        return this;
    }

    @SuppressWarnings("resource")
    public void display(PrintStream stream) {
        displayCurrentMenu(stream);
        doAction(new Scanner(System.in).nextInt());
    }

    private void displayCurrentMenu(PrintStream stream) {
        addDefaultItems();
        stream.println(title + ":");
        options.stream().forEach(option -> option.print(stream));
    }

    private void addDefaultItems() {
        if (addedDefaultOptions)
            return;
        if (backItem == BackItem.ENABLE)
            options.add(new MenuItemModel(options.size() + 1, ItemContentFactory.instance("Back", new BackAction(prev))));
        options.add(new MenuItemModel(options.size() + 1, ItemContentFactory.instance("Exit", () -> {
            System.exit(0);
        })));
        addedDefaultOptions = true;
    }

    private void doAction(int optionId) {
        if (optionId < 1 || optionId > options.size())
            return;
        options.get(optionId - 1).execute();
    }

    private class BackAction implements ItemAction {
        private Menu menu;

        public BackAction(Menu menu) {
            this.menu = menu;
        }

        @Override
        public void execute() {
            menu.display(System.out);
        }
    }
}
