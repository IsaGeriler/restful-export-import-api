package com.trupt.restfulExportImportApi.controller;

import com.trupt.restfulExportImportApi.service.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/export")
public class ExportApi {
    private final ExportService exportService;
    List<String> cellHeadersList = List.of("YAS", "BOY", "AGIRLIK");

    @GetMapping("/user")
    public ResponseEntity<InputStreamResource> exportToFile() {
        String filename = "usersTest.xlsx";
        ByteArrayInputStream data = exportService.exportToFile(cellHeadersList, false, false);
        InputStreamResource file = new InputStreamResource(data);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .body(new InputStreamResource(file));
    }

    @GetMapping("/user/sum")
    public ResponseEntity<InputStreamResource> exportToFileWithSum() {
        String filename = "usersTestSum.xlsx";
        ByteArrayInputStream data = exportService.exportToFile(cellHeadersList, false, true);
        InputStreamResource file = new InputStreamResource(data);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .body(new InputStreamResource(file));
    }

    @GetMapping("/user/average")
    public ResponseEntity<InputStreamResource> exportToFileWithAverage() {
        String filename = "usersTestAvg.xlsx";
        ByteArrayInputStream data = exportService.exportToFile(cellHeadersList, true, false);
        InputStreamResource file = new InputStreamResource(data);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .body(new InputStreamResource(file));
    }

    @GetMapping("/user/sum-and-average")
    public ResponseEntity<InputStreamResource> exportToFileWithSumAndAverage() {
        String filename = "usersTestSumAndAvg.xlsx";
        ByteArrayInputStream data = exportService.exportToFile(cellHeadersList, true, true);
        InputStreamResource file = new InputStreamResource(data);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .body(new InputStreamResource(file));
    }
}