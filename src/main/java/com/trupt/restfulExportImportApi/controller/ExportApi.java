package com.trupt.restfulExportImportApi.controller;

import com.trupt.restfulExportImportApi.service.ExportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import java.io.ByteArrayInputStream;
import java.util.Locale;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/export")
@Tag(name = "Export", description = "Operations related for exporting data from a database to an Excel file.")
public class ExportApi {
    private final ExportService exportService;
    private final LocaleResolver localeResolver;

    // Supported I18N language codes of this APIs current iteration
    private static final Set<String> SUPPORTED_LANGUAGES = Set.of("en", "es", "ja", "tr");

    @GetMapping("/user")
    @Operation(
            summary = "Export user data from a database to an Excel file.",
            description = "Returns a downloadable Excel file of the exported user data from a database, " +
                          "with headers being internationalized (I18N) with supported languages, " +
                          "according to user's specified language.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User data exported.", content = @Content(mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")),
                    @ApiResponse(responseCode = "404", description = "Provided language not found.", content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))),
                    @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
            }
    )
    public ResponseEntity<?> exportToFile(@RequestParam(name = "lang", required = false) String lang, HttpServletRequest request, HttpServletResponse response) {
        Locale locale = localeResolver.resolveLocale(request);

        if (lang != null && !lang.isEmpty()) {
            if (!SUPPORTED_LANGUAGES.contains(lang)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Provided language not found.");
            }
            locale = Locale.forLanguageTag(lang);
        }

        localeResolver.setLocale(request, response, locale);
        ByteArrayInputStream data;

        try {
            data = exportService.exportUsersToFile(locale);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating Excel file.");
        }

        InputStreamResource file = new InputStreamResource(data);
        String filename = "UsersList";
        String extension = ".xlsx";
        String langAbbreviation = locale.getLanguage().toUpperCase();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename +
                        "(" + langAbbreviation + ")" + extension)
                .body(file);
    }
}