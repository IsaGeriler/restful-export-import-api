package com.trupt.restfulExportImportApi.service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ExportService {
    ByteArrayInputStream exportToFile(List<String> cellHeadersList, boolean calculateAverage, boolean calculateSum);
}