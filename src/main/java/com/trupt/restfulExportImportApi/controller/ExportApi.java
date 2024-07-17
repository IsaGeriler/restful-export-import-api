package com.trupt.restfulExportImportApi.controller;

import com.trupt.restfulExportImportApi.service.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/export")
public class ExportApi {
    private final ExportService exportService;

    @GetMapping("/user")
    public ResponseEntity<InputStreamResource> exportToFile() {
        String filename = "UsersList.xlsx";
        ByteArrayInputStream data = exportService.exportToFile();
        InputStreamResource file = new InputStreamResource(data);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .body(new InputStreamResource(file));
    }
}