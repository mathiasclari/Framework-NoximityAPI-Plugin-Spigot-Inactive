package com.noximity.paper.gui.events;

import com.noximity.paper.gui.manager.GUIManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUIClickEvent {
    private Player player;
    private Inventory inventory;
    private ItemStack clickedItem;
    private int slot;
    private ClickType clickType;

    public GUIClickEvent(Player player,ItemStack clickedItem, Inventory inventory, int slot, ClickType clickType) {
        this.player = player;
        this.inventory = inventory;
        this.clickedItem = clickedItem;
        this.slot = slot;
        this.clickType = clickType;
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory getInventory() {
        inventory = GUIManager.guiMap.get(player).getInventory();
        return inventory;
    }

    public ItemStack getClickedItem() {
        clickedItem = inventory.getItem(slot);
        clickType = ClickType.valueOf(inventory.getItem(slot).getType().toString());

        return clickedItem;
    }

    public int getSlot() {
        return slot;
    }

    public ClickType getClickType() {
        return clickType;
    }
}
