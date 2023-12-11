package com.example.demo.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitApplicationRunner implements ApplicationRunner {

    private final NistDownloader nistDownloader;
    private final LocalDbInitializer localDbInitializer;

    public InitApplicationRunner(NistDownloader downloader, LocalDbInitializer initializer) {
        this.nistDownloader = downloader;
        this.localDbInitializer = initializer;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        nistDownloader.downloadAll();
        localDbInitializer.initialize();
    }

}
