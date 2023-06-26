package com.noximity.paper.gui.manager;

import com.noximity.paper.gui.events.GUIClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class GUIItem {
    private ItemStack itemStack;
    private Consumer<GUIClickEvent> clickHandler;

    public GUIItem(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.clickHandler = clickHandler;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Consumer<GUIClickEvent> getClickHandler() {
        return clickHandler;
    }
}
