package com.trupt.restfulExportImportApi.service;

import java.io.ByteArrayInputStream;
import java.util.Locale;

public interface ExportService {
    ByteArrayInputStream exportToFile(Locale locale);
}