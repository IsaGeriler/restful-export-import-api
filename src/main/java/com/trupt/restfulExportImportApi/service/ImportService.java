package com.trupt.restfulExportImportApi.service;

import com.trupt.restfulExportImportApi.dto.UserViewDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImportService {
    List<UserViewDTO> importUserFile(MultipartFile file, Class<?> type) throws Exception;
}