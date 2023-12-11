package com.example.demo.init;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NistDownloader {

    private final String dataPath;
    private final String urlInit;
    private final String urlBase;
    private final String urlTotalResults;

    public NistDownloader(
            @Value("${data.path}") String dataPath,
            @Value("${url.init}") String urlInit,
            @Value("${url.base}") String urlBase,
            @Value("${url.total}") String urlTotalResults) {
        this.dataPath = dataPath;
        this.urlInit = urlInit;
        this.urlBase = urlBase;
        this.urlTotalResults = urlTotalResults;
    }

    public void downloadAll() throws IOException {
        for (int i = 0; i < totalResults(); i += 10000) {
            String urlString = urlInit + i;
            String fileName = i + ".json";
            String response = getDataFromUrl(urlString);
            writeToFile(fileName, response);
        }
    }

    public void downloadUpdates(String fileName) throws IOException {
        OffsetDateTime start = OffsetDateTime.now()
                .minusDays(1)
                .withHour(12)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        OffsetDateTime end = OffsetDateTime.now()
                .withHour(12)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'%2B'hh:mm");
        String urlString = urlBase +
                "?lastModStartDate=" +
                start.format(formatter) +
                "&lastModEndDate=" +
                end.format(formatter);
        String response = getDataFromUrl(urlString);
        writeToFile(fileName, response);
    }

    private String getDataFromUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        connection.disconnect();
        return response.toString();
    }

    private void writeToFile(String fileName, String data) throws IOException {
        try (FileWriter writer = new FileWriter(dataPath + fileName)) {
            writer.write(data);
        }
    }

    private Integer totalResults() throws IOException {
        int totalResults = 0;
        String responseData = getDataFromUrl(urlTotalResults);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseData);
        JsonNode totalResultsNode = rootNode.get("totalResults");
        if (totalResultsNode != null && totalResultsNode.isInt()) {
            totalResults = totalResultsNode.asInt();
        }
        return totalResults;
    }

}
