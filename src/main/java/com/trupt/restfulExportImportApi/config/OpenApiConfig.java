package com.trupt.restfulExportImportApi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.ExternalDocumentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Exporter-Importer REST API w/Swagger-UI")
                        .version("1.0.0")
                        .description("REST API project which exports data from an entity, from database to an .xlsx file " +
                                "(internationalized) and import the data from the given .xlsx file to the database of " +
                                "that same entity.")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Isa Berk Geriler")
                                .email("igeriler@trupt.com"))
                        .license(new License().name("Apache 2.0").url("https://springdoc.org/")))
                .externalDocs(new ExternalDocumentation());
    }
}