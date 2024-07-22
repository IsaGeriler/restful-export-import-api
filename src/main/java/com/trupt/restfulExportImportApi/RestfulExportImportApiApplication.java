package com.trupt.restfulExportImportApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.trupt.utils.ExporterUtil;
import org.trupt.utils.ImporterUtil;

@SpringBootApplication
public class RestfulExportImportApiApplication {
    @Bean
    public ExporterUtil exporterUtil() {
        return new ExporterUtil();
    }

    @Bean
    public ImporterUtil importerUtil() {
        return new ImporterUtil();
    }

    public static void main(String[] args) {
        SpringApplication.run(RestfulExportImportApiApplication.class, args);
    }
}