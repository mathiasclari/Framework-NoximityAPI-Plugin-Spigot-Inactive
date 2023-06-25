package com.noximity.velocity;

import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;

import javax.inject.Inject;
import java.util.logging.Logger;

@Plugin(id = "framework", name = "FrameworkWaterfall", version = "1.0.0", description = "Framework is fundation plugin that is shaded into all other plugins made by Noximity.", authors = {"Noximity"})
public class FrameworkVelocity {
    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public FrameworkVelocity(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;

        logger.info("FrameworkWaterfall has been enabled! Server: " + server.getVersion().getName());
    }
}
