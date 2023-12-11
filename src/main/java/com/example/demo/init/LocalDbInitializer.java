package com.example.demo.init;

import com.example.demo.repository.CpeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class LocalDbInitializer {

    private final NistDownloader nistDownloader;
    private final String dataPath;
    private final JsonParser jsonParser;
    private final CpeRepository cpeRepository;

    public LocalDbInitializer(
            NistDownloader nistDownloader,
            @Value("${data.path}") String dataPath,
            JsonParser jsonParser,
            CpeRepository cpeRepository) {
        this.nistDownloader = nistDownloader;
        this.dataPath = dataPath;
        this.jsonParser = jsonParser;
        this.cpeRepository = cpeRepository;
    }

    public void initialize() {
        File directory = new File(dataPath);
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".json")) {
                        cpeRepository.saveAll(jsonParser.parse(file.getAbsolutePath()));
                    }
                }
            }
        }
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void update() throws IOException {
        String fileName = LocalDateTime.now().toLocalDate() + ".json";
        nistDownloader.downloadUpdates(fileName);
        cpeRepository.saveAll(jsonParser.parse(dataPath + fileName));
    }

}
