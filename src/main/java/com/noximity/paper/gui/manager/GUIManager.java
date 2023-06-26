package com.noximity.paper.gui.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.HashMap;
import java.util.Map;


public class GUIManager {
    public static Map<Player, Gui> guiMap;
    private Plugin plugin;

    public GUIManager(Plugin plugin) {
        this.guiMap = new HashMap<>();
        this.plugin = plugin;

        // Register events
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new GuiClickListener(), plugin);
    }

    public void createGui(Player player, String title, int rows) {
        Inventory inventory = Bukkit.createInventory(null, rows * 9, title);
        Gui gui = new Gui(inventory);
        guiMap.put(player, gui);
    }

    public void addItem(Player player, int slot, ItemStack item) {
        Gui gui = guiMap.get(player);
        if (gui != null) {
            gui.getInventory().setItem(slot, item);
        }
    }

    public void addSelection(Player player, int startSlot, int width, int height) {
        Gui gui = guiMap.get(player);
        if (gui != null) {
            gui.setStartSlot(startSlot);
            gui.setWidth(width);
            gui.setHeight(height);
        }
    }

    public void FillSelection(Player player, ItemStack item) {
        Gui gui = guiMap.get(player);
        if (gui != null) {
            Inventory inventory = gui.getInventory();
            int startSlot = gui.getStartSlot();
            int width = gui.getWidth();
            int height = gui.getHeight();

            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    int slot = startSlot + col + row * 9;
                    if (slot >= 0 && slot < inventory.getSize()) {
                        inventory.setItem(slot, item);
                    }
                }
            }
        }
    }

    public void addItemToSelection(Player player, int slot, ItemStack item) {
        Gui gui = guiMap.get(player);
        if (gui != null) {
            Inventory inventory = gui.getInventory();
            int startSlot = gui.getStartSlot();
            int width = gui.getWidth();
            int height = gui.getHeight();

            int row = (slot - startSlot) / 9;
            int col = (slot - startSlot) % 9;

            if (row >= 0 && row < height && col >= 0 && col < width) {
                int finalSlot = startSlot + col + row * 9;
                inventory.setItem(finalSlot, item);
            }
        }
    }

    public void openGui(Player player) {
        Gui gui = guiMap.get(player);
        if (gui != null) {
            player.openInventory(gui.getInventory());
        }
    }

    private int findSelectionStartSlot(Inventory inventory, ItemStack item) {
        ItemStack[] contents = inventory.getContents();
        int slots = contents.length;

        int width = item.getAmount();
        int height = item.getAmount() / 9;

        for (int i = 0; i < slots; i++) {
            int row = i / 9;
            int col = i % 9;
            int lastSlot = i + (width - 1) + (height - 1) * 9;

            if (col + width > 9 || row + height > inventory.getSize() / 9) {
                continue; // Selection goes out of bounds
            }

            boolean found = true;
            for (int j = i; j <= lastSlot; j++) {
                if (contents[j] != null && contents[j].getType() != Material.AIR) {
                    found = false;
                    break;
                }
            }

            if (found) {
                return i;
            }
        }

        return -1; // Selection not found
    }

    public class Gui {
        private Inventory inventory;
        private int startSlot;
        private int width;
        private int height;

        public Gui(Inventory inventory) {
            this.inventory = inventory;
        }

        public Inventory getInventory() {
            return inventory;
        }

        public int getStartSlot() {
            return startSlot;
        }

        public void setStartSlot(int startSlot) {
            this.startSlot = startSlot;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }

    private class GuiClickListener implements Listener {
        @EventHandler
        public void onInventoryClick(InventoryClickEvent event) {
            Player player = (Player) event.getWhoClicked();
            Gui gui = guiMap.get(player);
            if (gui != null && event.getClickedInventory() == gui.getInventory()) {
                event.setCancelled(true);
            }
        }
    }
}
