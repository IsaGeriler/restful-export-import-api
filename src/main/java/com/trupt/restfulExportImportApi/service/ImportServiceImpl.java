package com.trupt.restfulExportImportApi.service;

import com.trupt.restfulExportImportApi.dto.UserViewDTO;
import com.trupt.restfulExportImportApi.model.User;
import com.trupt.restfulExportImportApi.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.trupt.utils.ImporterUtil;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImportServiceImpl implements ImportService {
    private final UserRepository userRepository;
    private final ImporterUtil importerUtil;

    @Override
    public List<UserViewDTO> importUserFile(MultipartFile file, Class<?> type) throws Exception {
        Path tempPath = Files.createTempDirectory("");
        File tempFile = tempPath.resolve(Objects.requireNonNull(file.getOriginalFilename())).toFile();
        file.transferTo(tempFile);

        List<?> importedList = importerUtil.importFile(tempFile, type);

        if (UserViewDTO.class.equals(type)) {
            List<UserViewDTO> dtoList = (List<UserViewDTO>) importedList;
            List<User> userList = dtoList.stream().map(dto -> new User(dto.getName(), dto.getSurname(),
                    dto.getHeight(), dto.getWeight(), dto.getBirthdate())).collect(Collectors.toList());
            List<User> savedUsers = userRepository.saveAll(userList);
            return savedUsers.stream().map(UserViewDTO::of).collect(Collectors.toList());
        }
        throw new IllegalArgumentException("Passed object type of the list is not User!");
    }
}