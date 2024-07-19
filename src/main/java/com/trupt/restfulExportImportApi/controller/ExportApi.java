package com.trupt.restfulExportImportApi.controller;

import com.trupt.restfulExportImportApi.service.ExportService;
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
public class ExportApi {
    private final ExportService exportService;
    private final LocaleResolver localeResolver;

    @GetMapping("/user")
    public ResponseEntity<InputStreamResource> exportToFile(@RequestParam(name = "lang", required = false) String lang,
                                                            HttpServletRequest request, HttpServletResponse response) {
        Locale locale = localeResolver.resolveLocale(request);

        if (lang != null && !lang.isEmpty()) {
            locale = Locale.of(lang);
        }

        localeResolver.setLocale(request, response, locale);

        ByteArrayInputStream data = exportService.exportToFile(locale);
        InputStreamResource file = new InputStreamResource(data);

        String filename = "UsersList";
        String extension = ".xlsx";
        String langAbbrevation = locale.getLanguage().toUpperCase();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename
                        + "(" + langAbbrevation + ")" + extension)
                .body(new InputStreamResource(file));
    }
}