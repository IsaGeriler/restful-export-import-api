package com.trupt.restfulExportImportApi.service;

import com.trupt.restfulExportImportApi.dto.UserViewDTO;
import com.trupt.restfulExportImportApi.model.User;

import com.trupt.restfulExportImportApi.util.UserProcessor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.trupt.utils.ImporterUtil;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ImportServiceImpl implements ImportService {
    private final UserProcessor userProcessor;
    private final ImporterUtil importerUtil;

    @Override
    public List<UserViewDTO> importUserFile(MultipartFile file, Class<?> type) throws Exception {
        Path tempPath = Files.createTempDirectory("");
        File tempFile = tempPath.resolve(Objects.requireNonNull(file.getOriginalFilename())).toFile();
        file.transferTo(tempFile);

        List<?> importedList = importerUtil.importFile(tempFile, type);
        if (UserViewDTO.class.equals(type)) {
            List<User> userList = userProcessor.convertToUserList(importedList, UserViewDTO.class);
            return userProcessor.saveAndConvertToDTO(userList);
        }
        throw new IllegalArgumentException("Passed object type of the list is not User!");
    }
}