package com.trupt.restfulExportImportApi.service;

import java.io.ByteArrayInputStream;

public interface ExportService {
    ByteArrayInputStream exportToFile();
}