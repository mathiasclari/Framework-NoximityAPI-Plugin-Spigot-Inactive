package com.noximity.paper.storage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class SQLite {

    private String databasePath;
    private Connection connection;

    private Logger logger = Logger.getLogger("Minecraft");

    public SQLite(String databasePath) {
        this.databasePath = databasePath;
        this.connection = null;
    }

    public void connect() throws SQLException {
        File databaseFile = new File(databasePath);
        try {
            if (!databaseFile.exists()) {
                File databaseFolder = databaseFile.getParentFile();
                if (!databaseFolder.exists()) {
                    databaseFolder.mkdirs();
                }
                if (!databaseFile.createNewFile()) {
                    throw new IOException("Failed to create database file.");
                }
                initializeDatabase(databaseFile);
            }
            connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connect();
        }
        return connection;
    }

    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            connection = null;
        }
    }

    private void initializeDatabase(File databaseFile) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile.getAbsolutePath())) {
            // Create tables
            logger.info("Database initialized successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
