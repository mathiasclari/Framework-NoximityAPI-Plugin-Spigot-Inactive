package com.noximity.paper.language;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.noximity.paper.FrameworkPaper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class LanguageManager {
    private Map<String, String> languageMessages;

    public LanguageManager() {
        languageMessages = new HashMap<>();
    }

    public void loadLanguage(String filePath) {
        languageMessages = new HashMap<>();
        File languageFile = new File(filePath);

        if (languageFile.exists()) {
            try {
                Properties languageProperties = new Properties();
                FileInputStream fileInputStream = new FileInputStream(languageFile);
                languageProperties.load(fileInputStream);
                fileInputStream.close();

                // Retrieve all language messages from the language file
                for (String key : languageProperties.stringPropertyNames()) {
                    String value = languageProperties.getProperty(key);
                    languageMessages.put(key, value);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Failed to load language file: " + filePath);
            }
        } else {
            System.err.println("Language file not found: " + filePath);
        }
    }

    public String getMessage(String key) {
        return languageMessages.get(key);
    }

    public void downloadLanguageFiles(String repositoryBaseUrl, String targetFolderPath) {
        File targetFolder = new File(targetFolderPath);

        // Create the target folder if it doesn't exist
        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }

        try {
            // Open a connection to the repository URL
            URL url = new URL(repositoryBaseUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            // Get the response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response content
                String responseBody = IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);

                // Parse the JSON response
                Gson gson = new Gson();
                JsonArray filesArray = gson.fromJson(responseBody, JsonArray.class);

                // Loop through the files array and download each file
                for (JsonElement fileElement : filesArray) {
                    JsonObject fileObject = fileElement.getAsJsonObject();
                    String fileName = fileObject.get("name").getAsString();
                    String fileDownloadUrl = fileObject.get("download_url").getAsString();

                    // Create the target file object
                    File targetFile = new File(targetFolder, fileName);

                    // Check if the file already exists in the target folder
                    if (!targetFile.exists()) {
                        // Download the language file
                        try (InputStream inputStream = new URL(fileDownloadUrl).openStream()) {
                            FileUtils.copyInputStreamToFile(inputStream, targetFile);
                        }
                    }
                }
            } else {
                System.err.println("Failed to download language files. Response code: " + responseCode);
            }
        } catch (IOException e) {
            System.err.println("Failed to download language files.");
            e.printStackTrace();
        }
    }
}
