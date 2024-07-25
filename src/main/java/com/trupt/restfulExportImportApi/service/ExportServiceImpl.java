package com.trupt.restfulExportImportApi.service;

import com.trupt.restfulExportImportApi.dto.UserViewDTO;
import com.trupt.restfulExportImportApi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import org.trupt.utils.ExporterUtil;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class ExportServiceImpl implements ExportService {
    private final UserRepository userRepository;
    private final ExporterUtil exporterUtil;

    @Override
    public ByteArrayInputStream exportUsersToFile(Locale locale) {
        List<?> list = userRepository.findAll().stream().map(UserViewDTO::of).toList();
        return exporterUtil.exportFile(list, locale);
    }
}