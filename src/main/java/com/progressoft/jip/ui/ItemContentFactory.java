package com.progressoft.jip.ui;

import com.progressoft.jip.model.actions.ItemAction;

public class ItemContentFactory {
    public static ItemContent instance(String content, ItemAction action) {
	return new ItemContent(content, action);
    }
}
