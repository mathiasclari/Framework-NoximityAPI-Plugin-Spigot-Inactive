package com.noximity.paper.controller;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class VersionControl {

    public static final String REQUIRED_VERSION = "1.20";

    public static boolean isCorrectVersion() {
        String serverVersion = Bukkit.getVersion();
        return serverVersion.contains(REQUIRED_VERSION);
    }

    public static void pluginVersionCheck() {
        String pluginVersion = "1.0.0";

        try {
            URL url = new URL("https://raw.githubusercontent.com/Noximity/Framework/origin/version.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder builder = new StringBuilder();

            while (true) {
                String line = br.readLine();
                if (line == null) break;
                builder.append(line);
            }

            String data = builder.toString();
            int pluginId = 0;
            int latestId = 0;

            String[] var0 = data.split("\\|");
            for (String var1 : var0) {
                String[] versionId = var1.split(":");
                if (versionId[0].strip().equals(pluginVersion)) pluginId = Integer.parseInt(versionId[1].strip());
                if (versionId[0].strip().equals("latest")) latestId = Integer.parseInt(versionId[1].strip());
            }

            int diff = latestId - pluginId;

            if (diff > 0)
                Bukkit.getLogger().warning("You are running a version of the plugin that is " + diff + " versions out of date!\n    Please install the latest version from https://modrinth.com/plugin/genesismc/versions");
            if (diff == 0)
                Bukkit.getConsoleSender().sendMessage(Component.text("You are running the latest version of the plugin!"));
            if (diff < 0)
                Bukkit.getConsoleSender().sendMessage(Component.text("You are running a dev build! Join our discord server at https://discord.gg/Noximity or open an issue on github for any feedback"));

        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getLogger().warning("Failed to connect to version control website!\n    You may be using an outdated version of the plugin!\n    Please update to latest version here:");
        }
    }
}
