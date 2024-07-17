package com.trupt.restfulExportImportApi.service;

import com.trupt.restfulExportImportApi.dto.UserViewDTO;
import com.trupt.restfulExportImportApi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.trupt.utils.ExporterUtil;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportServiceImpl implements ExportService {
    private final UserRepository userRepository;
    private final ExporterUtil exporterUtil;

    @Override
    public ByteArrayInputStream exportToFile(List<String> cellHeadersList, boolean calculateAverage, boolean calculateSum) {
        List<?> list = userRepository.findAll().stream().map(UserViewDTO::of).toList();
        return exporterUtil.exportFile(list, cellHeadersList, calculateAverage, calculateSum);
    }
}