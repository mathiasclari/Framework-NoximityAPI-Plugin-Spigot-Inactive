package com.noximity.paper;

import org.bukkit.plugin.java.JavaPlugin;

public class FrameworkPaper extends JavaPlugin {

    private FrameworkPaper instance;

    public void onEnable() {
        instance = this;
        System.out.println("");
    }

    public void onDisable() {

    }

    public FrameworkPaper getInstance() {
        return instance;
    }


}
