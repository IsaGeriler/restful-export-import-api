package com.trupt.restfulExportImportApi.controller;

import com.trupt.restfulExportImportApi.service.ExportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import java.io.ByteArrayInputStream;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/export")
@Tag(name = "Exporter")
public class ExportApi {
    private final ExportService exportService;
    private final LocaleResolver localeResolver;

    @GetMapping("/user")
    @Operation(description = "Export user data from database to internationalized (I18N) .xlsx")
    public ResponseEntity<InputStreamResource> exportToFile(@RequestParam(name = "lang", required = false) String lang,
                                                            HttpServletRequest request, HttpServletResponse response) {
        Locale locale = localeResolver.resolveLocale(request);
        System.out.println("Initial Locale: " + locale.getLanguage());

        if (lang != null && !lang.isEmpty()) {
            locale = Locale.forLanguageTag(lang);
        }

        localeResolver.setLocale(request, response, locale);
        System.out.println("Updated Locale: " + locale.getLanguage());

        ByteArrayInputStream data = exportService.exportToFile(locale);
        InputStreamResource file = new InputStreamResource(data);

        String filename = "UsersList";
        String extension = ".xlsx";
        String langAbbrevation = locale.getLanguage().toUpperCase();

        System.out.println("Language: " + langAbbrevation + "[" + locale.getLanguage() + "]");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename +
                        "(" + langAbbrevation + ")" + extension)
                .body(new InputStreamResource(file));
    }
}